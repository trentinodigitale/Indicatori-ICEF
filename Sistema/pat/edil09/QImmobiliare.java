/**
 *Created on 17-mar-2006
 */

package c_elab.pat.edil09;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import java.util.Calendar;
import it.clesius.clesius.util.Sys;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import c_elab.pat.icef.Usufrutto;

/**
 * query per il quadro E della dichiarazione ICEF
 * 
 * @author g_barbieri
 */
public abstract class QImmobiliare extends c_elab.pat.icef09.QImmobiliare {

	/** QImmobiliare constructor  */ 
	public QImmobiliare() {
	}

	
	protected double getValoreICIRiga(int i) {
		return getValoreICIRiga(i,false);
	}

	
	/*
	protected double getValoreICIRiga(int i) {
		int yearRif = 2000;
		int monthRif = 12;
		int dayRif = 31;
		double round = 1.0;
		double aggiusta = 0.01;
		double val_ici_pesato = 0.0;
		int durata;
		Calendar theDate;

		try {
			yearRif = new Integer((String) records.getElement(i, 9)).intValue();
			Calendar dataRif = Calendar.getInstance();
			dataRif.set(yearRif, monthRif - 1, dayRif);
			//N.B. il valore ICI è già rapportato alla quota di possesso del patrimonio immobiliare (vedi query)
			double val_ici = Sys.round(records.getDouble(i, 3) - aggiusta, round);
			double peso_patrim = records.getDouble(i, 2) / 100;
			String tipo = (String) records.getElement(i, 5);
			val_ici_pesato = val_ici * peso_patrim;
			if (tipo.equals("PR")) {
				// proprietà
			} else if (tipo.equals("UV") || tipo.equals("SV") || tipo.equals("AV")) {
				// Usufrutto a vita, Uso a vita, Abitazione a vita
				theDate = BirthDate((String) records.getElement(i, 7), dataRif);
				val_ici_pesato = Usufrutto.getValoreUsuf_aVita(val_ici_pesato, theDate,	dataRif, yearRif);
			} else if (tipo.equals("UT") || tipo.equals("ST") || tipo.equals("AT")) {
				// Usufrutto a termine, Uso a termine, Abitazione a termine
				durata = new Integer((String) records.getElement(i, 6)).intValue();
				val_ici_pesato = Usufrutto.getValoreUsuf_aTermine(val_ici_pesato, durata, yearRif);
			} else if (tipo.equals("NV")) {
				// Nuda propr. con usufrutto a vita
				theDate = BirthDate((String) records.getElement(i, 7), dataRif);
				val_ici_pesato = Usufrutto.getValoreNudaProprieta_aVita(val_ici_pesato, theDate, dataRif, yearRif);
			} else if (tipo.equals("NT")) {
				// Nuda propr. con usufrutto a termine
				durata = new Integer((String) records.getElement(i, 6)).intValue();
				val_ici_pesato = Usufrutto.getValoreNudaProprieta_aTermine(val_ici_pesato, durata, yearRif);
			} else {
				// tipo non trovato
				System.out.println("ERRORE: Tipo diritto n. " + tipo + " non previsto nella classe Immobiliare");
				val_ici_pesato = 0;
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
		return val_ici_pesato;
	}
	*/


	/**
	 * inizializza la Table records con i valori letti dalla query sul DB
	 * 
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer
	 *            it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		// legge i dati degli immobili del quadro E
		StringBuffer sql = new StringBuffer();
		//											1                           		2                   				3                                        4                                     5                                     6                                          7                          8                                9                                   10						11									12								 13									14															15
		sql.append("SELECT Patrimoni_immobiliari.residenza_nucleo, R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, EDIL_immobile.immobile, EDIL_immobile.mq * Patrimoni_immobiliari.quota / 100 AS mq_quota_terreno ");
		sql.append("FROM Familiari ");
		sql.append("INNER JOIN Patrimoni_immobiliari ON Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
		sql.append("INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
		sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append("INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID ");
		sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("LEFT OUTER JOIN EDIL_immobile ON Patrimoni_immobiliari.immobile = EDIL_immobile.immobile AND Patrimoni_immobiliari.ID_dichiarazione = EDIL_immobile.ID_dichiarazione AND Familiari.ID_domanda = EDIL_immobile.ID_domanda AND Familiari.ID_dichiarazione = EDIL_immobile.ID_dichiarazione ");
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