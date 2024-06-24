/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA09_prov; 

/** 
 *@author a_cavalieri 
 */

public class Invalido_75 extends QProvvisorio { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			if (records.getBoolean(1,5))
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