package c_elab.pat.reddgar12;

import java.util.Calendar;

public class Anno extends QRedditoGaranzia {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double annoInizio = 0.0;
		
		try 
		{
			
			if(servizio==idServizioRedditoGaranzia)
			{
				//se il servizio è il reddito di garanzia (APAPI) prendo sempre il mese dopo di quello della data della prima trasmissione
				//della domanda, quindi se il mese è dicembre aumento l'anno
				Calendar dataPresentazione = datiRedditoGaranzia.getCalendar(1,10);
				int anno = dataPresentazione.get(Calendar.YEAR);
				int mese = dataPresentazione.get(Calendar.MONTH)+1;
				
				annoInizio = anno;
				if(mese==12)
				{
					annoInizio = annoInizio + 1;
				}
			}
			else if(servizio==idServizioRedditoGaranziaSociale)
			{
				//se il servizio è il reddito di garanzia sociale (Servizi Sociali) prendo l'anno e il mese indicati nella maschera
				int anno = datiRedditoGaranzia.getInteger(1,11);
				annoInizio = anno;
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		
		return annoInizio;
	}
		
}
