package c_elab.pat.allevContr20;

/**
 * 
 * @author a_pichler
 *
 */
public class Check extends c_elab.pat.allevContr20.QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		double check = 0.0;
		
		if(records.getElement(1, records.getIndexOfColumnName("escludi_ufficio"))!=null 
				&& 
				!records.getString(1, records.getIndexOfColumnName("escludi_ufficio")).equals("0")){
			 check = 1.0;//esclusa ufficio
			
		}
		return check;
		
	}
}
