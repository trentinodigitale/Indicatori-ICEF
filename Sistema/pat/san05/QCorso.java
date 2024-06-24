/**
 *Created on 18-giu-2004
 */
package c_elab.pat.san05;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati del corso di laurea dello studente
 *
 * @author g_barbieri
 */
public abstract class QCorso extends ElainNode {
    
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QCorso.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		     
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                   1                              2        
            sql.append(
            "SELECT SAN_R_Corsi.anni_corso, SAN_R_Corsi.anni_aggiuntivi ");
            sql.append("FROM SAN_Studenti INNER JOIN ");
            sql.append(
            "SAN_R_Corsi ON SAN_Studenti.ID_corso = SAN_R_Corsi.ID_corso AND SAN_Studenti.ID_anno_accademico = SAN_R_Corsi.ID_anno_accademico ");
            sql.append("WHERE SAN_Studenti.ID_domanda = ");
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
    	ElabStaticContext.getInstance().resetSession( QCorso.class.getName(), IDdomanda, "records" );

    }
    
    /** QCorso constructor */
    public QCorso(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}

