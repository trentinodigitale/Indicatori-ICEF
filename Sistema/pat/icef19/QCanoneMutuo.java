package c_elab.pat.icef19;

import it.clesius.clesius.util.Sys;

import java.util.Enumeration;
import java.util.Hashtable;

/** legge i valori del quadro D dove tipo detrazione vale 
 * - IMR (interessi mutuo su residenza)
 * - CNL (canone di locazione)
 * e applica la deduzione massima individuale per canone e mutuo
 * @author l_leonardi
 * 
 */
public class QCanoneMutuo extends QDetrazioni {




	private Hashtable<String,Double> hashtableSumImportoSpeseForDich = new Hashtable<String,Double>();
	private Hashtable<String,Double> hashtableMinNumCointestatariForDich = new Hashtable<String,Double>();
	private Hashtable<String,Double> hashtablePesoComponenteForDich = new Hashtable<String,Double>();

	private void addImportoSpese(String idDich, double importoCalcolato, double numCointestatari, double pesoComponente)
	{
		Double oldImportoSpese = (Double)hashtableSumImportoSpeseForDich.get(idDich);
		if(oldImportoSpese==null)
		{
			double importoSpese = importoCalcolato;
			hashtableSumImportoSpeseForDich.put(idDich, new Double(importoSpese));
		}
		else
		{
			double importoSpese = importoCalcolato + oldImportoSpese;
			hashtableSumImportoSpeseForDich.put(idDich, new Double(importoSpese));
		}
		Double oldNumCointestatari = (Double)hashtableMinNumCointestatariForDich.get(idDich);
		if(oldNumCointestatari==null)
		{
			double numCoint = numCointestatari;
			hashtableMinNumCointestatariForDich.put(idDich, new Double(numCoint));
		}
		else
		{
			if(numCointestatari<oldNumCointestatari)
			{
				double numCoint = numCointestatari;
				hashtableMinNumCointestatariForDich.put(idDich, new Double(numCoint));
			}
		}
		Double oldPesoComponente = (Double)hashtablePesoComponenteForDich.get(idDich);
		if(oldPesoComponente==null)
		{
			double pesoComp = pesoComponente;
			hashtablePesoComponenteForDich.put(idDich, new Double(pesoComp));
		}
	}

	private double getImportoSpese()
	{
		double totImportoSpese = 0;

		Enumeration<String> e = hashtableSumImportoSpeseForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			double importoSpese = ((Double)hashtableSumImportoSpeseForDich.get(key)).doubleValue();
			double minNumCointestatari = ((Double)hashtableMinNumCointestatariForDich.get(key)).doubleValue();
			double pesoComponente = ((Double)hashtablePesoComponenteForDich.get(key)).doubleValue();
			
			//correzione per non dividere per 0
			if(minNumCointestatari==0)
			{
				minNumCointestatari = 1;
			}
			
			double round = 1.0;
			double aggiusta = 0.01;
			double max_deduzione = Sys.round(((LocalVariables.LDC * pesoComponente / 100.0) / minNumCointestatari) - aggiusta, round);
			
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
	public double getValue(String idTpDetrazione, boolean usaDeduzioneMassima) {

		if (records == null || tb_componenti==null)
			return 0.0;

		try {
			double tot = 0.0;
			double round = 1.0;
			double aggiusta = 0.01;

			double totSpese = 0.0;

			for (int i = 1; i <= records.getRows(); i++) {
				if(usaDeduzioneMassima) 
				{
					if ( ((String) records.getElement(i, 1)).equals(idTpDetrazione))
					{
						double importo = records.getDouble(i, 3);
						double contributo = records.getDouble(i, 6);
						importo = Math.max(0, importo - contributo);
						double pesoComponente = records.getDouble(i, 2);
						double spese = Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
						String id_dichiarazione = records.getString(i, 4);
						
						double numCointestatari = 1;
						String cointestatari = records.getString(i, 5);
						if(cointestatari!=null && cointestatari.length()>0)
						{
							numCointestatari = records.getDouble(i, 5);
						}
						addImportoSpese(id_dichiarazione,spese,numCointestatari,pesoComponente);
						totSpese = totSpese + spese;
					}
				} 
				else 
				{
					if ( ((String) records.getElement(i, 1)).equals(idTpDetrazione) ) 
					{
						double importo = records.getDouble(i, 3);
						double contributo = records.getDouble(i, 6);
						importo = Math.max(0, importo - contributo);
						double pesoComponente = records.getDouble(i, 2);
						totSpese = totSpese + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
					}
				}
			}

			if(usaDeduzioneMassima)
			{
				tot = getImportoSpese();
			}
			else
			{
				tot = totSpese;
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