package c_elab.pat.san08;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/*****************************************************
 * classe Richiedente
 ******************************************************/

public class Richiedente extends ElainNode {
    
    public String getString() {
        return (String)(records.getElement(1,1)) + " " + (String)(records.getElement(1,2));
    }
    
    protected void reset() {
    }
    
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        //         1      2
        doQuery(
        "SELECT cognome, nome FROM Domande WHERE ((Domande.ID_domanda="
        + IDdomanda + "));"
        );
    }
    
    public Richiedente(){
    }
}

