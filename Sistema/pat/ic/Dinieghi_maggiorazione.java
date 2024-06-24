package c_elab.pat.ic;

public class Dinieghi_maggiorazione  extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue()  {
		
		
		try {
			return super.getDinieghi();
		} catch (Exception e) {
			e.printStackTrace();
			return 1111111.00;
		}
		
	}

}
