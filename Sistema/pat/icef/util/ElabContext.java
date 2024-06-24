package c_elab.pat.icef.util;

/*
 * Created on 14-lug-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


import java.util.Hashtable;


/**
 * @author a_cavalieri
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ElabContext {
			
	/**
	 * La tabella con tutti contesti
	 */
	private Hashtable sessions = new Hashtable();
	
	/** Il singleton della classe */
	private static ElabContext context; 
	
	/**
	 * 
	 * @return
	 */
	public synchronized static ElabContext getInstance(){
	
		if ( context == null ){
			context = new ElabContext();			
		}		
		return context;
	}
	
	/**
	 * Costruttore 
	 *
	 */
	private ElabContext(){}	
	
	
	private Object createKey( String className,  String id_domanda ){
		return className.concat( id_domanda ) ;
	}
	
	/**
	 * Rimuove il servizio id_servizio
	 * @param id_servizio
	 */
	public void resetSession( String className, String id_domanda ){
		sessions.remove( createKey( className, id_domanda ) );
	}	
	
	
	/**
	 * 
	 * @param id_servizio La chiave della tabella del servizio
	 * @return La tabella contenente le informazioni del servizio
	 */
	public ElabSession getSession( String className, String id_domanda ){
		
		Object id_session = createKey( className, id_domanda );
		ElabSession session = ( ElabSession )sessions.get( id_session );
							
		if ( session == null ){
			session = new ElabSession();
			sessions.put( id_session, session );			
		}
		
		return session;
	}
	

}
