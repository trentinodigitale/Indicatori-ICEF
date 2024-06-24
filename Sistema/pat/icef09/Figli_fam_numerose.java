/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef09;

import java.util.Arrays;
import java.util.Calendar;

/** legge dalla domanda le detrazione per figli a partire dal terzo
 * 
 * @author g_barbieri
 */
public class Figli_fam_numerose extends QParticolarita {
	
	double DF3 = 2000.0;  
	double DF4 = 1500.0;  
	double DF5 = 1000.0;  
	
	private int idDomandaSogliaMetodoCorretto = 2165538;
	
	private long[] arrayIDServiziSoloMetodoCorretto = {1050,3020,3021,17001,9020};
	
	private String[] arrayIDDomandeEccezioniSoloMetodoCorrettoCausaSblocco = {"2152367","2163583","2083274",
			"2147944","2081346","2083264","2090686","2091226","2095112","2095279","2101284","2103021",
			"2107937","2109551","2111075","2118086","2121123","2121172","2128492","2132162","2135270",
			"2144342","2147314","2148924","2149866","2150529","2151180","2152279","2155516","2156367",
			"2157335","2160942","2160971","2161426","2161613","2162306","2163404","2163500","2163878",
			"2164696"};
	
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
	            double pesoComponente = new Double((String) records.getElement(i, 8)).doubleValue();
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
				return DF3;
			} else if (figli == 4) {
				return DF3 + DF4;
			} else {
				return DF3 + DF4 + (figli - 4) * DF5;
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
		
		int IDdomandaInt = 0;
		if(!IDdomanda.startsWith("*"))
		{
			//modalità normale con domanda
			IDdomandaInt = Integer.parseInt(IDdomanda);
		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...
			//prendo un id domanda molto alto per fargli prendere sempre il valore col metodo corretto
			IDdomandaInt = 100000000;
		}
		
		boolean usaMetodoCorretto = false;
		
		boolean isServizioMetodoCorretto = false;
		for (int i = 0; i < arrayIDServiziSoloMetodoCorretto.length; i++) {
			long idServizio = arrayIDServiziSoloMetodoCorretto[i];
			if(servizio == idServizio)
			{
				isServizioMetodoCorretto = true;
				break;
			}
		}
		
		if(isServizioMetodoCorretto)
		{
			usaMetodoCorretto = true;
		}
		else
		{
			boolean isDomandaMetodoCorretto = false;
			for (int i = 0; i < arrayIDDomandeEccezioniSoloMetodoCorrettoCausaSblocco.length; i++) {
				String idDomandaEccezione = arrayIDDomandeEccezioniSoloMetodoCorrettoCausaSblocco[i];
				if(IDdomanda.equals(idDomandaEccezione))
				{
					isDomandaMetodoCorretto = true;
					break;
				}
			}
			
			if(isDomandaMetodoCorretto)
			{
				usaMetodoCorretto = true;
			}
			else
			{
				if(IDdomandaInt >= idDomandaSogliaMetodoCorretto)
				{
					usaMetodoCorretto = true;
				}
				else
				{
					usaMetodoCorretto = false;
				}
			}
		}
		
		if(usaMetodoCorretto)
		{
			tmpDate = (Calendar)dataRif.clone();
		}
		else
		{
			tmpDate = dataRif;
		}
		
		tmpDate.add(Calendar.YEAR, - eta);
			
		if ( dataNascita.after(tmpDate) )	{
			return 1;
		} else {
			return 0;
		}
	}
}