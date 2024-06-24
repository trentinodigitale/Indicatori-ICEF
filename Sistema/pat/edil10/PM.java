/** 
 *Created on 03-giu-2004 
 */

package c_elab.pat.edil10;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;


/** query per il quadro E della dichiarazione ICEF
 * @author g_barbieri
 * 
 * 
 * TODO: per i depositi bancari (BAN) fare la media della somma delle consistenze trimestrali o semestrali di ogni componente
 * 
 * 
 */
public class PM extends ElainNode {

	// Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3)
	double FIM = 5000.0;  

	/** PM constructor */
	public PM() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	private int anno_patrimonio;
	
	public void setAnno(String ID_domanda) {
		anno_patrimonio = 0;
		
		doQuery("select count(ID_dichiarazione) AS tot FROM Edil_familiari where id_domanda = "+ID_domanda);
		if(records!=null && records.getRows()>0) {
			if( records.getInteger(1,1)>0 )	{
				anno_patrimonio = 2007;
			}else {
				anno_patrimonio = 2008;
			}
		}
		return;
	}

	
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		
		setAnno(IDdomanda);
		
		StringBuffer sql = new StringBuffer();
		
		String table = "";
		if(anno_patrimonio==2007) {
			table = "EDIL_Familiari";
		}else if(anno_patrimonio==2008){
			table = "Familiari";
		}

		//                                         1                                    2                                    3                              4                     5											6											7									8
		sql.append(
		"SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Patrimoni_finanziari.interessi, "+table+".ID_dichiarazione, Patrimoni_finanziari.consistenza_31_03, Patrimoni_finanziari.consistenza_30_06, Patrimoni_finanziari.consistenza_30_09 ");
		sql.append("FROM  "+table);
		sql.append(" INNER JOIN Patrimoni_finanziari ON "+table+".ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
		sql.append(" INNER JOIN Domande ON "+table+".ID_domanda = Domande.ID_domanda ");
		sql.append(" INNER JOIN R_Relazioni_parentela ON "+table+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append(" WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);

		sql.append(" ORDER BY "+table+".ID_dichiarazione");
		doQuery(sql.toString());

	}
	
	public double getValue() {
		if(anno_patrimonio==2007) {
			return getValue_icef();
		}else if(anno_patrimonio==2008){
			return getValue_icef09();
		}else {
			return 0.0;
		}
	}
	
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue_icef09() 
	{

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		double fpim = 0.0;

		try 
		{
			String familiare="";
			for (int i = 1; i <= records.getRows(); i++) 
			{
				if (familiare.equals(""))
				{
					familiare=(String)records.getElement(i,5);
				}
				else 
				{
					String nextFam=(String)records.getElement(i,5);
					if (!nextFam.equals(familiare))
					{
						//togliere franchigia(5000) e sommare se positivo
						fpim = fpim - FIM;
						if (fpim>0)
						{
							tot = tot + fpim;
						}
						familiare = nextFam;
						fpim = 0.0;
					}//if diverso familiare
				}//else familiare vuoto
				// se depositi bancari media della somma delle consistenze * peso patrimonio
				if ( ((String) records.getElement(i, 1)).equals("BN3") )
				{
					double consistenza_31_03 = new Double((String) records.getElement(i, 6)).doubleValue();
					double consistenza_30_06 = new Double((String) records.getElement(i, 7)).doubleValue();
					double consistenza_30_09 = new Double((String) records.getElement(i, 8)).doubleValue();
					double consistenza_31_12 = new Double((String) records.getElement(i, 3)).doubleValue();
					
					double numSomme = 4.0;
					double mediaConsistenza = (consistenza_31_03 + consistenza_30_06 + consistenza_30_09 + consistenza_31_12)/numSomme;
					// consistenza
					fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0);
					// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				}
				else if ( ((String) records.getElement(i, 1)).equals("BN6") )
				{
					
					double consistenza_30_06 = new Double((String) records.getElement(i, 7)).doubleValue();
					double consistenza_31_12 = new Double((String) records.getElement(i, 3)).doubleValue();
					
					double numSomme = 2.0;
					double mediaConsistenza = (consistenza_30_06 + consistenza_31_12)/numSomme;
					// consistenza
					fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0);
					// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				} 
				else if ( ((String) records.getElement(i, 1)).equals("TIT")
						|| ((String) records.getElement(i, 1)).equals("PNQQ")
						|| ((String) records.getElement(i, 1)).equals("ALT") ) 
				{
					fpim = fpim + Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0;
				}
			}//for
			//ultimo familiare se presente: togliere franchigia(5000) e sommare se positivo
			fpim = fpim - FIM;
			if (fpim>0)
			{
				tot = tot + fpim;
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


	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	private double tasso_capitalizzazione = 0.02;
	public double getValue_icef() {
		
		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
                double fim = 0.0;

		try {
                    String familiare="";
			for (int i = 1; i <= records.getRows(); i++) {
                            if (familiare.equals(""))familiare=(String)records.getElement(i,5);
                            else {
                                String nextFam=(String)records.getElement(i,5);
                                if (!nextFam.equals(familiare)){
                                    //togliere franchigia(5000) e sommare se positivo
                                    fim = fim - 5000;
                                    if (fim>0) tot = tot + fim;
                                    familiare = nextFam;
                                    fim = 0.0;
                                }//if diverso familiare
                            }//else familiare vuoto
				// se depositi bancari max tra consistenza e interessi capitalizzati * peso patrimonio
				if ( ((String) records.getElement(i, 1)).equals("BAN") ) {
					fim = fim + java.lang.Math.max ( 
						// consistenza
						Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round),
						// interessi capitalizzati
						(new Double((String) records.getElement(i, 4)).doubleValue()) / tasso_capitalizzazione
					// peso patrimonio
					) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0;
				// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				// NB: PQS (partecipaz. con quota > 10%) e IMP (impr. individuali) valgono solo per ISEE! 
				} else if ( ((String) records.getElement(i, 1)).equals("TIT")
				|| ((String) records.getElement(i, 1)).equals("FIV")
				|| ((String) records.getElement(i, 1)).equals("PQI")
				|| ((String) records.getElement(i, 1)).equals("GES")
				|| ((String) records.getElement(i, 1)).equals("ALT") ) {
					fim = fim + Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0;
				}
			}//for
                        //ultimo familiare se presente: togliere franchigia(5000) e sommare se positivo
                        fim = fim - 5000;
                        if (fim>0) tot = tot + fim;
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}

}