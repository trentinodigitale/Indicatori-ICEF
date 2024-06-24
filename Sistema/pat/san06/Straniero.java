/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san06;

/** legge se lo studente è straniero, ovvero se non è cittadino italiano
 *
 * @author g_barbieri
 */
public class Straniero extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
           
            if( new Integer((String)(records.getElement(1,35))).intValue() == 0 && new Integer((String)(records.getElement(1,32))).intValue() == 2 )
            	return 1.0;
            else
            	return 0.0;
   
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
