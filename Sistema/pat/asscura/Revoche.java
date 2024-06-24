package c_elab.pat.asscura;

public class Revoche  extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue()  {
		
		
		try {
			return super.getRevoche();
		} catch (Exception e) {
			e.printStackTrace();
			return 1123406789;
		}
		
	}

}
