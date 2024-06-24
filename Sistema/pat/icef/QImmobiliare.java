/**
 *Created on 28-mag-2004
 */

package c_elab.pat.icef;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;


/** query per il quadro F della dichiarazione ICEF
 * @author g_barbieri
 */
public abstract class QImmobiliare extends DefComponentiDich {

	//TODO
	protected double valore_mq_imm_estero = 500.0;

	private Hashtable hashtableMaxUsofruttoForDich = new Hashtable();

	/** QImmobiliare constructor */
	public QImmobiliare() {
	}

	/**
	 * restituisce la data di nascita dell'usufruttuario.
	 */
	private Calendar BirthDate(String strDataNascita, Calendar dataRif)
	{
		if(strDataNascita != null && !strDataNascita.equals("")) {

			int anno =(new Integer(strDataNascita.substring(0,4))).intValue();
			int mese = (new Integer(strDataNascita.substring(5,7))).intValue() -1;
			int giorno =(new Integer(strDataNascita.substring(8,10))).intValue();

			Calendar rightNow = Calendar.getInstance();
			rightNow.set(anno,mese,giorno);
			return rightNow;
			//return new Date(anno,mese,giorno);
		} else {
			return dataRif;
		}
	}

	private boolean chechNuovaValutazioneNudaProprieta()
	{
		boolean ret = false;

		// legge i dati della nuova_valutazione_nuda_proprieta dal C_DefaultIn
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT default_value "+
				"FROM C_DefaultIn "+
		"WHERE (node = N'nuova_valutazione_nuda_proprieta') AND (ID_servizio = ");
		sql.append(servizio);
		sql.append(")");

