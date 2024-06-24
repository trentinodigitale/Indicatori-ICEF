package c_elab.pat.reddgar;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** query per il calcolo del reddito perso della dichiarazione ICEF attualizzata
 * @author l_leonardi
 */
public class Reddito_perso extends QDichiarazioneAttualizzata {

	//VERIFICAMI: verificare che sia la stessa ogni volta che cambia la dichiarazione attualizzataICEF
	private int idTpRedditoAttualizzatoQuadroB_2 = 2;
	private double percentualeC1Dip = 0.9;
	private double percentualeC2 = 0.95;
	private double percentualeC3 = 0.95;
	private double percentualeC4 = 0.95;
	
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

				int idDich = records.getInteger(i, 2);
				
				//Scorro tutti i C1Dip
				for (int j = 1; j <= datiC1Dip.getRows(); j++) 
				{
					int idDich2 =  datiC1Dip.getInteger(j, 4);
					if(idDich == idDich2)
					{
						if(quadroB_1_1 || quadroB_1_2 || quadroB_1_3 || quadroB_1_4 || quadroB_2 || quadroB_3_1 || quadroB_3_2)
						{
							//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C1Dip della dichiarazione ICEF preso al 90%
							if ( ((String) datiC1Dip.getElement(j, 1)).equals("DIP") ) 
							{
								double redditoC1Dip = Sys.round(new Double((String) datiC1Dip.getElement(j, 3)).doubleValue() - aggiusta, round) * new Double((String) datiC1Dip.getElement(j, 2)).doubleValue() / 100.0;
								redditoC1Dip = redditoC1Dip * percentualeC1Dip;
								tot = tot + redditoC1Dip;  
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
							//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C2 della dichiarazione ICEF preso al 95%
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
							redditoC2 = redditoC2 * percentualeC2;
							tot = tot + redditoC2;
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
							//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C3 della dichiarazione ICEF preso al 95%
							double redditoC3 = Sys.round(new Double((String) datiC3.getElement(j, 2)).doubleValue() - aggiusta, round) * new Double((String) datiC3.getElement(j, 1)).doubleValue() / 100.0;
							redditoC3 = redditoC3 * percentualeC3;
							tot = tot + redditoC3;
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
								//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo il C4 (solo SDP e FAM) della dichiarazione ICEF preso al 95%
								double redditoC4 = (
										java.lang.Math.max(
												// reddito dichiarato
												Sys.round(new Double((String) datiC4.getElement(j, 2)).doubleValue() - aggiusta, round), 
												// utile fiscale * quota
												Sys.round(new Double((String) datiC4.getElement(j, 3)).doubleValue() - aggiusta, round) * (new Double((String) datiC4.getElement(j, 4)).doubleValue()) / 100.0 
										)
								) * new Double((String) datiC4.getElement(j, 1)).doubleValue() / 100.0;
								
								redditoC4 = redditoC4 * percentualeC4;
								tot = tot + redditoC4;
							}
						}
					}
				}
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