package c_elab.pat.icef21;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import it.clesius.evolservlet.icef.PassaValoriIcef;

/** legge dalla domanda il n. componenti
 * @author g_barbieri
 */
public class N_componenti extends Q_componenti {

	/** N_componenti constructor */
	public N_componenti() {
	}


	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		try {
			return getNComponenti();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}
