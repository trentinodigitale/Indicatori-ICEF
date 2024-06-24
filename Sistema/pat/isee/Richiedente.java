package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** ritorna Cognome e nome del richiedente
 * @author g_barbieri
 */
public class Richiedente extends ElainNode {

	/** Richiedente constructor */
	public Richiedente(){
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	/** ritorna il valore String da passare a Clesius
	 * @see it.clesius.apps2core.ElainNode#getString()
	 * @return String
	 */
	public String getString() {
		return (String)(records.getElement(1,1)) + " " + (String)(records.getElement(1,2));
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {
		super.init(dataTransfer);
		//                 1              2
		doQuery(
		"SELECT Domande.cognome, Domande.nome FROM Domande WHERE (((Domande.ID_domanda)="
		+ IDdomanda + "));"
		);
	}
}
