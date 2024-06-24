/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge l'importo dei contributi del corso di laurea dello studente
 *
 * @author g_barbieri
 */
public class Contributi extends ElainNode {
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            int anno_accademico_della_domanda = 2000 + new Integer((String)(records.getElement(1,1))).intValue();
            int anno_prima_imm = new Integer((String)(records.getElement(1,2))).intValue();
            int anno_sospensione = new Integer((String)(records.getElement(1,3))).intValue();
            // es 1 + 2004 - 2002 - 1 = 2
            int anno = 1 + anno_accademico_della_domanda - anno_prima_imm - anno_sospensione;
            if (anno == 1) 
                return new Double((String)(records.getElement(1,4))).doubleValue();
            else
                return new Double((String)(records.getElement(1,5))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
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
        //                            1                              2                               3                                     4                            5
        sql.append(
        "SELECT UNI_Studenti.ID_anno_accademico, UNI_Studenti.anno_prima_imm, UNI_Studenti.beneficiato_sospensione, UNI_R_Contributi.importo_matr, UNI_R_Contributi.importo_succ ");
        sql.append("FROM UNI_R_Corsi_laurea ");
        sql.append(
        "INNER JOIN UNI_Studenti ON (UNI_R_Corsi_laurea.ID_anno_accademico = UNI_Studenti.ID_anno_accademico AND UNI_R_Corsi_laurea.ID_corso_laurea = UNI_Studenti.ID_corso_laurea AND UNI_R_Corsi_laurea.ID_facolta = UNI_Studenti.ID_facolta AND UNI_R_Corsi_laurea.ID_universita = UNI_Studenti.ID_universita) ");
        sql.append(
        "INNER JOIN UNI_R_Facolta ON (UNI_R_Corsi_laurea.ID_anno_accademico = UNI_R_Facolta.ID_anno_accademico AND UNI_R_Corsi_laurea.ID_facolta = UNI_R_Facolta.ID_facolta AND UNI_R_Corsi_laurea.ID_universita = UNI_R_Facolta.ID_universita) ");
        sql.append(
        "INNER JOIN UNI_R_Contributi ON (UNI_R_Facolta.ID_anno_accademico = UNI_R_Contributi.ID_anno_accademico AND UNI_R_Facolta.ID_tp_facolta = UNI_R_Contributi.ID_tp_facolta AND UNI_R_Facolta.ID_universita = UNI_R_Contributi.ID_universita AND UNI_R_Corsi_laurea.ID_tp_ordinamento = UNI_R_Contributi.ID_tp_ordinamento) ");
        sql.append("WHERE UNI_Studenti.ID_domanda = ");
        sql.append(IDdomanda);
        
        doQuery(sql.toString());
        
        //System.out.println(sql.toString());
        
    }
    
    /** Contributi constructor */
    public Contributi(){
    }
}
