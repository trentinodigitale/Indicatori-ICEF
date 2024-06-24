package c_elab.pat.san10;

/** legge se il funzionario OU decide di assegnare almeno il merito minimo
 *
 * @author a_t_termite
 */
public class Al_minimo extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            return java.lang.Math.abs(new Double((String)(records.getElement(1,2))).doubleValue());
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
