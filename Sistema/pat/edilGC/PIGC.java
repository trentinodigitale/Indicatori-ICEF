package c_elab.pat.edilGC;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PIGC extends QEdilDati {

	private static Log log = LogFactory.getLog( PIGC.class );
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	
        try {
        	double val = 0.0;
        	val = records.getDouble(1, 2);
            return val;
        } catch(NullPointerException n) {
        	log.error("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
        	log.error("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
