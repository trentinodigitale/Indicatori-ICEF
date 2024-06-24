package c_elab.pat.mant18;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/**
 * 
 * @author a_pichler
 *
 */
public class QDatiVariazioneDomanda extends ElainNode {

	
	public QDatiVariazioneDomanda() {	}

	protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QDatiVariazioneDomanda.class.getName(), IDdomanda, "records" );
    }
    
	public double getValue() {
        return 0.0;
    }
    
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDatiVariazioneDomanda.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
            super.init(dataTransfer);
       
	        StringBuffer sql = new StringBuffer();
			sql.append("SELECT  Domande.escludi_ufficio, Domande.escludi_ufficio_data, Doc.data_presentazione ");
			sql.append("FROM  Domande ");
			sql.append("INNER JOIN Doc ON Doc.ID = Domande.ID_domanda ");
			
			sql.append("WHERE (Domande.ID_domanda =  ");
			sql.append(IDdomanda);
			sql.append(") ");	
			
			doQuery(sql.toString());
	        
			session.setInitialized(true);
			session.setRecords( records );
		} else {
			records = session.getRecords();
        }
	}

    
}
