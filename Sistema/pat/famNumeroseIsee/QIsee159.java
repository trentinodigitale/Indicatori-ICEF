package c_elab.pat.famNumeroseIsee;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
/**
 * 
 */
public abstract class QIsee159 extends ElainNode {
    
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QIsee159.class.getName(), IDdomanda, "records" );
    }
    
 
    /**
     * Recupera i dati per theRecords da DOMANDE_ISEE159:<BR>
     * 1. DOMANDE.ID_DICHIARAZIONE,<BR>
     * 2. DOMANDE.CODICE_FISCALE,<BR>
     * 3. DOMANDE_ISEE159.PROTOCOLLO,<BR> 
     * 4. DOMANDE_ISEE159.ID_TP_ISEE,<BR>
     * 5. DOMANDE_ISEE159.ID_TP_MODALITA_CALCOLO,<BR>
     * 6. DOMANDE_ISEE159.ISEE,<BR>
     */
    
    public void init(RunawayData dataTransfer) {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QIsee159.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
            super.init(dataTransfer);
     
            doQuery(
            "SELECT domande.id_dichiarazione, "		
            + " domande.codice_fiscale, " 		
            + " isee159_clesiusdoc.protocollo_dsu as protocollo,   " 		
            + " r_servizi.id_tp_isee_default as id_tp_isee, " 		
            + " domande.non_presenta_isee, "
            + " r_servizi.id_tp_isee_richiesto, "
            + " domande.id_domanda "		
            + " FROM domande " 
            + " LEFT OUTER JOIN isee159_clesiusdoc on domande.id_dichiarazione=isee159_clesiusdoc.id_doc "
            + " INNER JOIN r_servizi ON domande.id_servizio = r_servizi.id_servizio "
            + " WHERE domande.id_domanda= " + IDdomanda + ";"
            );
            session.setInitialized(true);
         	session.setRecords( records );
        } else {
        	records = session.getRecords();
        }
        
    }
    
    
    public QIsee159(){
    }
    
    public double getValue() {
        return 0.0;
    }
}

