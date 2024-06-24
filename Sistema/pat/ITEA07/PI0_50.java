/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA07; 

/** 
 *@author a_cavalieri 
 *immobili con quota fino a 50% dell'anno precedente
 */

public class PI0_50 extends Immobiliare { 

	public double getValue() {

		// se param1 = false ritorna gli immobili con quota familiare fino a 50%
		// se param2 = true ritorna gli immobili dell'anno precedente
		return getValoreImmobili(false, true);
	}
}