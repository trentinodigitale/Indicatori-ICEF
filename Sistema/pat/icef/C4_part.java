/** 
 *Created on 03-giu-2004 
 */

package c_elab.pat.icef;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;


/** query per il quadro C4 della dichiarazione ICEF
 * @author g_barbieri
 */
public class C4_part extends DefComponentiDich {

	/** C4_part constructor */
	public C4_part() {
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
			//                                       1                                     2                                         3                                4
			sql.append(
			"SELECT R_Relazioni_parentela.peso_reddito, Redditi_partecipazione.reddito_dichiarato, Redditi_partecipazione.utile_fiscale, Redditi_partecipazione.quota ");
			sql.append("FROM Familiari ");
			sql.append(
			"INNER JOIN Redditi_partecipazione ON Familiari.ID_dichiarazione = Redditi_partecipazione.ID_dichiarazione ");
			sql.append(
			"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(
			"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
			String defDichType = DefDichElabConst.C4;
			String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
			if(componenti != null && componenti.length()>0)
			{
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componenti);

				testPrintln(sql.toString());
			}
			//Fine aggiunta eventuale definizione di componenti
		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

			String id_dichiarazione = IDdomanda.substring(1);

			//              		1                2              	3		  4
			sql.append(
				"SELECT     100 AS peso_reddito, reddito_dichiarato, utile_fiscale, quota ");
			sql.append(
				"FROM         Redditi_partecipazione ");
			sql.append(
				"WHERE     (ID_dichiarazione = ");
			sql.append(id_dichiarazione);
			sql.append(
				")");
		}

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
				tot = tot + (
						java.lang.Math.max(
								// reddito dichiarato
								Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round), 
								// utile fiscale * quota
								Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round) * (new Double((String) records.getElement(i, 4)).doubleValue()) / 100.0 
						)
				) * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0;
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
