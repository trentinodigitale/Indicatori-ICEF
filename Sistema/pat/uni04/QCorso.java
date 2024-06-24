/**
 *Created on 18-giu-2004
 */
package c_elab.pat.uni04;

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
            //                            1                              2                                           3                                           4
            sql.append(
            "SELECT UNI_R_Corsi_laurea.anni_corso, UNI_R_Corsi_laurea.anni_aggiuntivi, UNI_R_Corsi_laurea.anni_aggiuntivi_alloggio, UNI_R_Corsi_laurea.anni_aggiuntivi_invalidi ");
            sql.append("FROM UNI_R_Corsi_laurea ");
            sql.append(
            "INNER JOIN UNI_Studenti ON (UNI_R_Corsi_laurea.ID_anno_accademico = UNI_Studenti.ID_anno_accademico AND UNI_R_Corsi_laurea.ID_corso_laurea = UNI_Studenti.ID_corso_laurea AND UNI_R_Corsi_laurea.ID_facolta = UNI_Studenti.ID_facolta AND UNI_R_Corsi_laurea.ID_universita = UNI_Studenti.ID_universita) ");
            sql.append("WHERE UNI_Studenti.ID_domanda = ");
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

