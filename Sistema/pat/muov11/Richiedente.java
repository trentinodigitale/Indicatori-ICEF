/*
 * Richiedente.java
 *
 * Created on 29 ottobre 2007, 13.31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package c_elab.pat.muov11;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** 
 * @author a_pichler
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