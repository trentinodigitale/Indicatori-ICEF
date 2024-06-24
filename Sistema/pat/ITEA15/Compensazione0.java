package c_elab.pat.ITEA15; 

/** 
 *@author s_largher 
 */

public class Compensazione0 extends QCompensazione0 { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			if (records.getString(1,1)!=null)
				return records.getDouble(1, 1);
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