package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i benefici
 * @author g_barbieri
 */
public class Benefici extends ElainNode {
	
	
	/** Benefici constructor */
	public Benefici() {
	}

	/** ritorna il valore String da passare a Clesius
	 * @see it.clesius.apps2core.ElainNode#getString()
	 * @return String
	 */
	public String getString() {
		String benefits = "";

		for (int i = 1; i <= records.getRows(); i++) {
			try {
				benefits += (String) records.getElement(i, 1) + ";";
			} catch (NullPointerException n) {
				System.out.println(
					"Null pointer in "
						+ getClass().getName()
						+ ": "
						+ n.toString());
				return "0";
			}
		}

		if (benefits.equals("")) {
			return "0";
		} else {
			return benefits;
		}
	}
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);

		doQuery(
			"SELECT R_Benefici.nodo_risultato FROM R_Servizi INNER JOIN ((R_Periodi INNER JOIN Domande ON R_Periodi.ID_periodo = Domande.ID_periodo) INNER JOIN R_Benefici ON R_Periodi.ID_periodo = R_Benefici.ID_periodo) ON (R_Servizi.ID_servizio = R_Periodi.ID_servizio) AND (R_Servizi.ID_servizio = R_Benefici.ID_servizio) AND (R_Servizi.ID_servizio = Domande.ID_servizio) WHERE (((Domande.ID_domanda)="
				+ IDdomanda
				+ "));");
	}
}
