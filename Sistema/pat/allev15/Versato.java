package c_elab.pat.allev15;

/**
 * 
 * @author a_pichler
 *
 */
public class Versato extends QDati {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		double versamento = 0.0;

		try {
			if(records==null) {
				return versamento;
			}
			else if(records.getRows()==1) {
				versamento = records.getDouble(1, 1);
				return versamento;
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
		return versamento;
	}
}