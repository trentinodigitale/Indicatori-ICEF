/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san05;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 *legge se è un corso di tipo A o di tipo B, se è un corso di tipo A Bmax deve essere incrementata di 500 euro
 * 
 *
 * @author a_pichler
 */
public class Tipo_corso extends QStudente {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
       try {
            //se 0 non frequenta regolarmente se -1 frequenza regolare il valore assoluto diventa 1
            String tipo = (String)(records.getElement(1,27));
            
            if (tipo.equals("A")) return 1;
            else return 0.0;
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        
        }
        
    }
    
    
}
