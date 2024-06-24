package c_elab.pat.ITEA14_prov;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

public class Reddito_complessivo_annuo extends ElainNode {
	
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		//                                  1                   	  2						3
		sql.append("SELECT Familiari.id_dichiarazione, Dich_icef.ID_soggetto, Dich_icef.reddito_irpef "+
				"FROM Familiari "+
				"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda "+
				"inner join Dich_icef on Dich_icef.id_dichiarazione=Familiari.id_dichiarazione "+
				"WHERE Domande.id_domanda="+
				IDdomanda+
				" order by Familiari.id_dichiarazione");

		doQuery(sql.toString());
	}
	
	public double getValue() {
		
		double reddito = 0;
		
		reddito = count( records, "reddito_irpef" );
		
		return reddito;
	}
	
	public double count( Table table, String column ){
		
		String value = null;
		double count = 0;
		for ( int i = 1; i <= table.getRows(); i++ ){
			
			value = ( String )table.getElement(  i , 3 );
			if ( value != null ){
				
				count = count + Double.parseDouble( value );
			
			}
			
		}
		return count;
	} 
	
	protected void reset() {}
}
