package c_elab.pat.ic;


/*****************************************************
 * classe Grado
 * transcode da ID_tp_invalidita a Grado per la rete
 * Convenzione: codice 07+09 diventa 79
 ******************************************************/

public class Grado extends QDati {
	
    public double getValue() {
        double val = 0.0;
        int ID_tp_invalidita = 0;
        
        try {
            if(records.getElement(1,records.getIndexOfColumnName("ID_tp_invalidita"))!= null){
            	ID_tp_invalidita = records.getInteger(1, records.getIndexOfColumnName("ID_tp_invalidita") );
            }
            
            switch (ID_tp_invalidita) {
			case 1:
				val = 3;
				break;
			case 2:
				val = 4;
				break;
			case 3:
				val = 5;
				break;
			case 4:
				val = 5;
				break;
			case 5:
				val = 5;
				break;
			case 6:
				val = 11;
				break;
			case 7:
				val = 9;
				break;
			case 8:
				val = 10;
				break;
			case 9:
				val = 7;
				break;
			case 10:
				val = 6;
				break;
			case 11:
				val = 6;
				break;
			case 12:
				val = 6;
				break;
			case 13:
				val = 79;
				break;
			case 14:
				val = 11;
				break;
			case 15:
				val = 11;
				break;
			case 16:
				val = 10;
				break;
			default:
				val = 0;
				break;
			}
            
            return val;
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } 
    }
    
    public Grado(){		
    }
}
