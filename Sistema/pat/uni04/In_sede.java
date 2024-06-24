/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

/** legge se lo studente idoneo alla borsa può prendere solo la borsa in sede (es. ERASMUS)
 *
 * @author g_barbieri
 */
public class In_sede extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            int tipo_progetto = new Integer((String)(records.getElement(1,14))).intValue();
            if ( tipo_progetto == 2 )
                return 1.0;
            else
                return 0.0;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
