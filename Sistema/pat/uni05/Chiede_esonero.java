/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

/** legge se lo studente ha chiesto l'esonero tasse
 *
 * @author g_barbieri
 */
public class Chiede_esonero extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return java.lang.Math.abs(new Double((String)(records.getElement(1,8))).doubleValue());
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
