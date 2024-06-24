package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** legge dalla domanda il n. componenti
 * @author g_barbieri
 */
public class Entrate extends ElainNode {

	/** Entrate constructor */
	public Entrate() {
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
			"SELECT Dich_icef.reddito_irpef, Dich_icef.reddito_agrario_irap ");
		sql.append("FROM Familiari ");
		sql.append(
			"INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
		sql.append(
			"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double tot = 0.0;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				tot = tot + new Double((String) records.getElement(i, 1)).doubleValue() + new Double((String) records.getElement(i, 2)).doubleValue();
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
