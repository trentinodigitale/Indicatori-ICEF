/**
 *Created on 03-giu-2004
 */

package c_elab.pat.edilGC;

/** legge dalla domanda se è nucleo monogenitore 
 * 
 * @author s_largher
 */
public class Monogenitore0 extends QDomanda0 {

	// Deduzione per nuclei monogenitori fissata a 2.500 € (Art. 13 comma 5)
	private double DMG = 2500.0; 
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return java.lang.Math.abs(records.getDouble(1, 1)) * DMG;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}