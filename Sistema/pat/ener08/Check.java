/**
 *Created on 21-giu-2007
 */

package c_elab.pat.ener08;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 * @author g_barbieri
 */
public class Check extends QEnergia {

	private static Log log = LogFactory.getLog( Check.class );
	
	public Check() {	}

    public double getValue() {
    	double check = 0.0;
    	try {
	   		//se non residente a TN = 0
    		if (records.getInteger(1, 6)== 0){
    			check = 1.0;
            //se esclusa ufficio =-1	
    		}else if (records.getElement(1, 7)!=null && records.getInteger(1, 7)!= 0){
    			check = 2.0;
    		}
    		
    		return check;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return -1;
		}
    }
}
