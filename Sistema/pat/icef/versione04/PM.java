/** 
 *Created on 03-giu-2004 
 */

package c_elab.pat.icef.versione04;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import c_elab.pat.icef.DefComponentiDich;


/** query per il quadro E della dichiarazione ICEF
 * NB questa versione calcola come satbilito all'inizio, 
 * senza la franchigia di non dichiarabilità di 5.000
 * @author g_barbieri
 */
public class PM extends DefComponentiDich {
	
	//TODO !!!!
	private double tasso_capitalizzazione = 0.02;

	/** PM constructor */
	public PM() {
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
//		TODO
		//                                         1                                    2                                    3                                4
		sql.append(
			"SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Patrimoni_finanziari.interessi ");
		sql.append("FROM Familiari ");
		sql.append(
			"INNER JOIN Patrimoni_finanziari ON Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
		sql.append(
			"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append(
			"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);
		
		//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
		String defDichType = DefDichElabConst.E;
		String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
		if(componenti != null && componenti.length()>0)
		{
			sql.append(" AND Familiari.ID_dichiarazione in ");
			sql.append(componenti);
			
			testPrintln(sql.toString());
		}
		//Fine aggiunta eventuale definizione di componenti		
		
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

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				// se depositi bancari max tra consistenza e interessi capitalizzati * peso patrimonio
				if ( ((String) records.getElement(i, 1)).equals("BAN") ) {
					tot = tot + java.lang.Math.max ( 
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
					tot = tot + Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0;
				}
			}
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
