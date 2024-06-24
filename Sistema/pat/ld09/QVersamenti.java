/**
 *Created on 23-mag-2006 
 */
package c_elab.pat.ld09;

import java.util.*;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.Util_apapi;

/** legge i dati del richiedente
 *
 * @author g_barbieri 
 */
public abstract class QVersamenti extends ElainNode {
    
    private static Table             theRecords;
    private static boolean           initialized = false;
    protected static Hashtable 		 inputValues = new Hashtable();
	
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
        if (!initialized) {
        	super.init(dataTransfer);
    		StringBuffer sql = new StringBuffer();
    		
    		sql.append("SELECT  importo_E1, importo_E2 ");
    		sql.append("FROM PNS_lavdisc_versamenti");
    		sql.append(" WHERE ID_domanda =  ");
    		sql.append(IDdomanda);
    		sql.append(" ");		
    		
    		doQuery(sql.toString());
        	
        }

             
        }
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
        initialized = false;
        theRecords= null;
    }
    
    /** QRichiedente constructor */
    public QVersamenti(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
