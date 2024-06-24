/**
 *Created on 03-giu-2004
 */

package c_elab.pat.ass07.ce;

/**
 * legge i valori del quadro E dove residenza è false (0)
 * 
 * @author g_barbieri
 */
public class PI extends QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		double tot = 0.0;
		
		if (records == null)
			return tot;
		else
			// se param = false ritorna gli immobili oltre la residenza
			return tot + getValoreImmobili(false);
	}
}