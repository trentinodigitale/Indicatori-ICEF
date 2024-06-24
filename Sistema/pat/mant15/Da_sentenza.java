package c_elab.pat.mant15;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/**
 * 
 * @author a_pichler
 *
 */
public class Da_sentenza extends ElainNode {

	
	public Da_sentenza() {	}

	protected void reset() {
	}

	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT   tit_es_importo ");
		sql.append("FROM   AM_Richiedente ");
		sql.append("WHERE (AM_Richiedente.ID_domanda =  ");
		sql.append(IDdomanda);
		sql.append(") ");		
		
		doQuery(sql.toString());
	}

    public double getValue() {
    	
        try {
            return records.getDouble(1, 1);
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
