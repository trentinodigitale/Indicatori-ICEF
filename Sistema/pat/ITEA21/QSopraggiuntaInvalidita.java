package c_elab.pat.ITEA21;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import c_elab.pat.icef.util.ElabContext;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 * 
 * @author a_pichler
 *
 */
public abstract class QSopraggiuntaInvalidita extends ElainNode {
    //CAMBIAMI
	int idServizioVerifica = 13519; // per verifica requisiti
	protected Table datiEpuInv;
	
	/** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    	ElabContext.getInstance().resetSession(  QSopraggiuntaInvalidita.class.getName(), IDdomanda );
    	datiEpuInv = null;
     	
    }
    
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QSopraggiuntaInvalidita.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            if(servizio==idServizioVerifica)
    		{
    			sql.append("SELECT  EPU_inv_familiari.ID_domanda,EPU_inv.provvidenze_invalidi,tp_invalidita.coeff_invalidita2016, Familiari.spese_invalidita, EPU_inv.ID_domandaDaVerificare ");
    			sql.append("FROM            Domande INNER JOIN ");
    			sql.append("Familiari ON Domande.ID_domanda = Familiari.ID_domanda INNER JOIN ");
    			sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN ");
    			sql.append("EPU_inv_familiari ON Dich_icef.ID_soggetto = EPU_inv_familiari.ID_soggetto INNER JOIN ");
    			sql.append("EPU_inv ON EPU_inv.ID_domanda = EPU_inv_familiari.ID_domanda INNER JOIN ");
    			sql.append("Doc as doc1 ON domande.ID_domanda = Doc1.ID INNER JOIN ");
    			sql.append("Doc ON EPU_inv_familiari.ID_domanda = Doc.ID INNER JOIN ");
    			sql.append("R_Servizi ON Domande.ID_servizio = R_Servizi.ID_servizio INNER JOIN ");
    			sql.append("tp_invalidita ON EPU_inv_familiari.ID_tp_invalidita = tp_invalidita.ID_tp_invalidita ");
    			
    			//sql.append("WHERE (doc1.id_tp_stato > 3000) AND (Doc.crc IS NOT NULL) AND (Familiari.ID_tp_invalidita IS NULL OR  Familiari.ID_tp_invalidita = 0 OR  Familiari.ID_tp_invalidita = 7) AND ");
    			sql.append(" WHERE (Doc.ID_tp_stato>3000) AND ");
    			sql.append("(YEAR(EPU_inv_familiari.data_inv) = R_Servizi.anno_produzione_redditi_min + CASE WHEN month(EPU_inv_familiari.data_inv) = 12 THEN 3 ELSE 2 END OR ");
    			sql.append(" YEAR(EPU_inv_familiari.data_inv) = R_Servizi.anno_produzione_redditi_min + CASE WHEN month(EPU_inv_familiari.data_inv) = 12 THEN 2 ELSE 1 END)");
    			sql.append(" AND Domande.ID_domanda =");
    			sql.append(IDdomanda);
    			
    			doQuery(sql.toString());
    			datiEpuInv = records;
    			
	    			if(records!=null && records.getRows()>0){
	    				String idDomInv=records.getString(1, 1);
	    				try {
	    					dataTransfer.executeUpdate("UPDATE EPU_Dati set ID_domInvalido="+ idDomInv+" WHERE ID_domanda="+IDdomanda);
	    			} catch (Exception e) {
							e.printStackTrace();
							System.out.println("ERRORE UPDATE EPU_Dati");
					}
    				
    			}
    			session.setAttribute("datiEpuInv", datiEpuInv);
    		}
            
        	session.setInitialized(true);
			session.setRecords( records );
			
		}else {
			records = session.getRecords();
			datiEpuInv = (Table)session.getAttribute("datiEpuInv");
			
		}
		
		
		
    }
    
    
    
    /** QStudente constructor */
    public QSopraggiuntaInvalidita(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
