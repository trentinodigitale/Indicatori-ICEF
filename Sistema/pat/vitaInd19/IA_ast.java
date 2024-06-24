package c_elab.pat.vitaInd19;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


public class IA_ast extends ElainNode {

	protected Table tb_IA_PAT;
	protected Table tb_IA_ALTRO;

	/** IA_ast constructor */
	public IA_ast() {
	}

	/** resetta le variabili statiche 
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);

		StringBuffer sql = new StringBuffer();
		//                                     1
		sql.append(
			"SELECT Redditi_altri.importo AS dichiarato ");
		sql.append("FROM Familiari INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
		sql.append(" INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE (Redditi_altri.ID_tp_erogazione='"+VitaInd19_Params.tp_erogazioneIA+"') AND (Familiari.ID_domanda= ");
		sql.append(IDdomanda);
		sql.append(") AND R_Relazioni_parentela.ID_tp_relazione_parentela=1");
//System.out.println(sql.toString());
		doQuery(sql.toString());
		tb_IA_PAT = records;
		
		sql = null;
		sql = new StringBuffer();
		// 		   					              1         	   		         2				      	              3
		sql.append("SELECT AC_dati.importo_assegni_dpr_1124_65, AC_dati.importo_assegni_dpr_915_78, AC_dati.importo_assegni_dpr_1092_73 ");
		sql.append("FROM AC_dati ");
		sql.append("WHERE AC_dati.ID_domanda = ");
		sql.append(IDdomanda);
//System.out.println(sql.toString());
		doQuery(sql.toString());
		tb_IA_ALTRO = records;
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	
	
	
	
	public double getValue() {

		double ia_PAT = 0.0;
		double ia_ALTRO = 0.0;

		if (tb_IA_PAT != null) {
			try {
				// somma tutti gli importi letti dal quadro C5 (dichiarati)
				for (int i = 1; i <=tb_IA_PAT.getRows(); i++){
					ia_PAT = ia_PAT + (tb_IA_PAT.getDouble(i, 1));
				}
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}
		
		if (tb_IA_ALTRO != null && tb_IA_ALTRO.getRows()>0) {
			try {
				// somma tutti gli importi letti da AC_dati
				ia_ALTRO = tb_IA_ALTRO.getDouble(1, 1) + tb_IA_ALTRO.getDouble(1, 2) + tb_IA_ALTRO.getDouble(1, 3);
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}
		
		return ia_PAT + ia_ALTRO;
	}
}