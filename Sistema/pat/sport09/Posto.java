/**
 *Created on 21-giu-2007
 */

package c_elab.pat.sport09;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** 
 * @author a_pichler
 */
public class Posto  extends QStudente {

    public double getValue() {
    	if (records == null)
			return 0.0;

		try {
			return (new Integer((String) records.getElement(1, 6)).intValue());
		}catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
