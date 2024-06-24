/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef;

/** legge dalla domanda il n. invalidi oltre il 75% 
 * 
 * @author g_barbieri
 */
public class N_invalidi_75 extends QDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		try {
			return new Double((String) records.getElement(1, 4)).doubleValue();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}