/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san06;

/** legge per quanti anni oltre la durata regolare del corso di laurea dello studente
 * viene concessa la borsa e l'esonero tasse
 *
 * @author a_pichler
 */
public class Aggiuntivo extends QCorso {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,2))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
