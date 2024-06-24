/**
 *Created on 23-mag-2006 
 */
package c_elab.pat.ld08;

import java.util.*;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.Util_apapi;

/** legge i dati del richiedente
 *
 * @author g_barbieri 
 * 
 * check
 * 2  escluso per mancanza requisiti residenza
 *               
 */
public abstract class QRichiedente extends ElainNode {
    
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
            int check = 0;
        	
         // ------------------------------ richiedente
            StringBuffer sql = new StringBuffer();
            //                              1                              2                            3                            4                              5                             6
            sql.append(
            "SELECT PNS_richiedente.ID_contribuente, PNS_richiedente.copPrev_dal, PNS_richiedente.pensDir_data, PNS_richiedente.etaPens_dal, PNS_richiedente.resTAA_5anni, PNS_richiedente.resTAA_1anno ");
            sql.append(" FROM PNS_richiedente ");
            sql.append("WHERE PNS_richiedente.ID_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());

            if( !(records.getBoolean(1,5) || records.getBoolean(1,6)) )
            	check = 2;  //escluso per mancanza requisiti residenza
           
   
            inputValues.put("Check", new Double(check));
   
            initialized = true;
            theRecords = records;
        } else {
            records = theRecords;
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
    public QRichiedente(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
