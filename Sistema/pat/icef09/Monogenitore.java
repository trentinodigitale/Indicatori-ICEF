/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef09;

/** legge dalla domanda se è nucleo monogenitore 
 * 
 * @author g_barbieri
 */
public class Monogenitore extends QDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		// Deduzione per nuclei monogenitori fissata a 2.500 € (Art. 13 comma 5)
		double DMG = 2500.0; 

		try {
			return java.lang.Math.abs(new Double((String) records.getElement(1, 1)).doubleValue()) * DMG;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}