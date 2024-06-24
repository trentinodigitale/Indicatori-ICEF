/**
 *Created on 18-giu-2004
 */
package c_elab.pat.san06;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati del merito del corso di laurea dello studente
 *
 * @author a_pichler
 */
public abstract class QMerito extends ElainNode {
    
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
        //
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QMerito.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		    
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                          1                       2                    3                        4
            sql.append("SELECT  SAN_Merito.minimo_deroga, SAN_Merito.minimo, SAN_Merito.massimo, SAN_Merito.minimo_premio ");
            sql.append("FROM SAN_Studenti INNER JOIN ");
            sql.append("SAN_Merito ON SAN_Studenti.ID_anno_accademico = SAN_Merito.ID_anno_accademico AND SAN_Studenti.anno_prima_imm = SAN_Merito.anno_prima_imm AND SAN_Studenti.ID_tp_corso = SAN_Merito.ID_tp_corso ");
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
    	ElabStaticContext.getInstance().resetSession( QMerito.class.getName(), IDdomanda, "records" );
    }
    
    /** QMerito constructor */
    public QMerito(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}

