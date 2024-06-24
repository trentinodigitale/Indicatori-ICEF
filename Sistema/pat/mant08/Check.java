/**
 *Created on 21-giu-2007
 */

package c_elab.pat.mant08;

import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;
import it.clesius.db.util.Table;
import it.clesius.util.General1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** controlla i figli minorenni e maggiorenni:
 * 		-	1 = tutti i figli minorenni;
 * 		-	0 = almeno un figlio è maggiorenne
 * @author s_largher
 */
public class Check extends QFigli {

	private Calendar datarif  = Calendar.getInstance();
	private Table recordDataPres=null;
	
	public Check() {
		datarif.set(2009, 5, 15, 23, 59); // 15 giugno 2009
	}

    protected void reset() {
	}

	public void init(RunawayData dataTransfer) {
		
		super.init(dataTransfer);
	            
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT Doc.data_presentazione ");
		sql.append("FROM Doc ");
		sql.append("WHERE (Doc.id = ");
		sql.append(IDdomanda);
		sql.append(")");
		
		try {
			recordDataPres=dataTransfer.executeQuery(sql.toString());
        } catch (DBException e) {
        	System.out.println("Errore in Check.init: " + e.toString()) ; 
        }
	}

    public double getValue() {
    	
		Calendar datanascita = Calendar.getInstance();
		int check = 0;
        try {
        	String data_presentazione = (String)recordDataPres.getElement(1,1);
            Calendar dataPres = General1.getStringToCalendar(DateTimeFormat.toItDate(data_presentazione));
            for (int i=1; i<=records.getRows(); i++){
            	Calendar data_nascita_18 = General1.getStringToCalendar(DateTimeFormat.toItDate((String)records.getElement(i,1)));
            	data_nascita_18.add(Calendar.YEAR, 18);
            	if(dataPres.after(data_nascita_18)){
            		//figlio non minorenne
            		return 1.0;
    			}
            }
            return check;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 1.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 1.0;
        }
    }
}
