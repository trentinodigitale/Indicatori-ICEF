package c_elab.pat.uni23;

/** 
 */
public class Precedente extends QStudente {
    
    public double getValue() {
        try {
            // Se non è connessa non considero il dato borsa prec
            if (isee_connesso==null || isee_connesso.getRows()==0) {
                return 0.0;
            }
        	String valore = (String)(uni_dati.getElement(12,2));
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
