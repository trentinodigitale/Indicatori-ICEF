package c_elab.pat.du14.fnum;


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
		//check 2: l'intera domanda è stata esclusa d'ufficio
		boolean domandaEscusaUfficio = datiFamiglieNumerose.getBoolean(1, 4);
		if(domandaEscusaUfficio)
		{
			check += 20000000;
		}
		
		
		return check;
	}
}
