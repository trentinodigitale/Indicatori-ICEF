/**
 *Created on 4-giu-2007
 */

package c_elab.pat.san12;

/** stabilisce la fase del calcolo: Fase=0 (solo icef e fascia tasse) Fase=1 (calcolo normale, con Merito. ecc)
 *
 * @author l_leonardi
 */
public class Fase extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	//utilizzo il valore Merito per verificare in che fase sono
        	String merito = (String)(records.getElement(3,2));
        	if(merito!=null)
        	{
        		//Fase=1 (calcolo normale, con Merito. ecc)
        		return 1.0;
        	}
        	else
        	{
        		//Fase=0 (solo icef)
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
