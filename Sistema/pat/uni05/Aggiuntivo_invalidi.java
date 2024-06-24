/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

/** legge per quanti anni oltre la durata regolare del corso di laurea dello studente
 * viene concessa la borsa di studio per gli studenti invalidi
 *
 * @author g_barbieri
 */
public class Aggiuntivo_invalidi extends QCorso {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,4))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
