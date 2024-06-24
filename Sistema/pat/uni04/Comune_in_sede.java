/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge se il comune di residenza dello studente è tra quelli in sede
 *
 * @author g_barbieri
 */
public class Comune_in_sede extends ElainNode {
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
        if (records == null)
            return 0.0;
        
        try {
            if (records.getRows() > 0) {
                return 1.0;
            } else {
                return 0.0;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        
        StringBuffer sql = new StringBuffer();
        //                               1
        sql.append(
        "SELECT UNI_R_Comuni_in_sede.ID_comune ");
        sql.append("FROM Domande ");
        sql.append(
        "INNER JOIN UNI_Studenti ON Domande.ID_domanda = UNI_Studenti.ID_domanda ");
        sql.append(
        "INNER JOIN UNI_R_Corsi_laurea ON (UNI_R_Corsi_laurea.ID_anno_accademico = UNI_Studenti.ID_anno_accademico AND UNI_R_Corsi_laurea.ID_corso_laurea = UNI_Studenti.ID_corso_laurea AND UNI_R_Corsi_laurea.ID_facolta = UNI_Studenti.ID_facolta AND UNI_R_Corsi_laurea.ID_universita = UNI_Studenti.ID_universita) ");
        sql.append(
        "INNER JOIN UNI_R_Sedi_corsi ON UNI_R_Sedi_corsi.ID_sede_corso = UNI_R_Corsi_laurea.ID_sede_corso ");
        sql.append(
        "INNER JOIN UNI_R_Comuni_in_sede ON (UNI_R_Sedi_corsi.ID_sede_corso = UNI_R_Comuni_in_sede.ID_sede_corso AND UNI_R_Sedi_corsi.ID_anno_accademico = UNI_R_Comuni_in_sede.ID_anno_accademico) ");
        sql.append(
        "AND UNI_R_Comuni_in_sede.ID_comune = Domande.ID_comune_residenza ");
        sql.append("WHERE Domande.ID_domanda = ");
        sql.append(IDdomanda);
        
        doQuery(sql.toString());

    }
    
    /** Comune_in_sede constructor */
    public Comune_in_sede(){
    }
}
