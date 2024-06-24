package c_elab.pat.asscura;

public class UltimoProfilo extends QDati {
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return super.getUltimoProfilo();
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
