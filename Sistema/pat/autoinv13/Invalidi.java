package c_elab.pat.autoinv13;

import it.clesius.clesius.util.Sys;

/** legge dalla domanda la detrazione per gli invalidi
 * 
 * @author g_barbieri
 */
public class Invalidi extends c_elab.pat.icef13.QParticolarita {
	//NB questa classe estende la classe standard icef13.QParticolarita
	
	//CAMBIAMI: va cambiata ogni anno
	private static Integer assistitoMag=9580;  
	private static Integer assistitoMin=9590;  

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
				if ( records.getInteger(i, 7) != assistitoMag && records.getInteger(i, 7) != assistitoMin ) {
					// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
					
					// max perché la deduzione massima non viene più applicata
					double value = java.lang.Math.max( DMI, java.lang.Math.max( QBI * records.getDouble(i, 1), records.getDouble(i, 2) ) );
					double pesoReddito = 100;  //records.getDouble(i, 8); NB da rivedere!!!!!!!!
					value =	Sys.round( value - aggiusta, round) * pesoReddito / 100.0;	
						
					tot = tot + value;
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