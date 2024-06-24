package c_elab.pat.fgiov18;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

/**
 * 
 * @author a_pichler
 *
 */
public class Precedente extends ElainNode {

	public Precedente() {	}

	protected void reset() {
	}

	
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT     importo_corso1, importo_corso2, importo_corso3, importo_corso4 ");
		sql.append(" FROM         FGIO_Dati");
		sql.append(" WHERE (ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(")");
		
		doQuery(sql.toString());
	}
	

    public double getValue() {
    	
		double round = 1.0;
		double aggiusta = 0.01;
		
    	double importo_corso1 = 0.0;
    	double importo_corso2 = 0.0;
    	double importo_corso3 = 0.0;
    	double importo_corso4 = 0.0;
    	double tot_importi = 0.0;
    	double peso_par = 0.9;
    	try {
    		importo_corso1 = records.getDouble(1, 1) * peso_par;
    		importo_corso2 = records.getDouble(1, 2) * peso_par;
    		importo_corso3 = records.getDouble(1, 3) * peso_par;
    		importo_corso4 = records.getDouble(1, 4) * peso_par;
            
    		tot_importi = importo_corso1+importo_corso2+importo_corso3+importo_corso4;
    		tot_importi = Sys.round( tot_importi - aggiusta, round );
        	
            return tot_importi;
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
