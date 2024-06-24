package c_elab.pat.icef15;

import it.clesius.clesius.util.Sys;

/** 
 * @author s_largher
 * 
 *	somma valori ICI da quadro C3 e quadro C4
 *	se redditi da partecipazione si moltiplica per la quota e per il peso del patrimonio
 */
public class Imm_strumentali extends QvaloriICI {

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
			for (int row = 1; row <= records.getRows(); row++) {
				double valore =  Sys.round( ( records.getDouble(row,3) * records.getDouble(row,4) / 100.0 ) - aggiusta, round );
				tot = tot + Sys.round( ( valore * records.getDouble(row,5) / 100.0 ) - aggiusta, round );
			}
			return tot;
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}