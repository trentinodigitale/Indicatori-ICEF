/** 
 *Created on 03-giu-2004 
 */

package c_elab.pat.edilRE;

import c_elab.pat.edil11.DefComponentiDich;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;


/** query per il quadro E della dichiarazione ICEF
 * @author s_largher
 * TODO: per i depositi bancari (BAN) fare la media della somma delle consistenze trimestrali o semestrali di ogni componente
 * 
 */
public class PM_RE extends DefComponentiDich {

	// Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3)
	double FIM = 5000.0;  

	/** PM constructor */
	public PM_RE() {
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

		if(!IDdomanda.startsWith("*"))
		{
			//modalità normale con domanda
			//		TODO
			//                                         1                                    2                                    3                              4                     5											6											7									8
			sql.append("SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Patrimoni_finanziari.interessi, Familiari.ID_dichiarazione, Patrimoni_finanziari.consistenza_31_03, Patrimoni_finanziari.consistenza_30_06, Patrimoni_finanziari.consistenza_30_09 ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Patrimoni_finanziari ON Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			//sql.append(" AND (Patrimoni_finanziari.ID_tp_investimento NOT LIKE 'BN%') ");
			
			sql.append(" UNION ");
			
			// BN3 e BN6 alla nuova data di riferimento: 01/03/2012 oppure 15/05/2012
			sql.append(" SELECT  'BN' as ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, EDIL_familiari.pm as consistenza, 0 as interessi, EDIL_Familiari.ID_dichiarazione, 0 as consistenza_31_03, 0 as consistenza_30_06, 0 as consistenza_30_09 ");
			sql.append(" FROM  EDIL_familiari INNER JOIN R_Relazioni_parentela ON EDIL_familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela  ");
			sql.append(" WHERE Edil_Familiari.ID_domanda = "+IDdomanda );
			
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
					familiare=(String)records.getElement(i,5);
				}
				else {
					String nextFam=(String)records.getElement(i,5);
					if (!nextFam.equals(familiare))	{
						//togliere franchigia(5000) e sommare se positivo
						fpim = fpim - FIM;
						if (fpim>0)
						{
							tot = tot + fpim;
							//System.out.println(IDdomanda+" PM_RE - "+familiare+" = "+fpim);
						}
						familiare = nextFam;
						isPM_attualizzato = false;
						fpim = 0.0;
					}//if diverso familiare
				}//else familiare vuoto
				
				if ( records.getString(i,1).equals("BN") ) {
					double consistenza = records.getDouble(i, 3);
					if(consistenza>0) {
						fpim = fpim + (Sys.round(consistenza - aggiusta, round) * records.getDouble(i, 2) / 100.0);
					}else {
						fpim = fpim + 0;
					}					
					isPM_attualizzato = true;
				}				
				// se depositi bancari media della somma delle consistenze * peso patrimonio
				if ( !isPM_attualizzato && records.getString(i,1).equals("BN3") ) {
					double consistenza_31_03 = records.getDouble(i, 6);
					double consistenza_30_06 = records.getDouble(i, 7);
					double consistenza_30_09 = records.getDouble(i, 8);
					double consistenza_31_12 = records.getDouble(i, 3);
					
					double numSomme = 4.0;
					double mediaConsistenza = (consistenza_31_03 + consistenza_30_06 + consistenza_30_09 + consistenza_31_12)/numSomme;
					// consistenza
					fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * records.getDouble(i, 2) / 100.0);
					// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				}
				else if ( !isPM_attualizzato && records.getString(i,1).equals("BN6") ) {
					
					double consistenza_30_06 = records.getDouble(i, 7);
					double consistenza_31_12 = records.getDouble(i, 3);
					
					double numSomme = 2.0;
					double mediaConsistenza = (consistenza_30_06 + consistenza_31_12)/numSomme;
					// consistenza
					fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * records.getDouble(i, 2) / 100.0);
					// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				} 
				else if ( records.getString(i,1).equals("TIT")
						|| records.getString(i,1).equals("PNQQ")
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