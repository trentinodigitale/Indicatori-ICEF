package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


/** legge i dati del quadro F della Dichiarazione ICEF
 * @author g_barbieri
 */
public abstract class QImmobili extends ElainNode {

	/** QImmobili constructor */
	public QImmobili() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QImmobili.class.getName(), IDdomanda, "records" );
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QImmobili.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			//                                      1                                  2                                  3
			sql.append(
				"SELECT Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.mutuo_residuo, Patrimoni_immobiliari.residenza_nucleo ");
			sql.append("FROM Familiari ");
			sql.append(
				"INNER JOIN Patrimoni_immobiliari ON Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
			sql.append(
				"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("WHERE Patrimoni_immobiliari.ID_tp_diritto <>'NV' ");  //nuda proprietà a vita
			sql.append("AND Patrimoni_immobiliari.ID_tp_diritto <>'NT' ");    //nuda proprietà a termine
			sql.append("AND Domande.ID_domanda = ");
			sql.append(IDdomanda);

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
