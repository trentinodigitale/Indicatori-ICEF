package c_elab.pat.muov13;


/** 
 * muov13
 */
public class Km extends QAssegnazioni {

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		if (records == null)
			return 0.0;

		try {
			return java.lang.Math.min(new Double((String) records.getElement(1, 1)).doubleValue(), new Double((String) records.getElement(1, 2)).doubleValue());
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
