/**
 *Created on 21-giu-2007
 */

package c_elab.pat.ener08;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 * @author g_barbieri
 */
public class Combustibile extends QEnergia {

	private static Log log = LogFactory.getLog( Combustibile.class );
	
	public Combustibile() {	}

    public double getValue() {
    	
        try {
			return records.getDouble(1, 1);
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
