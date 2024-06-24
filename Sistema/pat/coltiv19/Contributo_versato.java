package c_elab.pat.coltiv19;

import java.math.BigDecimal;

public class Contributo_versato extends QDatiDomanda {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double importo = 0.0;
		
		try 
		{
			double importo_IVS = records.getDouble(1, 4);
			double importo_IVS_233_90 = records.getDouble(1, 5);
			double importo_IVS_160_75 = records.getDouble(1, 6);
			double importo_IVS_arretrati = records.getDouble(1, 7);
			double importo_IVS_233_90_arretrati = records.getDouble(1, 8);
			double importo_IVS_160_75_arretrati = records.getDouble(1, 9);
			
			importo = importo_IVS + importo_IVS_233_90 + importo_IVS_160_75 + importo_IVS_arretrati + importo_IVS_233_90_arretrati + importo_IVS_160_75_arretrati;
			
			BigDecimal bd = new BigDecimal(importo);
		    bd = bd.setScale(2,BigDecimal.ROUND_HALF_DOWN);
			importo = bd.doubleValue();
			
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		
		return importo;
	}
}