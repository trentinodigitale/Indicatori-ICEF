/**
 *Created on 4-giu-2007
 */

package c_elab.pat.uni09;

/** legge il merito min del corso di laurea dello studente
 *
 * @author a_t_termite
 */
public class Min extends QCorso {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,8))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 998.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 998.0;
        }
    }
}
