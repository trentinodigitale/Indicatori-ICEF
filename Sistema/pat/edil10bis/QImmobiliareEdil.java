/**
 *Created on 17-mar-2006
 */

package c_elab.pat.edil10bis;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import java.util.Calendar;
import it.clesius.clesius.util.Sys;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import c_elab.pat.icef.Usufrutto;
import c_elab.pat.icef.util.ElabSession;

/**
 * query per il quadro E della dichiarazione ICEF
 * 
 * @author g_barbieri
 */
public abstract class QImmobiliareEdil extends c_elab.pat.icef10.QImmobiliare {

	/** QImmobiliare constructor  */ 
	public QImmobiliareEdil() {
	}

	
	protected double getValoreICIRiga(int i) {
		return getValoreICIRiga(i,false);
	}

	//private int anno_patrimonio = 2009;
	

	/**
	 * inizializza la Table records con i valori letti dalla query sul DB
	 * 
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer
	 *            it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {
		ElabSession session;
		this.dataTransfer = dataTransfer;
		
		// legge i dati degli immobili del quadro E
		StringBuffer sql = new StringBuffer();
		
		String table = "Familiari";
		
		//											1                           		2                   				3                                        4                                     5                                     6                                          7                          8                                9                                   10						11									12								 13									14															15
		sql.append("SELECT Patrimoni_immobiliari.residenza_nucleo, R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, EDIL_immobile.immobile, EDIL_immobile.mq * Patrimoni_immobiliari.quota / 100 AS mq_quota_terreno ");
		sql.append("FROM "+table+" ");
		sql.append("INNER JOIN Patrimoni_immobiliari ON "+table+".ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
		sql.append("INNER JOIN Dich_icef ON "+table+".ID_dichiarazione = Dich_icef.ID_dichiarazione ");
		sql.append("INNER JOIN Domande ON "+table+".ID_domanda = Domande.ID_domanda ");
		sql.append("INNER JOIN Doc ON Domande.ID_domanda = Doc.ID ");
		sql.append("INNER JOIN R_Relazioni_parentela ON "+table+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("LEFT OUTER JOIN EDIL_immobile ON Patrimoni_immobiliari.immobile = EDIL_immobile.immobile AND Patrimoni_immobiliari.ID_dichiarazione = EDIL_immobile.ID_dichiarazione ");
		sql.append(" AND "+table+".ID_domanda = EDIL_immobile.ID_domanda AND "+table+".ID_dichiarazione = EDIL_immobile.ID_dichiarazione ");
		//sql.append("WHERE EDIL_immobile.immobile IS NULL AND Domande.ID_domanda = ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);
		
		super.setQuery(sql.toString());
		super.init(dataTransfer);
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