package c_elab.pat.ic;


/*****************************************************
 * classe Retta_carico_pubblico
 * al momento sempre a 0 in quanto la rete calcola inporti annuali ma la retta a totale carico di ente pubblico è da calcolare a mesi
 * viene quindi controllato a posteriori sulla servlet di calcolo importi mensili it.clesius.evolservlet.icef.ic.IcElabCaller
 ******************************************************/

public class Retta_carico_pubblico extends QDati {
	
    public double getValue() {
        double val = 0.0;
        
        try {
//            if(records.getElement(1,records.getIndexOfColumnName("reddito"))!= null){
//            	val = records.getDouble(1, records.getIndexOfColumnName("reddito") );
//            }
            
            return val;
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } 
    }
    
    public Retta_carico_pubblico(){		
    }
}
