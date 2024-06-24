package c_elab.pat.icef16;

import it.clesius.clesius.util.Sys;

/** legge dalla domanda la detrazione per gli invalidi
 * 
 * @author g_barbieri
 */
public class InvalidiNew extends QParticolarita {

	public static final double QBI_2017 = 2700.0;  

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

				double coeff_invalidita= records.getDouble(i, 9);
	
				// max perché la deduzione massima non viene più applicata
				double value = java.lang.Math.max( LocalVariables.DMI, java.lang.Math.max( QBI_2017 *coeff_invalidita, records.getDouble(i, 2) ) );
				
				double pesoReddito = 100;  //records.getDouble(i, 8); NB da rivedere!!!!!!!!
				value =	Sys.round( value - aggiusta, round) * pesoReddito / 100.0;	
					
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