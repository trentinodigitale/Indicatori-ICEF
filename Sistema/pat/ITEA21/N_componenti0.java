package c_elab.pat.ITEA21; 

/** 
 *@author s_largher 
 */
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

public class N_componenti0 extends ElainNode { 
	
	int idServizioVerifica = 13519; // per verifica requisiti <-- non aggiornare!
	int idServizioVerificaTione = 0; // per verifica requisiti tione //non più duplicata
	
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
			//                        1
			sql.append(
				"SELECT EPU_Familiari.familiare ");
			sql.append("FROM EPU_Familiari ");
			sql.append("WHERE EPU_Familiari.nuovo_ingresso = 0 AND EPU_Familiari.ID_domanda = ");
			sql.append(IDdomanda);
		}
		else if(servizio==idServizioVerificaTione)
		{
			//                        1
			sql.append(
				"SELECT EPU_Familiari.familiare ");
			sql.append("FROM EPU_Familiari ");
			sql.append("WHERE EPU_Familiari.nuovo_ingresso = 0 AND EPU_Familiari.ID_domanda = ");
			sql.append(IDdomanda);
		}
		else
		{
			//          				 1
			sql.append(
				"SELECT Familiari.familiare ");
			sql.append("FROM Familiari ");
			sql.append("WHERE Familiari.ID_domanda = ");
			sql.append(IDdomanda);
		}

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			return records.getRows();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}