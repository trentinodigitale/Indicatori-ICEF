package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

/** ritorna il punteggio aggiuntivo se monogenitori
 * @author g_barbieri
 */
public class Monogenitore extends QDichiarazioneISEE {

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		try {
			if (new Double((String) (records.getElement(1, 1))).doubleValue()
				!= 0) {
				return 0.2;
			} else {
				return 0.0;
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}