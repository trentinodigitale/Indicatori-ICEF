package c_elab.pat.coltiv14;


public class Valutato_apapi extends QDatiDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		double ret = 0.0;
		
		
		boolean valutato_apapi =  records.getBoolean(1, 12);
		
		if(valutato_apapi)
		{
			ret = 1.0;
		}
		
		
		return ret;
	}
}
