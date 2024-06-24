/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

/** legge il merito minimo con deroga del corso di laurea dello studente
 *
 * @author g_barbieri
 */
public class Min_deroga extends QMerito {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,1))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 999.0;
        }
    }
}
