
package c_elab.pat.reddgar13;


/** query per il calcolo dei patrimoni immobiliari di residenza della dichiarazione ICEF attualizzata
 * 
 * @author l_leonardi
 */
public class RES extends QImmobiliare {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;
			
		// se residenza = true ritorna la residenza
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean residenza = true;
		return getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);

	}
}