package c_elab.pat.reddgar17;

import java.util.Calendar;

public class Mese extends QRedditoGaranzia {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double meseInizio = 0.0;
		
		try 
		{
			if(servizio==idServizioRedditoGaranzia)
			{
				//se il servizio è il reddito di garanzia (APAPI) prendo sempre il mese dopo di quello della data della prima trasmissione
				//della domanda, quindi se il mese è dicembre prendo gennaio
				Calendar dataPresentazione = datiRedditoGaranzia.getCalendar(1,10);
				int mese = dataPresentazione.get(Calendar.MONTH)+1;
				
				meseInizio = mese;
				meseInizio++;
				if(meseInizio>12)
				{
					meseInizio = 1;
				}
			}
			else if(servizio==idServizioRedditoGaranziaSociale)
			{
				//se il servizio è il reddito di garanzia sociale (Servizi Sociali) prendo l'anno e il mese indicati nella maschera
				int mese = datiRedditoGaranzia.getInteger(1,7);
				meseInizio = mese;
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
		
		return meseInizio;
	}
		
}
