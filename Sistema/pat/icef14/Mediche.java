package c_elab.pat.icef14;


/** legge i valori del quadro D dove tipo detrazione vale 
 * - SPM (spese mediche)
 * - SPF (spese funebri)
 * - SPI (spese istruzione)
 * - SPU (spese studenti universitari)
 * e applica la deduzione massima individuale per spese mediche (SPM,SPF,SPI) e gli somma la deduzione massima individuale per spese studenti universitari (SPU)
 * @author g_barbieri
 * 
 */
public class Mediche extends QMediche {
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			
			boolean usaDeduzioneMassima = true;
			return getValue(usaDeduzioneMassima);
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}