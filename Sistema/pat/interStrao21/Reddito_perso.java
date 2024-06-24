package c_elab.pat.interStrao21;

import java.util.Enumeration;
import java.util.Hashtable;

import it.clesius.clesius.util.Sys;


/** query per il calcolo del reddito perso della dichiarazione ICEF attualizzata
 * @author l_leonardi
 */
public class Reddito_perso extends QDichiarazioneAttualizzata {

	//VERIFICAMI: verificare che sia la stessa ogni volta che cambia la dichiarazione attualizzataICEF
	private int idTpRedditoAttualizzatoQuadroB_2 = 2;
	
	private double DMD = 2500.0;
	private double C_DIP = 0.9;

	private double DMA = 1250.0;
	private double C_IMP = 0.95;

	private Hashtable tableImportiQuadroC1_Dip = new Hashtable();
	private Hashtable tableImportiAltriQuadri = new Hashtable();
	
	private void addImporto(Hashtable<String, Double> tableImporti, double importo, String idDich)
	{
		if ( tableImporti.containsKey(idDich) ) {
			double imp = ((Double)tableImporti.get(idDich)).doubleValue();
			tableImporti.remove(idDich);
			tableImporti.put(idDich,(Double)(imp+importo));
		}else {
			tableImporti.put(idDich,new Double(importo));
		}
	}
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean quadroB_1_1 = records.getBoolean(i, 7);
				boolean quadroB_1_2 = records.getBoolean(i, 20);
				boolean quadroB_1_3 = records.getBoolean(i, 8);
				boolean quadroB_1_4 = records.getBoolean(i, 21);
				boolean quadroB_2 = false;
				int idTpRedditoAttualizzato = records.getInteger(i, 18);
				if(idTpRedditoAttualizzato==idTpRedditoAttualizzatoQuadroB_2)
				{
					quadroB_2 = true;
				}
				boolean quadroB_3_1 = records.getBoolean(i, 11);
				boolean quadroB_3_2 = records.getBoolean(i, 22);
				boolean quadroB_3_3 = records.getBoolean(i, 3);
				boolean quadroB_3_4 = records.getBoolean(i, 23);

				int idDich = records.getInteger(i, 2);
				
				//Scorro tutti i C1Dip
				for (int j = 1; j <= datiC1Dip.getRows(); j++) 
				{
					int idDich2 =  datiC1Dip.getInteger(j, 4);
					if(idDich == idDich2)
					{
						if(quadroB_1_1 || quadroB_1_2 || quadroB_1_3 || quadroB_1_4 || quadroB_2 || quadroB_3_1 || quadroB_3_2 || quadroB_3_4)
						{
							//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C1Dip della dichiarazione ICEF
							//(togliendo individualmente il minimo tra il 10% del reddito e il valore di DMD)
							if ( ((String) datiC1Dip.getElement(j, 1)).equals("DIP") ) 
							{
								double redditoC1Dip = Sys.round(new Double((String) datiC1Dip.getElement(j, 3)).doubleValue() - aggiusta, round) * new Double((String) datiC1Dip.getElement(j, 2)).doubleValue() / 100.0;
								addImporto(tableImportiQuadroC1_Dip,redditoC1Dip,""+idDich);
							}
						}
					}
				}
				
				//Scorro tutti i C2
				for (int j = 1; j <= datiC2.getRows(); j++) 
				{
					int idDich2 =  datiC2.getInteger(j, 7);
					if(idDich == idDich2)
					{
						if(quadroB_1_3 || quadroB_1_4 || quadroB_2 || quadroB_3_3)
						{
							//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C2 della dichiarazione ICEF
							//(togliendo individualmente il minimo tra il 5% del reddito e il valore di DMA)
							double agricolo = (java.lang.Math.max (0,
									// quantità *
									new Double((String) datiC2.getElement(j, 2)).doubleValue() *
									// importo -
									new Double((String) datiC2.getElement(j, 3)).doubleValue() -
									// costo locazione terreni -
									new Double((String) datiC2.getElement(j, 4)).doubleValue() -
									// costo dipendenti
									new Double((String) datiC2.getElement(j, 5)).doubleValue()
							)) * 
							// quota possesso
							(new Double((String) datiC2.getElement(j, 6)).doubleValue()) / 100.0;
							//agricolo * peso reddito
							double redditoC2 = (agricolo * new Double((String) datiC2.getElement(j, 1)).doubleValue() / 100.0);
							addImporto(tableImportiAltriQuadri,redditoC2,""+idDich);
						}
					}
				}
				
