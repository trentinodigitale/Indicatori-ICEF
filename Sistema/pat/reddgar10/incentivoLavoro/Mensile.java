package c_elab.pat.reddgar10.incentivoLavoro; 

import it.clesius.db.sql.RunawayData;
import it.clesius.apps2core.ElainNode;


public class Mensile extends ElainNode { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
	double importo_mensile=0.0;
	try 
		{
			importo_mensile = (new Double((String) records.getElement(1, 1)).doubleValue());
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
		
		return importo_mensile;
	}
	
	public void reset() {
	}
	
	 public void init(RunawayData dataTransfer) {
			
	       	super.init(dataTransfer);
	    		StringBuffer sql = new StringBuffer();
	    		sql.append("SELECT  C_ElaOUT.numeric_value ");
	    		sql.append(" FROM GR_Incentivo_lavoro INNER JOIN ");
	    		sql.append(" C_ElaOUT ON GR_Incentivo_lavoro.ID_domanda_collegata = C_ElaOUT.ID_domanda");
	    		sql.append(" WHERE     (C_ElaOUT.node = N'mensile') AND (C_ElaOUT.numeric_value > 0) AND (GR_Incentivo_lavoro.ID_domanda=  ");
	    		sql.append(IDdomanda);
	    		sql.append(" )");		
	    		
	    		doQuery(sql.toString());
	    		
	    		
	        	
	 }

	             
	        
	    
	   
	    
}