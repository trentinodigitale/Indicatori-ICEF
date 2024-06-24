/** 
 *Created on 28-giu-2007 
 */

package c_elab.pat.ITEA11_prov; 

/** 
 *@author s_largher 
 */

public class FPM extends QProvvisorio { 
	
	//CAMBIAMI: va cambiata ogni anno
	int idServizioAccessoProvvisorio = 13305;
	double FPM_accessoProvvisorio = 35000;
	double FPM_invalido75_accessoProvvisorio = 70000;
	
	int idServizioVerificaRequisitiProvvisorio = 13304;
	double FPM_verificaRequisitiProvvisorio = 40000;
	double FPM_invalido75_verificaRequisitiProvvisorio = 80000;
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			
			double ret = 0.0;
			
			boolean isInvalido75 = records.getBoolean(1,5);
			
			if (!isInvalido75)
			{
				if(servizio==idServizioAccessoProvvisorio)
				{
					return FPM_accessoProvvisorio;
				}
				else if(servizio==idServizioVerificaRequisitiProvvisorio)
				{
					return FPM_verificaRequisitiProvvisorio;	
				}
			}
			else
			{
				if(servizio==idServizioAccessoProvvisorio)
				{
					return FPM_invalido75_accessoProvvisorio;
				}
				else if(servizio==idServizioVerificaRequisitiProvvisorio)
				{
					return FPM_invalido75_verificaRequisitiProvvisorio;	
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