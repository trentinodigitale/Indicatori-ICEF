package c_elab.pat.scuola18;
 
/*****************************************************
 * classe Precedente
 ******************************************************/

public class Precedente extends QStudente {
    
    public double getValue() {
        try {
        	return 0;
            // TODO return new Double((String)(records.getElement(1,2))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public Precedente(){
    }
}
