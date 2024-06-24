package c_elab.pat.uni21;

/** legge il merito dello studente
 */
public class Merito extends QStudente {
    
    public double getValue() {
        try {
        	//0 non è idoneo per merito, 1 è idoneo per merito
        	String valore = (String)(uni_dati.getElement(5,2));
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
