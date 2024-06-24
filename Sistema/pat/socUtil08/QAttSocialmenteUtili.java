package c_elab.pat.socUtil08;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 * 
 * @author s_largher
 *
 * Recupera i dati utilizzati dalle classi di trasformazione
 */
public abstract class QAttSocialmenteUtili extends ElainNode {
    
    public double getValue() {
        return 0.0;
    }
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QAttSocialmenteUtili.class.getName(), IDdomanda, "records" );
    }
    
    public QAttSocialmenteUtili(){
    }
    
    /**
     * Recupera i dati dalla tabella Domande:
     * 1. ID_provincia_residenza.
     * 
     * @param RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QAttSocialmenteUtili.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		    super.init(dataTransfer);
            
            //                       1                       2
            doQuery("SELECT   importo_pensione, retribuzione_percepita_anno "+
            		"FROM 	Att_socialmente_utili " +
            		"WHERE Att_socialmente_utili.ID_domanda=" + IDdomanda);
            
            session.setInitialized(true);
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}
    }
}

