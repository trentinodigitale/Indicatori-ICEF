/** 
 *Created on 03-giu-2004 
 */

package c_elab.pat.ass05.ce;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.anf.PassaValoriANF05;

/** legge dalla domanda il n. componenti
 * @author g_barbieri
 */
public class N_componenti extends ElainNode {

	/** N_componenti constructor */
	public N_componenti() {
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
		//                        1
		sql.append(
			"SELECT Familiari.familiare ");
		sql.append("FROM Familiari ");
		sql.append("WHERE Familiari.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(" AND Familiari.ID_dichiarazione in ");
		sql.append(PassaValoriANF05.getID_dichiarazioni(IDdomanda));

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			return records.getRows();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}
