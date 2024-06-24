package c_elab.pat.icef15;

import java.util.Calendar;

/** legge dalla domanda le detrazione per figli a partire dal terzo
 * 
 * @author g_barbieri
 */
public class Figli_fam_numerose extends QParticolarita {
	

		
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;
		
		if (records.getRows() == 0)
			return 0.0;

		double figli = 0.0;

		try {
			// data riferimento può essere:      TODO: verificare quale va bene !!!!
			// 1) fino al 31-12 anno redditi
			// 2) fino alla data domanda
			
			// 1) fino al 31-12 anno redditi
			Calendar dataRif = Calendar.getInstance();
			int yearRif = records.getInteger(1,5);
			int monthRif = 12;
			int dayRif = 31;
			dataRif.set(yearRif, monthRif -1, dayRif);
			
			// 2) fino alla data domanda
            //try {
    		//	dataRif = records.getCalendar(1, 6);
            //} catch (Exception e) {
            //	System.out.println("ERRORE in " + getClass().getName() + ": "	+ e.toString());
            //}
			
			for (int i = 1; i <= records.getRows(); i++) {
				Calendar dataNascita = Calendar.getInstance();
	            try {
					dataNascita = records.getCalendar(i, 3);
	            } catch (Exception e) {
	            	dataNascita.add(Calendar.YEAR, - 100);
	            	System.out.println("ERRORE in " + getClass().getName() + ": "	+ e.toString());
	            }
	            double pesoComponente = records.getDouble(i, 8);
	            if(pesoComponente>0)
	            {
					if ( records.getBoolean(i, 4) ) {                        // se studente fino a 25 anni
						figli = figli + isFiglio(dataNascita, dataRif, 25);
					} else {                                                 // per i non studenti fino a 18 anni
						figli = figli + isFiglio(dataNascita, dataRif, 18);
					}
	            }
			}
			
			if (figli < 3) {
				return 0.0;
			} else if (figli == 3) {
				return LocalVariables.DF3;
			} else if (figli == 4) {
				return LocalVariables.DF3 + LocalVariables.DF4;
			} else {
				return LocalVariables.DF3 + LocalVariables.DF4 + (figli - 4) * LocalVariables.DF5;
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
	
	private int isFiglio(Calendar dataNascita, Calendar dataRif, int eta) {
		
		Calendar tmpDate = null;
		
		tmpDate = (Calendar)dataRif.clone();
		
		tmpDate.add(Calendar.YEAR, - eta);
			
		if ( dataNascita.after(tmpDate) )	{
			return 1;
		} else {
			return 0;
		}
	}
}