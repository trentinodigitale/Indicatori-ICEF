package c_elab.pat.du12.acq;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.du.PassaValoriDu2012;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QContrPotAcqDatiAttualizzati extends ElainNode {

	protected Table datiC1Dip;
	protected Table datiC2;
	protected Table datiC3;
	protected Table datiC4;
	protected Table datiC5;
	protected Table imposte;

	/** QProvvisorio constructor */
	public QContrPotAcqDatiAttualizzati() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QContrPotAcqDatiAttualizzati.class.getName(), IDdomanda );
		datiC1Dip = null;
		datiC2 = null;
		datiC3 = null;
		datiC4 = null;
		datiC5 = null;
		imposte = null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QContrPotAcqDatiAttualizzati.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			StringBuffer sql = new StringBuffer();
			
			//                                  		1                                     2                            3						4
			sql.append("SELECT Redditi_dipendente.ID_tp_reddito, R_Relazioni_parentela.peso_reddito, Redditi_dipendente.importo, Familiari.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_dipendente ON Familiari.ID_dichiarazione = Redditi_dipendente.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");

			doQuery(sql.toString());
			datiC1Dip = records;
			
			sql = new StringBuffer();
			//         									 1                            2                            3                             4                                 5                             6						7
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_agricoli.quantita, R_Importi_agricoli.importo, Redditi_agricoli.costo_locazione, Redditi_agricoli.costo_dipendenti, Redditi_agricoli.quota, Familiari.ID_dichiarazione  ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN Redditi_agricoli ON Familiari.ID_dichiarazione = Redditi_agricoli.ID_dichiarazione ");
			sql.append("INNER JOIN R_Importi_agricoli ON (Redditi_agricoli.ID_tp_agricolo = R_Importi_agricoli.ID_tp_agricolo) AND (Redditi_agricoli.ID_zona_agricola = R_Importi_agricoli.ID_zona_agricola) AND (Redditi_agricoli.ID_dich = R_Importi_agricoli.ID_dich) ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");
			
			doQuery(sql.toString());
			datiC2 = records;
			
			sql = new StringBuffer();
			//          									1                              2							3
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_impresa.reddito_dichiarato, Familiari.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_impresa ON Familiari.ID_dichiarazione = Redditi_impresa.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");
			
			doQuery(sql.toString());
			datiC3 = records;
			
			sql = new StringBuffer();
			//          								   1                                     2                                         3                                4								   5					   6
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_partecipazione.reddito_dichiarato, Redditi_partecipazione.utile_fiscale, Redditi_partecipazione.quota, Redditi_partecipazione.ID_tp_impresa, Familiari.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_partecipazione ON Familiari.ID_dichiarazione = Redditi_partecipazione.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");
			
			doQuery(sql.toString());
			datiC4 = records;
			
			sql = new StringBuffer();
			//											   1					   2						   3
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_altri.importo, Redditi_altri.ID_tp_erogazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");
			
			doQuery(sql.toString());
			datiC5 = records;
			
			sql = new StringBuffer();
			//          							1                                    2                     3						4
			sql.append("SELECT Detrazioni.ID_tp_detrazione, R_Relazioni_parentela.peso_reddito, Detrazioni.importo, Detrazioni.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Detrazioni ON Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");
			
			doQuery(sql.toString());
			imposte = records;
            
			sql = new StringBuffer();
			
			//										1									2
			sql.append("SELECT   Familiari.ID_dichiarazione, DU_attualizzazione.ID_tp_reddito_attualizzato, "); 
			//										3									4
			sql.append("DU_attualizzazione.mensilita_antecedente, DU_attualizzazione.mensilita_2antecedente ");
			sql.append("FROM  DU_attualizzazione INNER JOIN ");
			sql.append("Familiari ON DU_attualizzazione.ID_dichiarazione = Familiari.ID_dichiarazione AND DU_attualizzazione.ID_domanda = Familiari.ID_domanda ");
			sql.append("WHERE     (Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");

			doQuery(sql.toString());			

			session.setInitialized( true );
			session.setRecords( records );
			
			session.setAttribute("datiC1Dip", datiC1Dip);
			session.setAttribute("datiC2", datiC2);
			session.setAttribute("datiC3", datiC3);
			session.setAttribute("datiC4", datiC4);
			session.setAttribute("datiC5", datiC5);
			session.setAttribute("imposte", imposte);

		} else {
			records = session.getRecords();
			datiC1Dip = (Table)session.getAttribute("datiC1Dip");
			datiC2 = (Table)session.getAttribute("datiC2");
			datiC3 = (Table)session.getAttribute("datiC3");
			datiC4 = (Table)session.getAttribute("datiC4");
			datiC5 = (Table)session.getAttribute("datiC5");
			imposte = (Table)session.getAttribute("imposte");
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
