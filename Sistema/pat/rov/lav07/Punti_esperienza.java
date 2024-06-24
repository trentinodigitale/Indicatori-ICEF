/** 
 *Created on 21-dic-2006 
 */

package c_elab.pat.rov.lav07;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** legge dalla domanda il n. componenti
 * @author g_barbieri
 */
public class Punti_esperienza extends ElainNode {

	/** Punti_esperienza constructor */
	public Punti_esperienza() {
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
		//                                1
		sql.append(
			"SELECT ROV_assunzioni.giorni_lavorati ");
		sql.append("FROM ROV_assunzioni ");
		sql.append("WHERE ROV_assunzioni.ID_domanda = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			return records.getDouble(1,1);
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}
