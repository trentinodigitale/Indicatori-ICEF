/**
 *Created on 03-giu-2004
 */

package c_elab.pat.edilGC;


/** legge i valori del quadro D dove tipo detrazione vale 
 * - SPM (spese mediche)
 * - SPF (spese funebri)
 * - SPI (spese istruzione)
 * e applica la deduzione massima individuale per spese mediche
 * @author s_largher
 * 
 */
public class Mediche0 extends QMediche0 {
	
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