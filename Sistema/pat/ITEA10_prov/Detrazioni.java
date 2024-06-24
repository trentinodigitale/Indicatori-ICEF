package c_elab.pat.ITEA10_prov;

import it.clesius.clesius.util.Sys;

public class Detrazioni extends QProvvisorio{
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		double round = 1.0;
		double aggiusta = 0.01;

		try {
			return Sys.round(new Double((String) records.getElement(1, 2)).doubleValue() - aggiusta, round);
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
