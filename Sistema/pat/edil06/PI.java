/**
 *Created on 17-mar-2006
 */

package c_elab.pat.edil06;

/**
 * legge i valori del quadro E 
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
			return tot + getValoreImmobili();
	}
}