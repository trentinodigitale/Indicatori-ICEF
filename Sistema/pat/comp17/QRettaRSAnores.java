package c_elab.pat.comp17;


import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;


public abstract class QRettaRSAnores extends ElainNode {

	public Table datiIND				= null;
	public Table datiRSA				= null;

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QRettaRSAnores.class.getName(), IDdomanda );
		datiIND				= null;
		datiRSA				= null;
	}



	/** inizializza la Table iAcDati con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {
		int it_tp_relazione_parentela_coniuge_non_residente = 12;
    	String	it_tp_erogazioneIndennitaPerCiechiInvalidiSordomuti="060";
    	String	it_tp_erogazionePensioniAssegniPerCiechiInvalidiSordomuti="061";
		ElabSession session =  ElabContext.getInstance().getSession(  QRettaRSAnores.class.getName(), IDdomanda  );
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();


			 sql = new StringBuffer();
            //								1						2						  
            sql.append("SELECT Domande.ID_domanda, Redditi_altri.importo ");        
    		sql.append("	FROM Domande INNER JOIN COMP_Dati ON Domande.ID_domanda = COMP_Dati.ID_domanda "); 
    		sql.append("	INNER JOIN Familiari  ON Domande.ID_domanda = Familiari.ID_domanda "); 
    		sql.append("	INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
    		sql.append("	INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
    		sql.append("	INNER JOIN Redditi_altri ON Dich_icef.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
            sql.append(" WHERE Domande.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(" AND R_Relazioni_parentela.ID_tp_relazione_parentela="+it_tp_relazione_parentela_coniuge_non_residente+" AND (Redditi_altri.ID_tp_erogazione='"+it_tp_erogazioneIndennitaPerCiechiInvalidiSordomuti+"' Or Redditi_altri.ID_tp_erogazione='"+it_tp_erogazionePensioniAssegniPerCiechiInvalidiSordomuti+"')");

			doQuery(sql.toString());
			datiIND = records;
			
			sql = new StringBuffer();
			// 		   					1         	   		         2                       
			sql.append("SELECT Domande.ID_domanda, COMP_Dati.rettaRSA_nores ");
    		sql.append(" FROM Domande INNER JOIN Familiari  ON Domande.ID_domanda = Familiari.ID_domanda "); 
    		sql.append(" INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
    		sql.append(" INNER JOIN COMP_Dati ON Domande.ID_domanda = COMP_Dati.ID_domanda");
    		sql.append(" WHERE (Domande.ID_domanda =");
    		sql.append(IDdomanda);
    		sql.append(") AND R_Relazioni_parentela.ID_tp_relazione_parentela="+it_tp_relazione_parentela_coniuge_non_residente+" ;");
			
			doQuery(sql.toString());
			datiRSA = records;
			
		} else {
			records				= session.getRecords();
			datiRSA				= (Table)session.getAttribute("datiRSA");
			datiIND  			= (Table)session.getAttribute("datiIND");
		}
	}

	
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}
