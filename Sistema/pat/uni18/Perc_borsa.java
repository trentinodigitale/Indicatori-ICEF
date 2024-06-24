package c_elab.pat.uni18;

/** Percentuale di borsa di studio di competenza.
 */
public class Perc_borsa extends QStudente {

	//se non ho fatto una scelta cioè mi arriva da UNI_dati = -1 forzo a 1 alla rete
	
	public double getValue() {
        try {
        	String valore = (String)(uni_dati.getElement(3,2));
        	if(valore != null){
        		double val = new Double(valore).doubleValue();
        		if(val >= 0){
        			return val;
        		}else{
        			return 1.0;
        		}
        	}else{
        		//se il valore non è presente (in UNI_dati = -1) passo 0 di default
        		return 1.0;
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
