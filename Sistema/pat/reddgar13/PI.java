
package c_elab.pat.reddgar13;


/**
 * query per il calcolo dei patrimoni immobiliari non di residenza della dichiarazione ICEF attualizzata
 * 
 * @author l_leonardi
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
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean residenza = false;
		return getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
		
	}
}