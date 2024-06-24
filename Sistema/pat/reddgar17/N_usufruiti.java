package c_elab.pat.reddgar17;


public class N_usufruiti extends QRedditoGaranzia {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double numeroUsufruiti = 0.0;
		
		try 
		{
			
			
			if(servizio == idServizioRedditoGaranzia)
			{
				numeroUsufruiti = 0.0;
				
				if(datiRinnovo!=null && datiRinnovo.getRows()>0)
				{
					int rinnovoConBeneficio = datiRinnovo.getInteger(1, 11);
				
					if(rinnovoConBeneficio!=-1)
					{
						numeroUsufruiti = rinnovoConBeneficio;
					}
				}
			}
			else if(servizio == idServizioRedditoGaranziaSociale)
			{
				
				numeroUsufruiti = 0.0;
			}
			
			
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		
		return numeroUsufruiti;
	}
		
}
