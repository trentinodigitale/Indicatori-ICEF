package c_elab.pat.muov13;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** 
 * @author s_largher
 *
 *  il richiedente della domanda è il portatore di minorazione
 */

public class Richiedente extends ElainNode {
   	
    public String getString() {
        return (String)(records.getElement(1,1)) + " " + (String)(records.getElement(1,2));
    }
    protected void reset() {
    }
    public void init(RunawayData dataTransfer) {
         super.init(dataTransfer);

	        doQuery(                               //1             
	        		"SELECT Domande.cognome, Domande.nome "+
	                "FROM Domande "+
	                "WHERE (Domande.ID_domanda = "+
	                IDdomanda + ");"
	        );        
         
      }
}