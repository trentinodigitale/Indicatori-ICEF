package c_elab.pat.stagionale17;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


public abstract class QDati extends ElainNode {

	/** QDati constructor */
	public QDati() {
	}

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
			sql.append("SELECT no_residenza, escludi_ufficio, id_tp_sex, data_nascita, data_presentazione, legge_68_99, disoccupato, lavAut ");
			sql.append("FROM LAV_dati_richiedente ");
			sql.append("INNER JOIN Domande ON LAV_dati_richiedente.id_domanda = Domande.id_domanda ");
			sql.append("INNER JOIN Doc ON Domande.id_domanda = Doc.ID ");
			sql.append("WHERE LAV_dati_richiedente.ID_domanda = ");
			sql.append(IDdomanda);
			//sql.append(" AND tp_cooperativa = "+ID_tp_extra_cooperativa);

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
	
}