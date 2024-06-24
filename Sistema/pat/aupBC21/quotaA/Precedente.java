package c_elab.pat.aupBC21.quotaA;

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
public class Precedente extends ElainNode {

	//VERIFICAMI: verificare che siano la stesse ogni anno che cambia la dichiarazione ICEF
	//dall'AUP 2019 aggiungere SIA/REI e AUP quota A
	private String idTpErogazioneSussidioStraordinario = "102";
	private String idTpErogazioneRedditoGaranziaSOCIALE = "113";
	private String idTpErogazioneRedditoGaranziaAPAPI = "027";
	private String idTpErogazioneRedditoQuotaA		 = "042";
	

	public Precedente() {	
	}

	protected void reset() {
		ElabContext.getInstance().resetSession( Precedente.class.getName(), IDdomanda );
	}

	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  Precedente.class.getName(), IDdomanda  );
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

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				String idTpErogazione = (String) records.getElement(i, 3);
				
				if(idTpErogazione.equals(idTpErogazioneSussidioStraordinario))
				{
					tot = tot + Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0;
				}
				else if(idTpErogazione.equals(idTpErogazioneRedditoGaranziaSOCIALE))
				{
					tot = tot + Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0;
				}
				else if(idTpErogazione.equals(idTpErogazioneRedditoGaranziaAPAPI))
				{
					tot = tot + Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0;
				}
				else if(idTpErogazione.equals(idTpErogazioneRedditoQuotaA))
				{
					tot = tot + Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0;
				}
			}
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}