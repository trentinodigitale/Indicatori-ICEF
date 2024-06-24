package c_elab.pat.vitaInd20;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;


/** query per immobili ultimi 5 anni
 * @author g_barbieri
 * 
 * 
 */
public class QImmobiliare extends DefComponentiDich {

	// Franchigia Individuale di non dichiarabilità sui terreni non edificabili non c'è in questo caso 
	double FIT = 0.0;

	private Hashtable hashtableMaxUsofruttoForDich = new Hashtable();
	private Hashtable hashtableFITForDich = new Hashtable();
	private String query = null;
	private int annoAC = 2016;

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
		/*
		if(res)       //TODO togliere??
		{
			//immobile di residenza (questa modifica non vale)
			return;
		}
		 */

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
			ret = valueICI - FIT;
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

	Method mUsufrutto=null;
	private Method getClassUsufrutto(String annol) throws Exception{
		if(mUsufrutto==null){
			Class  cu=Class.forName("c_elab.pat.icef"+annol+".Usufrutto");
			mUsufrutto=cu.getMethod("getValoreNudaProprieta_aTermine", Double.class,Integer.class,Integer.class);
			mUsufrutto.setAccessible(true);
		}
		return mUsufrutto;
		
	}

	/**
	 * calcola il valore degli immobili distringuendo tra gratuito e oneroso
	 * @throws  
	 * @throws Exception 
	 */
	protected double getValoreICIRiga(int i, boolean gratuito) throws Exception {

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
				//dichiarazione ICEF attualizzata  //TODO togliere?
				/*yearRif = new Integer((String)records.getElement(i,14)).intValue();
				monthRif = new Integer((String)records.getElement(i,15)).intValue();
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, yearRif);
				calendar.set(Calendar.MONTH, monthRif-1);
				int lastDay = calendar.getActualMaximum(Calendar.DATE);
				dayRif = lastDay;
				 */
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
					if(annoAC<=2013){
						ret = c_elab.pat.icef13.Usufrutto.getValoreUsuf_aVita(aNum, theDate, dataRif, yearRif);
					}else{
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreUsuf_aVita", double.class,Calendar.class,Calendar.class,int.class);
						ret=(Long)m.invoke(null, aNum, theDate, dataRif, yearRif);
					}
					
					
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
					if(annoAC<=2013){
						ret = c_elab.pat.icef12.Usufrutto.getValoreUsuf_aTermine(aNum, durata, yearRif);
					}else{
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreUsuf_aTermine", double.class,int.class,int.class);
						ret=(Long)m.invoke(null, aNum, durata, yearRif);
					}

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
					
					if(annoAC<=2013){
						value = c_elab.pat.icef12.Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);
					
					}else{
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreNudaProprieta_aVita", double.class,Calendar.class,Calendar.class,int.class);
						value=(Long)m.invoke(null, aNum, theDate, dataRif, yearRif);
					}
				}

				if(!gratuito)
				{
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					String idDich = ((String)records.getElement(i,10));
					String categoriaCatastale = ((String)records.getElement(i,4));
					boolean grat = true;
					if(!(((String)records.getElement(i,1)).equals("2")))
					{
						grat = false;
					}
					int annoProduzionePatrimoni = new Integer((String)records.getElement(i,9)).intValue();
					int annoProduzioneRedditi = new Integer((String)records.getElement(i,11)).intValue();
					addMaxUsofruttoForDichValue(idDich, 
							value, 
							grat, 
							categoriaCatastale,
							annoProduzionePatrimoni,
							annoProduzioneRedditi);
				}

				ret= value;

			} else if (tipo.equals("NT") ) {
				// Nuda propr. con usufrutto a termine
				durata = new Integer((String)records.getElement(i,6)).intValue();
				
				long value = 0;
			
				if(annoAC<=2013){
					value=c_elab.pat.icef12.Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);
				}else{
					String anno=""+(annoAC-2000);
					Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
					Method m=cu.getMethod("getValoreNudaProprieta_aTermine", double.class,int.class,int.class);
					value=(Long)m.invoke(null, aNum, durata, yearRif);
					
				
				}

				if(!gratuito)
				{
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					String idDich = ((String)records.getElement(i,10));
					String categoriaCatastale = ((String)records.getElement(i,4));
					boolean grat = true;
					if(!(((String)records.getElement(i,1)).equals("2")))
					{
						grat = false;
					}
					int annoProduzionePatrimoni = new Integer((String)records.getElement(i,9)).intValue();
					int annoProduzioneRedditi = new Integer((String)records.getElement(i,11)).intValue();
					addMaxUsofruttoForDichValue(idDich, 
							value, 
							grat, 
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
	 * calcola il valore degli immobili distringuendo tra gratuito e oneroso
	 */
	protected double getValoreImmobili( boolean gratuito, boolean usaDetrazioneMaxValoreNudaProprieta ) {
		return getValoreImmobili( false, gratuito, usaDetrazioneMaxValoreNudaProprieta );
	}

	/**
	 * calcola il valore degli immobili distringuendo tra gratuito e oneroso
	 */
	protected double getValoreImmobili(boolean tutti, boolean gratuito, boolean usaDetrazioneMaxValoreNudaProprieta ) {
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
					
					if (gratuito) {
						if ( ((String)records.getElement(i,1)).equals("2") ) {
							sommaValore = true;
						}
					} else {
						if ( ((String)records.getElement(i,1)).equals("1") ) {
							sommaValore = true;
						}
					}
				}

				if(sommaValore)
				{
					total += getValoreICIRiga(i,gratuito);
				}
			} catch(Exception n) {
				n.printStackTrace();
				return 0.0;
			} 
		}

		if(usaDetrazioneMaxValoreNudaProprieta)
		{
			//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
			//luigi 20/07/2018 rimosso
			//total = total - getTotalOfMaxUsofruttoForDich();
		}

		return total;
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QImmobiliare.class.getName(), IDdomanda );
	}



	protected String getDefaultQuery()	{
		StringBuffer sql = new StringBuffer();

		sql = new StringBuffer();

		//TODO fisso anno 2011 in query
		//                                         1      		   	     2                   				 3                                 4                                  5                                        6                          7                              8                                9                              10						            11									    12					         13
		sql.append("SELECT AC_immobili_ceduti.ID_tp_cessione, 100 AS peso_patrimonio, AC_immobili_ceduti.valore_ici, AC_immobili_ceduti.ID_tp_cat_catastale, AC_immobili_ceduti.ID_tp_diritto, AC_immobili_ceduti.anni_usufrutto, AC_immobili_ceduti.data_nascita_usufruttuario, Doc.data_presentazione, "+annoAC+" AS anno_produzione_patrimoni, 0 AS ID_dichiarazione, "+annoAC+" AS anno_produzione_redditi,  AC_immobili_ceduti.ID_tp_immobile, AC_immobili_ceduti.quota  ");
		sql.append("FROM Doc ");
		sql.append("INNER JOIN Domande ON Doc.ID = Domande.ID_domanda ");
		sql.append("INNER JOIN AC_immobili_ceduti ON Domande.ID_domanda = AC_immobili_ceduti.ID_domanda ");
		sql.append("WHERE (AC_immobili_ceduti.ID_tp_uso_immobile = 'RES' OR AC_immobili_ceduti.ID_tp_uso_immobile = 'ALT') ");
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
				/* //TODO togliere????
				String defDichType = DefDichElabConst.F;
				String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
				if(componenti != null && componenti.length()>0)
				{
					sql.append(" AND Familiari.ID_dichiarazione in ");
					sql.append(componenti);

					testPrintln(sql.toString());
				}
				 */
				//Fine aggiunta eventuale definizione di componenti
			}
			else
			{
				//TODO??????
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				/*
				String id_dichiarazione = IDdomanda.substring(1);

				//                                      														1                           2                            3                                  4                                       5                                     6                                          7                          8                                9                                   10							11									12										13								14						  15
				sql.append(
				"SELECT CASE WHEN AC_immobili_ceduti.ID_tp_uso_immobile = 'RES' THEN -1 ELSE 0 END as residenza_nucleo, 100 AS peso_patrimonio, AC_immobili_ceduti.valore_ici, AC_immobili_ceduti.ID_tp_cat_catastale, AC_immobili_ceduti.ID_tp_diritto, AC_immobili_ceduti.anni_usufrutto, AC_immobili_ceduti.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  AC_immobili_ceduti.ID_tp_immobile, AC_immobili_ceduti.quota, Dich_icef.anno_attualizzato, Dich_icef.mese  ");
				sql.append(
				"FROM         Doc INNER JOIN ");
				sql.append(
				"Dich_icef ON Doc.ID = Dich_icef.ID_dichiarazione INNER JOIN ");
				sql.append(
				"AC_immobili_ceduti ON Dich_icef.ID_dichiarazione = AC_immobili_ceduti.ID_dichiarazione ");
				sql.append(
				"WHERE (AC_immobili_ceduti.ID_tp_uso_immobile = 'RES' OR AC_immobili_ceduti.ID_tp_uso_immobile = 'ALT') AND (Dich_icef.ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(
				")");
				 */
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
