package c_elab.pat.apapi;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.trasmetti.GenerazioneEdizioneDocumento;

/**
 * 
 *
 * Recupera i dati utilizzati dalle classi di trasformazione
 */
public abstract class QDati extends ElainNode {
    
	
    public double getValue() {
        return 0.0;
    }
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QDati.class.getName(), IDdomanda, "records" );
    }
    
    public QDati(){
    }
    
    /**
     * @param RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDati.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
            super.init(dataTransfer);
            
            GenerazioneEdizioneDocumento ged = new GenerazioneEdizioneDocumento(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            
            sql.append(" SELECT "+ged.getCRC(IDdomanda)+" AS crc, modpag.crc as crc_pag ");
    		sql.append(" FROM Doc ");
    		sql.append(" inner join modpag on doc.id=modpag.id_beneficiario or doc.id=modpag.id_intestatario_pagamento ");
    		sql.append(" WHERE Doc.ID = "+IDdomanda+" and modpag.crc<>-1 ");
    		
    		doQuery(sql.toString());
            
    		session.setInitialized(true);
			session.setRecords( records );
        } else {
			records = session.getRecords();
        }
    }
}