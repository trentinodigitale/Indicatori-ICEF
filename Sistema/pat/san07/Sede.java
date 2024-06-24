package c_elab.pat.san07;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge la sede della residenza dello studente
 *
 * @author a_t_termite
 */
public class Sede extends QStudente  {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
        try {
            double val=new Double((String)(records.getElement(13,2))).doubleValue();
            if (val>3 || val<1) return 0.0;
            else return val;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
    
}
