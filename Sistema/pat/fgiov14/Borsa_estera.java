package c_elab.pat.fgiov14;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

/** 
 * @author s_largher
 */
public class Borsa_estera extends ElainNode {

	public Borsa_estera() {	}

	protected void reset() {
	}

	
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT     borsa_estera ");
		sql.append(" FROM         FGIO_Dati");
		sql.append(" WHERE (ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(")");
		
		doQuery(sql.toString());
	}
	

    public double getValue() {
    	double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		
    	double borsa_estera = 0.0;
    		try {
    		borsa_estera = (new Double((String) records.getElement(1, 1)).doubleValue());
    		borsa_estera = Sys.round( borsa_estera - aggiusta, round );
        	
            return borsa_estera;
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
