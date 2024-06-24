package c_elab.pat.asscura;


public class Cessazione extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return super.getCessazione();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
