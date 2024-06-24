package c_elab.pat.maternitaIsee;

/*****************************************************
                classe Check
		idoneo assegno maternità per termini domanda
******************************************************/


public class Altra_pensione_INPS extends QDati {
    public double getValue() {
        double deduzioni = 0.0;
        
        try {
        	if(dati.getElement(1, dati.getIndexOfColumnName("deduzioni"))!=null){
        		deduzioni = dati.getDouble(1, dati.getIndexOfColumnName("deduzioni"));
        	}
        	
        	return deduzioni;
        	
        } catch(Exception n) {
        	System.out.println("Exception in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    public Altra_pensione_INPS(){		//{{INIT_CONTROLS
		//}}
    }
}
