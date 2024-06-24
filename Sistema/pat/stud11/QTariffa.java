package c_elab.pat.stud11;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.stud.PassaValoriSTUD2011;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

/** 
 *
 * @author a_pichler
 */
public abstract class QTariffa extends ElainNode {
    
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     *
     *
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QTariffa.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            
            //                         1                   				2						   3							4						5						
            sql.append("SELECT     STUD_R_tariffe.ICEF_inf, STUD_R_tariffe.ICEF_sup, STUD_R_tariffe.importo_min, STUD_R_tariffe.importo_max, STUD_R_tariffe.sca, ");
            //							6							7						   8
            sql.append(" STUD_R_tariffe.ICEF_lim, STUD_R_tariffe.importo_0, STUD_R_tariffe.ID_tp_tariffa ");
            sql.append(" FROM STUD_R_tariffe INNER JOIN ");
            sql.append(" Domande ON STUD_R_tariffe.ID_servizio = Domande.ID_servizio");
            sql.append(" WHERE (Domande.ID_domanda = "+IDdomanda);
            sql.append(") AND (STUD_R_tariffe.ID_luogo = '"+PassaValoriSTUD2011.getID_luogo(IDdomanda)+"'");
            sql.append(") AND (STUD_R_tariffe.ID_scuola = "+PassaValoriSTUD2011.getID_scuola(IDdomanda));
            sql.append(") AND (STUD_R_tariffe.ID_tp_scuola = "+PassaValoriSTUD2011.getID_tp_scuola(IDdomanda));
            sql.append(") AND (STUD_R_tariffe.ID_tp_beneficio = '"+PassaValoriSTUD2011.getID_tp_beneficio(IDdomanda)+"'");
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
    	ElabStaticContext.getInstance().resetSession(QTariffa.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QTariffa(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
