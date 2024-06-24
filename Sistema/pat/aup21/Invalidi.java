package c_elab.pat.aup21;

import it.clesius.clesius.util.Sys;

/** legge dalla domanda la detrazione per gli invalidi
 * 
 * @author g_barbieri
 */
public class Invalidi extends c_elab.pat.icef21.QParticolarita {



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
				
				//franchigia = quota base * coeff invalidità
				double franchigia = c_elab.pat.icef21.LocalVariables.QBI * records.getDouble(i, 9);
				//spese = min tra doppio franchigia e spese dichiarate
				double spese = java.lang.Math.min( franchigia * 2, records.getDouble(i, 2) ) ;
				//detrazione = max tra franchigia e spese
				double value = java.lang.Math.max( franchigia, spese ) ;
				value =	Sys.round( value - aggiusta, round);	
					
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