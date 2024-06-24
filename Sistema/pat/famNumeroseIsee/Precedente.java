package c_elab.pat.famNumeroseIsee;

/**
 * Limite per il nucleo base in EURO dell'assegno famiglie numerose.
 **/
public class Precedente extends QDati {
    public double getValue() {
        try {
        	Integer isPrecedente 	= ( records.getInteger(1, records.getIndexOfColumnName("is_precedente")) );
            Double 	precedente  	= ( records.getDouble(1, records.getIndexOfColumnName("precedente")) ); 
            
            if(isPrecedente != 0){
            	return precedente.doubleValue();
            }else{
            	return 0.0;
            }
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    /**
     * 
     */
    public Precedente(){
    }
}
