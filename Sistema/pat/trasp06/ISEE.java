/**
 *Created on 18-mag-2004
 */

package c_elab.pat.trasp06;

/** legge l'ISEE 2004 - solo per la similazione! 
 *
 * @author g_barbieri
 */
public class ISEE extends QTrasp {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,1))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
