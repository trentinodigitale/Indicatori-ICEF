package c_elab.pat.reddgar10; 

import it.clesius.clesius.util.Sys;

/** 
 *@author l_leonardi 
 */

public class Decisione_ufficio extends QRedditoGaranzia { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double decisioneUfficio = 0.0;
		
		try 
		{
			if(domandaConAttestazioneInsussistenzaNecessitaProgettoSocialeCheSanaInconguita())
			{
				//l'addetto dei Servizi Sociali ha già deciso per una domanda in precedenza che, per la domanda in questione, 
				//va bene l'importo calcolato sui dati reddituali e patrimoniali e ha già generato per la domanda precedente
				//un'attestazione di insussistenza che sana anche l'incongruità.
				//Di conseguenza forzo la scelta dell'utente a NON accettare i l'importo dei consumi e quella dei servizi sociali 
				//al fatto che va bene l'importo calcolato sui dati reddituali e patrimoniali
				decisioneUfficio = 1.0;
			}
			else
			{
				int idTpSceltaIncongrua = datiRedditoGaranzia.getInteger(1, 14);
				if(idTpSceltaIncongrua == 0)
				{
					//l'utente ha scelto di NON accettare i l'importo dei consumi
					int idTpSceltaSocialeIcef = datiRedditoGaranzia.getInteger(1, 15);
					boolean attesaSceltaSocialeIcef = datiRedditoGaranzia.getBoolean(1, 19);
					
					if(!attesaSceltaSocialeIcef && idTpSceltaSocialeIcef == 1)
					{
						//l'addetto dei Servizi Sociali ha deciso che, per la domanda in questione seppur incongrua, 
						//va bene l'importo calcolato sui dati reddituali e patrimoniali e la doomanda non è più in attesa
						//che l'addetto dei Servizi Sociali faccia per la domanda un'attestazione di insussistenza come stabilito
						//nella delibera
						decisioneUfficio = 1.0;
					}
				}
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
		
		return decisioneUfficio;
	}
}