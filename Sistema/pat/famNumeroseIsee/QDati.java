package c_elab.pat.famNumeroseIsee;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
/**
 * 
 */
public abstract class QDati extends ElainNode {
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QDati.class.getName(), IDdomanda, "records" );
    }
    
    public void init(RunawayData dataTransfer,String caller) {
    	 super.init(dataTransfer);
    }

    public void init(RunawayData dataTransfer) {
        
    	
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDati.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
            super.init(dataTransfer);
     
            doQuery(
                    "SELECT Doc.data_presentazione, "		
                    + " Assegni.anno_fam_numerose, " 
                    + " Assegni.is_precedente, " 
                    + " Assegni.precedente, "
                    + " domande.id_tp_pagamento, " 
                    + " domande.codice_stato, " 
                    + " domande.CIN_pagamento, " 
                    + " domande.ABI_pagamento, " 
                    + " domande.CAB_pagamento, " 
                    + " domande.Cc_pagamento, " 
                    + " domande.IBAN, " 
                    + " Assegni.mese_start, "
                    + " Assegni.mese_end,"
                    + " domande.ID_provincia_residenza,"
                    + " coalesce(domande_permessi_soggiorno.id_tp_permesso, 0) as id_tp_permesso, "
                    + " domande.escludi_ufficio,"
                    + " domande.isee_non_conforme,"
                    + " domande.id_dichiarazione "
                    + " FROM doc "
                    + " inner join domande on doc.id=domande.id_domanda " 
                    + " INNER JOIN Assegni ON domande.id_domanda = Assegni.id_domanda "
                    + " LEFT OUTER JOIN domande_permessi_soggiorno on domande.ID_domanda = domande_permessi_soggiorno.id_domanda "
                    + " WHERE domande.id_domanda= " + IDdomanda + ";"
            		);
            session.setInitialized(true);
         	session.setRecords( records );
        } else {
        	records = session.getRecords();
        } 
    }

    public QDati(){
    }
    
    public double getValue() {
        return 0.0;
    }
}

