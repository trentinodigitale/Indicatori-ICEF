package c_elab.pat.aupBC22.quotaA;

import it.clesius.clesius.util.Sys;

/** query per il calcolo del reddito nuovo della dichiarazione ICEF attualizzata
 * @author l_leonardi
 */
public class Reddito_nuovo extends QAttualizza {

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
				boolean quadroB_1_1 = records.getBoolean(i, 3);   //riduzione_orario
				boolean quadroB_3_1 = records.getBoolean(i, 4) || records.getBoolean(i, 5) || records.getBoolean(i, 6);   //cessaz_lav_dip_indeter, cessaz_lav_dip_det, sosp_lav_dip_det
				boolean quadroB_3_4 = records.getBoolean(i, 7);   //cessaz_ammort
				boolean quadroB_3_2 = records.getBoolean(i, 8);   //cessaz_lav_atipico
				boolean quadroB_3_3 = records.getBoolean(i, 9);   //cessaz_lav_aut
				boolean quadroB_1_2 = records.getBoolean(i, 7);   //??????????????????????????????????

				double mensilita_antecedente = 0;
				double mensilita_2antecedente = 0;
				
				if( ((String) records.getElement(i, 10)) != null)
				{
					mensilita_antecedente = new Double((String) records.getElement(i, 10)).doubleValue();
				}
				if( ((String) records.getElement(i, 11)) != null)
				{
					mensilita_2antecedente = new Double((String) records.getElement(i, 11)).doubleValue();
				}
				
				if(quadroB_1_1 || quadroB_1_2 || quadroB_3_4)
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
				else if(quadroB_3_3)
				{
					//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF NON sommo niente
					//perchè si tratta di lavoratore disoccupato non per propria volontà  RIV.......
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