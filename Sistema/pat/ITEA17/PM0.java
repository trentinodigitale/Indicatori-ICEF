package c_elab.pat.ITEA17; 

/** 
 *@author s_largher 
 */

import it.clesius.db.sql.RunawayData;

/***************************************************************************************************/
/*** CAMBIAMI - NOTA BENE - VA CAMBIATA OGNI ANNO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***/
/***************************************************************************************************/
public class PM0 extends c_elab.pat.icef17.PM {
/***************************************************************************************************/
/*** CAMBIAMI - NOTA BENE - VA CAMBIATA OGNI ANNO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***/
/***************************************************************************************************/
	
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.dataTransfer=dataTransfer;
		StringBuffer sql = new StringBuffer();
		sql.append(
		"SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, EPU_Familiari.ID_dichiarazione ");

		sql.append("FROM EPU_Familiari ");
		sql.append(
		"INNER JOIN Patrimoni_finanziari ON EPU_Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
		sql.append(
		"INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append(
		"INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE EPU_Familiari.nuovo_ingresso = 0 AND Domande.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(" ORDER BY EPU_Familiari.ID_dichiarazione");
		
		doQuery(sql.toString());
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{
		return super.getValue();
	}
}