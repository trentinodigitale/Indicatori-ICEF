/**
 *Created on 03-giu-2004
 */

package c_elab.pat.regione;

/** legge dalla domanda se ci sono i genitori lavoratori 
 * 
 * @author g_barbieri
 */
public class Lavoratori extends QDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		try {
			if (records.getBoolean(1,1) && con_figli_minori)
				return 1.0;
			else
				return 0.0;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}