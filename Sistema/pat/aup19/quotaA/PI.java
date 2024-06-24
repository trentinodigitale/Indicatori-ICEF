package c_elab.pat.aup19.quotaA;

/**
 * legge i valori del quadro F dove residenza è false (0)
 * 
 * @author g_barbieri
 */
public class PI extends c_elab.pat.icef19.QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		// se residenza = false ritorna gli immobili oltre la residenza
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean residenza = false;
		double valoreImmobili = getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
		
		return valoreImmobili;
	}
}