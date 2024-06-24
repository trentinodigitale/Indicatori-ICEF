package c_elab.pat.ITEA10;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA10.util.Qutil;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/**query per il quadro D della dichiarazione ICEF anno precedente
 * 
 * @author a_cavalieri
 *
 */
public abstract class QDetrazioni0 extends Qutil {
	
	protected static Log log = LogFactory.getLog( QDetrazioni0.class );
	
	protected Table tb_componenti;

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QDetrazioni0.class.getName(), IDdomanda );
		tb_componenti = null;
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
				
				HashMap parmasComponenti = new HashMap();	
				parmasComponenti.put("id_domanda", IDdomanda);	
				loadSQL( parmasComponenti, "detrazioni_componenti0" );		
				tb_componenti = records;
				
				HashMap parmas = new HashMap();	
				parmas.put("id_domanda", IDdomanda);	
				loadSQL( parmas, "detrazioni0" );				
				
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
