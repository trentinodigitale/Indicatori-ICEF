package c_elab.pat.cana22;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;


public abstract class QDati extends ElainNode {

	public static boolean trace=true;

	/** QDati constructor */
	public QDati() {
	}

	private double						DOUBLE_25000		= 25000.00;
	private double						DOUBLE_10000000		= 10000000.00;
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QDati.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QDati.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT CANA_dati.importo, BAR_tp_invalidita.coeff_invalidita, Soggetti.data_nascita, Doc.data_presentazione, ");
			sql.append("piano_res, spesa_ammessa, rendita_catastale_res, importo_spese_tecniche, importo_spese_notarili ");
			sql.append("FROM Domande ");
			sql.append("INNER JOIN Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("INNER JOIN CANA_dati ON Domande.ID_domanda = CANA_dati.ID_domanda ");
			sql.append("INNER JOIN Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto ");
			sql.append("INNER JOIN BAR_tp_invalidita ON CANA_dati.ID_tp_invalidita = BAR_tp_invalidita.ID_tp_invalidita ");
			sql.append("WHERE CANA_dati.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());

			session.setInitialized( true );
			session.setRecords( records );

		} else {
			records = session.getRecords();
		}
	}
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
	
	public double getFranchigia_ast(){

		return DOUBLE_10000000;

	}
}
