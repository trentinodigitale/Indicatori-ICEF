/**
 *Created on 03-giu-2004
 */

package c_elab.pat.edilGC;

import it.clesius.clesius.util.Sys;

import java.util.Enumeration;
import java.util.Hashtable;

/** legge i valori del quadro D dove tipo detrazione vale 
 * - SPM (spese mediche)
 * - SPF (spese funebri)
 * - SPI (spese istruzione)
 * e applica o meno la deduzione massima individuale per spese mediche
 * @author s_largher
 * 
 */
public class QMediche0 extends QDetrazioni0 {
	
	
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
		
		for (int i = 1; i <= tb_componenti.getRows(); i++) {
				double pesoComponente = new Double((String) tb_componenti.getElement(i, 2)).doubleValue();
				String idDich = ((String)tb_componenti.getElement(i,1));
				addImportoSpese(idDich,pesoComponente);
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
							double importo = records.getDouble(i, 3);
							
							double pesoComponente = records.getDouble(i, 2);
							tot = tot + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
						}
				} else {
					if ( ((String) records.getElement(i, 1)).equals("SPM") ) 
						{
							double importo = records.getDouble(i, 3);
							
							double pesoComponente = records.getDouble(i, 2);
							tot = tot + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
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
}