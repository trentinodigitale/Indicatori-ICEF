/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA09; 

/** 
 *@author a_cavalieri 
 */
import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
public class C4_part0 extends ElainNode { 

	 public void init(RunawayData dataTransfer) {

			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			//                                       1                                     2                                         3                                4
			sql.append(
				"SELECT R_Relazioni_parentela.peso_reddito, Redditi_partecipazione.reddito_dichiarato, Redditi_partecipazione.utile_fiscale, Redditi_partecipazione.quota ");
			sql.append("FROM EPU_Familiari ");
			sql.append(
				"INNER JOIN Redditi_partecipazione ON EPU_Familiari.ID_dichiarazione = Redditi_partecipazione.ID_dichiarazione ");
			sql.append(
				"INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(
				"INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE EPU_Familiari.nuovo_ingresso = 0 AND Domande.ID_domanda = ");
			sql.append(IDdomanda);

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
	public void reset() { 	}
}