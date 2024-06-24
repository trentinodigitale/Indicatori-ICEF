package c_elab.pat.aup18.quotaA;


/** legge i valori del quadro E dove residenza è true (<>0)
 * 
 * @author g_barbieri
 */
public class RES extends c_elab.pat.icef18.QImmobiliare {

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