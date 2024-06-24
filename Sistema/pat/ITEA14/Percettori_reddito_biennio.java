package c_elab.pat.ITEA14;

import it.clesius.db.util.Table;

public class Percettori_reddito_biennio extends QItea {

		
	public double getValue() {
		
		int count = 0;
		//ciclo per ogni componente dell'anno prec e verifico se reddito_irpef > 4000
		count = count( getIrpef_precedente(), "reddito_irpef" );
		count = count + count( getIrpef_corrente(), "reddito_irpef" );
		
		return count;
	}
	
	public int count( Table table, String column ){
		
		String value = null;
		int count = 0;
		//ciclo per ogni componente dell'anno prec e verifico se reddito_irpef > 4000
		for ( int i = 1; i <= table.getRows(); i++ ){
			
			value = getElement( table, column , i );
			if ( value != null ){
				if ( Double.parseDouble( value ) > 4000 ){
					count++;
				}
			}
			
		}
		return count;
	} 
	
	

}
