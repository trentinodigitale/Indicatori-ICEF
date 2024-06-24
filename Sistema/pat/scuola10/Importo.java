package c_elab.pat.scuola10;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
/**
 * 
 * @author a_mazzon
 *
 */
public class Importo extends ElainNode {
	
	
    protected void reset() {
    }
    
    /**
     * Recupera i dati necessari dalla tabella SCUOLE_R_Corsi:
     * 1. borsa_max.
     * 
     * @param RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
        super.init(dataTransfer);

        doQuery(
        "SELECT SCUOLE_R_Corsi.borsa_max FROM SCUOLE_R_Corsi INNER JOIN SCUOLE_Studenti ON SCUOLE_R_Corsi.ID_corso = SCUOLE_Studenti.ID_corso WHERE (SCUOLE_Studenti.ID_domanda ="
        + IDdomanda + ");"
        );
    }
    
	/**
	 * Recupera il valore di borsa_max.
	 * 
	 * @return double
	 */
    public double getValue() {
        try {
            return (new Double((String)(records.getElement(1,1))).doubleValue());
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public Importo(){
    }
}
