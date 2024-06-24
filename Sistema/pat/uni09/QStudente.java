/**
 *Created on 4-giu-2007
 */
package c_elab.pat.uni09;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i dati della tabella UNI_Dati
 *
 * @author a_t_termite
 */
public abstract class QStudente extends ElainNode {
    
     
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
            StringBuffer sql = new StringBuffer();
            //                         1                            2
            sql.append("SELECT     UNI_Dati_R_Servizi.parametro, UNI_Dati.valore");
            sql.append("    FROM         Domande INNER JOIN");
            sql.append("          UNI_Dati_R_Servizi ON Domande.ID_servizio = UNI_Dati_R_Servizi.ID_servizio LEFT OUTER JOIN");
            sql.append("          UNI_Dati ON Domande.ID_domanda = UNI_Dati.ID_domanda AND UNI_Dati_R_Servizi.posizione = UNI_Dati.pos AND UNI_Dati_R_Servizi.parametro = UNI_Dati.parametro");
            sql.append(" WHERE     Domande.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(" ORDER BY UNI_Dati_R_Servizi.posizione ");

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
