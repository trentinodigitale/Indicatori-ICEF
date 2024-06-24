package c_elab.pat.asscura;

/**
 * legge i valori gli immobili ceduti a titolo gratuito (2)
 * Attenzione usata anche da pat/ai/pat.ai15.net.html
 * @author g_barbieri
 */
public class PI5_grat2 extends QImmobiliare {
	
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
		double valoreImmobiliCedutiATitologratuito = getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
		
		//non tolgo nessuna franchigia, è un nodo che mi serve solo per mostrare una riga nell'elabora
		//valoreImmobiliCedutiATitologratuito = valoreImmobiliCedutiATitologratuito - FIMC;
		//if(valoreImmobiliCedutiATitologratuito < 0) {
		//	valoreImmobiliCedutiATitologratuito = 0d;
		//}
		
		return valoreImmobiliCedutiATitologratuito;
		
	}
}