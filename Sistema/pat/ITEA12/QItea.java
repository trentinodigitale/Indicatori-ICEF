package c_elab.pat.ITEA12;

import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA12.util.Qutil;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QItea  extends Qutil {
	
	protected static Log log = LogFactory.getLog( QItea.class );
		
	private Table icef_corrente;
	private Table icef_precedente;
	
	private Table irpef_corrente;
	private Table irpef_precedente;
	
	private Table immob_corrente;
	private Table immob_precedente;

	
	public static void main(String[] args) throws IOException, DBException {

	}
		
	private QItea getInstance() {
		return ( QItea )ElabContext.getInstance().getSession( QItea.class.getName(), IDdomanda).getAttribute("instance");
	}

	protected void reset() {
		ElabContext.getInstance().resetSession( QItea.class.getName(), IDdomanda );
	}
		
	private void loadSQL( HashMap params ) throws IOException, DBException {
		
		Properties prop = new Properties();
		prop.load( QItea.class.getResourceAsStream( "sql/sql.properties" ) );
		icef_corrente 	= loadResult( "icef_corrente", prop, params );				
		icef_precedente = loadResult( "icef_precedente", prop, params );			
		
		irpef_corrente 	= loadResult( "irpef_corrente", prop, params );				
		irpef_precedente = loadResult( "irpef_precedente", prop, params );			
		
		immob_corrente = loadResult( "immob_corrente", prop, params );						
		immob_precedente = loadResult( "immob_precedente", prop, params );

	}
	
	public synchronized void init(RunawayData aDataTransfer) {
		
		ElabSession session = ElabContext.getInstance().getSession( QItea.class.getName(), IDdomanda );

		try {

			if (!session.isInitialized()) {
				super.init( aDataTransfer );

				HashMap params = new HashMap();

				params.put("id_domanda", IDdomanda);
				loadSQL(params);
				session.setAttribute( "instance" , this );
				
				session.setInitialized(true);
				session.setRecords(records);

			} else {
				records = session.getRecords();
			}

		} catch (Exception ex) {
			log
					.error(
							"errore nella inizializzazione di QItea",
							ex);
		}
	}
	
	public Table getIcef_precedente() {
		return getInstance().icef_precedente;
	}
	
	public Table getIcef_corrente() {
		return getInstance().icef_corrente;
	}

	public Table getIrpef_corrente() {
		return getInstance().irpef_corrente;
	}

	public Table getIrpef_precedente() {
		return getInstance().irpef_precedente;
	}

	public Table getImmob_corrente() {
		return getInstance().immob_corrente;
	}
	
	public Table getImmob_precedente() {
		return getInstance().immob_precedente;
	}
}
