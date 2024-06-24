package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


/** legge i dati della dichiarazione ISEE dalla tabella Domande
 * @author g_barbieri
 */
public abstract class QDichiarazioneISEE extends ElainNode {
	
	/** QDichiarazioneISEE constructor */
	public QDichiarazioneISEE() { 
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QDichiarazioneISEE.class.getName(), IDdomanda, "records" );
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDichiarazioneISEE.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();
			//                       1                        2                            3                      4                      5                      6
			sql.append(
				"SELECT Domande.un_genitore, Domande.genitori_lavoratori, Domande.n_invalidi_66_75, Domande.n_invalidi_75, Domande.canone_locazione, Doc.data_presentazione ");
			sql.append("FROM Domande ");
			sql.append("INNER JOIN Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());

			session.setInitialized(true);
			session.setRecords( records );
        } else {
			records = session.getRecords();
        }
	}
}
