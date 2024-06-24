/**
 *Created on 03-giu-2004
 */

package c_elab.pat.edilGC;

import it.clesius.clesius.util.Sys;

/** legge dalla domanda la detrazione per gli invalidi
 * 
 * @author s_largher
 */
public class Invalidi0 extends QParticolarita0 {

	double QBI = 5400.0;  
	double DMI = 0.0;

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
				
				// max perché la deduzione massima non viene più applicata
				double value = java.lang.Math.max( DMI, java.lang.Math.max( QBI * records.getDouble(i, 1), records.getDouble(i, 2) ) );
				double pesoComponente = records.getDouble(i, 8);
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