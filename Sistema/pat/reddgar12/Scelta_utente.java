package c_elab.pat.reddgar12; 

import it.clesius.clesius.util.Sys;

/** 
 *@author l_leonardi 
 */

public class Scelta_utente extends QRedditoGaranzia { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double sceltaUtente = 0.0;
		
		try 
		{
			if(domandaConAttestazioneInsussistenzaNecessitaProgettoSocialeCheSanaIncongruita())
			{
				//l'addetto dei Servizi Sociali ha già deciso per una domanda in precedenza che, per la domanda in questione, 
				//va bene l'importo calcolato sui dati reddituali e patrimoniali e ha già generato per la domanda precedente 
				//un'attestazione di insussistenza che sana anche l'incongruità. 
				//Di conseguenza forzo la scelta dell'utente a NON accettare i l'importo dei consumi e quella dei servizi sociali 
				//al fatto che va bene l'importo calcolato sui dati reddituali e patrimoniali
				sceltaUtente = 2.0;
			}
			else
			{
				if(servizio==idServizioRedditoGaranzia)
				{
					//se il servizio è il reddito di garanzia (APAPI)
					//l'utente non può più scegliere se accettare o meno l'importo dei consumi, vale sempre come se l'utente 
					//avesse scelto di accettare l'importo dei consumi
					sceltaUtente = 1.0;
				}
				else if(servizio==idServizioRedditoGaranziaSociale)
				{
					//se il servizio è il reddito di garanzia sociale (Servizi Sociali)
					
					int idTpSceltaIncongrua = datiRedditoGaranzia.getInteger(1, 14);
					if(idTpSceltaIncongrua == -1)
					{
						//significa che l'utente non ha ancora scelto se accettare o meno l'importo dei consumi
						sceltaUtente = 0.0;
					}
					else if(idTpSceltaIncongrua == 1)
					{
						//l'utente ha scelto di accettare l'importo dei consumi
						sceltaUtente = 1.0;
					}
					else if(idTpSceltaIncongrua == 0)
					{
						//l'utente ha scelto di NON accettare i l'importo dei consumi
						sceltaUtente = 2.0;
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
		
		return sceltaUtente;
	}
}