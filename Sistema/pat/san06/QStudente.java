/**
 *Created on 18-giu-2004
 */
package c_elab.pat.san06;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati dello studente
 *
 * @author a_pichler
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
  //                            1                            2                          3                              4                                      5                       6                           7                               8                       9
        sql.append("SELECT SAN_Studenti.ID_corso, SAN_Studenti.ID_sede_corso, SAN_Studenti.residenza_tn_dal, SAN_Studenti.anno_prima_imm, SAN_Studenti.uni_prima_imm, SAN_Studenti.ID_tp_iscrizione, SAN_Studenti.ID_anno_accademico, SAN_Studenti.anno_corso, SAN_Studenti.matricola,");
  //                                    10                            11                        12                                      13                               14                           15                               16
        sql.append(" SAN_Studenti.importo_borsa_prec, SAN_Studenti.num_parenti_uni, SAN_Studenti.ID_provincia_domicilio, SAN_Studenti.ID_comune_domicilio, SAN_Studenti.cap_domicilio, SAN_Studenti.indirizzo_domicilio, SAN_Studenti.telefono_domicilio,");
  //                       17                             18                              19                                   20                        21                         22                        23                                     24
        sql.append(" SAN_Studenti.altra_sede, SAN_Studenti.laurea_triennale, SAN_Studenti.laurea_specialistica, SAN_Studenti.affitto_data, SAN_Studenti.affitto_num, SAN_Studenti.affitto_serie, SAN_Studenti.affitto_agenzia_entrate, SAN_Studenti.fuori_sede, ");
 //                                             25                  26                         27                       28                  29                  30                      31                      32                          33                      34                          35                      36
        sql.append(" SAN_Studenti.affitto_ente_convenzionato, SAN_Studenti.frequenza_corsi,  SAN_R_Corsi.tipo, SAN_Studenti.crediti, SAN_Studenti.bonus, SAN_Studenti.bonus_max, SAN_Studenti.erasmus, SAN_Studenti.ID_tp_nucleo, SAN_Studenti.PI_estero, SAN_Studenti.RES_estero, SAN_Studenti.citt_italiana, SAN_Studenti.sospensione_studi ");
        
        sql.append(" FROM SAN_Studenti INNER JOIN SAN_R_Corsi ON SAN_Studenti.ID_corso = SAN_R_Corsi.ID_corso AND SAN_Studenti.ID_anno_accademico = SAN_R_Corsi.ID_anno_accademico");
            sql.append(" WHERE SAN_Studenti.ID_domanda = ");
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
