/**
 *Created on 03-giu-2004
 */

package c_elab.pat.ITEA09;

import it.clesius.db.sql.RunawayData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.icef09.Invalidi;

/** legge dalla domanda la detrazione per gli invalidi
 * 
 * @author g_barbieri
 */
public class Invalidi0 extends Invalidi {

	//CAMBIAMI: va cambiata ogni anno
	int idServizioVerifica = 13220; // per verifica requisiti
	
	protected static Log log = LogFactory.getLog( Invalidi0.class );
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {
		
		if(servizio==idServizioVerifica)
		{
			//solo se il servizio è quello di verifica requisiti prendo i valori per gli invalidi0
			//mettendo in join la tabella familiari con epu_familiari con clausola aggiuntiva di where
			//che non sia un nuovo ingresso (vedi query "particolarita0" in sql.properties)
			try {
				Particolarita0Util particolarita0 = new Particolarita0Util();
				particolarita0.init(dataTransfer);
				records = particolarita0.getTable(IDdomanda);
			} 
			catch (Exception ex) 
			{
				log.error("errore nella inizializzazione delle schede Particolarita0",ex);
			}
		}
		else
		{
			//se il servizio e quello di accesso invece prendo i valori dell'anno corrente
			super.init(dataTransfer);	
		}
	}
}