package c_elab.pat.abSoc21;

/**
 * Ritorna il valore 25.000
 */
public class Franchigia_ast extends QDati {
	
	/**
	 * 
	 */
	public double getValue() {
		try {
			return getFranchigia_ast();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

}
