/**
 *Created on 28-mag-2004
 */

package c_elab.pat.ass05.ce;

import it.clesius.clesius.util.Sys;

/** legge i valori del quadro C1 dove tipo reddito vale ANP
 * 
 * @author g_barbieri
 */
public class C1_aut extends QC1 {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				if ( ((String) records.getElement(i, 1)).equals("ANP") ) {
					tot = tot + Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}