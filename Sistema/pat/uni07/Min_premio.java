/**
 *Created on 4-giu-2007
 */

package c_elab.pat.uni07;

/** legge il merito minimo del corso di laurea dello studente a partire dal
 * quale si ottiene una borsa maggiorata
 *
 * @author a_t_termite
 */
public class Min_premio extends QCorso {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,9))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 998.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 998.0;
        }
    }
}
