package c_elab.pat.famNumeroseIsee;

/**
 * Limite per il nucleo base in EURO dell'assegno famiglie numerose.
 **/
public class Soglia extends QDati {
    public double getValue() {
        try {
        	String anno = (String)( records.getElement(1, records.getIndexOfColumnName("anno_fam_numerose")) );
            
            if ( anno.startsWith("2014") ) {
                return 8538.91;
            } else if(anno.startsWith("2015")){
            	return 8555.99;
            } else if(anno.startsWith("2016")){
            	return 8555.99;
            } else if(anno.startsWith("2017")){
            	return 8555.99;
            } else if(anno.startsWith("2018")){
            	return 8650.11;  
            } else if(anno.startsWith("2019")){
            	return 8745.26;
            } else if(anno.startsWith("2020")){
            	return 8788.99;    
            } else if(anno.startsWith("2021")){
            	return 8788.99;  
            } else if(anno.startsWith("2022")) {
            	return 8955.98;
            }else {
            	return 8788.99;  
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    /**
     * 
     */
    public Soglia(){
    }
}
