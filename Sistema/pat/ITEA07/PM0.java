/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA07; 

/** 
 *@author a_cavalieri 
 */

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

public class PM0 extends ElainNode { 

	private double tasso_capitalizzazione = 0.02;
	
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
//		TODO
		//                                         1                                    2                                    3                                4                     5
		sql.append(
			"SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Patrimoni_finanziari.interessi, EPU_Familiari.ID_dichiarazione ");
		sql.append("FROM EPU_Familiari ");
		sql.append(
			"INNER JOIN Patrimoni_finanziari ON EPU_Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
		sql.append(
			"INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append(
			"INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);
                sql.append(" ORDER BY EPU_Familiari.ID_dichiarazione");
		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
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