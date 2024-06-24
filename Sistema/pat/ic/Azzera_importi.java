package c_elab.pat.ic;


/*****************************************************
 * classe Azzera_importi
 * se settato a 1 dalla servlet IcElabCaller allora questo nodo azzera i 4 benefici calcolati dalla rete;
 * legge da IC_dati
 ******************************************************/

public class Azzera_importi extends QDati {
	
    public double getValue() {
        double val = 0.0;
        double dinieghi = 0.0;
        
        try {
            if(records.getElement(1,records.getIndexOfColumnName("elab_azzera_importi"))!= null){
            	val = records.getDouble(1, records.getIndexOfColumnName("elab_azzera_importi") );
            }
			dinieghi = super.getDinieghi();
			if(dinieghi>0){
				val = 1.0;
			}
            
            return val;
            
        } catch(Exception n) {
            System.out.println("Exception in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } 
    }
    
    public Azzera_importi(){		
    }
}
