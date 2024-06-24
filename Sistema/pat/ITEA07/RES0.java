/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA07; 

/** 
 *@author a_cavalieri 
 */

public class RES0 extends QImmobiliare { 

	public double getValue() {

		if (records == null)
			return 0.0;
			
		// se param = false ritorna la residenza	
		return getValoreImmobili(true);

	}
}