/**
 *Created on 23-mag-2006
 */

package c_elab.pat.ld08;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;
import it.clesius.util.General1;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * legge l'importo massimo in base al tipo di autorizzazione
 * 
 * @author g_barbieri
 */
public class Somma_gg extends ElainNode {
	private GregorianCalendar data_max = null;   
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	protected void reset() {
        data_max = null; 
	}

	public void init(RunawayData dataTransfer) {
	
	    super.init(dataTransfer);
	    
	    
	    StringBuffer sql = new StringBuffer();
	    	sql.append("SELECT MIN(data) AS mindata ");
			sql.append("FROM (SELECT ID_domanda, pensDir_data AS data ");
			sql.append("FROM  PNS_richiedente ");
			sql.append("UNION ");
			sql.append("SELECT ID_domanda, etaPens_dal ");
			sql.append("FROM PNS_richiedente AS PNS_richiedente_2 ");
			sql.append("UNION ");
			sql.append("SELECT ID_domanda, anniContr_dal ");
			sql.append("FROM PNS_richiedente AS PNS_richiedente_1) AS p ");
			sql.append("WHERE (ID_domanda= ");
			sql.append(IDdomanda);
			sql.append(") GROUP BY ID_domanda ");
			
			doQuery(sql.toString());
			
			if(super.getTable()!=null && super.getRows()>0){
				try {
					data_max = General1.getStringToCalendar(DateTimeFormat.toItDate((String) records.getElement(1,1)));
		           } catch (Exception e) {
	            	e.printStackTrace();
	            }
			}
			sql = new StringBuffer();
			sql.append("SELECT dal, al, giorni ");
			sql.append("FROM  PNS_periodi ");
			sql.append("WHERE ID_domanda =  ");
			sql.append(IDdomanda);
			sql.append(" ");		
			
			doQuery(sql.toString());
			
       
	}
	
	
	public double getValue() {
    	try {
        	int totGiorni=0;
        	if(records!=null && records.getRows()>0){
	        	for(int i=1; i<=records.getRows(); i++){
	        		
	        		String data_dal = (String) records.getElement(i, 1);
	        		String data_al = (String) records.getElement(i, 2);
	        		int giorni =  records.getInteger(i, 3);
	        		
	        		if(giorni==0){
		        		GregorianCalendar dal = General1.getStringToCalendar(DateTimeFormat.toItDate(data_dal));
		        		GregorianCalendar al = General1.getStringToCalendar(DateTimeFormat.toItDate(data_al));
		        		if( data_max!= null && al.after(data_max)){al=data_max;}

		        		if(data_max== null || dal.before(data_max)){
		        			giorni=(int)getGiorni(dal, al);
		        		}
		        	}
	        		totGiorni=giorni+totGiorni;
	        	}
        	}
            return totGiorni;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
	
	public double getGiorni(GregorianCalendar dal, GregorianCalendar al){
		
		//Differenza in giorni tra due date
		long milliseconds1 = dal.getTimeInMillis();
		long milliseconds2 = al.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		double diffDays = diff / (24 * 60 * 60 * 1000); //
		return  diffDays;
	}
	
	
}