package c_elab.pat.coltivdir20;

import java.math.BigDecimal;

public class Contributo_calcolato_arretrato extends QDatiDomanda {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double importo = 0.0;
		
		try 
		{
			for (int i = 1; i <= 12; i++) {
				double imp = getImportoMensile(annoContributoPrecedente, i);
				importo += imp;
				System.out.println("Anno: "+annoContributoPrecedente+", mese: "+i+", importo: "+imp);
			}
			
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
