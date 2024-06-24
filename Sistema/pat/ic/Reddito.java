package c_elab.pat.ic;


/*****************************************************
 * classe Reddito
 * calcola a partire dal reddito totale e i redditi conteggiati al 50% i restanti redditi da conteggiare al 100%
 ******************************************************/

public class Reddito extends QDati {
	
    public double getValue() {
        double val = 0.0;
        
        try {
            if(records.getElement(1,records.getIndexOfColumnName("reddito"))!= null){
            	val = records.getDouble(1, records.getIndexOfColumnName("reddito") );
            }
            
            return val;
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } 
    }
    
    public Reddito(){		
    }
}
