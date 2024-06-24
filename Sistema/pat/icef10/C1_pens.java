/**
 *Created on 28-mag-2004
 */

package c_elab.pat.icef10;

import it.clesius.clesius.util.Sys;

/** legge i valori del quadro C1 dove tipo reddito vale PNS (pensione)
 * 
 * @author s_largher
 */
public class C1_pens extends QC1 {

	
	public static final String PNS = "PNS";
	public static final double C_PNS = 1.0;
	
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				if ( records.getString(i, 1).equals(PNS) ) {
					
					double c_pr = records.getDouble(i, 2);
					double importo = records.getDouble(i, 3);
					
					//importo = importo * c_pr / 100.0;
					//importo = importo * C_PNS;
					
					tot = tot +  Sys.round( importo - aggiusta, round ) * c_pr / 100.0;
				}
			}
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