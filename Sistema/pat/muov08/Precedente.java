/** 
 *Created on 03-nov-2005 
 */

package c_elab.pat.muov08;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** 
 * copia muov07
 * 
 */
public class Precedente extends ElainNode {

	private static String ID_parentela_beneficiario_con_riduz ="913";
	
	/** Precedente constructor */
	public Precedente() {
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
		//                                         1                                2      
		sql.append(
			"SELECT MUOVERSI_Richiedente.indennita_accomp_importo, Redditi_altri.importo ");
		sql.append("FROM (Familiari INNER JOIN MUOVERSI_Richiedente ON Familiari.ID_domanda = MUOVERSI_Richiedente.ID_domanda) INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
		sql.append("WHERE (((Familiari.ID_relazione_parentela)=" + ID_parentela_beneficiario_con_riduz + ") AND ((MUOVERSI_Richiedente.ID_domanda)=");
		sql.append(IDdomanda);
		sql.append("))");
                
		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		if (records == null)
			return 0.0;
		double importo = 0;
		
		try {
			// somma tutti gli importi del quadro C5
			for (int i = 0; i <records.getRows(); i++){
				importo = importo + new Double((String) records.getElement(1, 2)).doubleValue();
			}
			// se l'importo della dich ICEF è superiore a quello della domanda 
			// ritorna quest'ultimo come importo da sottrarre
			if (importo >= new Double((String) records.getElement(1, 1)).doubleValue()){
				return new Double((String) records.getElement(1, 1)).doubleValue();
			} else {
				return 0.0;
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
