/**
 *Created on 03-giu-2004
 */

package c_elab.pat.regione;

/** legge dalla domanda se è nucleo monogenitore 
 * 
 * @author g_barbieri
 */
public class Monogenitore extends QDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (monogenitore && con_figli_minori) {
			return 1.0;
		} else {
			return 0.0;
		}
	}
}