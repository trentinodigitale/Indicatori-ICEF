package c_elab.pat.icef23;

/**
 * legge i valori del quadro F dove residenza è false (0)
 * 
 * @author g_barbieri
 */
public class PI extends QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		// se residenza = false ritorna gli immobili oltre la residenza
		boolean usaDetrazioneMaxValoreNudaProprieta = true;
		boolean residenza = false;
		return getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
		
	}
}