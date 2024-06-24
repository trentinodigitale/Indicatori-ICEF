package c_elab.pat.ic;

public class Revoche_pensione  extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue()  {
		
		
		try {
			return super.getRevoche();
		} catch (Exception e) {
			e.printStackTrace();
			return 111111111111.00;
		}
		
	}

}
