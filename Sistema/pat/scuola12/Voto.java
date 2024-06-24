package c_elab.pat.scuola12;
 
/*****************************************************
 * classe Voto
 ******************************************************/

public class Voto extends QStudente {
    
    public double getValue() {
        try {
        	if( records.getElement(1,2) != null ) {
        		//Assegno di studio pubbliche 2010
        		
        		return new Double((String)(records.getElement(1,2))).doubleValue();
        	}else return 0.0;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public Voto(){
    }
}
