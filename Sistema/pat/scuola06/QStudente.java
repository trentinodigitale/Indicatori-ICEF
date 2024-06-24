package c_elab.pat.scuola06;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 * 
 * @author a_mazzon
 *
 * Recupera i dati utilizzati dalle classi di trasformazione
 */
public abstract class QStudente extends ElainNode {
    
    public double getValue() {
        return 0.0;
    }
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QStudente.class.getName(), IDdomanda, "records" );
    }
     
    public QStudente(){
    }
    
    /**
     * Recupera i dati dalla tabella Domande:
     * 1. ID_provincia_residenza.
     * 
     * @param RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			
            super.init(dataTransfer);
            
            //                                  1                               2                           3                        4                      5                      6                           7
            doQuery("SELECT Domande.ID_provincia_residenza, SCUOLE_Studenti.media_voti, SCUOLE_Studenti.trasporti, SCUOLE_Studenti.mensa, SCUOLE_Studenti.tassa, SCUOLE_Studenti.libri, SCUOLE_Studenti.convitto_alloggio " +
            		"FROM Domande INNER JOIN SCUOLE_Studenti ON Domande.ID_domanda = SCUOLE_Studenti.ID_domanda " +
            		"WHERE Domande.ID_domanda=" + IDdomanda);
            
            session.setInitialized(true);
			session.setRecords( records );
        } else {
			records = session.getRecords();
        }
    }
}

