package c_elab.pat.ITEA07;

import it.clesius.db.sql.RunawayData;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA07.util.Qutil;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/**
 * 
 * @author a_cavalieri
 *
 */
public abstract class QDetrazioni0 extends Qutil {
	
	protected static Log log = LogFactory.getLog( QDetrazioni0.class );


	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QDetrazioni0.class.getName(), IDdomanda );
		
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {
		
		ElabSession session =  ElabContext.getInstance().getSession(  QDetrazioni0.class.getName(), IDdomanda  );
		
		try {
		
			if (!session.isInitialized()) {
				super.init(dataTransfer);
				//StringBuffer sql = new StringBuffer();
				
				//                             1                                    2                      3
				/*sql.append(
					"SELECT Detrazioni.ID_tp_detrazione, R_Relazioni_parentela.peso_reddito, Detrazioni.importo ");
				sql.append("FROM Familiari ");
				sql.append(
					"INNER JOIN Detrazioni ON Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione ");
				sql.append(
					"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
				sql.append(
					"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("WHERE Domande.ID_domanda = ");
				sql.append(IDdomanda);*/
	
				HashMap parmas = new HashMap();	
				parmas.put("id_domanda", IDdomanda);	
				loadSQL( parmas, "detrazioni0" );				
				
				//doQuery(sql.toString());
	
				session.setInitialized( true );
				session.setRecords( records );
			
			} else {
				records = session.getRecords();
			}
		

		} catch (Exception ex) {
			log
					.error(
							"errore nella inizializzazione delle schede assegno di cura",
							ex);
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
