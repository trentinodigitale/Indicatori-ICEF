/**
 *Created on 28-mag-2004
 */

package c_elab.pat.edilGC;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** query per i dati della domanda
 * @author s_largher
 */
public abstract class QDomanda0 extends ElainNode {

	/** QDomanda constructor */
	public QDomanda0() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QDomanda0.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QDomanda0.class.getName(), IDdomanda );

		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				//                       1                        2
				sql.append(
					"SELECT Domande.un_genitore, Domande.genitori_lavoratori, Domande.mq, Domande.lavoro_femminile ");
				sql.append("FROM Domande ");
				sql.append("WHERE Domande.ID_domanda = ");
				sql.append(IDdomanda);
			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);

				//              		1                      2
				sql.append(
					"SELECT     0 AS un_genitore, 0 AS genitori_lavoratori ");
				sql.append(
					"FROM         Dich_icef ");
				sql.append(
					"WHERE     (ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(
					")");
			}

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
