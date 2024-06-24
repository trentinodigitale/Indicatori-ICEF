package c_elab.pat.sport11;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** 
 *
 * @author a_pichler
 */
public abstract class QStudente extends ElainNode {
    
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     *
     *
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                         1                   						2									   3								4							5							6								7										8						9						10							11
            sql.append("SELECT     SPORT_Studente.conv_naz_assoluta, SPORT_Studente.conv_naz_categoria, SPORT_Studente.ID_tp_classifica, SPORT_Studente.part_assoluta, SPORT_Studente.part_categoria, SPORT_tp_classifica.posizione, SPORT_Studente.residenzaTAA, SPORT_Studente.merito_scolastico, SPORT_Studente.tesserato, SPORT_tp_classifica.punti, SPORT_tp_disciplina.indice_frequenza ");
            sql.append(" FROM  Domande INNER JOIN SPORT_Studente ON Domande.ID_domanda = SPORT_Studente.ID_domanda  ");
            sql.append(" INNER JOIN SPORT_tp_classifica ON SPORT_Studente.ID_tp_classifica = SPORT_tp_classifica.ID_tp_classifica INNER JOIN");
            sql.append(" SPORT_tp_disciplina ON Domande.ID_servizio = SPORT_tp_disciplina.ID_servizio AND ");
            sql.append(" SPORT_Studente.ID_tp_disciplina = SPORT_tp_disciplina.ID_tp_disciplina");
            sql.append(" WHERE     (SPORT_Studente.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(")");
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
    	ElabStaticContext.getInstance().resetSession(QStudente.class.getName(), IDdomanda, "records" );
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
