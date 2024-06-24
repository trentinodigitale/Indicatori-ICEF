/**
 *Created on 4-giu-2007
 */

package c_elab.pat.uni09;

/** legge gli anni di iscrizione al ciclo di studi
 *  (al netto del periodo di sospensione in caso di nuovo ordinamento)
 *
 * @author a_t_termite
 */
public class Anni extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return new Double((String)(records.getElement(3,2))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
