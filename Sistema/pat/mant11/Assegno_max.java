/**
 *Created on 21-giu-2007
 */

package c_elab.pat.mant11;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;
import it.clesius.util.General1;

import java.util.GregorianCalendar;


public class Assegno_max extends ElainNode {

	
	public Assegno_max() {	}

	protected void reset() {
	}

	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT   data_presentazione ");
		sql.append("FROM Doc ");
		sql.append("WHERE  (data_presentazione >= CONVERT(DATETIME, '2011-07-01 00:00:00', 102)) AND (ID =  ");
		sql.append(IDdomanda);
		sql.append(") ");		
		
		doQuery(sql.toString());
	}

    public double getValue() {
    	
        try {
        	if(records!=null && records.getRows()>0 ){
        		return 302.00;
        		
        	}else{
        		return 294.06;
        	}
            
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
