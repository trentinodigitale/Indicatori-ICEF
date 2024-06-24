package c_elab.pat.uni19.pac19;

import c_elab.pat.uni19.QStudente;

/** 
 */
public class Precedente extends QStudente {
    
    public double getValue() {
        try {
        	String valore = (String)(uni_dati.getElement(8,2));
        	if(valore!=null){
        		return Math.abs(new Double(valore).doubleValue());
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
