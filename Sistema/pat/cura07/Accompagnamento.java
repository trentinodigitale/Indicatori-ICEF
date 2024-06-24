/**
 *Created on 31-mag-2005
 */

package c_elab.pat.cura07;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge l'assegno di accompagnamento che viene sottratto per compensare
 * l'importo inserito nella dichiarazione ICEF 
 * @author g_barbieri
 */
public class Accompagnamento extends ElainNode {
	
	/** Accompagnamento constructor */
	public Accompagnamento() {
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
		//                                                      1                                                       2
		sql.append("SELECT sanitest.L6_Assistiti.importo_indennita_accompagnamento, sanitest.L6_Assistiti.importo_indennita_accompagnamento_altri ");
		sql.append("FROM sanitest.L6_Assistiti ");
		sql.append("WHERE sanitest.L6_Assistiti.Id_assistito = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
        try {
            double totAccompagnamento = Double.parseDouble((String)(records.getElement(1,1)))+Double.parseDouble((String)(records.getElement(1,2)));
            return totAccompagnamento;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
