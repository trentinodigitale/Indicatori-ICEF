/**
 *Created on 23-mag-2006
 */

package c_elab.pat.sport11;

/** 
 * legge i motivi di non idoneità 
 * 
 * 3 tesserato
 *
 * @author a_pichler
 */

public class Check3 extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
    	if (records == null)
			return 0.0;
    	
		try {
			if(records.getInteger(1, 9)!= 0){				
				return 0.0;
			}else{
				return 1.0;
			}        
		} catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 1.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 1.0;
        }
    }
}