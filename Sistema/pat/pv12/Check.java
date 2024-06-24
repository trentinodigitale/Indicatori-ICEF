/**
 *Created on 23-mag-2006
 */

package c_elab.pat.pv12;

/** legge i motivi di non idoneità per residenza, data presentazione domanda
 *
 * @author g_barbieri
 */
public class Check extends QRichiedente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
	public double getValue() {
		try {
			return ((Double)inputValues.get("Check")).doubleValue();
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