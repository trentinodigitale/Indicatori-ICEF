/**
 *Created on 23-mag-2006
 */

package c_elab.pat.pcn5;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/**
  */
public class Escluso_ufficio extends ElainNode {

	protected void reset() {
 	}

	public void init(RunawayData dataTransfer) {
	
	    super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT escludi_ufficio ");
			sql.append("FROM  Domande ");
			sql.append("WHERE ID_domanda =  ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
	}
	
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {
		try {
			return records.getInteger(1, 1);
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}