/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef09;

import it.clesius.clesius.util.Sys;

/** legge dalla domanda la detrazione per gli invalidi
 * 
 * @author g_barbieri
 */
public class Invalidi extends QParticolarita {

	double QBI = 5400.0;  
	double DMI = 10800.0;  

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
				// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
				double value = java.lang.Math.min( DMI, java.lang.Math.max( QBI * records.getDouble(i, 1), records.getDouble(i, 2) ) );
				double pesoComponente = new Double((String) records.getElement(i, 8)).doubleValue();
				value =	Sys.round( value - aggiusta, round) * pesoComponente / 100.0;	
					
				tot = tot + value;
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