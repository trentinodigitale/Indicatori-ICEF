package c_elab.pat.vitaInd18;


public class Check extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return super.getCheck();
		} catch (Exception e) {
			e.printStackTrace();
			return 1123406789;
		}
		
	}
}
