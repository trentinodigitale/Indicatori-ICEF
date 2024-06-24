package c_elab.pat.ITEA18; 

/** 
 *@author s_largher 
 */

public class FPM extends QInvalido_75 { 
	
	//CAMBIAMI: va cambiata ogni anno
	int idServizioAccesso = 13318;
	int idServizioCanoneModerato = 13418;
	double FPM_accesso = 35000;
	double FPM_invalido75_accesso = 70000;
	
	int idServizioVerificaRequisiti = 13518;
	double FPM_verificaRequisiti = 40000;
	double FPM_invalido75_verificaRequisiti = 80000;
	
	int idServizioVerificaRequisitiTione = 0; // per verifica requisiti tione //2015 - non duplicato
	
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
				if(servizio==idServizioAccesso || servizio==idServizioCanoneModerato)
				{
					return FPM_accesso;
				}
				else if(servizio==idServizioVerificaRequisiti)
				{
					return FPM_verificaRequisiti;	
				}
				else if(servizio==idServizioVerificaRequisitiTione)
				{
					return FPM_verificaRequisiti;	
				} else {
					System.out.println("ERRORE GRAVE in " + getClass().getName() + ": il servizio "+ servizio + " non è gestito in questa classe!");
					return FPM_accesso;
				}
			}
			else
			{
				if(servizio==idServizioAccesso || servizio==idServizioCanoneModerato)
				{
					return FPM_invalido75_accesso;
				}
				else if(servizio==idServizioVerificaRequisiti)
				{
					return FPM_invalido75_verificaRequisiti;	
				}
				else if(servizio==idServizioVerificaRequisitiTione)
				{
					return FPM_invalido75_verificaRequisiti;	
				} else {
					System.out.println("ERRORE GRAVE in " + getClass().getName() + ": il servizio "+ servizio + " non è gestito in questa classe!");
					return FPM_invalido75_accesso;
				}
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