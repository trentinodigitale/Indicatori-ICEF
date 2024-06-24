package c_elab.pat.uni19;

/** legge se lo studente ha chiesto l'alloggio
 */
public class Chiede_alloggio extends QStudente {
    
    public double getValue() {
        try {
        	String valore = (String)(uni_dati.getElement(1,2));
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
