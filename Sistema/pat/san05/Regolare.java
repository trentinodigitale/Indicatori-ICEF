/**
 *Created on 18-giu-2004
 */

package c_elab.pat.san05;

/** legge se lo studente frequenta regolarmente tutti i corsi 
 * Requisito di merito per studenti iscritti ad anni successivi al primo
 *
 * @author g_barbieri
 */
public class Regolare extends QStudente {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
        try {
            //se 0 non frequenta regolarmente se -1 frequenza regolare il valore assoluto diventa 1
            int frequenza_corsi = java.lang.Math.abs(new Integer((String)(records.getElement(1,26))).intValue());
            if (frequenza_corsi==1) return 1;
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
