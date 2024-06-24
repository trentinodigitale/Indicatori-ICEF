package c_elab.pat.autoinv21;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author g_barbieri
 */
public class Eta_ast extends QDati {
	
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		double eta = 0.0;

		try {
			if(records==null)
			{
				return eta;
			}
			if(records.getRows()<1)
			{
				return eta;
			}
			
			
			Calendar dataDomanda = GregorianCalendar.getInstance();
			try {
				dataDomanda = records.getCalendar(1, 1);
	        } catch (Exception e) {
	        	System.out.println("Errore di lettura della data di presentazione per la domanda " + IDdomanda);
	        	e.printStackTrace();
	        	dataDomanda.set(1900, 0, 1, 0, 0);
	        }

	        Calendar dataNascita = GregorianCalendar.getInstance();
			try {
				dataNascita = records.getCalendar(1, 2);
	        } catch (Exception e) {
	        	System.out.println("Errore di lettura della data di nascita assistito per la domanda " + IDdomanda);
	        	e.printStackTrace();
	        	dataNascita.set(1900, 0, 1, 0, 0);
	        }
	        
	        // se date inconsistenti
	        if (dataNascita.after(dataDomanda))
	        	dataNascita = dataDomanda;
	        
	        int diff_anni = dataDomanda.get(Calendar.YEAR) - dataNascita.get(Calendar.YEAR); 
	        dataNascita.add(dataNascita.YEAR, diff_anni);
	        if (dataNascita.before(dataDomanda))
	        	// ha già compiuto gli anni
	        	eta = diff_anni;
	        else
	        	// deve ancora compiere gli anni
	        	eta = diff_anni - 1;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("ERROR Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return eta;
	}
}