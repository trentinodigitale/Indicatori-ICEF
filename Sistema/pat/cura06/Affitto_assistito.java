/**
 *Created on 31-mag-2005
 */

package c_elab.pat.cura06;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge l'affitto dell'assistito dalla dichiarazione ICEF
 * @author g_barbieri
 */
public class Affitto_assistito extends ElainNode {
	
	/** Accompagnamento constructor */
	public Affitto_assistito() {
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
		//                               1
		sql.append("SELECT Detrazioni.importo ");
		sql.append("FROM Familiari ");
		sql.append("INNER JOIN Detrazioni ON Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione ");
		sql.append("WHERE (Familiari.ID_relazione_parentela = 65 OR Familiari.ID_relazione_parentela = 74) AND Detrazioni.ID_tp_detrazione='CNL' AND Familiari.ID_domanda = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,1))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
