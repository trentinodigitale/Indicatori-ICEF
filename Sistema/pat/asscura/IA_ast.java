package c_elab.pat.asscura;

import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 * Attenzione usata anche da pat/ia/pat.ia15.net.html
 * @author a_mazzon
 *
 */
public class IA_ast extends ElainNode {

	//CAMBIAMI: va cambiata ogni anno
	private static	String	tp_erogazioneIA		= "060";

	protected		Table	tb_IA_PAT=null;;
	protected		Table	tb_IA_ALTRO=null;;

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

		Calendar comapre= Calendar.getInstance();
		comapre.set(Calendar.YEAR, 2016);
		comapre.set(Calendar.DAY_OF_MONTH, 1);
		comapre.set(Calendar.MONTH, 6);
		comapre.set(Calendar.HOUR, 1);
	
		try {
			Table tb=this.dataTransfer.executeQuery("Select data_presentazione from doc where id="+IDdomanda);
			Calendar data_presentazione=tb.getCalendar(1, 1);
			if(data_presentazione.after(comapre)){
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
		}


		
		StringBuffer sql = new StringBuffer();
		// 01. Redditi_altri.importo AS dichiarato
		sql.append("SELECT Redditi_altri.importo AS dichiarato ");
		sql.append("FROM Familiari ");
		sql.append(" INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
		sql.append(" INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append(" WHERE Redditi_altri.ID_tp_erogazione='"+tp_erogazioneIA+"' AND Familiari.ID_domanda= ");
		sql.append(IDdomanda);		
		sql.append(" AND R_Relazioni_parentela.ID_tp_relazione_parentela = 1");
		doQuery(sql.toString());
		tb_IA_PAT = records;
		
		sql = null;
		sql = new StringBuffer();
		/*
		 * 01. AC_dati.importo_assegni_dpr_1124_65, 
		 * 02. AC_dati.importo_assegni_dpr_915_78, 
		 * 03. AC_dati.importo_assegni_dpr_1092_73
		 */
		sql.append("SELECT AC_dati.importo_assegni_dpr_1124_65, AC_dati.importo_assegni_dpr_915_78, AC_dati.importo_assegni_dpr_1092_73 ");
		sql.append("FROM AC_dati ");
		sql.append("WHERE AC_dati.ID_domanda = ");
		sql.append(IDdomanda);
		doQuery(sql.toString());
		tb_IA_ALTRO = records;
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	
	
	
	/**
	 * 
	 */
	public double getValue() {

		double ia_PAT		= 0.0;
		double ia_ALTRO		= 0.0;

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