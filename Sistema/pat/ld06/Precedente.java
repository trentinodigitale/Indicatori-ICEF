/**
 *Created on 23-mag-2006 
 */

package c_elab.pat.ld06;

/** legge quanti mesi/settimane sono gia stati pagati con le domande precedenti
 *
 * @author g_barbieri
 */
public class Precedente extends QPrecedenti {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return settimane;
    }
}
