package c_elab.pat.apapi;


/*****************************************************
 * classe Presentazione
 * copia il crc ricalcolato in QDati come beneficio
 ******************************************************/

public class Presentazione extends QDati {
	
	double crc = 0.0;
	
    public double getValue() {
    	
    	if(records.getElement(1, records.getIndexOfColumnName("crc"))!=null){
    		crc = records.getDouble(1, records.getIndexOfColumnName("crc"));
    	}
    	for(int i=1;i<=records.getRows(); i++){
    		crc = crc + records.getDouble(i, records.getIndexOfColumnName("crc_pag"));
    	}
    	
    	return arrotonda(crc*0.0001, 0);
    	
    }
    
    public Presentazione(){		
    }
    
    private static double arrotonda(double value, int numCifreDecimali) {
		double temp = Math.pow(10, numCifreDecimali);
		return Math.round(value * temp) / temp; 
	}
	
}
