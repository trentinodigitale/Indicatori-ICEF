/**
 *Created on 03-giu-2004
 */

package c_elab.pat.regione;

/** legge dalla domanda il n. invalidi tra 66% e 75% 
 * 
 * @author g_barbieri
 */
public class N_invalidi_66 extends QDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		return 0.0;  // gli invalidi hanno sempre punteggio 0,5
					 // per cui sono tutti nella classe N_invalidi_75 
	}
}