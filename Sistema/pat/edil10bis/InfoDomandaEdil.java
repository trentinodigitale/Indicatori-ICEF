package c_elab.pat.edil10bis;

import it.clesius.apps2core.format.CRCDomanda;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;

public class InfoDomandaEdil extends CRCDomanda {

	//domande
	private int id_tp_stato_doc;
	private String stato_doc;
	private String ente_erogatore;
	private String servizio;
	private String data_elaborazione_dom;
	private String ente_inseritore_dom;
	private int crc_elain;
	
	
	//dichiarazioni
	private class DichIsee {
		private String id_dichiarazione;
		private String data_elaborazione_dichiarazione;
		private String fingerprint_dich;
		private String protocollo_dich;
		private String data_presentazione_dich;
		private String ente_inseritore_dich;
		private String ente_inseritore_dich_luogo;
		
		//aggiunta
		private String anno_reddito;
		private String anno_patrimonio;
	
	};
	
	private DichIsee[] dichiarazioni;
	private DichIsee[] dichiarazioni_patrimonio; 
	
	
	
	private static String FONT = "";//"Verdana";
	private static String FONT_INFO_DOMANDA = "size='2'";//"Verdana";	
	
	
     
	/**
	 * 
	 * @return
	 */
	private boolean useDichiarazione(){
		return ( dichiarazioni.length > 0 );
	}
	private boolean useDichiarazionePatrimonio(){
		return ( dichiarazioni_patrimonio.length > 0 );
	}

	/**
	 * 
	 */
	public void init(RunawayData aDataTransfer) {
		super.init(aDataTransfer);		
	}
	
	
    public String getString()
    {
    	loadTableDich();
    	loadTableDichPatrimonio();
		loadTableDoc();
    	
    	StringBuffer footer = new StringBuffer();
        
    	//se esiste la dichiarazione
    	if ( useDichiarazionePatrimonio() ){
    		footer.append( createInfoElabConDichRedditoPatrimonio() );
    	}else if ( useDichiarazione() ){
    		footer.append( createInfoElabConDich() );
    	}
    	
    	//senza dichiarazione
    	else {
    		//footer.append( createInfoElabSenzaDich() );
    	}
    	
    	
    	//footer.append( "<br><hr>" );
    	
    	footer.append( createInfoElabStatoDom() );
    	footer.append( createInfoDomanda() );
    	
    	//footer.append( "<HR><CENTER>Clesius S.r.l.</CENTER></BODY></HTML>" );
        return footer.toString();
    }
    
    
    /**
     * 
     * @return
     */
    private String createInfoDomanda(){
    	
    	
    	String row_height = "29%";
		String row_width  = "16%";
		String table_height = "57";
		String table_width  = "80%";
		String table_border  = "1";
		
		StringBuffer result = new StringBuffer();
		
		
		result.append("<table align='center' border='" + table_border + "' width='" + table_width + "' height='" + table_height + "'>");
		result.append("<tr>");
		result.append( createRowCellHeader( "id domanda", row_width, row_height ) );
		result.append( createRowCellHeader( "ente erogatore", row_width, row_height ) );
		result.append( createRowCellHeader( "data elaborazione", row_width, row_height ) );
		result.append( createRowCellHeader( "ente inseritore", row_width, row_height ) );
		result.append( createRowCellHeader( "riassunto dati", row_width, row_height ) );
		result.append( createRowCellHeader( "stato", row_width, row_height ) );
		result.append("</tr>");
		
		result.append("<tr>");
		result.append( createRowCell( IDdomanda, row_width, row_height ) );
		result.append( createRowCell( ente_erogatore, row_width, row_height ) );
		result.append( createRowCell( data_elaborazione_dom, row_width, row_height ) );
		result.append( createRowCell( ente_inseritore_dom, row_width, row_height ) );
		result.append( createRowCell( String.valueOf( crc_elain ), row_width, row_height ) );
		result.append( createRowCell( stato_doc, row_width, row_height ) );
		result.append("</tr>");
		result.append("</table>");
		      	
       	return result.toString();
    }
   
    
    /**
     * 
     * @return
     */
    private String createInfoElabStatoDom(){
    	StringBuffer result = new StringBuffer();
    	   	
    	//se non è completa
    	if ( id_tp_stato_doc != 1 ){    		
    		result.append( "<p><font " + FONT_INFO_DOMANDA + "><b>Attenzione:</b> la presente elaborazione è provvisoria in quanto la domanda non " +
    			"è nello stato &quot;completa&quot; </font></p>" );
    	}
    	
    	return result.toString();
    }
    
    
    /**
	 * 
	 * @param value
	 * @return
	 */
	private String createRowCell( String value, String row_width, String row_height  ){
		
		return "<td align='center' width=" + row_width + " height=" + row_height + 
		"><font " + FONT_INFO_DOMANDA + ">"+ value +"</font></td>";
				
	}
    
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private String createRowCellHeader( String value, String row_width, String row_height  ){
		
		return "<td align='center' width='" + row_width + "' height='" + row_height + 
		"'><strong><font " + FONT_INFO_DOMANDA + ">"+ value +"</font></strong></td>";
				
	}
    
