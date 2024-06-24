package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

/** somma il n. di componenti invalidi tra 66 e 75 con quelli superiori a 75
 * @author g_barbieri
 */
public class Invalido extends QDichiarazioneISEE {

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		try {
			return (new Double((String) (records.getElement(1, 3))).doubleValue() + new Double((String) (records.getElement(1, 4))).doubleValue()) * 0.5;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}