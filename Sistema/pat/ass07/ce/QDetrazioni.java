/** 
 *Created on 03-giu-2004
 */

package c_elab.pat.ass07.ce; 

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.anf.PassaValoriANF07;

/** query per il quadro D (Detrazioni) della dichiarazione ICEF
 * @author g_barbieri
 */
public abstract class QDetrazioni extends ElainNode {

	/** QDetrazioni constructor */
	public QDetrazioni() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QDetrazioni.class.getName(), IDdomanda, "records" );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDetrazioni.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			//                             1                                    2                      3
			sql.append(
				"SELECT Detrazioni.ID_tp_detrazione, R_Relazioni_parentela.peso_reddito, Detrazioni.importo ");
			sql.append("FROM Familiari ");
			sql.append(
				"INNER JOIN Detrazioni ON Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione ");
			sql.append(
				"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(
				"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append(" WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(" AND Familiari.ID_dichiarazione in ");
			sql.append(PassaValoriANF07.getID_dichiarazioni(IDdomanda));

			doQuery(sql.toString());

			session.setInitialized(true);
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
