/**
 *Created on 10-genn-2006
 */

package c_elab.pat.san06;

/** legge l'importo delle borse di studio percepite dall'OU nell'anno precedente
 *
 * @author a_pichler
 */
public class RES_estero extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(1,34))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
