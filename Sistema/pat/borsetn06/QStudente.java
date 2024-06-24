/**
 *Created on 10-genn-2006
 */
package c_elab.pat.borsetn06;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati dello studente
 *
 * @author a_pichler
 */
public abstract class QStudente extends ElainNode {
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {

            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                         1                            2                   3
            sql.append(
            "SELECT TN_Studenti.importo_borsa_prec, TN_Studenti.num_parenti_uni, fabbricati_estero ");
            sql.append("FROM TN_Studenti ");
            sql.append("WHERE TN_Studenti.ID_domanda = ");
            sql.append(IDdomanda);
            
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
    	ElabStaticContext.getInstance().resetSession( QStudente.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QStudente(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
