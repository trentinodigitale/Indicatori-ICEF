package c_elab.pat.ITEA21; 

/** 
 *@author m_bacci 
 */
import it.clesius.db.sql.RunawayData;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA21.util.Qutil;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public class QCompensazione0 extends Qutil { 

	protected static Log log = LogFactory.getLog( QCompensazione0.class );


	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QCompensazione0.class.getName(), IDdomanda );
		
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {
		
		ElabSession session =  ElabContext.getInstance().getSession(  QCompensazione0.class.getName(), IDdomanda  );
		
		try {
		
			if (!session.isInitialized()) {
				super.init(dataTransfer);
				
				HashMap parmas = new HashMap();	
				parmas.put("id_domanda", IDdomanda);	
				loadSQL( parmas, "importo_assegno0" );				
				
				session.setInitialized( true );
				session.setRecords( records );		
			
			} else {
				records = session.getRecords();
			}

		} catch (Exception ex) {
			log
					.error(
							"errore nella inizializzazione delle schede QImportoAssegno",
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