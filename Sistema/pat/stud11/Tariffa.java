/**
 *Created on 23-mag-2006
 */

package c_elab.pat.stud11;

import it.clesius.clesius.util.Sys;


/** 
 * legge i motivi di non idoneità 
 * 
 * 1 resindenza in TAA
 *
 * @author a_pichler
 */
public class Tariffa extends QTariffa {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
		if (records == null)
			return 0.0;
    
		try {
    	   return new Double((String)(records.getElement(1,8))).doubleValue();
    
    	} catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}