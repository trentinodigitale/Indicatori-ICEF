package c_elab.pat.maternitaIsee;

import java.util.Calendar;

/**
 * Limite per il nucleo base in EURO dell'assegno maternità.
 **/
public class Soglia extends QDati {
    public double getValue() {
        try {
        	
            Calendar cal_figlio = getData();
            
            Integer anno_int = ( cal_figlio.get(Calendar.YEAR) );
            String anno = anno_int.toString();
            
            if ( anno.startsWith("2014") ) {
                return 16921.11;
            } else if ( anno.startsWith("2015") ){ 
            	return 16954.95;
            } else if ( anno.startsWith("2016") ){ 
            	return 16954.95;
            } else if ( anno.startsWith("2017") ){ 
            	return 16954.95;
            } else if ( anno.startsWith("2018") ){
            	return 17141.45; 
            } else if ( anno.startsWith("2019") ){
            	return 17330.01;
            } else if ( anno.startsWith("2020") ){
            	return 17416.66;  
            } else if ( anno.startsWith("2021") ){
            	return 17416.66;  
            } else if ( anno.startsWith("2022") ){
            	return 17747.58;
            } else if (anno.startsWith("2023")) {
				return 19185.13;
			} else {
            	return 19185.13; 
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
