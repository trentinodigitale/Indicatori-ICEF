/**
 *Created on 28-mag-2004
 */

package c_elab.pat.ITEA07; 

import it.clesius.db.sql.RunawayData;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA07.util.Qutil;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** query per il quadro C1 della dichiarazione ICEF
 * @author g_barbieri
 */
public abstract class QC1 extends Qutil {

	private static Log log = LogFactory.getLog( QC1.class );
	
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QC1.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QC1.class.getName(), IDdomanda );
		
		try {
		if (!session.isInitialized()) {
			super.init(dataTransfer);
					
			HashMap parmas = new HashMap();
			parmas.put("id_domanda", IDdomanda);
			loadSQL( parmas, "QC1");

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
