package c_elab.pat.icef10.util;

import it.clesius.db.util.Table;

import java.util.Hashtable;

public class ElabSession {

	
	private Hashtable attributes = new Hashtable();
	private boolean initialized;
	private Table records;
	
	
	
	public Table getRecords() {
		return records;
	}


	public void setRecords(Table records) {
		this.records = records;
	}


	public boolean isInitialized() {
		return initialized;
	}


	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}


	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttribute( Object key, Object value ){
		attributes.put( key, value );
	}
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getAttribute( Object key ){
		return attributes.get( key );
	}
	
	

}
