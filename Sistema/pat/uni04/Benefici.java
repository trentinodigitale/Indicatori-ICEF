package c_elab.pat.uni04;

/**
 *Created on 03-giu-2004
 */

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.clesius.util.Sys;


/** legge i benefici
 * @author g_barbieri
 */
public class Benefici extends ElainNode {
    
    /** ritorna il valore String da passare a Clesius
     * @see it.clesius.apps2core.ElainNode#getString()
     * @return String
     */
    public String getString() {
        String benefits = "";
        
        //borsa
        if (new Double((String)(records.getElement(1,1))).doubleValue() != 0) {
            benefits += "borsa" +  Sys.getSep() + "possibilità borsa" + Sys.getSep() + "borsa in sede" + Sys.getSep() + "borsa fuori sede" + Sys.getSep();
        }
        //tasse
        if (new Double((String)(records.getElement(1,2))).doubleValue() != 0) {
            benefits += "tassa" + Sys.getSep() + "importo tassa" + Sys.getSep();
        }
        //alloggio
        if (new Double((String)(records.getElement(1,3))).doubleValue() != 0) {
            benefits += "alloggio" + Sys.getSep();
        }
        return benefits;
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
        
        ////                         1                             2                             3     
        doQuery(
        "SELECT UNI_Studenti.domanda_borsa, UNI_Studenti.domanda_esenzione, UNI_Studenti.domanda_alloggio FROM UNI_Studenti WHERE (((UNI_Studenti.ID_domanda)="
        + IDdomanda + "));"
        );
    }
    
    /** Benefici constructor */
    public Benefici(){
    }
}

