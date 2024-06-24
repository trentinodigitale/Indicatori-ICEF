package c_elab.pat.barch16;

import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import c_elab.pat.icef14.QImmobiliare;

/**
 * query per il quadro E della dichiarazione ICEF
 * 
 * @author s_largher
 */
public abstract class QTerreniBar extends c_elab.pat.icef15.QImmobiliare {

	/** QImmobiliare constructor  */ 
	public QTerreniBar() {
	}
	
	protected double getValoreICIRiga(int i) {
		return getValoreICIRiga(i,false);
	}

	protected void reset() {
		ElabContext.getInstance().resetSession( QTerreniBar.class.getName(), IDdomanda );
	}
	
	/**
	 * inizializza la Table records con i valori letti dalla query sul DB
	 * 
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer
	 *            it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QTerreniBar.class.getName(), IDdomanda );
		super.setDataTransfer(dataTransfer);
		if (!session.isInitialized()) {
			
			// legge i dati degli immobili del quadro E
			StringBuffer sql = new StringBuffer();
			
			String table = "Familiari";
			
			sql.append("SELECT 0 as residenza_nucleo, ");
			//                           		      	2                   				3                                        4                                     5                                     6                                          7                          8                                9                                   10						11									12								 13									14					15
			sql.append("R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, BAR_immobili.immobile, BAR_immobili.mq * Patrimoni_immobiliari.quota / 100 AS mq_quota_terreno ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Patrimoni_immobiliari ON Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
			sql.append("INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("LEFT OUTER JOIN BAR_immobili ON Patrimoni_immobiliari.immobile = BAR_immobili.immobile AND Patrimoni_immobiliari.ID_dichiarazione = BAR_immobili.ID_dichiarazione ");
			sql.append(" AND "+table+".ID_domanda = BAR_immobili.ID_domanda AND "+table+".ID_dichiarazione = BAR_immobili.ID_dichiarazione ");
			sql.append("WHERE (Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' OR Patrimoni_immobiliari.ID_tp_uso_immobile = 'ALT') AND (Patrimoni_immobiliari.ID_tp_immobile = 'TE') ");
			sql.append("AND Domande.ID_domanda = ");
			sql.append(IDdomanda);

			//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
			String defDichType = DefDichElabConst.F;
			String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
			if(componenti != null && componenti.length()>0)
			{
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componenti);
				testPrintln(sql.toString());
			}
			doQuery(sql.toString());
			session.setInitialized( true );
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}

	}

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}