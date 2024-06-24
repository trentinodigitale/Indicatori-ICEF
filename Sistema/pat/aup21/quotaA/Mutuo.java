package c_elab.pat.aup21.quotaA;


/** legge i valori del quadro D dove tipo detrazione vale 
 * - IMR (interessi mutuo su residenza)
 * e applica la deduzione massima individuale per canone e mutuo
 * @author l_leonardi
 * 
 */
public class Mutuo extends c_elab.pat.icef21.QCanoneMutuo {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		try {
			boolean usaDeduzioneMassima = true;
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