package c_elab.pat.mant15;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/**
 * 
 *
 * Recupera i dati utilizzati dalle classi di trasformazione
 */
public abstract class QFigli extends ElainNode {
    
	//CAMBIAMI: va cambiata ogni anno
	String minore="14621";

	
    public double getValue() {
        return 0.0;
    }
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QFigli.class.getName(), IDdomanda, "records" );
    }
    
    public QFigli(){
    }
    
    /**
     * @param RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QFigli.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            
            //sql.append("SELECT count(Soggetti.id_soggetto) as totale_figli ");
            sql.append("SELECT Soggetti.data_nascita ");
    		sql.append("FROM Familiari ");
    		sql.append("INNER JOIN R_Relazioni_parentela  ");
    		sql.append("ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela  ");
    		sql.append("INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
    		sql.append("INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto ");
    		sql.append("WHERE (Familiari.ID_domanda =  ");
    		sql.append(IDdomanda);
    		sql.append(") AND (R_Relazioni_parentela.ID_relazione_parentela = "+minore+")");
    		
    		doQuery(sql.toString());
            
    		session.setInitialized(true);
			session.setRecords( records );
        } else {
			records = session.getRecords();
        }
    }
}