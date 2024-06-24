package c_elab.pat.reddgar17; 

import it.clesius.clesius.util.Sys;

/** 
 *@author l_leonardi 
 */

public class Abitazione extends QRedditoGaranzia { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double affitto = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		
		try 
		{
			double affittoMensileLordo = (new Double((String) datiRedditoGaranzia.getElement(1, 2)).doubleValue());
			double affittoMensileContributo = (new Double((String) datiRedditoGaranzia.getElement(1, 23)).doubleValue());
			double affittoMensile = 0;
			if (affittoMensileContributo > affittoMensileLordo){
				//passo affitto mensile a 0
			}else{
				affittoMensile = affittoMensileLordo - affittoMensileContributo;
			}
			double mutuoMensile = (new Double((String) datiRedditoGaranzia.getElement(1, 12)).doubleValue());
				
			affitto = Sys.round(((affittoMensile + mutuoMensile) * 12) - aggiusta, round);
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
		
		return affitto;
	}
}