/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

/** legge l'importo delle borse di studio percepite dall'OU nell'anno precedente
 *
 * @author g_barbieri
 */
public class Borse_percepite extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,17))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
