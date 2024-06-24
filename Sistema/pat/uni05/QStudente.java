/**
 *Created on 18-giu-2004
 */
package c_elab.pat.uni05;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati dello studente
 *
 * @author g_barbieri
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
            //                         1                            2                               3                                 4                            5                        6                              7                           8                             9                      10                       11                             12                             13 ID_tp_citt                 14                             15                              16                                    17                             18                            19                         20                              21                                 22                         23                                  24                                    25
            sql.append(
            "SELECT UNI_Studenti.merito_minimo, UNI_Studenti.ID_anno_accademico, UNI_Studenti.anno_prima_imm, UNI_Studenti.beneficiato_sospensione, UNI_Studenti.bonus, UNI_Studenti.domanda_alloggio, UNI_Studenti.domanda_borsa, UNI_Studenti.domanda_esenzione, UNI_Studenti.ID_facolta, UNI_Studenti.crediti, UNI_Studenti.merito_ridotto, UNI_Studenti.annualita_sostenute, UNI_Studenti.citt_italiana, UNI_Studenti.ID_tp_progetto, UNI_Studenti.percent_invalido, UNI_Studenti.non_prende_alloggio_in_s, UNI_Studenti.importo_borsa_prec, UNI_Studenti.num_parenti_uni, UNI_Studenti.icef_anno_prec, UNI_Studenti.fuorisede_a_privato, UNI_Studenti.fuorisede_a_opera, UNI_Studenti.ID_tp_nucleo, UNI_Studenti.ID_tp_cittadinanza, UNI_Studenti.forza_borsa_fuori_sede, UNI_Studenti.forza_borsa_in_sede ");
            sql.append("FROM UNI_Studenti ");
            sql.append("WHERE UNI_Studenti.ID_domanda = ");
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
