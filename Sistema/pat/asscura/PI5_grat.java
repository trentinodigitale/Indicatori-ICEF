package c_elab.pat.asscura;

/**
 * legge i valori gli immobili ceduti a titolo gratuito (2)
 * Attenzione usata anche da pat/ai/pat.ai15.net.html
 * @author g_barbieri
 */
public class PI5_grat extends QImmobiliare {

	double FIMC = 20000.0; 
	
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
		
//		// INIZIO COMMENTA PER REVERT AC IMMOBILI CEDUTI //
		double valoreImmobiliCedutiATitologratuito = getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
		
		//tolgo comunque tutta la franchigia di 20.000 dal valore degli immobili ceduti a titolo gratuito // <-- non devo farlo se ci sono immobili diversi da quello di residenza!
		//nuova richiesta: da applicare solo se anno redditi/patrimonio >= 2020
		int annoProduzionePatrimonio = 0;
		if(annoProduzionePatrimoni.getRows() > 0) {
			annoProduzionePatrimonio = new Integer((String)annoProduzionePatrimoni.getElement(1,1)).intValue();
		}
		if(annoProduzionePatrimonio >= 2021) {
			double valoreImmobiliDiversiDaResidenza = getValoreImmobili2( false, false, true );
			
			if(valoreImmobiliDiversiDaResidenza >= 20000d) {
				FIMC = 0d;
			}else {
				FIMC = FIMC - valoreImmobiliDiversiDaResidenza;
			}
			
			valoreImmobiliCedutiATitologratuito = valoreImmobiliCedutiATitologratuito - FIMC;
			if(valoreImmobiliCedutiATitologratuito < 0) {
				valoreImmobiliCedutiATitologratuito = 0d;
			}			
		}

		
		return valoreImmobiliCedutiATitologratuito;
		
//		// FINE COMMENTA PER REVERT AC IMMOBILI CEDUTI //
		
	//	return getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
		
	}
}