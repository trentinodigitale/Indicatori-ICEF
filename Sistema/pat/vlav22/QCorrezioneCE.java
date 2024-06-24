package c_elab.pat.vlav22;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** 
 *
 * @author g_barbieri
 */
public abstract class QCorrezioneCE extends ElainNode {
    
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     *
     *
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QCorrezioneCE.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                                        1                   				2						    3					4
            sql.append("SELECT VLAV_Dati.patrimonio_immobiliare, VLAV_Dati.patrimonio_finanziario, VLAV_Dati.rendite, VLAV_Dati.altre_erogazioni ");
            sql.append("FROM VLAV_Dati ");
            sql.append("WHERE (VLAV_Dati.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(")");
            doQuery(sql.toString());
            
        	session.setInitialized(true);
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}
    }
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession(QCorrezioneCE.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QCorrezioneCE(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
