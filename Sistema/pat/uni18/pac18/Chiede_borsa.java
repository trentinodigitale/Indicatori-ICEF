package c_elab.pat.uni18.pac18;

import c_elab.pat.uni18.QStudente;

/** legge se lo studente ha chiesto la borsa
 *
 * @author s_largher
 */
public class Chiede_borsa extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	String valore = (String)(uni_dati.getElement(1,2));
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
