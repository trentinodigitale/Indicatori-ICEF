package c_elab.pat.coltivdir20;

public class Contributo_calcolato_apapi extends QDatiDomanda {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double importo = 0.0;
		
		try 
		{
			importo = records.getDouble(1, 13);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		
		return importo;
	}
}
