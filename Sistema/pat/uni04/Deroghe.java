/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

/** legge se lo studente del vecchio ordinamento ha una deroga per militare, maternità o lavoro
 *
 * @author g_barbieri
 */
public class Deroghe extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return java.lang.Math.abs(new Double((String)(records.getElement(1,11))).doubleValue());
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
