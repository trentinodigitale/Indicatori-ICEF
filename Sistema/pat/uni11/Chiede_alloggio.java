/**
 *Created on 4-giu-2007
 */

package c_elab.pat.uni11;

/** legge se lo studente ha chiesto l'alloggio
 *
 * @author a_t_termite
 */
public class Chiede_alloggio extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	String valore = (String)(records.getElement(1,2));
        	if(valore!=null)
        	{
        		return java.lang.Math.abs(new Double(valore).doubleValue());
        	}
        	else
        	{
        		//se il valore non è presente passo 0 di default
        		return 0.0;
        	}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
