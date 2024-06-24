package c_elab.pat.icef21;

import it.clesius.clesius.util.Sys;

/** legge i valori del quadro D dove tipo detrazione vale IMP (imposte)
 * 
 * @author g_barbieri
 */
public class Imposte extends QDetrazioni {

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
				if ( ((String) records.getElement(i, 1)).equals("IMP") ) {
					tot = tot + Sys.round(records.getDouble(i, 3) - aggiusta, round) * records.getDouble(i, 2) / 100.0;
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