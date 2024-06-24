package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

/** legge dalla domanda il n. componenti e ritorna il valore 
 *  della scala d'equivalenza
 * @author g_barbieri
 */
public class Componenti extends QComponenti {

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		int total = 0;
		double param = 0.0;

		try {
			total = records.getRows();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
		
		switch (total) {
			case 0 :
				param = 1.00;
				break;
			case 1 :
				param = 1.00;
				break;
			case 2 :
				param = 1.57;
				break;
			case 3 :
				param = 2.04;
				break;
			case 4 :
				param = 2.46;
				break;
			case 5 :
				param = 2.85;
				break;
			default :
				param = 2.85 + (0.35 * (total - 5));
				break;
		}
		return param;
	}
}