    /**
     * 
     * @return
     */
    private String createInfoElabConDich(){
    	StringBuffer result = new StringBuffer();
    	
    	result.append( "<p><font face='" + FONT + "'>La presente domanda è stata valutata sulla base delle " +
    			"seguenti dichiarazioni ICEF:</font></p>" );
    	
    	result.append( "<ul>" );
    	for ( int i = 0; i < dichiarazioni.length; i++ ){
    		
    		result.append( "<li><font face='" + FONT + "'>dichiarazione con numero protocollo <b>" + dichiarazioni[ i ].protocollo_dich + "</b>" +
	    					", con codice ISEENET <b>" +  dichiarazioni[ i ].id_dichiarazione + "</b>" +
	    					", anno reddito <b>" +  dichiarazioni[ i ].anno_reddito  + "</b>" +
	    					", anno patrimonio <b>" +  dichiarazioni[ i ].anno_patrimonio  + "</b>, " +	    					
	    					"presentata in data <b>" +
	    					dichiarazioni[ i ].data_presentazione_dich + "</b> a <b>" + dichiarazioni[ i ].ente_inseritore_dich_luogo + 
							"</b> presso l'ente <b>" + 
	    					dichiarazioni[ i ].ente_inseritore_dich + "</b>" + 
	    					"</font></li>" );
        }
    	result.append( "</ul>" );
     
    	return result.toString();
    }
    
