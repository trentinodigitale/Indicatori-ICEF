/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san05;

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
        "SELECT SAN_R_Comuni_in_sede.ID_comune ");
        sql.append("FROM Familiari INNER JOIN ");
        sql.append(
        "Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN ");
        sql.append(
        "R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela INNER JOIN ");
        sql.append(
        "SAN_Studenti INNER JOIN ");
        sql.append(
        "SAN_R_Comuni_in_sede ON SAN_Studenti.ID_anno_accademico = SAN_R_Comuni_in_sede.ID_anno_accademico AND ");
        sql.append(
        "SAN_Studenti.ID_sede_corso = SAN_R_Comuni_in_sede.ID_sede_corso ON Familiari.ID_domanda = SAN_Studenti.ID_domanda AND  ");
        sql.append(
        "Dich_icef.ID_comune_residenza = SAN_R_Comuni_in_sede.ID_comune ");
        sql.append(" WHERE (R_Relazioni_parentela.ruolo = 1) AND (Familiari.ID_domanda ='");
        sql.append(IDdomanda);
        sql.append("');");
        
        doQuery(sql.toString());

    }
    
    /** Comune_in_sede constructor */
    public Comune_in_sede(){
    }
}
