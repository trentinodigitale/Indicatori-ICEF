/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san06;

/** legge se lo studente fouri sede prende alloggio in sede
 *
 * @author g_barbieri
 */
public class Prende_alloggio extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            //se 0 pendolare se -1 alloggia in sede prendendo il valore assoluto diventa 1
            int fuori_sede = java.lang.Math.abs(new Integer((String)(records.getElement(1,24))).intValue());
            
            // toglie la negazione: 1 diventa 0 e 0 diventa abs(-1) ovvero 1
            int prende_alloggio_in_sede = java.lang.Math.abs(fuori_sede - 1);
            if (prende_alloggio_in_sede==0) return 1;
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
