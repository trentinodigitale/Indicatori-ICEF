package c_elab.pat.scuola17;

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
        	
        	if( records.getElement(1,3) != null ) {
        		//Assegno di studio pubbliche 2015
        		
        		total +=  records.getDouble(1,3)
        		+ records.getDouble(1,4)
                + records.getDouble(1,5)
                + records.getDouble(1,6)
                + records.getDouble(1,7);
        	}
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