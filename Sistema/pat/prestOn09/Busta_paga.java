/**
 *Created on 31-mag-2005
 */

package c_elab.pat.prestOn09;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i dati della busta paga del mese precedente nella Tabella PSO_lavoratori
* @author a_pichler
 */
public class Busta_paga extends ElainNode {
	
	/** Accompagnamento constructor */
	public Busta_paga() {
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
		//                        1                    
		sql.append("SELECT     ultima_paga ");
		sql.append("FROM    PSO_lavoratori ");
		sql.append("WHERE ID_domanda = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());
                
                
        }

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
        try {               
          return new Double((String)(records.getElement(1,1))).doubleValue();                
           
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
