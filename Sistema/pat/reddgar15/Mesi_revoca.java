package c_elab.pat.reddgar15;


public class Mesi_revoca extends QRedditoGaranzia {
	
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double numeroMesiRevoca = 0.0;
		
		try 
		{
			int durataMesi = datiRedditoGaranzia.getInteger(1, 8);
			int mesiRevoca = datiRedditoGaranzia.getInteger(1, 17); 
			
			if(mesiRevoca>durataMesi)
			{
				mesiRevoca = durataMesi;
			}
			numeroMesiRevoca = mesiRevoca;
			
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
		
		return numeroMesiRevoca;
	}
		
}
