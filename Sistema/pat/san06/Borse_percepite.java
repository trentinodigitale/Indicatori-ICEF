/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san06;

/** legge l'importo delle borse di studio percepite dall'OU nell'anno precedente
 *
 * @author a_pichler
 */
public class Borse_percepite extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,10))).doubleValue();
        } catch(NullPointerException n) {
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
