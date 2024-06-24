/**
 *Created on 29-giu-2007
 */

package c_elab.pat.edil08;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** legge l'indennizzo finanziario
 * @author g_barbieri
 */
public class Indennizzo_M extends QEdilDati {

	private static Log log = LogFactory.getLog( Indennizzo_M.class );
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	
        try {
            return records.getDouble(1, 2);
        } catch(NullPointerException n) {
        	log.error("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
        	log.error("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}