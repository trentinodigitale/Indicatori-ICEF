package c_elab.pat.icef19;

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




	private Hashtable<String,Double> hashtableMaxImportoSpeseMFIForDich = new Hashtable<String,Double>();
	private Hashtable<String,Double> hashtableSumImportoSpeseUForDich = new Hashtable<String,Double>();
	private Hashtable<String,Double> hashtablePesoComponenteUForDich = new Hashtable<String,Double>();

	private void addImportoSpeseMFI(String idDich, double pesoComponente)
	{
		Double oldImportoSpese = (Double)hashtableMaxImportoSpeseMFIForDich.get(idDich);
		if(oldImportoSpese==null)
		{
			double round = 1.0;
			double aggiusta = 0.01;
			double importoSpeseMFI = (Sys.round(LocalVariables.DMSPMFI - aggiusta, round) * pesoComponente / 100.0);
			hashtableMaxImportoSpeseMFIForDich.put(idDich, new Double(importoSpeseMFI));
		}
	}

	private double getImportoSpeseMFI(double importoCalcolato)
	{
		double maxImportoSpese = 0;

		Enumeration<String> e = hashtableMaxImportoSpeseMFIForDich.keys();
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
	
	private double getImportoSpeseU(double importoSpese,double pesoComponente )
	{
		double tot_importoSpeseSPU=0;
		
			double round = 1.0;
			double aggiusta = 0.01;
			//17 luglio 2015 richiesta modifica di massima deduzione a riga e non a dichiarazione
			double max_deduzione = Sys.round((LocalVariables.DMSPU * pesoComponente / 100.0) - aggiusta, round);
			// se le spese sono maggiori della deduzione massima detrai l'importo max altrimenti le spese
			if(importoSpese>max_deduzione)
			{
				tot_importoSpeseSPU=max_deduzione;
			}else{
				tot_importoSpeseSPU=importoSpese;
			}

		return tot_importoSpeseSPU;
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
						//AGGIUNGERE QUI LA LOGICA DI DEDUZIONE MASSIMA PER RIGA E NON PER COMPONENTE
						double importo = records.getDouble(i, 3);
						double pesoComponente = records.getDouble(i, 2);
						double speseU = Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
						String id_dichiarazione = records.getString(i, 4);
						speseU=getImportoSpeseU(speseU,pesoComponente);
						
						addImportoSpeseU(id_dichiarazione,speseU,pesoComponente);
						totSpeseU = totSpeseU + speseU;
					}
				}
				tot += totSpeseU;

				//tot += getImportoSpeseU();
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