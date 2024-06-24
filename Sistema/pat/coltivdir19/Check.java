package c_elab.pat.coltivdir19;



public class Check  extends QDatiDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		double check = 0.0;
		
		if(records.getBoolean(1, 14)){
			 check = 1.0;//esclusa ufficio
			
		}
		return check;
	}
	protected void reset() {
	}
}
