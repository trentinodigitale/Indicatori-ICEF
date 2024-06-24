/**
 *Created on 4-giu-2007
 */
package c_elab.pat.uni09;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i dati del corso di laurea dello studente
 *
 * @author a_t_termite
 */
public abstract class QCorso extends ElainNode {
    
	//CAMBIAMI: va cambiata ogni anno
	String servizio = "1050";
     
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QCorso.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		    super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                            1                              2                                           3                                           4                          5                                   6                           7                       8                   9
            sql.append(
            "SELECT     UNI_R_Corsi_Laurea_2.aggiuntivo, UNI_R_Corsi_Laurea_2.aggiuntivo_alloggio, UNI_R_Corsi_Laurea_2.aggiuntivo_invalidi, UNI_R_Corsi_Laurea_2.con_merito, UNI_R_Corsi_Laurea_2.corso_esterno, UNI_R_Corsi_Laurea_2.durata, UNI_R_Merito_2.massimo, UNI_R_Merito_2.minimo, UNI_R_Merito_2.min_premio");
            sql.append(" FROM         UNI_R_Corsi_Laurea_2 INNER JOIN");
            sql.append("   UNI_Dati ON UNI_R_Corsi_Laurea_2.tp_corso = UNI_Dati.valore INNER JOIN");
            sql.append("   UNI_R_Merito_2 ON UNI_R_Corsi_Laurea_2.tp_corso = UNI_R_Merito_2.tp_corso AND UNI_R_Corsi_Laurea_2.ID_anno_accademico = UNI_R_Merito_2.ID_anno_accademico AND UNI_R_Corsi_Laurea_2.ID_servizio = UNI_R_Merito_2.ID_servizio INNER JOIN");
            sql.append("   UNI_Dati UNI_Dati_Imm ON UNI_R_Merito_2.immatricolazione = UNI_Dati_Imm.valore");
            sql.append(" WHERE UNI_R_Corsi_Laurea_2.ID_servizio="+servizio+" AND UNI_Dati.parametro = 'TIPO_CORSO' AND UNI_Dati_Imm.parametro = 'ANNO_PRIMA_IMM' AND UNI_Dati.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(" AND UNI_Dati_Imm.ID_domanda = ");
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
    	ElabStaticContext.getInstance().resetSession( QCorso.class.getName(), IDdomanda, "records" );

    }
    
    /** QCorso constructor */
    public QCorso(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}

