package c_elab.pat.interStrao15;

import it.clesius.clesius.util.Sys;



public class Invalidi extends QInterStrao {

	double QBI = 5400.0;

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (particolarita == null)
			return 0.0;

		//if (records == null)
			//return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int j = 1; j <= particolarita.getRows(); j++){
				
				double speseRsa =  0.0; 
				if (particolarita.getElement(j, 5) != null ){
					speseRsa = particolarita.getDouble(j, 5);
				}
				double value = java.lang.Math.max( QBI * particolarita.getDouble(j, 1), particolarita.getDouble(j, 2) );
				double pesoReddito = particolarita.getDouble(j, 3);
				value = java.lang.Math.max(speseRsa, value);
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

