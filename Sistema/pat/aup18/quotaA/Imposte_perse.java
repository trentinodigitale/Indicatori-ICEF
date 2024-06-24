package c_elab.pat.aup18.quotaA;

import it.clesius.clesius.util.Sys;


/** query per il calcolo delle imposte perse della dichiarazione ICEF attualizzata
 * @author l_leonardi
 */
public class Imposte_perse extends QAttualizza {

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

				int idDich = records.getInteger(i, 2);

				if(quadroB_1_1 || quadroB_1_2 || quadroB_3_1 || quadroB_3_2 || quadroB_3_3 || quadroB_3_4)
				{
					//se selezionato uno di questi quadri della dichiarazione attualizzata ICEF sommo le imposte della dichiarazione ICEF

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