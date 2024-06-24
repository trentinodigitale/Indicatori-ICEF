/**
*Created on 23-mag-2006
*/

package c_elab.pat.ld07;

/** legge il n. di mesi/settimane già pagate nei trimestri precedenti
 *
 * @author g_barbieri
 */
public class T_precedente extends QPrecedenti {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
    public double getValue() {
        return t_consumato; 
    }
}
