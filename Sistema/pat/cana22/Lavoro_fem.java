package c_elab.pat.cana22;

import c_elab.pat.icef21.QDomanda;

/** 
 * 
 * @author s_largher
 */
public class Lavoro_fem extends QDomanda {


	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		try {
			tot = java.lang.Math.abs(records.getDouble(1, 4)) * 4000.00;
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}