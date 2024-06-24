/**
 *Created on 21-giu-2007
 */

package c_elab.pat.stud08;

import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** corregge l'errore sul calcolo tariffa trasporti a partire da un certo ID 
 * @author g_barbieri
 */
public class Metodo extends ElainNode {

	
	/** Metodo constructor */
	public Metodo() {
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
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	
        if(new Long(IDdomanda).longValue() > 874950) {
        	return 1;
        } else {
        	return 0;
        }
    }
}
