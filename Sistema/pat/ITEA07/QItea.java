package c_elab.pat.ITEA07;

import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA07.util.Qutil;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QItea  extends Qutil {
	
	protected static Log log = LogFactory.getLog( QItea.class );
	//private static QItea instance;
	
	
	private Table icef_corrente;
	private Table icef_precedente;
	
	private Table irpef_corrente;
	private Table irpef_precedente;
	
	private Table epuImmobiliTutti;
	private Table epuImmobiliFranchigia;
	private Table epuImmobiliTutti_prec;
	private Table epuImmobiliFranchigia_prec;
	private Table immob;
	private Table immob_prec;
	private HashSet quote_familiari_fino_a_50 = new HashSet();
	
	public static void main(String[] args) throws IOException, DBException {
		/*QItea itea  = new QItea();
		HashMap parmas = new HashMap();
		parmas.put("id_domanda", "" );
		itea.loadSQL( parmas );	*/
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
		
		epuImmobiliTutti = loadResult( "epuImmobiliTutti", prop, params );						
		epuImmobiliFranchigia = loadResult( "epuImmobiliFranchigia", prop, params );						
		epuImmobiliTutti_prec = loadResult( "epuImmobiliTutti_prec", prop, params );						
		epuImmobiliFranchigia_prec = loadResult( "epuImmobiliFranchigia_prec", prop, params );						
		immob = loadResult( "immob", prop, params );						
		immob_prec = loadResult( "immob_prec", prop, params );						
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
				// records = theRecords;
				records = session.getRecords();
			}

		} catch (Exception ex) {
			log
					.error(
							"errore nella inizializzazione delle schede assegno di cura",
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


	public Table getepuImmobiliTutti() {
		return getInstance().epuImmobiliTutti;
	}

	public Table getepuImmobiliFranchigia() {
		return getInstance().epuImmobiliFranchigia;
	}

	public Table getepuImmobiliTutti_prec() {
		return getInstance().epuImmobiliTutti_prec;
	}

	public Table getepuImmobiliFranchigia_prec() {
		return getInstance().epuImmobiliFranchigia_prec;
	}
	
	public Table getImmob() {
		return getInstance().immob;
		//return immob;
	}
	
	public Table getImmob_prec() {
		return getInstance().immob_prec;
	}

	public HashSet getQuote_familiari_fino_a_50() {
		return getInstance().quote_familiari_fino_a_50;
	}
	
}

