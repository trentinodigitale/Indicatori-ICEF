/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA07; 

/** 
 *@author a_cavalieri
 * immobili con quota oltre 50% dell'anno precedente 
 */

public class PI0 extends Immobiliare { 

	public double getValue() {

		// se param1 = true ritorna gli immobili con quota familiare oltre 50%
		// se param2 = true ritorna gli immobili dell'anno precedente
		return getValoreImmobili(true, true);
	}
}