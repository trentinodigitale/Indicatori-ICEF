/**
 *Created on 4-giu-2007
 */

package c_elab.pat.uni10;

/** legge il merito dello studente
 *
 * @author a_t_termite
 */
public class Merito extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	//0 non è idoneo per merito, 1 è idoneo per merito
            return java.lang.Math.abs(new Double((String)(records.getElement(5,2))).doubleValue());
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
