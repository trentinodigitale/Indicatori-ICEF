package c_elab.pat.mant17;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/**
 * 
 * @author a_pichler
 *
 */
public class Precedente extends ElainNode {

	//CAMBIAMI: va CONTROLLATA ogni anno
	protected static String ID_tp_erogazione_assegni_figli= "980";
	protected static String ID_tp_erogazione_assegni_figli_anticipo= "114";
	
	protected static int ID_tp_relazione_parentela_richiedente= 1; 

	public Precedente() {	}

	protected void reset() {
	}

	
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT Redditi_altri.importo,  AM_Richiedente.importo_indennita_accompagnamento ");
		sql.append(" FROM Familiari ");
		sql.append(" INNER JOIN R_Relazioni_parentela on Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append(" INNER JOIN AM_Richiedente ON Familiari.ID_domanda = AM_Richiedente.ID_domanda LEFT OUTER JOIN");
		sql.append(" Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione AND (Redditi_altri.ID_tp_erogazione =  '");
		sql.append(ID_tp_erogazione_assegni_figli);
		sql.append("' OR Redditi_altri.ID_tp_erogazione ='");
		sql.append(ID_tp_erogazione_assegni_figli_anticipo);
		sql.append("') WHERE  (R_Relazioni_parentela.ID_tp_relazione_parentela = ");
		sql.append(ID_tp_relazione_parentela_richiedente);
		sql.append(") AND (Familiari.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(")");
		
		doQuery(sql.toString());
	}
	

    public double getValue() {
    	double c5_tot = 0.0;
      try {
        	
        	for (int i = 1; i <= records.getRows(); i++) {
        		c5_tot = c5_tot + (new Double((String) records.getElement(i, 1)).doubleValue());
    		}
        	return c5_tot;

        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
