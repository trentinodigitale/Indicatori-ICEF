package c_elab.pat.interStrao17;

import it.clesius.db.sql.RunawayData;

/**
 * legge i valori del quadro F dove residenza è false (0)
 * 
 * @author g_barbieri
 */
public class QImmobiliare extends c_elab.pat.icef17.QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		// se residenza = false ritorna gli immobili oltre la residenza
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean residenza = false;
		return getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
		
	}
	
	/**
	 * inizializza la Table records con i valori letti dalla query sul DB
	 * 
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer
	 *            it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		StringBuffer sql = new StringBuffer();
		//      																										 1
		sql.append("SELECT CASE WHEN Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' THEN -1 ELSE 0 END as residenza_nucleo, ");
        //  									  2                   				3                                        4                                     5                                     6                                          7                          8                             9                        			10								11										12								 13						   14					   15
		sql.append("R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, Dich_icef.anno_attualizzato, Dich_icef.mese ");
		sql.append("FROM Familiari_ext ");
		sql.append("INNER JOIN Patrimoni_immobiliari ON Familiari_ext.ID_dichiarazione_ext = Patrimoni_immobiliari.ID_dichiarazione ");
		sql.append("INNER JOIN Dich_icef ON Familiari_ext.ID_dichiarazione_ext = Dich_icef.ID_dichiarazione ");
		sql.append("INNER JOIN Domande ON Familiari_ext.ID_domanda = Domande.ID_domanda ");
		sql.append("INNER JOIN Doc ON Familiari_ext.ID_domanda = Doc.ID ");
        sql.append("INNER JOIN Familiari ON Familiari_ext.ID_domanda = Familiari.ID_domanda AND Familiari_ext.ID_dichiarazione = Familiari.ID_dichiarazione ");
		sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE (Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' OR Patrimoni_immobiliari.ID_tp_uso_immobile = 'ALT') ");
		sql.append("AND Domande.ID_domanda = ");
		sql.append(IDdomanda);
		
		super.setQuery(sql.toString());
		super.init(dataTransfer);
	}
}