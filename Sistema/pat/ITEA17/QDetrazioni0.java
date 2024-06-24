package c_elab.pat.ITEA17;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import c_elab.pat.icef17.DefComponentiDich;


public abstract class QDetrazioni0 extends DefComponentiDich {

	protected static Log log = LogFactory.getLog( QDetrazioni0.class );

	protected Table tb_componenti;

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QDetrazioni0.class.getName(), IDdomanda );
		tb_componenti = null;
	}

	/*detrazioni0 =  SELECT Detrazioni.ID_tp_detrazione, R_Relazioni_parentela.peso_reddito, Detrazioni.importo, Detrazioni.ID_dichiarazione \
			   FROM EPU_Familiari INNER JOIN Detrazioni ON EPU_Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione \
			   INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda \
			   INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela \
			   WHERE EPU_Familiari.nuovo_ingresso = 0 AND Domande.ID_domanda = ${id_domanda}

	detrazioni_componenti0 =  SELECT EPU_Familiari.ID_dichiarazione, R_Relazioni_parentela.peso_reddito \
			   FROM EPU_Familiari \
			   INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda \
			   INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela \
			   WHERE Domande.ID_domanda = ${id_domanda}*/

	
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QDetrazioni0.class.getName(), IDdomanda  );

		try {

			if (!session.isInitialized()) {
				super.init(dataTransfer);

				StringBuffer sqlComponenti = new StringBuffer();
				sqlComponenti.append("SELECT EPU_Familiari.ID_dichiarazione, R_Relazioni_parentela.peso_reddito  ");
				sqlComponenti.append("FROM EPU_Familiari  ");
				sqlComponenti.append("INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda  ");
				sqlComponenti.append("INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela  ");
				sqlComponenti.append("WHERE Domande.ID_domanda = "+IDdomanda+" ");

				StringBuffer sql = new StringBuffer();
				//                             1                                    2                     3						4							 5
				sql.append("SELECT Detrazioni.ID_tp_detrazione, R_Relazioni_parentela.peso_reddito, Detrazioni.importo, Detrazioni.ID_dichiarazione, Detrazioni.cointestatari ");
				sql.append("FROM EPU_Familiari INNER JOIN Detrazioni ON EPU_Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione ");
				sql.append("INNER JOIN Domande ON EPU_Familiari.ID_domanda = Domande.ID_domanda ");
				sql.append("INNER JOIN R_Relazioni_parentela ON EPU_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("WHERE EPU_Familiari.nuovo_ingresso = 0 AND Domande.ID_domanda = "+IDdomanda+" ");

				doQuery(sqlComponenti.toString());
				tb_componenti = records;
				doQuery(sql.toString());
		
				session.setInitialized( true );
				session.setRecords( records );
				session.setAttribute("tb_componenti", tb_componenti);

			} else {
				records = session.getRecords();
				tb_componenti = (Table)session.getAttribute("tb_componenti");
			}

		} catch (Exception ex) {
			log.error("errore nella inizializzazione delle schede QDetrazioni0",ex);
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
