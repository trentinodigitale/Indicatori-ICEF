/**
 *Created on 23-mag-2006
 */

package c_elab.pat.sport09;

/** 
 * legge i motivi di non idoneità 
 * 
 * 2 merito scolastico
 *
 * @author a_pichler
 */

public class Check2 extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
    	
		try {
			if(records.getInteger(1, 8)!= 0){
				
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