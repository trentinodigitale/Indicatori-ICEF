package c_elab.pat.prestOn08;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati della tabella UNI_Dati
 *	
 * @author a_pichler
 */
public abstract class QRichiedente extends ElainNode {
         
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     *
     *
     */
    public void init(RunawayData dataTransfer) {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QRichiedente.class.getName(), IDdomanda, "records" );

    	if (!session.isInitialized()) {
    		
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                         1                   																				     2		   3			4
            sql.append("SELECT     sp_sanitarie + sp_legali + sp_funerali + sp_alloggio + sp_scuola + sp_pensione + sp_neonati AS spese, gravidanza, canone, int_mutuo");
            sql.append("    FROM         PSO_richiedente");
            sql.append(" WHERE     PSO_richiedente.ID_domanda = ");
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
    	ElabStaticContext.getInstance().resetSession( QRichiedente.class.getName(), IDdomanda, "records" );
       
    }
    
    /** QStudente constructor */
    public QRichiedente(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
