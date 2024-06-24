package c_elab.pat.pcn5;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QPcn5 extends ElainNode {
	
	protected Table datiPcn5;
	
	/** QFamiglieNumerose constructor */
	public QPcn5() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		datiPcn5 = null;
		ElabContext.getInstance().resetSession(  QPcn5.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QPcn5.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();

			// 		   					1 
			sql.append("SELECT Doc.data_presentazione ");
			sql.append("FROM Domande INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			datiPcn5 = records;
			
			sql = new StringBuffer();	
			//							   1										2
			sql.append("SELECT Doc_edizioni_esiti.importo, Doc_edizioni_esiti.edizione_doc ");
			sql.append("FROM Doc_edizioni_esiti ");
			sql.append("WHERE Doc_edizioni_esiti.id_doc = ");
			sql.append(IDdomanda + " ");
			sql.append("ORDER BY Doc_edizioni_esiti.edizione_doc DESC");
			
			doQuery(sql.toString());
			
			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("datiPcn5", datiPcn5);

		} else {
			records = session.getRecords();
			datiPcn5 = (Table)session.getAttribute("datiPcn5");
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
