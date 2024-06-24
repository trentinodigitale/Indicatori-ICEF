package c_elab.pat.icef13;

/** legge dalla domanda se è nucleo monogenitore 
 * 
 * @author g_barbieri
 */
public class Monogenitore extends QDomanda {

	// Deduzione per nuclei monogenitori fissata a 2.500 € (Art. 13 comma 5)
	// Da pannello Monogenitore alla data di presentazione della domanda
	private double DMG = 2500.0; 
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	
	public double getValue() {
		try {
			return java.lang.Math.abs(isMonogenitore()) * DMG;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (Exception nfe) {
			System.out.println("ERROR Exception in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}