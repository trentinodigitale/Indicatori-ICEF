package c_elab.pat.du12.acq;

import it.clesius.clesius.util.Sys;

/**
 * @author l_leonardi
 */
public class Reddito_nuovo extends QContrPotAcqDatiAttualizzati {
	

	//VERIFICAMI: verificare i dati sottostanti 	
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
				int ID_tp_reddito_attualizzato = records.getInteger(i, 2);
				
				boolean quadroB_1_1 = false;
				boolean quadroB_1_2 = false;
				boolean quadroB_3_1 = false;
				boolean quadroB_3_2 = false;
				boolean quadroB_3_3 = false;
				boolean quadroB_3_4 = false;
				
				if(ID_tp_reddito_attualizzato == 1)
				{
					quadroB_1_1 = true;
				}
				else if(ID_tp_reddito_attualizzato == 2)
				{
					quadroB_1_2 = true;
				}
				else if(ID_tp_reddito_attualizzato == 3)
				{
					quadroB_3_1 = true;
				}
				else if(ID_tp_reddito_attualizzato == 4)
				{
					quadroB_3_2 = true;
				}
				else if(ID_tp_reddito_attualizzato == 5)
				{
					quadroB_3_3 = true;
				}
				else if(ID_tp_reddito_attualizzato == 6)
				{
					quadroB_3_4 = true;
				}

				int idDich = records.getInteger(i, 1);
				
				double mensilita_antecedente = 0;
				double mensilita_2antecedente = 0;
				
				if( ((String) records.getElement(i, 3)) != null)
				{
					mensilita_antecedente = new Double((String) records.getElement(i, 3)).doubleValue();
				}
				if( ((String) records.getElement(i, 4)) != null)
				{
					mensilita_2antecedente = new Double((String) records.getElement(i, 4)).doubleValue();
				}
				
				if(quadroB_1_1 || quadroB_1_2 || quadroB_3_4)
				{
					//se selezionato uno di questi quadri della domanda sommo le mensilità dichiarate 
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
					//se selezionato uno di questi quadri della domanda sommo le mensilità dichiarate prese al 100%
					//perchè si tratta di ammortiz. sociale
					double numMesi = 6.0;
					double redditoNuovo = (mensilita_antecedente + mensilita_2antecedente);
					redditoNuovo = redditoNuovo * numMesi;
					
					tot = tot + (Sys.round(redditoNuovo - aggiusta, round));
				}
				else if(quadroB_3_3)
				{
					//se selezionato uno di questi quadri della domanda NON sommo niente
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