package c_elab.pat.famNumeroseIsee;

/**
 * Importo mensile dell'assegno in EURO per l'assegno famiglie numerose.
 */
public class Mensile_assegno extends QDati {
	
	/**
	 * @param
	 */
    public double getValue() {
        try {
            String anno = (String)( records.getElement(1, records.getIndexOfColumnName("anno_fam_numerose")) );
            
            if ( anno.startsWith("2014") ) {
                return 141.02;
            } else if(anno.startsWith("2015")){
            	return 141.30;
            } else if(anno.startsWith("2016")){
            	return 141.30;
            } else if(anno.startsWith("2017")){
            	return 141.30;
            } else if(anno.startsWith("2018")) {
            	return 142.85;
            } else if(anno.startsWith("2019")) {
            	return 144.42;
            } else if(anno.startsWith("2020")) {
            	return 145.14;  
            } else if(anno.startsWith("2021")) {
            	return 145.14;   
            }else if (anno.startsWith("2022")) {
				return 147.90;
			}else {
            	return 145.14;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public Mensile_assegno(){
    }
}
