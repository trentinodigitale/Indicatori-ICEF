package c_elab.pat.vittime14;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i dati della tabella UNI_Dati
 *
 * @author s_largher
 */
public abstract class QVittime extends ElainNode {
    
     
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QVittime.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT  VDONNE_Dati.residenza_tn, VDONNE_Dati.no_gratuito_patrocinio, ");
			sql.append("VDONNE_Dati.spese_procedimento_penale, VDONNE_Dati.spese_procedimento_civile,  ");
			sql.append("VDONNE_Dati.ammonimento, VDONNE_Dati.procedimento_penale, ");
			sql.append("VDONNE_Dati.spesa, Soggetti.ID_tp_sex ");
			sql.append("FROM Domande ");
			sql.append("INNER JOIN VDONNE_dati ON Domande.ID_domanda = VDONNE_dati.ID_domanda ");
			sql.append("INNER JOIN Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto ");
			sql.append(" WHERE Domande.ID_domanda = "+IDdomanda+";");

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
    	ElabStaticContext.getInstance().resetSession( QVittime.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QVittime(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
