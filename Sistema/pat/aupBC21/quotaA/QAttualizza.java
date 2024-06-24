package c_elab.pat.aupBC21.quotaA;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.PassaValoriIcef;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QAttualizza extends ElainNode {

	protected Table datiC1Dip;
	protected Table datiC2;
	protected Table datiC3;
	protected Table datiC4;
	protected Table datiC5;
	protected Table imposte;
	protected Table datiDoc; 
	
	/** QProvvisorio constructor */
	public QAttualizza() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QAttualizza.class.getName(), IDdomanda );
		datiC1Dip 	= 	null;
		datiC2 		= 	null;
		datiC3 		= 	null;
		datiC4 		= 	null;
		datiC5 		= 	null;
		imposte 	= 	null;
		datiDoc		=	null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QAttualizza.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			StringBuffer sql = new StringBuffer();
			//  		1                            
			sql.append("SELECT Doc.data_presentazione ");
			sql.append("FROM Doc ");
			sql.append("WHERE Doc.ID = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			datiDoc = records;

			sql = new StringBuffer();
			//                                  		1                                     2                            3						4
			sql.append("SELECT Redditi_dipendente.ID_tp_reddito, R_Relazioni_parentela.peso_reddito, Redditi_dipendente.importo, Familiari.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_dipendente ON Familiari.ID_dichiarazione = Redditi_dipendente.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			
			String componentiPV =  PassaValoriIcef.getID_dichiarazioni(IDdomanda); //classe metodo DefComponentiDich
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}


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
			
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}

			
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
			
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}

			
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
			
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}

			
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
			
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}
			
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
			
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}
			
			doQuery(sql.toString());
			imposte = records;
            
			sql = new StringBuffer();
			//						            					1						            		2					  				    3		 
			sql.append("SELECT AUP_familiari_attualizzata.ID_attualizzata, AUP_familiari_attualizzata.ID_dichiarazione, AUP_familiari_attualizzata.riduzione_orario, "); 
			//										      4									                   5					  				        6
			sql.append("AUP_familiari_attualizzata.cessaz_lav_dip_indeter, AUP_familiari_attualizzata.cessaz_lav_dip_det, AUP_familiari_attualizzata.sosp_lav_dip_det, "); 
			//										     7										     8					  				           9
			sql.append("AUP_familiari_attualizzata.cessaz_ammort, AUP_familiari_attualizzata.cessaz_lav_atipico, AUP_familiari_attualizzata.cessaz_lav_aut, "); 
			//									           10								               11
			sql.append("AUP_familiari_attualizzata.lav_imp_mese_prec, AUP_familiari_attualizzata.lav_imp_due_mesi_prec ");
			
			sql.append("FROM  AUP_familiari_attualizzata ");
			sql.append("WHERE     (AUP_familiari_attualizzata.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
			
			String componentiAttualizzata =  PassaValoriIcef.getID_dichiarazioniDomandaAttualizzata(IDdomanda); //classe metodo DefComponentiDich
			if(componentiAttualizzata != null && componentiAttualizzata.length()>0){
				sql.append(" AND AUP_familiari_attualizzata.id_attualizzata in ");
				sql.append(componentiAttualizzata);
			}
			
			// TODO
			// gestire il periodo di attualizzazione passato dalla servlet calcolo --> AND AUP_familiari_attualizzata.ID_attualizzata = [valore passato dalla servlet]
			// TODO
			sql.append("ORDER BY AUP_familiari_attualizzata.ID_dichiarazione ");
			
			doQuery(sql.toString());			

			session.setInitialized( true );
			session.setRecords( records );
			
			session.setAttribute("datiC1Dip", datiC1Dip);
			session.setAttribute("datiC2", datiC2);
			session.setAttribute("datiC3", datiC3);
			session.setAttribute("datiC4", datiC4);
			session.setAttribute("datiC5", datiC5);
			session.setAttribute("imposte", imposte);
			session.setAttribute("datiDoc", datiDoc);

		} else {
			records = session.getRecords();
			datiC1Dip = (Table)session.getAttribute("datiC1Dip");
			datiC2 = (Table)session.getAttribute("datiC2");
			datiC3 = (Table)session.getAttribute("datiC3");
			datiC4 = (Table)session.getAttribute("datiC4");
			datiC5 = (Table)session.getAttribute("datiC5");
			imposte = (Table)session.getAttribute("imposte");
			datiDoc= (Table)session.getAttribute("datiDoc");
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