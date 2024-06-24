package c_elab.pat.du12.acq;

import it.clesius.clesius.util.Sys;

/**
 * @author l_leonardi
 */
public class Imposte_perse extends QContrPotAcqDatiAttualizzati {


	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
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

				if(quadroB_1_1 || quadroB_1_2 || quadroB_3_1 || quadroB_3_2 || quadroB_3_3 || quadroB_3_4)
				{
					//se selezionato uno di questi quadri della domanda sommo le imposte della dichiarazione ICEF

					for (int j = 1; j <= imposte.getRows(); j++) 
					{
						int idDich2 =  imposte.getInteger(j, 4);
						if(idDich == idDich2)
						{
							if ( ((String) imposte.getElement(j, 1)).equals("IMP") ) 
							{
								tot = tot + Sys.round(new Double((String) imposte.getElement(j, 3)).doubleValue() - aggiusta, round) * new Double((String) imposte.getElement(j, 2)).doubleValue() / 100.0;
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