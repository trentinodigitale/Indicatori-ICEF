package c_elab.pat.muov15;


/** 
 * 
 */
public class Km extends QAssegnazioni {

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() { 
		
		if (records == null || records.getRows()==0)
			return 0.0;

		try {
			return java.lang.Math.min( records.getDouble(1, 1), records.getDouble(1, 2) );
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