				//Scorro tutti i C3
				for (int j = 1; j <= datiC3.getRows(); j++) 
				{
					int idDich2 =  datiC3.getInteger(j, 3);
					if(idDich == idDich2)
					{
						if(quadroB_1_3 || quadroB_1_4 || quadroB_2 || quadroB_3_3)
						{
							//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C3 della dichiarazione ICEF
							//(togliendo individualmente il minimo tra il 5% del reddito e il valore di DMA)
							double redditoC3 = Sys.round(new Double((String) datiC3.getElement(j, 2)).doubleValue() - aggiusta, round) * new Double((String) datiC3.getElement(j, 1)).doubleValue() / 100.0;
							addImporto(tableImportiAltriQuadri,redditoC3,""+idDich);
						}
					}
				}
				
				//Scorro tutti i C4
				for (int j = 1; j <= datiC4.getRows(); j++) 
				{
					int idDich2 =  datiC4.getInteger(j, 6);
					if(idDich == idDich2)
					{
						if(quadroB_3_3)
						{
							String idTpImpresa = (String) datiC4.getElement(j, 5);
							if ( idTpImpresa.equals("SDP") || idTpImpresa.equals("SD2") || idTpImpresa.equals("FAM") || idTpImpresa.equals("FA2") ) 
							{
								//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C4 (solo SDP e FAM) della dichiarazione ICEF
								//(togliendo individualmente il minimo tra il 5% del reddito e il valore di DMA)
								double redditoC4 = (
										java.lang.Math.max(
												// reddito dichiarato
												Sys.round(new Double((String) datiC4.getElement(j, 2)).doubleValue() - aggiusta, round), 
												// utile fiscale * quota
												Sys.round(new Double((String) datiC4.getElement(j, 3)).doubleValue() - aggiusta, round) * (new Double((String) datiC4.getElement(j, 4)).doubleValue()) / 100.0 
										)
								) * new Double((String) datiC4.getElement(j, 1)).doubleValue() / 100.0;
								
								addImporto(tableImportiAltriQuadri,redditoC4,""+idDich);
							}
						}
					}
				}
			}
			
			//Sommo i redditi persi togliendo individualmente il minimo tra il 10% del reddito e il valore di DMD perchè sono
			//redditi da lavoro dipendente (Quardo C1_dip)
			Enumeration importiQuadroC1_Dip = tableImportiQuadroC1_Dip.keys();
			while(importiQuadroC1_Dip.hasMoreElements())  {
				String id_dich = (String)importiQuadroC1_Dip.nextElement();
				double imp = ((Double)tableImportiQuadroC1_Dip.get(id_dich)).doubleValue();
				double min = Math.min( imp * ( 1 - C_DIP) , DMD );
				imp = imp - min;
				tot += imp;
			}
			
			//Sommo i redditi persi togliendo individualmente il minimo tra il 5% del reddito e il valore di DMA perchè sono
			//gli altri tipi di reddito (quadri C2, C3, C4)
			Enumeration importiAltriQuadri = tableImportiAltriQuadri.keys();
			while(importiAltriQuadri.hasMoreElements())  {
				String id_dich = (String)importiAltriQuadri.nextElement();
				double imp = ((Double)tableImportiAltriQuadri.get(id_dich)).doubleValue();
				double min = Math.min( imp * ( 1 - C_IMP) , DMA );
				imp = imp - min;
				tot += imp;
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