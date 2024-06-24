/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef;


/** legge i valori del quadro E dove residenza è true (<>0)
 * 
 * @author g_barbieri
 */
public class RES extends QImmobiliare {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;
			
		// se param = false ritorna la residenza	
		return getValoreImmobili(true);

	}
}