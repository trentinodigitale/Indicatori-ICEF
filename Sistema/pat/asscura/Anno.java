package c_elab.pat.asscura;

/**
 * @author g_barbieri
 */
public class Anno extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		double anno = 1900.0;

		try {
        
	        anno = super.getAnnoRiferimentoDaDataPresentazione(); 
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("ERROR Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		
		return anno;
	}
}
