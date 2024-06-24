package c_elab.pat.interStrao16;

import java.util.Calendar;

public class Mese extends QInterStrao {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double meseInizio = 0.0;
		
		try 
		{
			Calendar dataPresentazione = datiInterStrao.getCalendar(1,1);
			int mese = dataPresentazione.get(Calendar.MONTH)+1;
			
			meseInizio = mese;
			meseInizio++;
			if(meseInizio>12)
			{
				meseInizio = 1;
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
