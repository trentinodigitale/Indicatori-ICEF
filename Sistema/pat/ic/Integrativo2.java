package c_elab.pat.ic;


/*****************************************************
 * classe Indennita1
 * ricava a partire dall'anno, il default Indennita1 da passare alla rete;
 * legge da IC_C_DefaultIn
 ******************************************************/

public class Integrativo2 extends QDati {
	
    public double getValue() {
        double val = 0.0;
        
        try {
            if(records.getElement(1,records.getIndexOfColumnName("Integrativo2"))!= null){
            	val = records.getDouble(1, records.getIndexOfColumnName("Integrativo2") );
            }
            
            return val;
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } 
    }
    
    public Integrativo2(){		
    }
}
