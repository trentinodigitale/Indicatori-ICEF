package c_elab.pat.asscura;

/**
 * Ritorna il valore di AC_configurazioni.Franchigia_ast
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
