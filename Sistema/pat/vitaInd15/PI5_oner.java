package c_elab.pat.vitaInd15;

/**
 * legge i valori gli immobili ceduti a titolo oneroso (1)
 * 
 * @author g_barbieri
 */
public class PI5_oner extends QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		// se gratuito = false ritorna gli immobili ceduti a titolo oneroso
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean gratuito = false;
		return getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
		
	}
}