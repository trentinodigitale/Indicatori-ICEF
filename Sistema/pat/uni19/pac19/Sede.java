package c_elab.pat.uni19.pac19;

import c_elab.pat.uni19.QStudente;

/** legge la sede della residenza dello studente
 */
public class Sede extends QStudente  {
    
	//il pendolare è come in sede
	
	//se non ho fatto una scelta cioè mi arriva 0 oppure null che implica che un UNI_dati = -1 forzo a 1 -> in sede
	
    public double getValue() {
        
        try {
        	//1=in sede; 2=fuori sede; 3=pendolare
        	String valore = (String)(uni_dati.getElement(2,2));
        	if(valore!=null){
        		double val = new Double(valore).doubleValue();
        		if (val>3 || val<1){
        			return 1.0;
        		}else{
        			return val;
        		}
        	}else{
        		//se il valore non è presente passo 0 di default
        		return 1.0;
        	}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 1.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 1.0;
        }
    }
    
}