    private String createInfoElabConDichRedditoPatrimonio(){
    	StringBuffer result = new StringBuffer();
    	
    	result.append( "<p><font face='" + FONT + "'>La presente domanda è stata valutata sulla base delle " +
		"seguenti dichiarazioni ICEF per la situazione REDDITUALE 2008:</font></p>" );

		result.append( "<ul>" );
		for ( int i = 0; i < dichiarazioni.length; i++ ){
			
			result.append( "<li><font face='" + FONT + "'>dichiarazione con numero protocollo <b>" + dichiarazioni[ i ].protocollo_dich + "</b>" +
							", con codice ISEENET <b>" +  dichiarazioni[ i ].id_dichiarazione + "</b>" +
							", anno reddito <b>" +  dichiarazioni[ i ].anno_reddito  + "</b>" +
							", anno patrimonio <b>" +  dichiarazioni[ i ].anno_patrimonio  + "</b>, " +	    					
							"presentata in data <b>" +
							dichiarazioni[ i ].data_presentazione_dich + "</b> a <b>" + dichiarazioni[ i ].ente_inseritore_dich_luogo + 
							"</b> presso l'ente <b>" + 
							dichiarazioni[ i ].ente_inseritore_dich + "</b>" + 
							"</font></li>" );
		}
		result.append( "</ul>" );

    	result.append( "<p><font face='" + FONT + "'>mentre per la situazione PATRIMONIALE 2007 sulla base delle seguenti dichiarazioni ICEF:</font></p>" );
    	
    	result.append( "<ul>" );
    	for ( int i = 0; i < dichiarazioni_patrimonio.length; i++ ){
    		
    		result.append( "<li><font face='" + FONT + "'>dichiarazione con numero protocollo <b>" + dichiarazioni_patrimonio[ i ].protocollo_dich + "</b>" +
	    					", con codice ISEENET <b>" +  dichiarazioni_patrimonio[ i ].id_dichiarazione + "</b>" +
	    					", anno reddito <b>" +  dichiarazioni_patrimonio[ i ].anno_reddito  + "</b>" +
	    					", anno patrimonio <b>" +  dichiarazioni_patrimonio[ i ].anno_patrimonio  + "</b>, " +	    					
	    					"presentata in data <b>" +
	    					dichiarazioni_patrimonio[ i ].data_presentazione_dich + "</b> a <b>" + dichiarazioni_patrimonio[ i ].ente_inseritore_dich_luogo + 
							"</b> presso l'ente <b>" + 
							dichiarazioni_patrimonio[ i ].ente_inseritore_dich + "</b>" + 
	    					"</font></li>" );
        }
    	result.append( "</ul>" );
     
    	return result.toString();
    }
    
    
    protected String createSQLDichPatrimonio(){
    	
    	String sqlDich =   
    		"SELECT distinct C_Elab.elab_date,  Doc1.protocollo,  " +
    		"Doc1.data_presentazione,  Doc1.luogo_presentazione,  Doc1.ente , Edil_familiari.id_dichiarazione , " +
    		"Edil_familiari.ID_relazione_parentela, " +
    		"Dich_icef.anno_produzione_redditi, Dich_icef.anno_produzione_patrimoni " +
    		"FROM C_Elab  " +
    		"INNER JOIN Doc ON C_Elab.id_domanda = Doc.ID  " +
    		"INNER JOIN Edil_familiari ON C_Elab.id_domanda = Edil_familiari.ID_domanda  " +
    		"INNER JOIN Doc Doc1 ON Edil_familiari.id_dichiarazione = Doc1.ID " +
    		"INNER JOIN Dich_icef ON Dich_icef.id_dichiarazione = Edil_familiari.id_dichiarazione  " +    		
    		"WHERE Doc.ID = " + IDdomanda +
    		" ORDER BY ID_relazione_parentela";
    	
    	return sqlDich;
    }
    public void loadTableDichPatrimonio(){
		doQuery( createSQLDichPatrimonio() );
		dichiarazioni_patrimonio = new DichIsee[ getRows() ];
		
		for (  int i = 1; i <= getRows(); i++ ){
			dichiarazioni_patrimonio[ i - 1 ] = new DichIsee();
			dichiarazioni_patrimonio[ i - 1 ].data_elaborazione_dichiarazione = 	DateTimeFormat.toItDate( getString( i, 1 ) );
			//dichiarazioni[ i - 1 ].fingerprint_dich = 				  	getString( i, 2 );
			dichiarazioni_patrimonio[ i - 1 ].protocollo_dich = 					getString( i, 2 );
			dichiarazioni_patrimonio[ i - 1 ].data_presentazione_dich = 			DateTimeFormat.toItDate( getString( i, 3 ) );
			dichiarazioni_patrimonio[ i - 1 ].ente_inseritore_dich_luogo = 		getString( i, 4 );
			dichiarazioni_patrimonio[ i - 1 ].ente_inseritore_dich = 				getString( i, 5 );
			dichiarazioni_patrimonio[ i - 1 ].id_dichiarazione = 					getString( i, 6 );
			dichiarazioni_patrimonio[ i - 1 ].anno_reddito = 						getString( i, 8 );
			dichiarazioni_patrimonio[ i - 1 ].anno_patrimonio = 					getString( i, 9 );
			
		}
    }
    
