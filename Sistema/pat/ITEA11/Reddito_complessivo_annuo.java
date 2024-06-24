package c_elab.pat.ITEA11;

import it.clesius.db.util.Table;

public class Reddito_complessivo_annuo extends QItea {

	
	public double getValue() {
		
		double reddito = 0;
		
		reddito = count( getIrpef_corrente(), "reddito_irpef" );
		
		return reddito;
	}
	
	public double count( Table table, String column ){
		
		String value = null;
		double count = 0;
		for ( int i = 1; i <= table.getRows(); i++ ){
			
			value = getElement( table, column , i );
			if ( value != null ){
				
				count = count + Double.parseDouble( value );
			
			}
			
		}
		return count;
	} 
	
}
