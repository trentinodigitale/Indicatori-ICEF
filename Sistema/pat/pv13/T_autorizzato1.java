/**
 *Created on 23-mag-2006
 */

package c_elab.pat.pv13;

/** legge il n. di mesi/settimane autorizzate
 *
 * @author g_barbieri
 */
public class T_autorizzato1 extends QRichiedente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
	public double getValue() {
		try {
			return ((Double)inputValues.get("T_autorizzato1")).doubleValue();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "
					+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in "
					+ getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}