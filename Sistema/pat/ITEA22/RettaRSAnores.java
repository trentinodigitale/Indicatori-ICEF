package c_elab.pat.ITEA22; 

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** 
 *
 * @author g_barbieri
 */
public  class RettaRSAnores extends ElainNode {
    
     
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( RettaRSAnores.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
            StringBuffer sql = new StringBuffer();
            //								1						2						  3
/*
            CAMBIATO IMPOSTAZIONE: il coniuge non residente non va caricato e si detrae direttamente l'importo caricato 
            sql.append("SELECT EPU_Dati.ID_domanda, EPU_Dati.rettaRSA_nores, Redditi_altri.importo ");        
    		sql.append("	FROM EPU_Dati INNER JOIN Familiari ON EPU_Dati.ID_domanda = Familiari.ID_domanda "); 
    		sql.append("	INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
    		sql.append("	INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
    		sql.append("	INNER JOIN Redditi_altri ON Dich_icef.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
            sql.append(" WHERE EPU_Dati.ID_domanda = ");
            sql.append(IDdomanda); 
            sql.append(" AND R_Relazioni_parentela.ID_tp_relazione_parentela=12 AND (Redditi_altri.ID_tp_erogazione='060' Or Redditi_altri.ID_tp_erogazione='061')");
*/
            sql.append("SELECT EPU_Dati.ID_domanda, EPU_Dati.rettaRSA_nores ");        
    		sql.append("	FROM EPU_Dati "); 
            sql.append(" WHERE EPU_Dati.ID_domanda = ");
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
    	ElabStaticContext.getInstance().resetSession( RettaRSAnores.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public RettaRSAnores(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {

		if (records == null)
			return 0.0;

		double retta = 0.0;
		double provvidenzeIC = 0.0;

		try {
			//for (int row = 1; row <= records.getRows(); row++) {
			//	provvidenzeIC +=  records.getDouble(row,3);
			//}
			retta +=  records.getDouble(1,2);
			
			return java.lang.Math.max(0.0, retta-provvidenzeIC);
			
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
    }
}
