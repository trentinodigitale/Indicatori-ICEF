package c_elab.pat.reddgar12;

import c_elab.pat.icef12.QCanoneMutuo;


/** legge i valori del quadro D dove tipo detrazione vale 
 * - IMR (interessi mutuo su residenza)
 * e applica la deduzione massima individuale per canone e mutuo
 * @author l_leonardi
 * 
 */
public class Mutuo extends QCanoneMutuo {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		try {
			boolean usaDeduzioneMassima = false;
			return getValue("IMR",usaDeduzioneMassima);
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}