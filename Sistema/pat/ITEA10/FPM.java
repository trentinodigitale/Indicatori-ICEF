/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA10; 

/** 
 *@author a_cavalieri 
 */

public class FPM extends QInvalido_75 { 
	
	//CAMBIAMI: va cambiata ogni anno
	int idServizioAccesso = 13025;
	double FPM_accesso = 35000;
	double FPM_invalido75_accesso = 70000;
	
	int idServizioVerificaRequisiti = 13225;
	double FPM_verificaRequisiti = 40000;
	double FPM_invalido75_verificaRequisiti = 80000;
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			
			double ret = 0.0;
			
			boolean isInvalido75 = records.getBoolean(1,1);
			
			if (!isInvalido75)
			{
				if(servizio==idServizioAccesso)
				{
					return FPM_accesso;
				}
				else if(servizio==idServizioVerificaRequisiti)
				{
					return FPM_verificaRequisiti;	
				}
			}
			else
			{
				if(servizio==idServizioAccesso)
				{
					return FPM_invalido75_accesso;
				}
				else if(servizio==idServizioVerificaRequisiti)
				{
					return FPM_invalido75_verificaRequisiti;	
				}
			}
			return ret;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}