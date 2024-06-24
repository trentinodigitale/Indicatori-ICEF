package c_elab.pat.reddgar;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** query per il calcolo del reddito perso della dichiarazione ICEF attualizzata
 * @author l_leonardi
 */
public class Reddito_nuovo extends QDichiarazioneAttualizzata {

	//VERIFICAMI: verificare che sia la stessa ogni volta che cambia la dichiarazione attualizzataICEF
	private int idTpRedditoAttualizzatoQuadroB_2 = 2;
	private double redditoFissoImpresa = 5000.0;
	
	
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
				
				if(quadroB_1_1 || quadroB_1_2 || quadroB_1_3 || quadroB_1_4)
				{
					//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo le mensilità dichiarate prese al 90%
					//perchè si tratta di lavoratore dipendente
					double numMesi = 6.0;
					double percentuale = 0.9;
					double redditoNuovo = (mensilita_antecedente + mensilita_2antecedente);
					redditoNuovo = redditoNuovo * numMesi;
					redditoNuovo = redditoNuovo * percentuale;
	
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