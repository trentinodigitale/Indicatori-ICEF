/**
 *Created on 21-giu-2007
 */

package c_elab.pat.mant09;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** 
 * @author a_pichler
 */
public class Precedente extends ElainNode {

	//CAMBIAMI: va cambiata ogni anno
	protected static String ID_tp_erogazione_assegni_figli= "980";
	protected static int ID_relazione_parentela_richiedente= 1685; 

	public Precedente() {	}

	protected void reset() {
	}

	
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT Redditi_altri.importo,  AM_Richiedente.importo_indennita_accompagnamento ");
		sql.append(" FROM Familiari INNER JOIN");
		sql.append(" AM_Richiedente ON Familiari.ID_domanda = AM_Richiedente.ID_domanda LEFT OUTER JOIN");
		sql.append(" Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione AND Redditi_altri.ID_tp_erogazione =  '");
		sql.append(ID_tp_erogazione_assegni_figli);
		sql.append("' WHERE  (Familiari.ID_relazione_parentela = ");
		sql.append(ID_relazione_parentela_richiedente);
		sql.append(") AND (Familiari.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(")");
		
		doQuery(sql.toString());
	}
	

    public double getValue() {
    	double c5_tot = 0.0;
    	double inden_acc =0.0;
        try {
        	
        	inden_acc = (new Double((String) records.getElement(1, 2)).doubleValue());
        	for (int i = 1; i <= records.getRows(); i++) {
        		c5_tot = c5_tot + (new Double((String) records.getElement(i, 1)).doubleValue());
    		}
            return java.lang.Math.min(c5_tot, inden_acc);
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
