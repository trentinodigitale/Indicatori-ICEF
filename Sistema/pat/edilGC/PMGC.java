package c_elab.pat.edilGC;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PMGC extends QEdilDati {

	private static Log log = LogFactory.getLog( PMGC.class );
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	
        try {
        	double val = 0.0;
        	
        	double pm = records.getDouble(1,1);
        	double prefinanziamento = records.getDouble(1,3);
        	
        	//pm al netto del prefinanziamento
        	val = pm - prefinanziamento;
        	
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