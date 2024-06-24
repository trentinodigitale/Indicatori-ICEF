package c_elab.pat.vitaInd21;


import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;


public abstract class QDati extends ElainNode {

	public Table datiC5					= null;
	private double						DOUBLE_25000		= 25000.00;

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QDati.class.getName(), IDdomanda );
		datiC5				= null;
	}



	/** inizializza la Table iAcDati con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QDati.class.getName(), IDdomanda  );
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();


			/*Recupero dati del C5 delle dichiarazioni connesse
			 * 01. R_Relazioni_parentela.peso_reddito, 
			 * 02. Redditi_altri.importo, 
			 * 03. Redditi_altri.ID_tp_erogazione
			 */
			sql = new StringBuffer();
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_altri.importo, Redditi_altri.ID_tp_erogazione ");
			sql.append(" FROM Familiari ");
			sql.append(" INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append(" INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(" INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append(" WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			datiC5 = records;
			
			sql = new StringBuffer();
			// 		   					1         	   		         2                       
			sql.append("SELECT Doc.data_presentazione,   ");
			sql.append("Soggetti.data_nascita   ");
			sql.append("FROM Doc INNER JOIN ");
			sql.append("Domande ON Doc.ID = Domande.ID_domanda INNER JOIN ");
			sql.append("Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			System.out.println(sql.toString());
			doQuery(sql.toString());
			
		} else {
			records				= session.getRecords();
			datiC5				= (Table)session.getAttribute("datiC5");;
		}
	}

	public double getFranchigia_ast(){

		return DOUBLE_25000;

	}
	
	public double getCheck(){
			return 0;
	}


	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}
