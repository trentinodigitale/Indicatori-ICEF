package c_elab.pat.asscura;


public class Gg26 extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		try {
			return super.getNumeroGiorniPresenzaMese(26);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
