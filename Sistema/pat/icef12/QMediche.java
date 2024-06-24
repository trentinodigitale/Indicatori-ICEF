package c_elab.pat.icef12;

import it.clesius.clesius.util.Sys;

import java.util.Enumeration;
import java.util.Hashtable;

/** legge i valori del quadro D dove tipo detrazione vale 
 * - SPM (spese mediche)
 * - SPF (spese funebri)
 * - SPI (spese istruzione)
 * - SPU (spese studenti universitari)
 * e applica la deduzione massima individuale per spese mediche (SPM,SPF,SPI) e gli somma la deduzione massima individuale per spese studenti universitari (SPU)
 * @author g_barbieri
 * 
 */
public class QMediche extends QDetrazioni {


	// Deduzione massima individuale per spese mediche, funebri e di istruzione fissata a 2.000 € (Art. 13 comma 3)
	double DMSPMFI = 2000.0;
	// Deduzione massima individuale per spese studenti universitari fissata a 2.000 €
	double DMSPU = 2000.0;

	private Hashtable hashtableMaxImportoSpeseMFIForDich = new Hashtable();
	private Hashtable hashtableSumImportoSpeseUForDich = new Hashtable();
	private Hashtable hashtablePesoComponenteUForDich = new Hashtable();

	private void addImportoSpeseMFI(String idDich, double pesoComponente)
	{
		Double oldImportoSpese = (Double)hashtableMaxImportoSpeseMFIForDich.get(idDich);
		if(oldImportoSpese==null)
		{
			double round = 1.0;
			double aggiusta = 0.01;
			double importoSpeseMFI = (Sys.round(DMSPMFI - aggiusta, round) * pesoComponente / 100.0);
			hashtableMaxImportoSpeseMFIForDich.put(idDich, new Double(importoSpeseMFI));
		}
	}

	private double getImportoSpeseMFI(double importoCalcolato)
	{
		double maxImportoSpese = 0;

		Enumeration e = hashtableMaxImportoSpeseMFIForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			Double importoSpese = (Double)hashtableMaxImportoSpeseMFIForDich.get(key);
			maxImportoSpese+=importoSpese.doubleValue();
		}

		double ret = importoCalcolato;
		if(importoCalcolato>maxImportoSpese)
		{
			ret = maxImportoSpese;
		}
		return ret;
	}
	
	private void addImportoSpeseU(String idDich, double importoCalcolato, double pesoComponente)
	{
		Double oldImportoSpese = (Double)hashtableSumImportoSpeseUForDich.get(idDich);
		if(oldImportoSpese==null)
		{
			double importoSpeseU = importoCalcolato;
			hashtableSumImportoSpeseUForDich.put(idDich, new Double(importoSpeseU));
		}
		else
		{
			double importoSpeseU = importoCalcolato + oldImportoSpese;
			hashtableSumImportoSpeseUForDich.put(idDich, new Double(importoSpeseU));
		}
		Double oldPesoComponente = (Double)hashtablePesoComponenteUForDich.get(idDich);
		if(oldPesoComponente==null)
		{
			double pesoComp = pesoComponente;
			hashtablePesoComponenteUForDich.put(idDich, new Double(pesoComp));
		}
	}
	
	private double getImportoSpeseU()
	{
		double totImportoSpese = 0;

		Enumeration e = hashtableSumImportoSpeseUForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			double importoSpese = ((Double)hashtableSumImportoSpeseUForDich.get(key)).doubleValue();
			double pesoComponente = ((Double)hashtablePesoComponenteUForDich.get(key)).doubleValue();
			
			double round = 1.0;
			double aggiusta = 0.01;
			double max_deduzione = Sys.round((DMSPU * pesoComponente / 100.0) - aggiusta, round);
			
			if(importoSpese>max_deduzione)
			{
				importoSpese = max_deduzione;
			}
			
			totImportoSpese+=importoSpese;
		}

		return totImportoSpese;
	}


	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue(boolean usaDeduzioneMassima) {

		if (records == null || tb_componenti==null)
			return 0.0;

		try {
			for (int i = 1; i <= tb_componenti.getRows(); i++) {
				double pesoComponente = new Double((String) tb_componenti.getElement(i, 2)).doubleValue();
				String idDich = ((String)tb_componenti.getElement(i,1));
				addImportoSpeseMFI(idDich,pesoComponente);
			}

			double tot = 0.0;
			double round = 1.0;
			double aggiusta = 0.01;

			double totSpeseMFI = 0.0;

			for (int i = 1; i <= records.getRows(); i++) {
				if(usaDeduzioneMassima) 
				{
					if ( ((String) records.getElement(i, 1)).equals("SPM") 
							|| ((String) records.getElement(i, 1)).equals("SPF")
							|| ((String) records.getElement(i, 1)).equals("SPI") ) 
					{
						double importo = records.getDouble(i, 3);

						double pesoComponente = records.getDouble(i, 2);
						totSpeseMFI = totSpeseMFI + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
					}
				} 
				else 
				{
					if ( ((String) records.getElement(i, 1)).equals("SPM") ) 
					{
						double importo = records.getDouble(i, 3);

						double pesoComponente = records.getDouble(i, 2);
						totSpeseMFI = totSpeseMFI + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
					}
				}
			}

			if(usaDeduzioneMassima)
			{
				tot = getImportoSpeseMFI(totSpeseMFI);
			}
			else
			{
				tot = totSpeseMFI;
			}

			if(usaDeduzioneMassima) 
			{
				double totSpeseU = 0.0;
				for (int i = 1; i <= records.getRows(); i++) 
				{
					if ( ((String) records.getElement(i, 1)).equals("SPU") ) 
					{
						double importo = records.getDouble(i, 3);
						double pesoComponente = records.getDouble(i, 2);
						double speseU = Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
						String id_dichiarazione = records.getString(i, 4);
						addImportoSpeseU(id_dichiarazione,speseU,pesoComponente);
						totSpeseU = totSpeseU + speseU;
					}
				}
				tot += getImportoSpeseU();
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