package c_elab.pat.ic;


/*****************************************************
 * classe Ricovero
 * al momento sempre a 0 in quanto la rete calcola inporti annuali ma il ricovero è da calcolare a mesi
 * viene quindi controllato a posteriori sulla servlet di calcolo importi mensili it.clesius.evolservlet.icef.ic.IcElabCaller
 ******************************************************/

public class Ricovero extends QDati {
	
    public double getValue() {
        double val = 0.0;
//        boolean ricovero = false;
        
        try {
//            if(records.getElement(1,records.getIndexOfColumnName("ricovero"))!= null){
//            	ricovero = records.getBoolean(1, records.getIndexOfColumnName("ricovero") );
//            }
//            
//            if(ricovero){
//            	val = 1.0;
//            }
            
            return val;
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } 
        
    }
    
    public Ricovero(){		
    }
}
