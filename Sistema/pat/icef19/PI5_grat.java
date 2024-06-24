package c_elab.pat.icef19;

/**
 * legge i valori gli immobili ceduti a titolo gratuito (2)
 * 
 * @author g_barbieri
 */
public class PI5_grat extends QImmobiliCeduti {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		// se gratuito = true ritorna gli immobili ceduti a titolo gratuito
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean gratuito = true;
		return getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
		
	}
}