package c_elab.pat.ITEA12.util;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.DBException;
import it.clesius.db.util.Table;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.pat.ITEA12.QItea;

public abstract class Qutil  extends ElainNode {
	
	private static Log log = LogFactory.getLog( Qutil.class );
	
	
	public void loadSQL( HashMap params, String sql_name ) throws IOException, DBException {
		
		Properties prop = new Properties();
		prop.load( QItea.class.getResourceAsStream( "sql/sql.properties" ) );
		records = loadResult( sql_name, prop, params );				
							
	}

	public Table loadResult( String sql_key, Properties prop, HashMap params ) throws DBException{
		
		String sql = prop.getProperty( sql_key );
		Iterator names = params.keySet().iterator ();
		
		String name = null;		
				
		while (names.hasNext ()) {
			name = ( String ) names.next ();
			sql = sql.replaceAll ( "\\$\\{".concat( name).concat("\\}"), ( String ) params.get ( name ));
		}		
		
		log.trace( sql ); 
		return dataTransfer.executeQuery( sql );
	}	
	

	
	
	public static String getElement( Table table, String columnName, int row ) {
		
		int index = table.getIndexOfColumnName( columnName );
		return ( String )table.getElement(  row , index );
	}
	
}

