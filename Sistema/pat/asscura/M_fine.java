package c_elab.pat.asscura;

public class M_fine  extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return super.getMeseFine();
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}