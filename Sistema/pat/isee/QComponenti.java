package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


/** legge i componenti dalla tabella Familiari
 * @author g_barbieri
 */
public abstract class QComponenti extends ElainNode {

	/** QComponenti constructor */
	public QComponenti() {
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabStaticContext.getInstance().resetSession( QComponenti.class.getName(), IDdomanda, "records" );
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QComponenti.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			//                 1
			doQuery(
				"SELECT Familiari.familiare FROM Familiari WHERE Familiari.ID_domanda="
					+ IDdomanda );
			session.setInitialized(true);
			session.setRecords( records );
        } else {
			records = session.getRecords();
        }
	}
}
