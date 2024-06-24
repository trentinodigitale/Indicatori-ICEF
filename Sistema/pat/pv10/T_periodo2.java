/**
*Created on 23-mag-2006
*/

package c_elab.pat.pv10;

/** legge il n. di mesi/settimane versate
 *
 * @author g_barbieri
 */
public class T_periodo2 extends QRichiedente {
	
	//private int anno_servizio = 0;
	//private int trimestre = 0;
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
		try {
			return ((Double)inputValues.get("T_periodo2")).doubleValue();
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