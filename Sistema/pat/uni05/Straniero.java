/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

/** legge se lo studente è da considerare straniero, 
 * ovvero se non è cittadino italiano e la famiglia di origine risiede all'estero
 *
 * @author g_barbieri
 */
public class Straniero extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
    	int italiano = 1;
        try {
        	if (servizio == 1010) // nuova domanda 				NB modificare ogni anno!!!!!!!
        		italiano = Math.abs(new Integer((String)(records.getElement(1,13))).intValue());
        	else // riconferma
	        	if ( ((String)(records.getElement(1,23))).equals("Z000") )
	        		italiano = 1;
	        	else
	        		italiano = 0;
        	//   SE non è cittadino italiano AND nucleo familiare risiede all'estero (ID_tp_nucleo=2)
            if( italiano == 0 && new Integer((String)(records.getElement(1,22))).intValue() == 2 )
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
