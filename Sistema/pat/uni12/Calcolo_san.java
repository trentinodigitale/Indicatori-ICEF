package c_elab.pat.uni12;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** stabilisce la fase del calcolo: Calcolo_san=0 (calcolo borsa università standard) Calcolo_san=1 (calcolo borsa università per sanità)
 */
public class Calcolo_san extends ElainNode {
    
	//CAMBIAMI o VERIFICAMI
	String idScuolaSanita = "2";
	
    protected void reset() {
    }
    
    public double getValue() {
        
        if (records == null)
            return 0.0;
        
        try {
            if (records.getRows() > 0) {
            	String idScuola = records.getString(1, 1);
            	if(idScuola!=null && idScuola.equals(idScuolaSanita))
            	{
            		return 1.0;
            	}
            	else
            	{
            		return 0.0;
            	}
                
            } else {
                return 0.0;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        StringBuffer sql = new StringBuffer();
        	       
        sql.append("SELECT ID_scuola ");
        sql.append("FROM UNI_Domanda ");
        sql.append("WHERE (UNI_Domanda.ID_domanda =");
        sql.append(IDdomanda);
        sql.append(");");
        
        doQuery(sql.toString());
        //System.out.println(sql.toString());

    }
}
