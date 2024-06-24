package c_elab.pat.vlav14;

import it.clesius.apps2core.ElainNode;

/** legge se la domanda è esclusa d’ufficio per irregolarità.
 *
 * @author v_pasqualino
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
        	//0 non è escluso, 1 è escluso
        	//String valore = (String)(records.getElement(7,2));
        	String valore = null;
        	if(valore!=null){
        		return java.lang.Math.abs(new Double(valore).doubleValue());
        	}else{
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
