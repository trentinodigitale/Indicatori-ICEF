/**
 *Created on 21-giu-2007
 */

package c_elab.pat.ener08;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 * @author g_barbieri
 */
public class N_familiari extends ElainNode {

	private static Log log = LogFactory.getLog( N_familiari.class );
	
	public N_familiari() {}
    
	protected void reset() {}
	
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        
        doQuery(
        "SELECT COUNT(Energia_familiari.ID_soggetto) AS n_familiari FROM Energia_familiari GROUP BY Energia_familiari.ID_domanda HAVING Energia_familiari.ID_domanda="
        + IDdomanda
        );
    }

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
