/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA09; 

/** 
 *@author a_cavalieri 
 */

public class FPM_v extends QInvalido_75 { 
	
	//CAMBIAMI: va cambiata ogni anno
	int idServizioVerificaRequisiti = 13220;
	double FPM_verificaRequisiti = 40000;
	double FPM_invalido75_verificaRequisiti = 80000;
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			
			boolean isInvalido75 = records.getBoolean(1,1);
			
			if (!isInvalido75)
			{
				return FPM_verificaRequisiti;
			}
			else
			{
				return FPM_invalido75_verificaRequisiti;
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}