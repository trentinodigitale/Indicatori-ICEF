/**
 *Created on 21-giu-2007
 */

package c_elab.pat.mant10;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;



public class Deroga extends ElainNode {

	
	public Deroga() {	}

	protected void reset() {
	}

	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT  deroga ");
		sql.append("FROM  AM_Richiedente ");
		sql.append("WHERE (AM_Richiedente.ID_domanda =  ");
		sql.append(IDdomanda);
		sql.append(") ");	
		
		doQuery(sql.toString());
	}

    public double getValue() {
    	
        try {
        	if( !records.getString(1, 1).equals("0") ) {
        		return 1.0;
        	}
        	return 0.0;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