    protected String createSQLDich(){
    	
    	String sqlDich =   
    		"SELECT distinct C_Elab.elab_date,  Doc1.protocollo,  " +
    		"Doc1.data_presentazione,  Doc1.luogo_presentazione,  Doc1.ente , Familiari.id_dichiarazione , " +
    		"Familiari.ID_relazione_parentela, " +
    		"Dich_icef.anno_produzione_redditi, Dich_icef.anno_produzione_patrimoni " +
    		"FROM C_Elab  " +
    		"INNER JOIN Doc ON C_Elab.id_domanda = Doc.ID  " +
    		"INNER JOIN Familiari ON C_Elab.id_domanda = Familiari.ID_domanda  " +
    		"INNER JOIN Doc Doc1 ON Familiari.id_dichiarazione = Doc1.ID " +
    		"INNER JOIN Dich_icef ON Dich_icef.id_dichiarazione = Familiari.id_dichiarazione  " +    		
    		"WHERE Doc.ID = " + IDdomanda +
    		" ORDER BY ID_relazione_parentela";
    	
    	return sqlDich;
    }
    public void loadTableDich(){
	   	
		doQuery( createSQLDich() );
		dichiarazioni = new DichIsee[ getRows() ];
		
		for (  int i = 1; i <= getRows(); i++ ){
			dichiarazioni[ i - 1 ] = new DichIsee();
			dichiarazioni[ i - 1 ].data_elaborazione_dichiarazione = 	DateTimeFormat.toItDate( getString( i, 1 ) );
			//dichiarazioni[ i - 1 ].fingerprint_dich = 				  	getString( i, 2 );
			dichiarazioni[ i - 1 ].protocollo_dich = 					getString( i, 2 );
			dichiarazioni[ i - 1 ].data_presentazione_dich = 			DateTimeFormat.toItDate( getString( i, 3 ) );
			dichiarazioni[ i - 1 ].ente_inseritore_dich_luogo = 		getString( i, 4 );
			dichiarazioni[ i - 1 ].ente_inseritore_dich = 				getString( i, 5 );
			dichiarazioni[ i - 1 ].id_dichiarazione = 					getString( i, 6 );
			dichiarazioni[ i - 1 ].anno_reddito = 						getString( i, 8 );
			dichiarazioni[ i - 1 ].anno_patrimonio = 					getString( i, 9 );
			
		}
		
}
    

    private void loadTableDoc(){
    	
    	String sqlDom = 
    		"SELECT distinct " +
    		"Doc.ID_tp_stato_doc, " + //1
    		"tp_stati_doc.tp_stato_doc, " +//2
    		"R_enti_1.ente, " +//3
			"R_Servizi.servizio, " + //4
			"C_elab.elab_date, " + //5
			"R_enti.ente " + //6
			"FROM Doc " +
    		"INNER JOIN tp_stati_doc ON Doc.ID_tp_stato_doc = tp_stati_doc.ID_tp_stato_doc " +
    		"INNER JOIN Domande ON Doc.ID = Domande.ID_domanda " +
			"INNER JOIN R_Utenti ON Doc.ID_user = R_Utenti.ID_user " +
			"INNER JOIN R_Enti ON R_Utenti.ID_ente = R_Enti.ID_ente " + 
			"INNER JOIN R_Enti R_enti_1 ON Domande.ID_ente_erogatore = R_enti_1.ID_ente " +
			"INNER JOIN R_Servizi ON Domande.ID_servizio = R_Servizi.id_servizio " +
			"INNER JOIN C_elab ON C_elab.ID_domanda = Domande.ID_domanda " +
			"WHERE Doc.ID=" + IDdomanda;
    		
    	doQuery( sqlDom );
    	id_tp_stato_doc = 	Integer.parseInt( getString( 1, 1 ) );
    	stato_doc = 		getString( 1, 2 );   	
    	ente_erogatore = 	getString( 1, 3 );
    	servizio =			getString( 1, 4 );
    	data_elaborazione_dom = 	DateTimeFormat.toItDate( getString( 1, 5 ) );
    	ente_inseritore_dom = 					getString( 1, 6 );
    	crc_elain = calculateCrcElain();
    }
}
