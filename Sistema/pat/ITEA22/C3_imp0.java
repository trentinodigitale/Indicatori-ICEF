package c_elab.pat.ITEA22; 

/** 
 *@author s_largher 
 */
import java.util.Hashtable;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

public class C3_imp0 extends ElainNode { 
	
	private Hashtable<String, Double[]> table_spese;
	
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		//                                      1                              2									3
		sql.append(
			"SELECT R_Relazioni_parentela.peso_reddito, Redditi_impresa.reddito_dichiarato, Redditi_impresa.ID_dichiarazione ");
		sql.append("FROM EPU_Familiari ");
		sql.append(
			"INNER JOIN Redditi_impresa ON EPU_Familiari.ID_dichiarazione = Redditi_impresa.ID_dichiarazione ");
		sql.append(
			"INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append(
			"INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE EPU_Familiari.nuovo_ingresso = 0 AND Domande.ID_domanda = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());

	}

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
		
		table_spese = new Hashtable<String, Double[]>();
		
		try {
			for (int i = 1; i <= records.getRows(); i++) {
				
				double peso_par = records.getDouble(i, 1);
				double importo = records.getDouble(i, 2);
				String id_dich = records.getString(i, 3);
				
				if ( table_spese.containsKey(id_dich) ) {
					double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
					table_spese.remove(id_dich);
					table_spese.put( id_dich, new Double[]{ (Double)(imp+importo), (Double)peso_par} );
				}else {
					table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
				}
				
				//importo = importo * peso_par / 100.0;
				
				tot = tot +  Sys.round( importo - aggiusta, round ) * peso_par / 100.0;
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
	
	public Hashtable<String, Double[]> getTable_spese() {
		return table_spese;
	}
}