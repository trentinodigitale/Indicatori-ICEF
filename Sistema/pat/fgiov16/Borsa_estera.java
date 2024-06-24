package c_elab.pat.fgiov16;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

/**
 * 
 * @author a_pichler
 *
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
    	double round = 1.0;
		double aggiusta = 0.01;
		
    	double borsa_estera = 0.0;
    	try {
    		borsa_estera = records.getDouble(1, 1);
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
