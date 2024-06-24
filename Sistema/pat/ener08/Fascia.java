/**
 *Created on 21-giu-2007
 */

package c_elab.pat.ener08;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 * @author g_barbieri
 */
public class Fascia extends QEnergia {

	private static Log log = LogFactory.getLog( Fascia.class );
	
	public Fascia() {	}

    public double getValue() {
    	
        try {
        	if ( (records.getString(1, 3)).equals("022205") ) {
        		// comune di Trento
        			if ( records.getInteger(1, 4) == 0 ) {
            			return 1; //fascia E
        			} else {
            			return 2; //fascia F
        			}
        	} else {
        		return records.getDouble(1, 2); 
        	}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
