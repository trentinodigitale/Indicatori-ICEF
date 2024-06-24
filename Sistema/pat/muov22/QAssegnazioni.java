package c_elab.pat.muov22;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** 
 * 
 */
public class QAssegnazioni extends ElainNode {

	//CAMBIAMI ogni anno dopo che vengono copiati i dati in MUOVERSI_km_assegnati - FATTO
	private static String anno = "2022";
	
	/** Km constructor */
	public QAssegnazioni() {
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
		StringBuffer sql = new StringBuffer();
		
		//                                   1                                    2										3						4
		sql.append("SELECT MUOVERSI_Richiedente.km_richiesti, MUOVERSI_km_assegnati.km_assegnati, MUOVERSI_km_assegnati.assegno_cura, MUOVERSI_km_assegnati.reddito_garanzia ");
		sql.append(" FROM         MUOVERSI_Richiedente INNER JOIN");
		sql.append(" Domande ON Domande.ID_domanda = MUOVERSI_Richiedente.ID_domanda INNER JOIN");
		sql.append(" MUOVERSI_km_assegnati ON MUOVERSI_km_assegnati.codice_fiscale = Domande.codice_fiscale");
		sql.append(" WHERE (MUOVERSI_Richiedente.ID_domanda="+IDdomanda +") AND (MUOVERSI_km_assegnati.anno=" + anno + ")");
			
		doQuery(sql.toString());
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
			return 0.0;
	}
}
