/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni04;

/** legge se lo studente idoneo alla borsa può prendere solo la borsa fuori sede (stranieri, dottorato, doppia laurea)
 *
 * @author g_barbieri
 */
public class Fuori_sede extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            //cittadinanza
            int cittadinanza_italiana = java.lang.Math.abs(new Integer((String)(records.getElement(1,13))).intValue());
            //facoltà
            String facolta = (String)(records.getElement(1,9));
            //doppia laurea
            int tipo_progetto = new Integer((String)(records.getElement(1,14))).intValue();
            if ( cittadinanza_italiana == 0 || facolta.equals("DOT") || tipo_progetto == 3 )
                return 1.0;
            else
                return 0.0;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
