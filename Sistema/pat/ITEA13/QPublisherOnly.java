package c_elab.pat.ITEA13;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i dati della tabella UNI_Dati
 *
 * @author d_miorandi
 */
public abstract class QPublisherOnly extends ElainNode {
    
     
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QPublisherOnly.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
            StringBuffer sql = new StringBuffer();
            //
            sql.append("SELECT ");        
    		sql.append("	Domande.ID_domanda, "); 
    		sql.append("	CASE tp_cittadinanza.tipo WHEN 'UE' THEN 1 ELSE 0 END AS 'CittadinanzaUE', ");
    		sql.append("	CASE Domande.ID_provincia_residenza WHEN 'TN' THEN 1 ELSE 0 END AS 'ResidenzaTN', ");
    		sql.append("	coalesce(EPU_Dati.id_tp_presenza_nucleo, 0) AS 'UscitaAssegnatarioDalNucleo', ");
    		sql.append(" 	CASE coalesce(EPU_Dati.invalido_75, 0) WHEN 0 THEN 0 ELSE 1 END AS 'PresenzaInvalidoMalato', ");
    		sql.append("	CASE coalesce(EPU_Dati.no_sublocazione, 0) WHEN 0 THEN 0 ELSE 1 END AS 'AlloggioNonCedutoInSublocazione' ");
    		sql.append("FROM ");
    		sql.append("	Domande INNER JOIN ");
    		sql.append(" 	EPU_Dati ON Domande.ID_domanda = EPU_Dati.ID_domanda INNER JOIN ");
    		sql.append("	tp_cittadinanza ON Domande.ID_tp_cittadinanza = tp_cittadinanza.ID_tp_cittadinanza ");
            sql.append(" WHERE Domande.ID_domanda = ");
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
    	ElabStaticContext.getInstance().resetSession( QPublisherOnly.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QPublisherOnly(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
