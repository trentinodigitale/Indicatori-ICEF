package c_elab.pat.ITEA18; 

/** 
 *@author s_largher 
 */

public class Invalido_75 extends QInvalido_75 { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			if (records.getBoolean(1,1))
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