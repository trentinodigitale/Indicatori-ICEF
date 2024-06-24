package c_elab.pat.fnum;


public class Check extends QFamiglieNumerose {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		double check = 0.0;
		
		if (datiFamiglieNumerose == null)
			return 9.0;
		
		boolean residenzaTAA =  datiFamiglieNumerose.getBoolean(1, 4);
		
		if(!residenzaTAA)
		{
			check += 1.0;
		}
		//check 2: l'intera domanda è stata esclusa d'ufficio
		boolean domandaEscusaUfficio = datiFamiglieNumerose.getBoolean(1, 5);
		if(domandaEscusaUfficio)
		{
			check += 20000000;
		}
		
		
		return check;
	}
}
