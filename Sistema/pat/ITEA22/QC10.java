package c_elab.pat.ITEA22; 

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA22.util.Qutil;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.db.sql.RunawayData;

/** query per il quadro C1 della dichiarazione ICEF
 * @author s_largher
 */
public abstract class QC10 extends Qutil {

	private static Log log = LogFactory.getLog( QC10.class );
	
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QC10.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QC10.class.getName(), IDdomanda );
		
		try {
		if (!session.isInitialized()) {
			super.init(dataTransfer);
					
			HashMap parmas = new HashMap();
			parmas.put("id_domanda", IDdomanda);
			loadSQL( parmas, "QC10");

			session.setInitialized( true );
			session.setRecords( records );
			
		} else {
			records = session.getRecords();
		}
		} catch (Exception ex) {
			log
					.error(
							"errore nella inizializzazione QC10",
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
