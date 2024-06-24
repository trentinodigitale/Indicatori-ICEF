package c_elab.pat.tnTril17;

/** legge dalla domanda se è nucleo monogenitore 
 * 
 * @author g_barbieri
 */
public class Monogenitore extends QDomanda {


	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	
	public double getValue() {
		try {
			return java.lang.Math.abs(isMonogenitore()) * LocalVariables.DMG;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (Exception nfe) {
			System.out.println("ERROR Exception in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}