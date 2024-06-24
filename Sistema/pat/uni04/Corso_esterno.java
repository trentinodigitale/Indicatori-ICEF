/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge se corso di laurea dello studente è estreno all'Università (ISIT, ecc.)
 *
 * @author g_barbieri
 */
public class Corso_esterno extends ElainNode {
    
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
            return java.lang.Math.abs(new Double((String)(records.getElement(1,1))).doubleValue());
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
        //                      1
        sql.append(
        "SELECT UNI_R_Facolta.esterno ");
        sql.append("FROM UNI_R_Facolta ");
        sql.append(
        "INNER JOIN UNI_Studenti ON UNI_R_Facolta.ID_facolta = UNI_Studenti.ID_facolta ");
        sql.append("WHERE UNI_Studenti.ID_domanda = ");
        sql.append(IDdomanda);
        
        doQuery(sql.toString());

        //System.out.println(sql.toString());
        
    }
    
    /** Corso_esterno constructor */
    public Corso_esterno(){
    }
}
