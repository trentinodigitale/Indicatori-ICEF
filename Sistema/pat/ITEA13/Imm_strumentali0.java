package c_elab.pat.ITEA13;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

/** 
 *	somma valori ICI da quadro C3 e quadro C4
 *	se redditi da partecipazione si moltiplica per la quota
 */
public class Imm_strumentali0 extends ElainNode {
	
	//CAMBIAMI: va cambiata ogni anno 
	int idServizioVerifica = 13240; // per verifica requisiti
	int idServizioVerificaTione = 596004; // per verifica requisiti tione
	
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		if(servizio==idServizioVerifica)
		{
			//          							1              2		 	 3        4
			sql.append("SELECT   Familiari.ID_dichiarazione, ID_tp_impresa, valore_ici, quota ");
			sql.append("FROM     Familiari ");
			sql.append("INNER JOIN ");
			sql.append("	(SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, quota_capitale as quota ");
			sql.append("		FROM  Redditi_partecipazione ");
			sql.append("	 UNION ");
			sql.append("	 SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, 100 AS quota ");
			sql.append("		FROM  Redditi_impresa ");
			sql.append("	) AS redd ON Familiari.ID_dichiarazione = redd.ID_dichiarazione ");
			sql.append("WHERE     (Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");			
		}
		else if(servizio==idServizioVerificaTione)
		{
			//          							1              2		 	 3        4
			sql.append("SELECT   Familiari.ID_dichiarazione, ID_tp_impresa, valore_ici, quota ");
			sql.append("FROM     Familiari ");
			sql.append("INNER JOIN ");
			sql.append("	(SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, quota_capitale as quota ");
			sql.append("		FROM  Redditi_partecipazione ");
			sql.append("	 UNION ");
			sql.append("	 SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, 100 AS quota ");
			sql.append("		FROM  Redditi_impresa ");
			sql.append("	) AS redd ON Familiari.ID_dichiarazione = redd.ID_dichiarazione ");
			sql.append("WHERE     (Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");			
		}
		else
		{
			
			//          							   1              2		 	     3        4
			sql.append("SELECT   EPU_Familiari.ID_dichiarazione, ID_tp_impresa, valore_ici, quota ");
			sql.append("FROM     EPU_Familiari ");
			sql.append("INNER JOIN ");
			sql.append("	(SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, quota_capitale as quota ");
			sql.append("		FROM  Redditi_partecipazione ");
			sql.append("	 UNION ");
			sql.append("	 SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, 100 AS quota ");
			sql.append("		FROM  Redditi_impresa ");
			sql.append("	) AS redd ON EPU_Familiari.ID_dichiarazione = redd.ID_dichiarazione ");
			sql.append("WHERE     (EPU_Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
		}
		doQuery(sql.toString());
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
			for (int row = 1; row <= records.getRows(); row++) {
				tot +=  Sys.round( ( records.getDouble(row,3) * records.getDouble(row,4) / 100.0 ) - aggiusta, round );
			}
			return tot;
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}