package c_elab.pat.cura12;

import it.clesius.clesius.util.Sys;

/** legge dalla domanda la detrazione per gli invalidi
 * 
 * @author g_barbieri
 */
public class Invalidi extends c_elab.pat.icef10.QParticolarita {

	//CAMBIAMI: va cambiata ogni anno
	String assist1="6007";  // nuova dom
	String assist2="6017";  // riaccertam
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
				/**
				 * Ai sensi dell’art. 4, comma 4 della deliberazione della Giunta provinciale n. 1122 del
				 * 15/5/2009, si esclude dal calcolo del reddito .... l'indennità di accompagnamento goduta dall'assistito, poiché la stessa è
				 * concessa al solo titolo della minorazione in relazione al bisogno di assistenza accertato.
				 * 
				 */
				if( !records.getString(i, 7).equals(assist1) && !records.getString(i, 7).equals(assist2) ) {
					
					double value = java.lang.Math.max( DMI, java.lang.Math.max( QBI * records.getDouble(i, 1), records.getDouble(i, 2) ) );
					double pesoComponente = records.getDouble(i, 8);
					value =	Sys.round( value - aggiusta, round) * pesoComponente / 100.0;	
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