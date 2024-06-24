/** 
 *Created on 03-nov-2005 
 */

package c_elab.pat.muov07;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** query QAssegnazioni
 * @author g_barbieri
 */
public class QAssegnazioni extends ElainNode {

	private static long ID_servizio_con_riduz = 8002;
	private static String ID_parentela_beneficiario_con_riduz = "360";
	private static String anno = "2007";
	
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
		
		if ( servizio == ID_servizio_con_riduz ) {
			// se il servizio è con riduzione il join alla tabella MUOVERSI_km_assegnati 
			// si fa con la tabella Familiari 
			//                                   1                                    2										3
			sql.append(
				"SELECT MUOVERSI_Richiedente.km_richiesti, MUOVERSI_km_assegnati.km_assegnati, MUOVERSI_km_assegnati.assegno_cura ");
			sql.append("FROM (((Familiari INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione) INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN MUOVERSI_km_assegnati ON Soggetti.codice_fiscale = MUOVERSI_km_assegnati.codice_fiscale) INNER JOIN MUOVERSI_Richiedente ON Familiari.ID_domanda = MUOVERSI_Richiedente.ID_domanda ");
			sql.append("WHERE (((Familiari.ID_relazione_parentela)=" + ID_parentela_beneficiario_con_riduz + ") AND ((MUOVERSI_km_assegnati.anno)=" + anno + ") AND ((Familiari.ID_domanda)=");
			sql.append(IDdomanda);
			sql.append("))");
		} else {
			// se il servizio è senza riduzione  
			doQuery("SELECT ID_domanda FROM MUOVERSI_Beneficiario WHERE ID_domanda = " + IDdomanda);
			boolean richiedente_non_beneficiario = false;
			if (records != null) {
				if (records.getRows() > 0) {
					richiedente_non_beneficiario = true;
				}
			}
			
			if (richiedente_non_beneficiario) {
				// se il servizio è senza riduzione ed il beneficiario NON è il richedente 
				// il join alla tabella MUOVERSI_km_assegnati si fa con la tabella MUOVERSI_Beneficiario
				//                                   1                                    2                                   3
				sql.append(
					"SELECT MUOVERSI_Richiedente.km_richiesti, MUOVERSI_km_assegnati.km_assegnati, MUOVERSI_km_assegnati.assegno_cura ");
				sql.append("FROM MUOVERSI_Richiedente INNER JOIN MUOVERSI_Beneficiario ON MUOVERSI_Richiedente.ID_domanda = MUOVERSI_Beneficiario.ID_domanda INNER JOIN Soggetti ON MUOVERSI_Beneficiario.ID_soggetto = Soggetti.ID_soggetto INNER JOIN MUOVERSI_km_assegnati ON Soggetti.codice_fiscale = MUOVERSI_km_assegnati.codice_fiscale ");
				sql.append("WHERE MUOVERSI_km_assegnati.anno=" + anno + " AND MUOVERSI_Richiedente.ID_domanda=");
				sql.append(IDdomanda);
			} else {
				// se il servizio è senza riduzione ed il beneficiario è il richedente 
				// il join alla tabella MUOVERSI_km_assegnati si fa con la tabella Domande
				//                                   1                                    2                                    3
				sql.append(
					"SELECT MUOVERSI_Richiedente.km_richiesti, MUOVERSI_km_assegnati.km_assegnati, MUOVERSI_km_assegnati.assegno_cura ");
				sql.append("FROM ((MUOVERSI_Richiedente INNER JOIN Domande ON MUOVERSI_Richiedente.ID_domanda = Domande.ID_domanda) INNER JOIN Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN MUOVERSI_km_assegnati ON Soggetti.codice_fiscale = MUOVERSI_km_assegnati.codice_fiscale ");
				sql.append("WHERE MUOVERSI_km_assegnati.anno=" + anno + " AND Domande.ID_domanda=");
				sql.append(IDdomanda);
			}
		}
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
