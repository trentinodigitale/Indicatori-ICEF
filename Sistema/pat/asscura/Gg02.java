package c_elab.pat.asscura;


public class Gg02 extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return super.getNumeroGiorniPresenzaMese(2);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
