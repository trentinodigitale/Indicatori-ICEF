package c_elab.pat.aup21.quotaA;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.clesius.util.Sys;

/**
 * 
 * @author g_barbieri
 *
 */
public class PDC extends ElainNode {

	private String idTpErogazionePdC = "047";

	public PDC() {	
	}

	protected void reset() {
		ElabContext.getInstance().resetSession( PDC.class.getName(), IDdomanda );
	}

	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  PDC.class.getName(), IDdomanda  );
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			sql = new StringBuffer();
			//											   1					   2						   3
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_altri.importo, Redditi_altri.ID_tp_erogazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
		}else{
			records = session.getRecords();
		}
	}


	public double getValue() {

		//if (records == null)
			return 0.0;

//		double tot = 0.0;
//		double round = 1.0;
//		double aggiusta = 0.01;
//
//		try {
//			for (int i = 1; i <= records.getRows(); i++) {
//				String idTpErogazione = (String) records.getElement(i, 3);
//				
//				if(idTpErogazione.equals(idTpErogazionePdC))
//				{
//					tot = tot + Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0;
//				}
//			}
//			return tot;
//		} catch (NullPointerException n) {
//			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
//			return 0.0;
//		} catch (NumberFormatException nfe) {
//			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
//			return 0.0;
//		}
	}
}