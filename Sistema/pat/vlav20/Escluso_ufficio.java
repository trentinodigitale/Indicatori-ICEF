package c_elab.pat.vlav20;

import it.clesius.apps2core.ElainNode;

/** legge se la domanda è esclusa d’ufficio per irregolarità.
 *
 */
public class Escluso_ufficio extends ElainNode {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     * Il nodo è presente nella rete, ma non si hanno indicazioni da dove andare a prendere l'informazione 
     * servirà in futuro
     */
	
	 protected void reset() {
	 }
	 
    public double getValue() {
        try {

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
