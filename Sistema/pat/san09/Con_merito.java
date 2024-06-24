package c_elab.pat.san09;

/** legge se il corso è con valutazione del merito
 *
 * @author g_barbieri
 */
public class Con_merito extends QCorso {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
        try {
            return new Double((String)(records.getElement(1,4))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
}
