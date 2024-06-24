/**
 *Created on 29-giu-2007
 */

package c_elab.pat.edil07;

import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** legge il numero dei minori o i maggiorenni fino a 20 anni frequentanti
 * @author g_barbieri
 */
public class Prefinanziamento extends ElainNode {

	private static Log log = LogFactory.getLog( Prefinanziamento.class );
	private Calendar datarif  = Calendar.getInstance();
	
	/** Prefinanziamento constructor */
	public Prefinanziamento() {
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
		//                                    1
		sql.append("SELECT EDIL_dati.prefinanziamento ");
		sql.append("FROM EDIL_dati ");
		sql.append("WHERE EDIL_dati.ID_domanda = ");
		sql.append(IDdomanda);
		
		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	
        try {
            return records.getDouble(1, 1);
        } catch(NullPointerException n) {
        	log.error("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
        	log.error("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
