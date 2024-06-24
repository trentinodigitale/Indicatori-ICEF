package c_elab.pat.asscura;

public class M_inizio  extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return super.getMeseInizio();
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}