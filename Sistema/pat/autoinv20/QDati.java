package c_elab.pat.autoinv20;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;


public abstract class QDati extends ElainNode {

	public static boolean trace=true;

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
			// 		   					1         	   		         2
			sql.append("SELECT Doc.data_presentazione, Soggetti.data_nascita ");
			sql.append("FROM Doc INNER JOIN ");
			sql.append("Domande ON Doc.ID = Domande.ID_domanda INNER JOIN ");
			sql.append("Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			//System.out.println(sql.toString());

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