		try {
			Table table =dataTransfer.executeQuery(sql.toString());

			if(table.getRows()>0)
			{
				int value = new Integer((String)table.getElement(1,1)).intValue();

				if(value == 1)
				{
					ret = true;
				}
			}
		} catch (DBException e) {
			System.out.println("Errore DBException in QImmobiliare.chechNuovaValutazioneNudaProprieta: " + e.toString()) ; 
		} catch (Exception e) {
			System.out.println("Errore Exception in QImmobiliare.chechNuovaValutazioneNudaProprieta: " + e.toString()) ;
		}
		return ret;
	}

	private void addMaxUsofruttoForDichValue(String idDich, 
			long value,
			boolean res,
			String categoriaCatastale,
			int annoProduzionePatrimoni,
			int annoProduzioneRedditi)
	{
		if(res)
		{
			//immobile di residenza (questa modifica non vale)
			return;
		}

		if(annoProduzionePatrimoni<2007 || annoProduzioneRedditi<2007)
		{
			//lista anni patrimoni o redditi già liquidati (questa modifica non vale)
			return;
		}

		//Tutti gli A tranne A1,A8,A9
		if(categoriaCatastale.equalsIgnoreCase("A2") ||
				categoriaCatastale.equalsIgnoreCase("A3") ||
				categoriaCatastale.equalsIgnoreCase("A4") ||
				categoriaCatastale.equalsIgnoreCase("A5") ||
				categoriaCatastale.equalsIgnoreCase("A6") ||
				categoriaCatastale.equalsIgnoreCase("A7") ||
				categoriaCatastale.equalsIgnoreCase("A10")||
				categoriaCatastale.equalsIgnoreCase("A11"))
		{
			Long oldMaxUsofrutto = (Long)hashtableMaxUsofruttoForDich.get(idDich);
			if(oldMaxUsofrutto!=null)
			{
				long oldValue = oldMaxUsofrutto.longValue();

				if(value>oldValue)
				{
					//cerco solo il valore massimo diversificato per residenza per ogni soggetto
					hashtableMaxUsofruttoForDich.put(idDich, new Long(value));
				}
			}
			else
			{
				//nessun valore finora per ogni soggetto
				hashtableMaxUsofruttoForDich.put(idDich,  new Long(value));
			}
		}
	}

	private double getTotalOfMaxUsofruttoForDich()
	{
		double ret = 0;

		Enumeration e = hashtableMaxUsofruttoForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			Long maxUsofrutto = (Long)hashtableMaxUsofruttoForDich.get(key);
			ret+=maxUsofrutto.longValue();
		}
		return ret;
	}


	/**
	 * calcola il valore degli immobili distringuendo tra residenza e altri
	 */
	protected double getValoreImmobili( boolean residenza ) {
		double total = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		int durata;
		Calendar theDate;

		boolean nuovaValutazioneNudaProprieta = chechNuovaValutazioneNudaProprieta();

		for (int i = 1; i <= records.getRows(); i++ ){
			try {

				boolean sommaValore = false;
				if ( 
						((((String)records.getElement(i,1)).equals("0")) && !residenza ) 
						|| 
						(!(((String)records.getElement(i,1)).equals("0")) && residenza ) ) {
					sommaValore = true;
				}

				String tipo = (String)records.getElement(i,5);
				//N.B. il valore ICI è già rapportato alla quota di possesso del patrimonio immobiliare (vedi query)
				double val_ici = Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round);
				double peso_patrim = new Double((String)records.getElement(i,2)).doubleValue()/ 100;
				double aNum = val_ici * peso_patrim;

				int yearRif = 2000;
				int monthRif = 12;
				int dayRif = 31;
				//tratta i casi fino ad agosto 2005 dove datarif era la data della domanda
				if ( new Long((String)records.getElement(i,10)).longValue() < 93000 ) {
					String strDataRif = (String)records.getElement(i,8);
					yearRif = new Integer(strDataRif.substring(0,4)).intValue();
					monthRif = new Integer(strDataRif.substring(5,7)).intValue();
					dayRif = new Integer(strDataRif.substring(8,10)).intValue();
					// tratta i nuovi casi dove datarif è il 31/12 dell'anno di rif del patrimonio
				} else {
					yearRif = new Integer((String)records.getElement(i,9)).intValue();
				}
				Calendar dataRif = Calendar.getInstance();
				dataRif.set(yearRif, monthRif -1, dayRif);

				//System.out.println("data rif = " + yearRif + " - " + monthRif + " - " + dayRif);

				if(tipo.equals("PR")) {
					// proprietà
					if(sommaValore)
					{
						total += aNum;
					}
				} else if (tipo.equals("UV") || tipo.equals("SV") || tipo.equals("AV") ) {
					// Usufrutto a vita, Uso a vita, Abitazione a vita
					theDate = BirthDate((String)records.getElement(i,7), dataRif);
					if(sommaValore)
					{
						total += Usufrutto.getValoreUsuf_aVita(aNum, theDate, dataRif, yearRif);
					}
				} else if (tipo.equals("UT") || tipo.equals("ST") || tipo.equals("AT") ) {
					// Usufrutto a termine, Uso a termine, Abitazione a termine
					durata = new Integer((String)records.getElement(i,6)).intValue();
					if(sommaValore)
					{
						total += Usufrutto.getValoreUsuf_aTermine(aNum, durata, yearRif);
					}
				} else if (tipo.equals("NV") ) {
					// Nuda propr. con usufrutto a vita
					theDate = BirthDate((String)records.getElement(i,7), dataRif);
					long value = Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);

					if(nuovaValutazioneNudaProprieta && !residenza)
					{
						//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
						String idDich = ((String)records.getElement(i,10));
						String categoriaCatastale = ((String)records.getElement(i,4));
						boolean res = true;
						if((((String)records.getElement(i,1)).equals("0")))
						{
							res = false;
						}
						int annoProduzionePatrimoni = new Integer((String)records.getElement(i,9)).intValue();
						int annoProduzioneRedditi = new Integer((String)records.getElement(i,11)).intValue();
						addMaxUsofruttoForDichValue(idDich, 
								value, 
								res, 
								categoriaCatastale,
								annoProduzionePatrimoni,
								annoProduzioneRedditi);
					}

					if(sommaValore)
					{
						total += value;
					}

				} else if (tipo.equals("NT") ) {
					// Nuda propr. con usufrutto a termine
					durata = new Integer((String)records.getElement(i,6)).intValue();
					long value = Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);

					if(nuovaValutazioneNudaProprieta && !residenza)
					{
						//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
						String idDich = ((String)records.getElement(i,10));
						String categoriaCatastale = ((String)records.getElement(i,4));
						boolean res = true;
						if((((String)records.getElement(i,1)).equals("0")))
						{
							res = false;
						}
						int annoProduzionePatrimoni = new Integer((String)records.getElement(i,9)).intValue();
						int annoProduzioneRedditi = new Integer((String)records.getElement(i,11)).intValue();
						addMaxUsofruttoForDichValue(idDich, 
								value, 
								res, 
								categoriaCatastale,
								annoProduzionePatrimoni,
								annoProduzioneRedditi);
					}

					if(sommaValore)
					{
						total += value;
					}
				} else {
					// tipo non trovato
					System.out.println("Tipo diritto n. " + tipo + " non previsto nella classe Immobiliare");

					if(sommaValore)
					{
						total += 0;
					}
				}
			} catch(NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}

		if(nuovaValutazioneNudaProprieta)
		{
			//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
			total = total - getTotalOfMaxUsofruttoForDich();
		}

		return total;
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QImmobiliare.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QImmobiliare.class.getName(), IDdomanda );
		super.init(dataTransfer);
		
		if (!session.isInitialized()) {

			/* tolto dalla versi0ne dell'icef del 24-05-05
            // legge i mq degli immobili all'estero
			StringBuffer sql = new StringBuffer();
			//                               1                                    2
			sql.append(
				"SELECT Dich_icef.mq_complessivi, R_Relazioni_parentela.peso_patrimonio ");
			sql.append("FROM Familiari ");
			sql.append(
				"INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
			sql.append(
				"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(
				"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());

            tb_imm_estero = records;
			 */          

			// legge i dati degli immobili del quadro E
			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				//                                      1                                         2                                  3                                                                       4                                       5                                     6                                          7                          8                                9                                   10						11
				sql.append(
				"SELECT Patrimoni_immobiliari.residenza_nucleo, R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici * Patrimoni_immobiliari.quota / 100, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi ");
				sql.append("FROM Familiari ");
				sql.append(
				"INNER JOIN Patrimoni_immobiliari ON Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
				sql.append(
				"INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
				sql.append(
				"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
				sql.append(
				"INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID ");
				sql.append(
				"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("WHERE Domande.ID_domanda = ");
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
				//Fine aggiunta eventuale definizione di componenti
			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);

				//                                      1                           2                                  3                                                                       4                                       5                                     6                                          7                          8                                9                                   10							11
				sql.append(
				"SELECT     Patrimoni_immobiliari.residenza_nucleo, 100 AS peso_patrimonio, Patrimoni_immobiliari.valore_ici * Patrimoni_immobiliari.quota / 100, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi  ");
				sql.append(
				"FROM         Doc INNER JOIN ");
				sql.append(
				"Dich_icef ON Doc.ID = Dich_icef.ID_dichiarazione INNER JOIN ");
				sql.append(
				"Patrimoni_immobiliari ON Dich_icef.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
				sql.append(
				"WHERE     (Dich_icef.ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(
				")");
			}

			doQuery(sql.toString());
			//System.out.println(sql.toString());

			session.setInitialized( true );
			session.setRecords( records );

		} else {
			//records = theRecords;
			records = session.getRecords();
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
