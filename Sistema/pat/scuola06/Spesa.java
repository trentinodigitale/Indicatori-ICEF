package c_elab.pat.scuola06;

/*****************************************************
 * classe Spesa
 *
 ******************************************************/

public class Spesa extends QStudente {
    
    public Spesa(){
    }
     
    public double getValue() {
    	
        double total = 0.0;
        
        try {        
        total +=  (new Double((String)(records.getElement(1,3))).doubleValue())
                + (new Double((String)(records.getElement(1,4))).doubleValue())
                + (new Double((String)(records.getElement(1,5))).doubleValue())
                + (new Double((String)(records.getElement(1,6))).doubleValue())
                + (new Double((String)(records.getElement(1,7))).doubleValue());
        
                return total;
                
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch(NumberFormatException nfe) {
            System.out.println("ERROR: Number format exception in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}