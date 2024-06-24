package c_elab.pat.du20.fnum;


public class Check extends QFamiglieNumerose {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		double check = 0.0;
		
		if (datiFamiglieNumerose == null)
			return 9.0;
		
		if (datiFamiglieNumerose.getRows()<1)
			return 8.0;
		
		boolean residenzaTAA =  datiFamiglieNumerose.getBoolean(1, 3);
		
		if(!residenzaTAA)
		{
			check += 1.0;
		}
		//check 2: escludi solo fnum o l'intera domanda è stata esclusa d'ufficio
		boolean domandaEscusaUfficio = false;
		if(	(datiFamiglieNumerose.getElement(1, datiFamiglieNumerose.getIndexOfColumnName("escludi_ufficio_fnum"))!= null && c_elab.pat.Util_apapi.stringToBoolean((String) datiFamiglieNumerose.getElement(1, datiFamiglieNumerose.getIndexOfColumnName("escludi_ufficio_fnum"))))
				|| (datiFamiglieNumerose.getElement(1, datiFamiglieNumerose.getIndexOfColumnName("escludi_ufficio"))!= null && c_elab.pat.Util_apapi.stringToBoolean((String) datiFamiglieNumerose.getElement(1, datiFamiglieNumerose.getIndexOfColumnName("escludi_ufficio"))))
		){
			domandaEscusaUfficio=true;
		}

		if(domandaEscusaUfficio)
		{
			check += 20000000;
		}
		
		
		return check;
	}
}
