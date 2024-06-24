/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA09; 

/** 
 *@author a_cavalieri 
 */
import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

public class C2_agr0 extends ElainNode { 

	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		
		//                                     1                            2                            3                             4                                 5                             6
		sql.append(
			"SELECT R_Relazioni_parentela.peso_reddito, Redditi_agricoli.quantita, R_Importi_agricoli.importo, Redditi_agricoli.costo_locazione, Redditi_agricoli.costo_dipendenti, Redditi_agricoli.quota ");
		sql.append("FROM EPU_Familiari ");
		sql.append(
			"INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append(
			"INNER JOIN Redditi_agricoli ON EPU_Familiari.ID_dichiarazione = Redditi_agricoli.ID_dichiarazione ");
		sql.append(
			"INNER JOIN R_Importi_agricoli ON (Redditi_agricoli.ID_tp_agricolo = R_Importi_agricoli.ID_tp_agricolo) AND (Redditi_agricoli.ID_zona_agricola = R_Importi_agricoli.ID_zona_agricola) AND (Redditi_agricoli.ID_dich = R_Importi_agricoli.ID_dich)  ");	 
		sql.append(
			"INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE EPU_Familiari.nuovo_ingresso = 0 AND Domande.ID_domanda = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());
		
		//System.out.println(sql.toString());

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
		
		try {
			for (int i = 1; i <= records.getRows(); i++) {
				double agricolo = (java.lang.Math.max (0,
					// quantità *
					new Double((String) records.getElement(i, 2)).doubleValue() *
					// importo -
					new Double((String) records.getElement(i, 3)).doubleValue() -
					// costo locazione terreni -
					new Double((String) records.getElement(i, 4)).doubleValue() -
					// costo dipendenti
					new Double((String) records.getElement(i, 5)).doubleValue()
				)) * 
				// quota possesso
				(new Double((String) records.getElement(i, 6)).doubleValue()) / 100.0;
				//            agricolo * peso reddito
				tot = tot + (agricolo * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0);
			}
			return Sys.round(tot - aggiusta, round);
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}