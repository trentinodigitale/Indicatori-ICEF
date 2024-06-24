package c_elab.pat.interStrao18;

import java.text.ParseException;
import java.util.Calendar;

import it.clesius.clesius.util.Sys;

/** query per il calcolo del reddito nuovo della dichiarazione ICEF attualizzata
 * @author l_leonardi
 */
public class Reddito_nuovo extends QDichiarazioneAttualizzata {

	//VERIFICAMI: verificare che sia la stessa ogni volta che cambia la dichiarazione attualizzataICEF
	private int idTpRedditoAttualizzatoQuadroB_2 = 2;
	private double redditoFissoImpresa = 5000.0;
	
	private double DMD = 2500.0;
	private double C_DIP = 0.9;
	
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
				
				double mensilita_antecedente = 0;
				double mensilita_2antecedente = 0;
				
				if( ((String) records.getElement(i, 15)) != null)
				{
					mensilita_antecedente = new Double((String) records.getElement(i, 15)).doubleValue();
				}
				if( ((String) records.getElement(i, 16)) != null)
				{
					mensilita_2antecedente = new Double((String) records.getElement(i, 16)).doubleValue();
				}
				
				if(quadroB_1_1 || quadroB_1_2 || quadroB_1_3 || quadroB_1_4 || quadroB_3_4)
				{
					//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo le mensilità dichiarate 
					//(togliendo individualmente il minimo tra il 10% del reddito e il valore di DMD)
					//perchè si tratta di lavoratore dipendente
					double numMesi = 6.0;
					double redditoNuovo = (mensilita_antecedente + mensilita_2antecedente);
					redditoNuovo = redditoNuovo * numMesi;				
					double min = Math.min( redditoNuovo * ( 1 - C_DIP) , DMD );
					redditoNuovo = redditoNuovo - min;
					tot = tot + (Sys.round(redditoNuovo - aggiusta, round));
				}
				else if(quadroB_3_1 || quadroB_3_2)
				{
					//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo le mensilità dichiarate prese al 100%
					//perchè si tratta di ammortiz. sociale
					double numMesi = 6.0;
					double redditoNuovo = (mensilita_antecedente + mensilita_2antecedente);
					redditoNuovo = redditoNuovo * numMesi;
					
					tot = tot + (Sys.round(redditoNuovo - aggiusta, round));
				}
				else if(quadroB_2)
				{
					//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo 5.000 euri fissi
					//perchè si tratta di lavoratore autonomo
					tot = tot + redditoFissoImpresa;
				}
				else if(quadroB_3_3)
				{
					//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF NON sommo niente
					//perchè si tratta di lavoratore disoccupato non per propria volontà
				}
				
				boolean non_percezione_altri_redditi = records.getBoolean(i, 26);
				if(!non_percezione_altri_redditi)
				{
					//se selezionato questo quadro della dichiarazione attualizzata ICEF sommo le mensilità dichiarate
					//perchè si tratta di percezione di redditi non dichiarati nella dichiarazione attualizzata
					//Li prendo al 100%
					double perc_redd_mensilita_antecedente = 0;
					double perc_redd_mensilita_2antecedente = 0;
					
					if( ((String) records.getElement(i, 27)) != null)
					{
						perc_redd_mensilita_antecedente = new Double((String) records.getElement(i, 27)).doubleValue();
					}
					if( ((String) records.getElement(i, 28)) != null)
					{
						perc_redd_mensilita_2antecedente = new Double((String) records.getElement(i, 28)).doubleValue();
					}
					
					double numMesi = 6.0;
					double redditoNuovo = (perc_redd_mensilita_antecedente + perc_redd_mensilita_2antecedente);
					redditoNuovo = redditoNuovo * numMesi;
					
					tot = tot + (Sys.round(redditoNuovo - aggiusta, round));
				}
				
				if(!non_percezione_altri_redditi)
				{
					Calendar comapre= Calendar.getInstance();
					comapre.set(Calendar.YEAR, 2016);
					comapre.set(Calendar.DAY_OF_MONTH, 1);
					comapre.set(Calendar.MONTH, 6);
					comapre.set(Calendar.HOUR, 1);
					
					Calendar data_presentazione = datiDoc.getCalendar(1, 1);
					//CAMBIATO PER MODIFICA INVALIDI se la domanda è stata presentata prima di questa data allora 
					//valuti nel reddito nuovo anche le entrate dell'invalidità altrimenti no
					if(data_presentazione.before(comapre)){
						//se selezionato questo quadro della dichiarazione attualizzata ICEF sommo le mensilità dichiarate
						//perchè si tratta di percezione di redditi non dichiarati nella dichiarazione attualizzata che fanno riferiemnto ad invalidi
						//Li prendo al 50%
						double perc_redd_mensilita_antecedente_invalidi = 0;
						double perc_redd_mensilita_2antecedente_invalidi = 0;
						
						if( ((String) records.getElement(i, 30)) != null)
						{
							perc_redd_mensilita_antecedente_invalidi = new Double((String) records.getElement(i, 30)).doubleValue();
						}
						if( ((String) records.getElement(i, 31)) != null)
						{
							perc_redd_mensilita_2antecedente_invalidi = new Double((String) records.getElement(i, 31)).doubleValue();
						}
						
						double numMesi = 6.0;
						double percentuale = 0.5;
						double redditoNuovo = (perc_redd_mensilita_antecedente_invalidi + perc_redd_mensilita_2antecedente_invalidi);
						redditoNuovo = redditoNuovo * numMesi * percentuale;
						
						tot = tot + (Sys.round(redditoNuovo - aggiusta, round));
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
		}catch (ParseException pe) {
			System.out.println("ERROR ParseException in " + getClass().getName() + ": " + pe.toString());
			return 0.0;
		}
	}
}