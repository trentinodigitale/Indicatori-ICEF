/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA10; 

/** 
 *@author a_cavalieri 
 */

import java.util.Enumeration;
import java.util.Hashtable;

import it.clesius.clesius.util.Sys;
public class Mediche0 extends QDetrazioni0 { 
	
	private int idDomandaSogliaMetodoCorretto = 2247323;
	private long[] arrayIDServiziSoloMetodoCorretto = {13025};
	
	// Deduzione massima individuale per spese mediche, funebri e di istruzione fissata a 2.000 € (Art. 13 comma 3)
	double DMS = 2000.0;
	
	private Hashtable hashtableMaxImportoSpeseForDich = new Hashtable();

	private void addImportoSpese(String idDich, double pesoComponente)
	{
		Double oldImportoSpese = (Double)hashtableMaxImportoSpeseForDich.get(idDich);
		if(oldImportoSpese==null)
		{
			double round = 1.0;
			double aggiusta = 0.01;
			double importoSpese = (Sys.round(DMS - aggiusta, round) * pesoComponente / 100.0);
			hashtableMaxImportoSpeseForDich.put(idDich, new Double(importoSpese));
		}
	}
	
	private double getImportoSpese(double importoCalcolato)
	{
		double maxImportoSpese = 0;
		
		Enumeration e = hashtableMaxImportoSpeseForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			Double importoSpese = (Double)hashtableMaxImportoSpeseForDich.get(key);
			maxImportoSpese+=importoSpese.doubleValue();
		}
		
		double ret = importoCalcolato;
		if(importoCalcolato>maxImportoSpese)
		{
			ret = maxImportoSpese;
		}
		return ret;
	}

	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue(boolean usaDeduzioneMassima) {
		
		if (records == null)
			return 0.0;
		
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
			if(IDdomandaInt>idDomandaSogliaMetodoCorretto)
			{
				usaMetodoCorretto = true;
			}
			else
			{
				usaMetodoCorretto = false;
			}
		}
		
		if(usaMetodoCorretto)
		{
			for (int i = 1; i <= tb_componenti.getRows(); i++) {
				double pesoComponente = new Double((String) tb_componenti.getElement(i, 2)).doubleValue();
				String idDich = ((String)tb_componenti.getElement(i,1));
				addImportoSpese(idDich,pesoComponente);
			}
		}
		
		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				if(usaDeduzioneMassima) {
					if ( ((String) records.getElement(i, 1)).equals("SPM") 
							|| ((String) records.getElement(i, 1)).equals("SPF")
							|| ((String) records.getElement(i, 1)).equals("SPI") ) 
						{
							double importo = new Double((String) records.getElement(i, 3)).doubleValue();
							
							double pesoComponente = new Double((String) records.getElement(i, 2)).doubleValue();
							tot = tot + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
							String idDich = ((String)records.getElement(i,4));
							
							if(!usaMetodoCorretto)
							{
								addImportoSpese(idDich,pesoComponente);
							}
						}
				} else {
					if ( ((String) records.getElement(i, 1)).equals("SPM") ) 
						{
							double importo = new Double((String) records.getElement(i, 3)).doubleValue();
							
							double pesoComponente = new Double((String) records.getElement(i, 2)).doubleValue();
							tot = tot + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
							String idDich = ((String)records.getElement(i,4));
							
							if(!usaMetodoCorretto)
							{
								addImportoSpese(idDich,pesoComponente);
							}
						}
				}
			}
			
			if(usaDeduzioneMassima)
			{
				tot = getImportoSpese(tot);
			}
			
			return tot;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
	
	public double getValue() {

		try {
			boolean usaDeduzioneMassima = true;
			return getValue(usaDeduzioneMassima);
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}