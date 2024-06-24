package c_elab.pat.autoinv21;

/**
 * @author s_largher
 */
public class Spesa_ammessa extends QVeicoli {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		double spesa = 0.0;

		try {
			if(records==null) {
				return spesa;
			}
			if(records.getRows()==1) {
				spesa = records.getDouble(1, 1);
				return spesa;
			}
			
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return spesa;
	}
}