package c_elab.pat.sport12;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

/** 
 *
 * @author s_largher
 */
public abstract class QAssegno_imposto extends ElainNode {
    
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     *
     *
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QAssegno_imposto.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            
            //                         					   1                   				   2
            sql.append("SELECT  Dati_famiglia.ID_tp_obbligo_mantenimento, Dati_famiglia.assegno_extra ");
            sql.append(" FROM Dati_famiglia INNER JOIN ");
            sql.append(" Domande ON Dati_famiglia.ID_domanda = Domande.ID_domanda ");
            sql.append(" WHERE (Domande.ID_domanda = "+IDdomanda);
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
    	ElabStaticContext.getInstance().resetSession(QAssegno_imposto.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QAssegno_imposto(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
