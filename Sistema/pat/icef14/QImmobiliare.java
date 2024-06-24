package c_elab.pat.icef14;

import it.clesius.clesius.util.Sys;
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
 * 
 * 
 * TODO: applicare la franchigia individuale FIT per i terreni agricoli (ID_tp_immobile = TA)
 * 
 */
public class QImmobiliare extends DefComponentiDich {



	private Hashtable<String,Long> hashtableMaxUsofruttoForDich = new Hashtable<String,Long>();
	private Hashtable<String,Double> hashtableFITForDich = new Hashtable<String,Double>();
	private String query = null;

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
		} else {
			return dataRif;
		}
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

		Enumeration<String> e = hashtableMaxUsofruttoForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			Long maxUsofrutto = (Long)hashtableMaxUsofruttoForDich.get(key);
			ret+=maxUsofrutto.longValue();
		}
		return ret;
	}

	private double getICIForTerreniAgricoli(String idDich, double valueICI)
	{
		double ret = valueICI;

		Double restoValueICI = (Double)hashtableFITForDich.get(idDich);
		if(restoValueICI!=null)
		{
			double fitRimasto = restoValueICI.doubleValue();
			
			if(fitRimasto>0)
			{
				double resto = 0.0;
				ret = valueICI - fitRimasto;
				if(ret<0)
				{
					resto = Math.abs(ret);
					ret = 0.0;
				}
				
				hashtableFITForDich.put(idDich, new Double(resto));
			}
		}
		else
		{
			double resto = 0.0;
			ret = valueICI - LocalVariables.FIT;
			if(ret<0)
			{
				resto = Math.abs(ret);
				ret = 0.0;
			}
			
			//nessun valore finora per il soggetto
			hashtableFITForDich.put(idDich,  new Double(resto));
		}
		return ret;
	}
	
	
	
	/**
	 * calcola il valore degli immobili distringuendo tra residenza e altri
	 */
	protected double getValoreICIRiga(int i, boolean residenza) {
		
		double ret = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		int durata;
		Calendar theDate;
		
		try {
			String tipo = (String)records.getElement(i,5);
			String tipologiaTerreno = (String)records.getElement(i,12);

			double val_ici_non_rapportato_a_quota_possesso = records.getDouble(i, 3);
			double quota_possesso_immobile = records.getDouble(i, 13);
			
			double tmp = 100.0;
			double val_ici_rapportato_a_quota_possesso = val_ici_non_rapportato_a_quota_possesso * quota_possesso_immobile / tmp;
			
			if(tipologiaTerreno.equals("TA"))
			{
				//tolgo la Franchigia Individuale di non dichiarabilità sui terreni non edificabili (Art. 16 comma 4)
				String idDich = ((String)records.getElement(i,10));
				val_ici_rapportato_a_quota_possesso = getICIForTerreniAgricoli(idDich,val_ici_rapportato_a_quota_possesso);
			}	
			
			double val_ici = Sys.round(val_ici_rapportato_a_quota_possesso - aggiusta, round);
			double peso_patrim = new Double((String)records.getElement(i,2)).doubleValue()/ 100;
			double aNum = val_ici * peso_patrim;
			
			int yearRif = new Integer((String)records.getElement(i,9)).intValue();
			int monthRif = 12;
			int dayRif = 31;
			
			if(yearRif==0)
			{
				//dichiarazione ICEF attualizzata
				yearRif = new Integer((String)records.getElement(i,14)).intValue();
				monthRif = new Integer((String)records.getElement(i,15)).intValue();
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, yearRif);
				calendar.set(Calendar.MONTH, monthRif-1);
				int lastDay = calendar.getActualMaximum(Calendar.DATE);
				dayRif = lastDay;
			}
			
			Calendar dataRif = Calendar.getInstance();
			dataRif.set(yearRif, monthRif -1, dayRif);

			if(tipo.equals("PR")) {
				ret = aNum;
			} else if (tipo.equals("UV") || tipo.equals("SV") || tipo.equals("AV") ) {
				// Usufrutto a vita, Uso a vita, Abitazione a vita
				if(tipologiaTerreno.equals("TA"))
				{
					//modifica 14/07/2009 se terreno agricolo e tipo = UV, SV o AV prendo tutto il valore  
					ret = aNum;
				}
				else
				{
					theDate = BirthDate((String)records.getElement(i,7), dataRif);
					ret = Usufrutto.getValoreUsuf_aVita(aNum, theDate, dataRif, yearRif);
				}
				
			} else if (tipo.equals("UT") || tipo.equals("ST") || tipo.equals("AT") ) {
				// Usufrutto a termine, Uso a termine, Abitazione a termine
				if(tipologiaTerreno.equals("TA"))
				{
					//modifica 14/07/2009 se terreno agricolo e tipo = UT, ST o AT prendo tutto il valore 
					ret = aNum;
				}
				else
				{
					durata = new Integer((String)records.getElement(i,6)).intValue();
					ret = Usufrutto.getValoreUsuf_aTermine(aNum, durata, yearRif);
				}
			} else if (tipo.equals("NV") ) {
				// Nuda propr. con usufrutto a vita
				long value = 0;
				
				if(tipologiaTerreno.equals("TA"))
				{
					//modifica 14/07/2009 se terreno agricolo e tipo = NV il valore è zero 
					value = 0;
				}
				else
				{
					theDate = BirthDate((String)records.getElement(i,7), dataRif);
					value = Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);
				}

				if(!residenza)
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

				ret= value;

			} else if (tipo.equals("NT") ) {
				// Nuda propr. con usufrutto a termine
				durata = new Integer((String)records.getElement(i,6)).intValue();
				long value = Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);

				if(!residenza)
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

				ret = value;
			} else {
				// tipo non trovato
				System.out.println("Tipo diritto n. " + tipo + " non previsto nella classe Immobiliare");

				ret = 0.0;
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
		return ret;
	}
	
	public double getValoreTotaleImmobili( boolean usaDetrazioneMaxValoreNudaProprieta ) 
	{
		return getValoreImmobili( true, false, usaDetrazioneMaxValoreNudaProprieta );
	}
	
	/**
	 * calcola il valore degli immobili distringuendo tra residenza e altri
	 */
	protected double getValoreImmobili( boolean residenza, boolean usaDetrazioneMaxValoreNudaProprieta ) {
		return getValoreImmobili( false, residenza, usaDetrazioneMaxValoreNudaProprieta );
	}
	
	/**
	 * calcola il valore degli immobili distringuendo tra residenza e altri
	 */
	protected double getValoreImmobili(boolean tutti, boolean residenza, boolean usaDetrazioneMaxValoreNudaProprieta ) {
		double total = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		int durata;
		Calendar theDate;

		for (int i = 1; i <= records.getRows(); i++ ){
			try {
				
				boolean sommaValore = false;
				if(tutti)
				{
					sommaValore = true;
				}
				else
				{
					if ( 
							((((String)records.getElement(i,1)).equals("0")) && !residenza ) 
							|| 
							(!(((String)records.getElement(i,1)).equals("0")) && residenza ) ) {
						sommaValore = true;
					}
				}
				
				if(sommaValore)
				{
					total += getValoreICIRiga(i,residenza);
				}
			} catch(NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}

		if(usaDetrazioneMaxValoreNudaProprieta)
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
	
	protected String getDefaultQuery()
	{
		StringBuffer sql = new StringBuffer();
		//      																										  1
		sql.append("SELECT CASE WHEN Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' THEN -1 ELSE 0 END as residenza_nucleo, ");
		//                           		      	2                   				3                                        4                                     5                                     6                                          7                          8                                9                                   10						11									12								 13									14					15
		sql.append("R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, Dich_icef.anno_attualizzato, Dich_icef.mese  ");
		sql.append("FROM Familiari ");
		sql.append("INNER JOIN Patrimoni_immobiliari ON Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
		sql.append("INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
		sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append("INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID ");
		sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE (Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' OR Patrimoni_immobiliari.ID_tp_uso_immobile = 'ALT') ");
		sql.append("AND Domande.ID_domanda = ");
		sql.append(IDdomanda);
		
		return sql.toString();
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QImmobiliare.class.getName(), IDdomanda );
		super.init(dataTransfer);
		
		if (!session.isInitialized()) {

			// legge i dati degli immobili del quadro E
			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				if(query==null)
				{
					query = getDefaultQuery();
				}
				sql.append(query);
				
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

				//                                      														1                           2                            3                                  4                                       5                                     6                                          7                          8                                9                                   10							11									12										13								14						  15
				sql.append(
				"SELECT CASE WHEN Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' THEN -1 ELSE 0 END as residenza_nucleo, 100 AS peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, Dich_icef.anno_attualizzato, Dich_icef.mese  ");
				sql.append(
				"FROM         Doc INNER JOIN ");
				sql.append(
				"Dich_icef ON Doc.ID = Dich_icef.ID_dichiarazione INNER JOIN ");
				sql.append(
				"Patrimoni_immobiliari ON Dich_icef.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
				sql.append(
				"WHERE (Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' OR Patrimoni_immobiliari.ID_tp_uso_immobile = 'ALT') AND (Dich_icef.ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(
				")");
			}

			doQuery(sql.toString());
			//System.out.println(sql.toString());

			session.setInitialized( true );
			session.setRecords( records );

		} else {
			records = session.getRecords();
		}
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public void setTable(Table table) {
		this.records = table;
	}
	
	public void setDataTransfer(RunawayData dataTransfer) {
		this.dataTransfer = dataTransfer;
	}
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2012);
		calendar.set(Calendar.MONTH, 2-1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		System.out.println(lastDay);
	}
}
