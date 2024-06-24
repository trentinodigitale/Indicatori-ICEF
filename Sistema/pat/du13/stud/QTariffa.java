package c_elab.pat.du13.stud;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.du.PassaValoriDu2013;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

/** 
 *
 * @author l_leonardi
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
            //							6							7						   8							9					 10
            sql.append(" STUD_R_tariffe.ICEF_lim, STUD_R_tariffe.importo_0, STUD_R_tariffe.ID_tp_tariffa, STUD_R_tariffe.per_1, STUD_R_tariffe.per_2, ");
            //							  11					12					  13					14
            sql.append(" STUD_R_tariffe.per_3, STUD_R_tariffe.per_4, STUD_R_tariffe.per_5, STUD_R_tariffe.per_6 ");
            sql.append(" FROM STUD_R_tariffe INNER JOIN ");
            sql.append(" Domande ON STUD_R_tariffe.ID_servizio = Domande.ID_servizio");
            sql.append(" WHERE (Domande.ID_domanda = "+IDdomanda);
            sql.append(") AND (STUD_R_tariffe.ID_luogo = '"+PassaValoriDu2013.getID_luogo(IDdomanda)+"'");
            sql.append(") AND (STUD_R_tariffe.ID_scuola = "+PassaValoriDu2013.getID_scuola(IDdomanda));
            sql.append(") AND (STUD_R_tariffe.ID_tp_scuola = "+PassaValoriDu2013.getID_tp_scuola(IDdomanda));
            sql.append(") AND (STUD_R_tariffe.ID_tp_beneficio = '"+PassaValoriDu2013.getID_tp_beneficio(IDdomanda)+"'");
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
