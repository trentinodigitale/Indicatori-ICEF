package c_elab.pat.uni19.icefParitario19;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i dati della tabella UNI_Dati
 *
 * @author s_largher
 */
public abstract class QStudente extends ElainNode {
    
     
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see ElainNode#init(RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT  ID_domanda, redditi, imposte, PI, PM, n_componenti ");
            sql.append(" FROM   UNI_Icef_parificato");
            sql.append(" WHERE  UNI_Icef_parificato.ID_domanda = ");
            sql.append(IDdomanda);

            doQuery(sql.toString());
            
            session.setInitialized(true);
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}
    }
    
    /** resetta le variabili statiche
     * @see ElainNode#reset()
     */
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QStudente.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QStudente(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
