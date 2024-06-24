/**
 *Created on 23-mag-2006
 */

package c_elab.pat.ld08;

/**
 * legge l'importo massimo in base al tipo di autorizzazione
 * 
 * @author g_barbieri
 */
public class E1 extends QVersamenti {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {
		try {
			return records.getDouble(1,1);
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