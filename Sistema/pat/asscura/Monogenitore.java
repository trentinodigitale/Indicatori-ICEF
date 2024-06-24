package c_elab.pat.asscura;

/** legge dalla domanda se è nucleo monogenitore 
 * Attenzione usata anche da pat/ai/pat.ai15.net.html
 * @author g_barbieri
 */
public class Monogenitore extends QDomanda {

	//VERIFICAMI: se periodo = 30500 (quello iniziale) calcola monogenitore con regole icef12 altrimenti calcola con regole icef13
	
	// Deduzione per nuclei monogenitori fissata a 2.500 € (Art. 13 comma 5)
	// Da pannello Monogenitore alla data di presentazione della domanda
	private double DMG = 2500.0; 
	
	
	/** 
	 * ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			if (records.getInteger(1, 11) == 30500){
				return java.lang.Math.abs(records.getDouble(1, 1)) * DMG; //calcolo con regole ICEF12
			} else {
				//calcolo con regole ICEF13 - verificare se va bene negli anni successivi
				// attenzione anche per servizio 61000
				return java.lang.Math.abs(isMonogenitore()) * DMG; 
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception nfe) {
			System.out.println("ERROR Exception in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}