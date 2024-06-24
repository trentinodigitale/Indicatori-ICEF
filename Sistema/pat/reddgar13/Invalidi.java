package c_elab.pat.reddgar13;

import it.clesius.clesius.util.Sys;



public class Invalidi extends QRedditoGaranzia {

	double QBI = 5400.0;

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (particolarita == null)
			return 0.0;

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++)
			{
				int idDich = records.getInteger(i, 2);
				double speseRsa = records.getDouble(i, 34);

				for (int j = 1; j <= particolarita.getRows(); j++) 
				{
					int idDich2 =  particolarita.getInteger(j, 4);
					if(idDich == idDich2)
					{
						double value = java.lang.Math.max( QBI * particolarita.getDouble(j, 1), particolarita.getDouble(j, 2) );
						double pesoReddito = particolarita.getDouble(j, 3);
						value = java.lang.Math.max(speseRsa, value);
						value =	Sys.round( value - aggiusta, round) * pesoReddito / 100.0;	

						tot = tot + value;
					}

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

