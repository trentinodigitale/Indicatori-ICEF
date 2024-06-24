package c_elab.pat.isee;

/** 
 *Created on 08-giu-2004 
 */

/** legge dalla domanda il canone di locazione
 * @author g_barbieri
 */
public class Affitto extends QDichiarazioneISEE {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		try {
			return new Double((String) records.getElement(1, 5)).doubleValue();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
