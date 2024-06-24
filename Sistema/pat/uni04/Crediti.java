/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

/** legge il numero di crediti dichiarati dallo studente (Nuovo ordinamento)
 *
 * @author g_barbieri
 */
public class Crediti extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            //                                      crediti
            return new Double((String)(records.getElement(1,10))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
