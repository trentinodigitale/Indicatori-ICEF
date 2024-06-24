package c_elab.pat.ITEA08;

import it.clesius.db.util.Table;

import java.util.HashSet;

public class Pensionati_biennio extends QItea {

	public double getValue() {
		
		int count = 0;
		HashSet set = new HashSet();
		
		//ciclo per ogni componente dell'anno prec e verifico se ID_tp_reddito > 0
		
		count = count( getIcef_precedente(), "ID_tp_reddito", set );
		count = count + count( getIcef_corrente(), "ID_tp_reddito", set );
		
		return count;
	}
	
	public int count( Table table, String column, HashSet set ){
		
		//String value = null;
		int count = 0;
		String id_soggetto;
		String importo; 				
		String id_tp_reddito;
		
		//ciclo per ogni componente dell'anno prec e verifico se ID_tp_reddito > 4000
		for ( int i = 1; i <= table.getRows(); i++ ){
			
			id_tp_reddito = getElement( table, column , i );
			id_soggetto = getElement( table, "ID_soggetto" , i );
			importo = getElement( table, "importo" , i );
						
			if ( importo != null ){
				if ( Double.parseDouble( importo ) > 0 && 
						!set.contains( id_soggetto )  && 
						id_tp_reddito.equalsIgnoreCase("PNS")  ){
					set.add( id_soggetto );
					count++;					
				}
			}
			
		}
		return count;
	} 
	
	
	
	
	

}
