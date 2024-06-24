/**
 *Created on 4-giu-2007
 */

package c_elab.pat.uni11;

/** legge l'ICEF dell'anno precedente
 *
 * @author a_t_termite
 */
public class Fascia_forzata extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	//valore == 0.0 corrisponde a nessuna forzatura
        	String valore = (String)(records.getElement(9,2));
        	if(valore!=null)
        	{
        		return new Double(valore).doubleValue();
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
