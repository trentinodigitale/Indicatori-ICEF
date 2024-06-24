/** 
 *Created on 02-lug-2013 
 */

package c_elab.pat.edil15;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** query per il quadro E della dichiarazione ICEF
 * @author s_largher
 * 
 */
public class PM_edil extends ElainNode {

	// Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3)
	double FIM = 5000.0;  

	/** PM constructor */
	public PM_edil() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		
		if(!IDdomanda.startsWith("*")) {
			
			int id_tp_intervento = 0;
			sql.append("SELECT  ID_tp_intervento ");
			sql.append(" FROM  EDIL_dati ");
			sql.append("WHERE id_domanda = "+IDdomanda);
			
			doQuery(sql.toString());
			if(records!=null && records.getRows()>0) {
				id_tp_intervento = records.getInteger(1, 1);
			}
			
			records = null;
			sql = new StringBuffer();
			
			//modalità normale con domanda
			//                                         1                                    2                                    3                              4
			sql.append("SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Familiari.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Patrimoni_finanziari ON Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			
			//intervento nuovo 
			if( id_tp_intervento == 4 ) {
			
				sql.append(" UNION ");
				
				// BAN alla nuova data di riferimento: 16/05/2013
				// NB il codice dell'investimento attualizzato deve essere il primo nel sort!
				sql.append(" SELECT  'ABAN' as ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, EDIL_familiari.pm as consistenza, EDIL_Familiari.ID_dichiarazione ");
				sql.append(" FROM  EDIL_familiari INNER JOIN R_Relazioni_parentela ON EDIL_familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela  ");
				sql.append(" WHERE Edil_Familiari.ID_domanda = "+IDdomanda );
			
			}
			
			sql.append(" ORDER BY ID_dichiarazione, id_tp_investimento ");
		}
		//System.out.println("sql = "+sql.toString());
		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		double fpim = 0.0;

		try {
			boolean isPM_attualizzato = false;
			String familiare="";
			for (int i = 1; i <= records.getRows(); i++) {
				if (familiare.equals("")) {
					familiare=(String)records.getElement(i,4);
				}
				else {
					String nextFam=(String)records.getElement(i,4);
					if (!nextFam.equals(familiare))	{
						//togliere franchigia(5000) e sommare se positivo
						fpim = fpim - FIM;
						if (fpim>0)
						{
							tot = tot + fpim;
							//System.out.println(IDdomanda+" PM_EDIL - "+familiare+" = "+fpim);
						}
						familiare = nextFam;
						isPM_attualizzato = false;
						fpim = 0.0;
					}//if diverso familiare
				}//else familiare vuoto
				
				if ( records.getString(i,1).equals("ABAN") ) {
					double consistenza = records.getDouble(i, 3);
					if(consistenza>0) {
						fpim = fpim + (Sys.round(consistenza - aggiusta, round) * records.getDouble(i, 2) / 100.0);
					}else {
						fpim = fpim + 0;
					}					
					isPM_attualizzato = true;
				}				
				// se depositi bancari media della somma delle consistenze * peso patrimonio
				if ( !isPM_attualizzato && records.getString(i,1).equals("BAN") ) {
					double consistenza = records.getDouble(i, 3);
					fpim = fpim + (Sys.round(consistenza - aggiusta, round) * records.getDouble(i, 2) / 100.0);
					// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				}
				else if ( records.getString(i,1).equals("TIT")
						|| records.getString(i,1).equals("PNQQ")
						|| records.getString(i,1).equals("CPP")
						|| records.getString(i,1).equals("ALT") ) {
					fpim = fpim + Sys.round(records.getDouble(i, 3) - aggiusta, round) * records.getDouble(i, 2) / 100.0;
				}
			}//for
			//ultimo familiare se presente: togliere franchigia(5000) e sommare se positivo
			fpim = fpim - FIM;
			if (fpim>0)
			{
				tot = tot + fpim;
				//System.out.println(IDdomanda+" PM_RE - "+familiare+" = "+fpim);
			}
			return tot;
		} 
		catch (NullPointerException n) 
		{
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} 
		catch (NumberFormatException nfe) 
		{
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}