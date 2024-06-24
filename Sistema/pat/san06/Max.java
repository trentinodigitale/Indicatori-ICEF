/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san06;

/** legge il merito max del corso di laurea dello studente
 *
 * @author a_pichler
 */
public class Max extends QMerito {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,3))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 999.0;
        }
    }
}