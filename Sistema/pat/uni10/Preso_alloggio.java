/**
 *Created on 4-giu-2007
 */

package c_elab.pat.uni10;

/** legge se lo studente fouri sede prende alloggio in sede
 *
 * @author a_t_termite
 */
public class Preso_alloggio extends QStudente {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	//0=sconosciuto, 1=Si, 2=No
            return new Double((String)(records.getElement(8,2))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
