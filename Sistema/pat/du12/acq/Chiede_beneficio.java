package c_elab.pat.du12.acq;

public class Chiede_beneficio extends QContrPotAcq {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		
		double chiedeBeneficio = 0.0;
		
		try 
		{
			if(datiContrPotAcq != null && datiContrPotAcq.getString(1,4)!=null && !datiContrPotAcq.getString(1,4).equals("0"))
			{
				chiedeBeneficio = 1.0;
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
		return chiedeBeneficio;
	}
		
}
