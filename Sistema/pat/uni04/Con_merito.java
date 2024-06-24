/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

/** legge se esiste la facoltà è Dottorando o SISS
 *  per questi il merito non conta per cui è sempre 1
 *
 * @author g_barbieri
 */
public class Con_merito extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
        try {
            //verifico se è dottorato o un ssis
            if ( ((String)records.getElement(1,9)).equals("DOT") || ((String)records.getElement(1,9)).equals("SSIS")) {
                //per i dottorandi e i ssis il merito non ha rilevanza
                return 0.0;  //NB 0 significa che il merito non ha rilevanza e vale sempre 1
            } else {
                return 1.0;  //NB 1 significa che il merito ha rilevanza e vale l'interpolazione min-max
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
}
