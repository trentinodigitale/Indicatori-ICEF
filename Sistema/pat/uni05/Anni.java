/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

/** legge gli anni di iscrizione al ciclo di studi
 *  (al netto del periodo di sospensione in caso di nuovo ordinamento)
 *
 * @author g_barbieri
 */
public class Anni extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
            int anno_accademico_della_domanda = 2000 + new Integer((String)(records.getElement(1,2))).intValue();
            int anno_prima_imm = new Integer((String)(records.getElement(1,3))).intValue();
            int anno_sospensione = java.lang.Math.abs(new Integer((String)(records.getElement(1,4))).intValue());
            // es 1 + 2004 - 2002 - 1 = 2
            return 1 + anno_accademico_della_domanda - anno_prima_imm - anno_sospensione;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
