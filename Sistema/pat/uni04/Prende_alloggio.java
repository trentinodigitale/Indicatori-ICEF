/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

/** legge se lo studente fouri sede prende alloggio in sede
 *
 * @author g_barbieri
 */
public class Prende_alloggio extends QStudente {

    //ATTENZIONE!!!!
    //se true allora prende in considerazione il campo non prende alloggio in sede
    //se false allora prende in considerazione i campi relativi ad alloggio opera/privato
    public static boolean primaDopo=false;
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            int non_prende_alloggio_in_sede = java.lang.Math.abs(new Integer((String)(records.getElement(1,16))).intValue());
            int fuorisede_alloggio_privato = java.lang.Math.abs(new Integer((String)(records.getElement(1,20))).intValue());
            int fuorisede_alloggio_opera = java.lang.Math.abs(new Integer((String)(records.getElement(1,21))).intValue());
            // toglie la negazione: 1 diventa 0 e 0 diventa abs(-1) ovvero 1
            int prende_alloggio_in_sede = java.lang.Math.abs(non_prende_alloggio_in_sede - 1);
            if (primaDopo && prende_alloggio_in_sede==1) return 1;
            if ((!primaDopo) && (fuorisede_alloggio_privato==1 || fuorisede_alloggio_opera==1)) return 1;
            else return 0;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
