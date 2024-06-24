package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import it.clesius.clesius.util.Sys;

/** mutuo abitazione di residenza
 * @author g_barbieri
 */
public class Mutuo extends QImmobili {

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		double total = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		for (int i = 1; i <= records.getRows(); i++) {
			try {
				if (new Double((String) records.getElement(i, 3)).doubleValue() != 0) {
					total += Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round);
				}
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}
		return total;
	}
}
