/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

/** legge se lo studente idoneo alla borsa può prendere solo la borsa fuori sede (dottorato, doppia laurea)
 * NB: a partire dal 2005 il caso dello straniero è gestito nella classe Straniero
 *
 * @author g_barbieri
 */
public class Fuori_sede extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            //cittadinanza - tolto nel 2005 (vedi commento alla classe)
            //int cittadinanza_italiana = java.lang.Math.abs(new Integer((String)(records.getElement(1,13))).intValue());
            //facoltà 
            String facolta = (String)(records.getElement(1,9));
            //doppia laurea
            int tipo_progetto = new Integer((String)(records.getElement(1,14))).intValue();
            if ( facolta.equals("DOT") || tipo_progetto == 3 )
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
