/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef11;


/** 
 * 
 * @author s_largher
 */
public class Lavoro_fem extends QDomanda {

	// Deduzione individuale per lavoro femminile fissata a 1000 € (Art. 13 comma 5)
	private double DLF = 1000.0;
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		try {
			tot = java.lang.Math.abs(records.getDouble(1, 4)) * DLF;
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