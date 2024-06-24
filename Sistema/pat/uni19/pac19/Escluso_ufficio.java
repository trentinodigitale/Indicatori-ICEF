package c_elab.pat.uni19.pac19;

import c_elab.pat.uni19.QStudente;

/** legge se la domanda è esclusa d’ufficio per irregolarità.
 */
public class Escluso_ufficio extends QStudente {
    
	//se non ho fatto una scelta cioè mi arriva da UNI_dati = -1 forzo a 0
	
    public double getValue() {
        try {
        	//0 non è escluso, 1 è escluso
        	String valore = (String)(uni_dati.getElement(4,2));
        	if(valore!=null){
        		double val = new Double(valore).doubleValue();
        		if(val >= 0){
        			return val;
        		}else{
        			return 0.0;
        		}
        	}else{
        		//se il valore non è presente (in UNI_dati = -1) passo 0 di default
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
