package c_elab.pat.uni17.dichICEF17;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i benefici
 * @author s_largher
 */
public class Benefici extends ElainNode {
    
    /** ritorna il valore String da passare a Clesius
     * @see it.clesius.apps2core.ElainNode#getString()
     * @return String
     */
    public String getString() {
        String benefits = "";
        
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
    }
    
    /** Benefici constructor */
    public Benefici(){
    }
}

