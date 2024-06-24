package c_elab.pat.maternitaIsee;

import java.util.Calendar;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;
import it.clesius.db.util.Table;
import it.clesius.util.General1;
/**
 * 
 */
public abstract class QDati extends ElainNode {
    
	protected Table 		dati;
	protected Table         minori;
	private Calendar		data;		
	
	
    protected void reset() {
    	  ElabStaticContext.getInstance().resetSession( QDati.class.getName(), IDdomanda, "records" );
    	  dati = null;
    	  minori = null;
    	  data = null;
    }
    

    public void init(RunawayData dataTransfer) {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDati.class.getName(), IDdomanda, "records" );
    	
    	try {
	        if (!session.isInitialized()) {
	            super.init(dataTransfer);
	            
	            doQuery(
		            "SELECT Doc.data_presentazione, "		
		            + " Assegni.deduzioni, "
		            + " domande.id_tp_pagamento, " 
		            + " domande.codice_stato, " 
		            + " domande.CIN_pagamento, " 
		            + " domande.ABI_pagamento, " 
		            + " domande.CAB_pagamento, " 
		            + " domande.Cc_pagamento, " 
		            + " domande.IBAN, "
		            + " domande.ID_provincia_residenza, "
		            + " coalesce(domande_permessi_soggiorno.id_tp_permesso, 0) as id_tp_permesso, "
		            + " domande.escludi_ufficio, "
		            + " domande.isee_non_conforme,"
		            + " domande.id_dichiarazione, "
		            + " assegni.is_richiesta"
		            + " FROM doc "
		            + " inner join domande on doc.id=domande.id_domanda " 
		            + " INNER JOIN Assegni ON domande.id_domanda = Assegni.id_domanda "
		            + " LEFT OUTER JOIN domande_permessi_soggiorno on domande.ID_domanda = domande_permessi_soggiorno.id_domanda "
		            + " WHERE domande.id_domanda= " + IDdomanda + ";"
		            );
	            dati = records;
	            
	            doQuery(
                    "SELECT assegni_minori.id_domanda, "		
                    + " assegni_minori.id_minore, "
                    + " assegni_minori.data_nascita, "
                    + " assegni_minori.data_ingresso_famiglia "
                    + " FROM assegni_minori "
                    + " WHERE assegni_minori.id_domanda = " + IDdomanda + ";"
                );
	            minori = records;
	            
	            session.setAttribute("dati", dati);
	            session.setAttribute("minori", minori);
	            
	            data = getDataFiglio();
	            
	        } else {
	        	dati = (Table)session.getAttribute("dati");
	        	minori = (Table)session.getAttribute("minori");
	        	data = null;
	        }
    	 } catch(NullPointerException n) {
             System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
         }
        
    }
    
    
    public QDati(){
    }
    
    
    public Calendar getDataFiglio() {
		
		Calendar data_minore 	= Calendar.getInstance();
    	Calendar data 			= Calendar.getInstance();
		
		try {
        	super.init(dataTransfer);
        	for (int i=1; i<=minori.getRows(); i++){
        		String data_nascita_string  =  (String) minori.getString(i, minori.getIndexOfColumnName("data_nascita"));
        		Calendar data_nascita = General1.getStringToCalendar(DateTimeFormat.toItDate(data_nascita_string)); 
        		String data_ingresso_string =  (String) minori.getString(i, minori.getIndexOfColumnName("data_ingresso_famiglia"));
        		Calendar data_ingresso = General1.getStringToCalendar(DateTimeFormat.toItDate(data_ingresso_string)); 
        		
        		if (data_ingresso == null){
        			data_minore = data_nascita;
        		} else {
        			// caso in cui c'è un figlio adottato/affidato
        			if(data_nascita.before(data_ingresso) || data_nascita.equals(data_ingresso)){
        				data_minore = data_ingresso;
        			}else {
        				data_minore = data_nascita;
        			}
        		}
        		
        		if(data_minore.before(data)){
        			data = data_minore;
        		}
        	}
            
            return data;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return data;
        }
    }
    
    


	public void setData(Calendar data) {
		this.data = data;
	}


	public Calendar getData() {
		return data;
	}
}

