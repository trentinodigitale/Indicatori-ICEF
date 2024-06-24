package c_elab.pat.aup19.quotaA;


/** legge i valori del quadro E dove residenza è true (<>0)
 * 
 * @author g_barbieri
 */
public class RES extends c_elab.pat.icef19.QImmobiliare {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;
			
		// se residenza = true ritorna la residenza
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean residenza = true;
		double valoreImmobili = getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
		
		//ipotesi 1: fare qui query, andando a leggere dalla AUP_familiari_patrimonio
//		if(servizio==70020) {
//			valoreImmobili = 0d;
//		}
		return valoreImmobili;
	}
}