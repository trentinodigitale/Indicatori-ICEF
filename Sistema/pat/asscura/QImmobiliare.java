package c_elab.pat.asscura;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.PassaValoriIcef;

/**
 * query per immobili ultimi 5 anni
 * @author g_barbieri
 *
 */
public class QImmobiliare extends DefComponentiDich {

	// Franchigia Individuale di non dichiarabilità sui terreni non edificabili non c'è in questo caso 
	double						FIT								= 0.0;

	@SuppressWarnings("rawtypes")
	private			Hashtable	hashtableMaxUsofruttoForDich	= new Hashtable();
	@SuppressWarnings("rawtypes")
	private			Hashtable	hashtableFITForDich				= new Hashtable();
	private			String		query							= null;
	private			int			annoAC							= 2011;

	private final	int			MONTH_JUNE						= 5;

	private final	int			ID_SERVIZIO_30600				= 30600;
	private final	int			ID_SERVIZIO_30618				= 30618;

	private final	int			ID_SERVIZIO_30500				= 30500;
	private final	int			ID_PERIODO_30518				= 30518;

	private final	int			ID_SERVIZIO_61000				= 61000;
	private final	int			ID_SERVIZIO_61018				= 61018;
	
	public Table		iImmobiliDiversiDaResidenza	= null;
	public Table		annoProduzionePatrimoni	= null;

	/** QImmobiliare constructor */
	public QImmobiliare() {
	}

	/**
	 * restituisce la data di nascita dell'usufruttuario.
	 * 
	 * @param strDataNascita
	 * @param dataRif
	 * @return
	 */
	private Calendar BirthDate(String strDataNascita, Calendar dataRif) {
		if(strDataNascita != null && !strDataNascita.equals("")) {
			int			anno		= (new Integer(strDataNascita.substring(0,4))).intValue();
			int			mese		= (new Integer(strDataNascita.substring(5,7))).intValue() -1;
			int			giorno		= (new Integer(strDataNascita.substring(8,10))).intValue();
			Calendar	rightNow	= Calendar.getInstance();
			rightNow.set(anno,mese,giorno);
			return rightNow;
		} else {
			return dataRif;
		}
	}

	/**
	 * 
	 * @param idDich
	 * @param value
	 * @param res
	 * @param categoriaCatastale
	 * @param annoProduzionePatrimoni
	 * @param annoProduzioneRedditi
	 */
	@SuppressWarnings("unchecked")
	private void addMaxUsofruttoForDichValue(String idDich, 
			long value,
			boolean res,
			String categoriaCatastale,
			int annoProduzionePatrimoni,
			int annoProduzioneRedditi) {

		//Tutti gli A tranne A1,A8,A9
		if(categoriaCatastale.equalsIgnoreCase("A2") ||
				categoriaCatastale.equalsIgnoreCase("A3") ||
				categoriaCatastale.equalsIgnoreCase("A4") ||
				categoriaCatastale.equalsIgnoreCase("A5") ||
				categoriaCatastale.equalsIgnoreCase("A6") ||
				categoriaCatastale.equalsIgnoreCase("A7") ||
				categoriaCatastale.equalsIgnoreCase("A10")||
				categoriaCatastale.equalsIgnoreCase("A11"))  {
			Long oldMaxUsofrutto = (Long)hashtableMaxUsofruttoForDich.get(idDich);
			if(oldMaxUsofrutto!=null) {
				long oldValue = oldMaxUsofrutto.longValue();

				if(value>oldValue) {
					//cerco solo il valore massimo diversificato per residenza per ogni soggetto
					hashtableMaxUsofruttoForDich.put(idDich, new Long(value));
				}
			} else {
				//nessun valore finora per ogni soggetto
				hashtableMaxUsofruttoForDich.put(idDich,  new Long(value));
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private double getTotalOfMaxUsofruttoForDich() {
		double ret = 0;

		Enumeration e = hashtableMaxUsofruttoForDich.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			Long maxUsofrutto = (Long)hashtableMaxUsofruttoForDich.get(key);
			ret+=maxUsofrutto.longValue();
		}
		return ret;
	}

	/**
	 * 
	 * @param idDich
	 * @param valueICI
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private double getICIForTerreniAgricoli(String idDich, double valueICI) {

		double ret				= valueICI;
		Double restoValueICI	= (Double)hashtableFITForDich.get(idDich);

		if(restoValueICI!=null) {
			double fitRimasto	= restoValueICI.doubleValue();

			if(fitRimasto>0) {
				double resto	= 0.0;
				ret				= valueICI - fitRimasto;
				if(ret<0) {
					resto	= Math.abs(ret);
					ret		= 0.0;
				}

				hashtableFITForDich.put(idDich, new Double(resto));
			}
		} else {
			double resto = 0.0;
			ret = valueICI - FIT;
			if(ret<0) {
				resto	= Math.abs(ret);
				ret		= 0.0;
			}

			//nessun valore finora per il soggetto
			hashtableFITForDich.put(idDich,  new Double(resto));
		}
		return ret;
	}

	Method mUsufrutto=null;

	/**
	 * 
	 * @param annol
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private Method getClassUsufrutto(String annol) throws Exception {
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected double getValoreICIRiga(int i, boolean gratuito) throws Exception {

		double		ret			= 0.0;
		double		round		= 1.0;
		double		aggiusta	= 0.01;
		int			durata;
		Calendar	theDate;

		try {
			String tipo										= (String)records.getElement(i,5);
			String tipologiaTerreno							= (String)records.getElement(i,12);

			double val_ici_non_rapportato_a_quota_possesso	= records.getDouble(i, 3);
			double quota_possesso_immobile					= records.getDouble(i, 13);

			double tmp										= 100.0;
			double val_ici_rapportato_a_quota_possesso		= val_ici_non_rapportato_a_quota_possesso * quota_possesso_immobile / tmp;

			if(tipologiaTerreno.equals("TA")) {
				//tolgo la Franchigia Individuale di non dichiarabilità sui terreni non edificabili (Art. 16 comma 4)
				String idDich = ((String)records.getElement(i,10));
				val_ici_rapportato_a_quota_possesso = getICIForTerreniAgricoli(idDich,val_ici_rapportato_a_quota_possesso);
			}	

			double	val_ici		= Sys.round(val_ici_rapportato_a_quota_possesso - aggiusta, round);
			double	peso_patrim	= new Double((String)records.getElement(i,2)).doubleValue()/ 100;
			double	aNum		= val_ici * peso_patrim;

			int 	yearRif		= new Integer((String)records.getElement(i,9)).intValue();
			int 	monthRif	= 12;
			int 	dayRif		= 31;

			if(yearRif == 0) {
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
				if(tipologiaTerreno.equals("TA")) {
					//modifica 14/07/2009 se terreno agricolo e tipo = UV, SV o AV prendo tutto il valore  
					ret = aNum;
				} else {
					theDate = BirthDate((String)records.getElement(i,7), dataRif);
					if(annoAC<=2013) {
						ret = c_elab.pat.icef13.Usufrutto.getValoreUsuf_aVita(aNum, theDate, dataRif, yearRif);
					} else {
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreUsuf_aVita", double.class,Calendar.class,Calendar.class,int.class);
						ret=(Long)m.invoke(null, aNum, theDate, dataRif, yearRif);
					}
				}

			} else if (tipo.equals("UT") || tipo.equals("ST") || tipo.equals("AT") ) {
				// Usufrutto a termine, Uso a termine, Abitazione a termine
				if(tipologiaTerreno.equals("TA")) {
					//modifica 14/07/2009 se terreno agricolo e tipo = UT, ST o AT prendo tutto il valore 
					ret = aNum;
				} else {
					durata = new Integer((String)records.getElement(i,6)).intValue();
					if(annoAC<=2013){
						ret = c_elab.pat.icef12.Usufrutto.getValoreUsuf_aTermine(aNum, durata, yearRif);
					} else {
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreUsuf_aTermine", double.class,int.class,int.class);
						ret=(Long)m.invoke(null, aNum, durata, yearRif);
					}

				}
			} else if (tipo.equals("NV") ) {
				// Nuda propr. con usufrutto a vita
				long value = 0;

				if(tipologiaTerreno.equals("TA")) {
					//modifica 14/07/2009 se terreno agricolo e tipo = NV il valore è zero 
					value = 0;
				} else {
					theDate = BirthDate((String)records.getElement(i,7), dataRif);

					if(annoAC<=2013){
						value = c_elab.pat.icef12.Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);

					} else {
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreNudaProprieta_aVita", double.class,Calendar.class,Calendar.class,int.class);
						value=(Long)m.invoke(null, aNum, theDate, dataRif, yearRif);
					}
				}

				if(!gratuito) {
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					String idDich = ((String)records.getElement(i,10));
					String categoriaCatastale = ((String)records.getElement(i,4));
					boolean grat = true;
					if(!(((String)records.getElement(i,1)).equals("2"))) {
						grat = false;
					}
					int annoProduzionePatrimoni = new Integer((String)records.getElement(i,9)).intValue();
					int annoProduzioneRedditi	= new Integer((String)records.getElement(i,11)).intValue();
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

				if(annoAC<=2013) {
					value=c_elab.pat.icef12.Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);
				} else {
					String anno=""+(annoAC-2000);
					Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
					Method m=cu.getMethod("getValoreNudaProprieta_aTermine", double.class,int.class,int.class);
					value=(Long)m.invoke(null, aNum, durata, yearRif);
				}

				if(!gratuito) {
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					String idDich = ((String)records.getElement(i,10));
					String categoriaCatastale = ((String)records.getElement(i,4));
					boolean grat = true;
					if(!(((String)records.getElement(i,1)).equals("2"))) {
						grat = false;
					}
					int annoProduzionePatrimoni	= new Integer((String)records.getElement(i,9)).intValue();
					int annoProduzioneRedditi	= new Integer((String)records.getElement(i,11)).intValue();
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
	
	/**
	 * calcola il valore degli immobili distringuendo tra gratuito e oneroso
	 * @throws  
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected double getValoreICIRiga2(int i, boolean gratuito) throws Exception {

		double		ret			= 0.0;
		double		round		= 1.0;
		double		aggiusta	= 0.01;
		int			durata;
		Calendar	theDate;

		try {
			String tipo										= (String)iImmobiliDiversiDaResidenza.getElement(i,5);
			String tipologiaTerreno							= (String)iImmobiliDiversiDaResidenza.getElement(i,12);

			double val_ici_non_rapportato_a_quota_possesso	= iImmobiliDiversiDaResidenza.getDouble(i, 3);
			double quota_possesso_immobile					= iImmobiliDiversiDaResidenza.getDouble(i, 13);

			double tmp										= 100.0;
			double val_ici_rapportato_a_quota_possesso		= val_ici_non_rapportato_a_quota_possesso * quota_possesso_immobile / tmp;

			if(tipologiaTerreno.equals("TA")) {
				//tolgo la Franchigia Individuale di non dichiarabilità sui terreni non edificabili (Art. 16 comma 4)
				String idDich = ((String)iImmobiliDiversiDaResidenza.getElement(i,10));
				val_ici_rapportato_a_quota_possesso = getICIForTerreniAgricoli(idDich,val_ici_rapportato_a_quota_possesso);
			}	

			double	val_ici		= Sys.round(val_ici_rapportato_a_quota_possesso - aggiusta, round);
			double	peso_patrim	= new Double((String)iImmobiliDiversiDaResidenza.getElement(i,2)).doubleValue()/ 100;
			double	aNum		= val_ici * peso_patrim;

			int 	yearRif		= new Integer((String)iImmobiliDiversiDaResidenza.getElement(i,9)).intValue();
			int 	monthRif	= 12;
			int 	dayRif		= 31;

			if(yearRif == 0) {
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
				if(tipologiaTerreno.equals("TA")) {
					//modifica 14/07/2009 se terreno agricolo e tipo = UV, SV o AV prendo tutto il valore  
					ret = aNum;
				} else {
					theDate = BirthDate((String)iImmobiliDiversiDaResidenza.getElement(i,7), dataRif);
					if(annoAC<=2013) {
						ret = c_elab.pat.icef13.Usufrutto.getValoreUsuf_aVita(aNum, theDate, dataRif, yearRif);
					} else {
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreUsuf_aVita", double.class,Calendar.class,Calendar.class,int.class);
						ret=(Long)m.invoke(null, aNum, theDate, dataRif, yearRif);
					}
				}

			} else if (tipo.equals("UT") || tipo.equals("ST") || tipo.equals("AT") ) {
				// Usufrutto a termine, Uso a termine, Abitazione a termine
				if(tipologiaTerreno.equals("TA")) {
					//modifica 14/07/2009 se terreno agricolo e tipo = UT, ST o AT prendo tutto il valore 
					ret = aNum;
				} else {
					durata = new Integer((String)iImmobiliDiversiDaResidenza.getElement(i,6)).intValue();
					if(annoAC<=2013){
						ret = c_elab.pat.icef12.Usufrutto.getValoreUsuf_aTermine(aNum, durata, yearRif);
					} else {
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreUsuf_aTermine", double.class,int.class,int.class);
						ret=(Long)m.invoke(null, aNum, durata, yearRif);
					}

				}
			} else if (tipo.equals("NV") ) {
				// Nuda propr. con usufrutto a vita
				long value = 0;

				if(tipologiaTerreno.equals("TA")) {
					//modifica 14/07/2009 se terreno agricolo e tipo = NV il valore è zero 
					value = 0;
				} else {
					theDate = BirthDate((String)iImmobiliDiversiDaResidenza.getElement(i,7), dataRif);

					if(annoAC<=2013){
						value = c_elab.pat.icef12.Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);

					} else {
						String anno=""+(annoAC-2000);
						Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
						Method m=cu.getMethod("getValoreNudaProprieta_aVita", double.class,Calendar.class,Calendar.class,int.class);
						value=(Long)m.invoke(null, aNum, theDate, dataRif, yearRif);
					}
				}

				if(!gratuito) {
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					String idDich = ((String)iImmobiliDiversiDaResidenza.getElement(i,10));
					String categoriaCatastale = ((String)iImmobiliDiversiDaResidenza.getElement(i,4));
					boolean grat = true;
					if(!(((String)iImmobiliDiversiDaResidenza.getElement(i,1)).equals("2"))) {
						grat = false;
					}
					int annoProduzionePatrimoni = new Integer((String)iImmobiliDiversiDaResidenza.getElement(i,9)).intValue();
					int annoProduzioneRedditi	= new Integer((String)iImmobiliDiversiDaResidenza.getElement(i,11)).intValue();
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

				if(annoAC<=2013) {
					value=c_elab.pat.icef12.Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);
				} else {
					String anno=""+(annoAC-2000);
					Class  cu=Class.forName("c_elab.pat.icef"+anno+".Usufrutto");
					Method m=cu.getMethod("getValoreNudaProprieta_aTermine", double.class,int.class,int.class);
					value=(Long)m.invoke(null, aNum, durata, yearRif);
				}

				if(!gratuito) {
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					String idDich = ((String)iImmobiliDiversiDaResidenza.getElement(i,10));
					String categoriaCatastale = ((String)iImmobiliDiversiDaResidenza.getElement(i,4));
					boolean grat = true;
					if(!(((String)records.getElement(i,1)).equals("2"))) {
						grat = false;
					}
					int annoProduzionePatrimoni	= new Integer((String)iImmobiliDiversiDaResidenza.getElement(i,9)).intValue();
					int annoProduzioneRedditi	= new Integer((String)iImmobiliDiversiDaResidenza.getElement(i,11)).intValue();
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

	/**
	 * 
	 * @param usaDetrazioneMaxValoreNudaProprieta
	 * @return
	 */
	public double getValoreTotaleImmobili( boolean usaDetrazioneMaxValoreNudaProprieta ) {
		return getValoreImmobili( true, false, usaDetrazioneMaxValoreNudaProprieta );
	}

	/**
	 * calcola il valore degli immobili distringuendo tra gratuito e oneroso
	 * 
	 * @param gratuito
	 * @param usaDetrazioneMaxValoreNudaProprieta
	 * @return
	 */
	protected double getValoreImmobili( boolean gratuito, boolean usaDetrazioneMaxValoreNudaProprieta ) {
		return getValoreImmobili( false, gratuito, usaDetrazioneMaxValoreNudaProprieta );
	}

	/**
	 * calcola il valore degli immobili distringuendo tra gratuito e oneroso
	 * @param tutti
	 * @param gratuito
	 * @param usaDetrazioneMaxValoreNudaProprieta
	 * @return
	 */
	protected double getValoreImmobili(boolean tutti, boolean gratuito, boolean usaDetrazioneMaxValoreNudaProprieta ) {
		double		total		= 0.0;
		boolean		calcoloCorretto = false;
		try{
			Table t1 = dataTransfer.executeQuery("Select domande.id_periodo, domande.id_servizio, doc.data_presentazione "
					+ "	from domande "
					+ "	inner join doc on doc.id = domande.id_domanda "
					+ " where domande.id_domanda = "
					+ IDdomanda);
			int idServizio	= t1.getInteger(1, 2);
			int idPeriodo	= t1.getInteger(1, 1);
			
			if ( (idServizio == ID_SERVIZIO_30500 && idPeriodo < ID_PERIODO_30518)
					|| (idServizio >= ID_SERVIZIO_30600 && idServizio < ID_SERVIZIO_30618) 
					|| (idServizio >= ID_SERVIZIO_61000 && idServizio < ID_SERVIZIO_61018) ) {
				calcoloCorretto = false;
			} else {
				calcoloCorretto = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		for (int i = 1; i <= records.getRows(); i++ ){
			try {

				boolean sommaValore = false;
				if(tutti) {
					sommaValore = true;
				} else {

					if (calcoloCorretto) {

						if (gratuito) {
							if ( ((String)records.getElement(i,1)).equals("2") ) {
								sommaValore = true;
							}
						} else {
							if ( ((String)records.getElement(i,1)).equals("1") ) {
								sommaValore = true;
							}
						}
					} else {
						if ( ((((String)records.getElement(i,1)).equals("0")) && !gratuito ) 
								|| 
								(!(((String)records.getElement(i,1)).equals("0")) && gratuito ) ) {
							sommaValore = true;
						}
					}


				}

				if(sommaValore) {
					total += getValoreICIRiga(i,gratuito);
				}
			} catch(Exception n) {
				n.printStackTrace();
				return 0.0;
			} 
		}

		if(usaDetrazioneMaxValoreNudaProprieta) {
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
		iImmobiliDiversiDaResidenza=null;
	}



	/**
	 * Torna la query per il recupero degli immobili ceduti (AC_immobili_ceduti):<BR>
	 * - valorizza l'anno con l'anno letto in ac_configurazione anno Inizio_vettore - 1 per AC;<BR>
	 * - valorizza l'anno con l'anno letto da doc.data_presentazione -2 se nel primo semestre e -1 nel secondo semestra per AI.<BR>
	 * @return
	 */
	protected String getDefaultQuery() {
		StringBuffer sql = new StringBuffer();
		try{
			Table t1 = dataTransfer.executeQuery("Select domande.id_periodo, domande.id_servizio, doc.data_presentazione "
					+ "	from domande "
					+ "	inner join doc on doc.id = domande.id_domanda "
					+ " where domande.id_domanda = "
					+ IDdomanda);

			int idServizio	= t1.getInteger(1, 2);
			int idPeriodo	= t1.getInteger(1, 1);

			sql.append("SELECT  Inizio_vettore FROM AC_configurazione where " 
					+" id_servizio="
					+ idServizio
					+" and id_periodo="
					+ idPeriodo
					+ " order by Inizio_periodo asc"
					);

			Table t= dataTransfer.executeQuery(sql.toString());
			if (t != null && t.getRows() > 0) {
				annoAC =	t.getCalendar(1, 1).get(Calendar.YEAR); 

				if(annoAC > 2013){
					annoAC = annoAC - 1;
				}else{
					//è fisso perche' abbiamo sbagliato nel 2012 -2013
					annoAC = 2011;
				}
			} else {
				if (t1.getCalendar(1, 3).get(Calendar.MONTH) <= MONTH_JUNE) {
					annoAC = t1.getCalendar(1, 3).get(Calendar.YEAR) - 2;
				} else {
					annoAC = t1.getCalendar(1, 3).get(Calendar.YEAR) - 1;
				};
			}

		}catch(Exception e){
			e.printStackTrace();
		}


		sql = new StringBuffer();

		//TODO fisso anno 2011 in query
		sql.append("SELECT AC_immobili_ceduti.ID_tp_cessione, ");
		sql.append(" 100 AS peso_patrimonio, ");
		sql.append(" AC_immobili_ceduti.valore_ici, ");
		sql.append(" AC_immobili_ceduti.ID_tp_cat_catastale, ");
		sql.append(" AC_immobili_ceduti.ID_tp_diritto, ");
		sql.append(" AC_immobili_ceduti.anni_usufrutto, ");
		sql.append(" AC_immobili_ceduti.data_nascita_usufruttuario, ");
		sql.append(" Doc.data_presentazione, ");
		sql.append(annoAC);
		sql.append(" AS anno_produzione_patrimoni, ");
		sql.append(" 0 AS ID_dichiarazione, ");
		sql.append(annoAC);
		sql.append(" AS anno_produzione_redditi, ");
		sql.append(" AC_immobili_ceduti.ID_tp_immobile, ");
		sql.append(" AC_immobili_ceduti.quota  ");
		sql.append(" FROM Doc ");
		sql.append(" INNER JOIN Domande ON Doc.ID = Domande.ID_domanda ");
		sql.append(" INNER JOIN AC_immobili_ceduti ON Domande.ID_domanda = AC_immobili_ceduti.ID_domanda ");
		sql.append(" WHERE (AC_immobili_ceduti.ID_tp_uso_immobile = 'RES' OR AC_immobili_ceduti.ID_tp_uso_immobile = 'ALT') ");
		sql.append(" AND Domande.ID_domanda = ");
		sql.append(IDdomanda);

		return sql.toString();
	}
	
	protected String getDefaultQuery2()
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
		//modifica luigi 19/07/2018
		sql.append("WHERE (Patrimoni_immobiliari.ID_tp_uso_immobile = 'RES' OR Patrimoni_immobiliari.ID_tp_uso_immobile = 'ALT') and Patrimoni_immobiliari.quota >=5 ");
		sql.append("AND Domande.ID_domanda = ");
		sql.append(IDdomanda);
		
		String componentiPV =  PassaValoriIcef.getID_dichiarazioni(IDdomanda); //classe metodo DefComponentiDich
		if(componentiPV != null && componentiPV.length()>0){
			sql.append(" AND Familiari.ID_dichiarazione in ");
			sql.append(componentiPV);
		}
		
		return sql.toString();
	}
	
	protected String getDefaultQuery3()
	{
		StringBuffer sql = new StringBuffer();
		//      						1
		sql.append("SELECT top (1) dich_icef.anno_produzione_patrimoni ");
		sql.append("FROM domande, familiari, dich_icef " );
		sql.append("WHERE Domande.ID_domanda = Familiari.id_domanda ");
		sql.append("AND Familiari.id_dichiarazione = dich_icef.id_dichiarazione ");
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

			doQuery(getDefaultQuery3().toString());
			annoProduzionePatrimoni=records;
			
			doQuery(getDefaultQuery2().toString());
			iImmobiliDiversiDaResidenza=records;
			
			// legge i dati degli immobili ceduti dichiarati nel tab "Assistito"
			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*")) {
				//modalità normale con domanda
				if(query==null) {
					query = getDefaultQuery();
				}
				sql.append(query);
			}

			doQuery(sql.toString());

			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute( "annoProduzionePatrimoni",		annoProduzionePatrimoni);
			session.setAttribute( "iImmobiliDiversiDaResidenza",		iImmobiliDiversiDaResidenza);

		} else {
			records = session.getRecords();
			annoProduzionePatrimoni	= (Table)session.getAttribute( "annoProduzionePatrimoni" );
			iImmobiliDiversiDaResidenza	= (Table)session.getAttribute( "iImmobiliDiversiDaResidenza" );
		}
	}
	
	/**
	 * calcola il valore degli immobili distringuendo tra residenza e altri
	 */
	protected double getValoreImmobili2(boolean tutti, boolean residenza, boolean usaDetrazioneMaxValoreNudaProprieta ) {
		double total = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		int durata;
		Calendar theDate;

		//non posso usare records perchè in questa classe contiene i dati relativi agli immobili ceduti e non a quelli diversi dalla residenza
		for (int i = 1; i <= iImmobiliDiversiDaResidenza.getRows(); i++ ){
			try {
				
				boolean sommaValore = false;
				if(tutti)
				{
					sommaValore = true;
				}
				else
				{
					if ( 
							((((String)iImmobiliDiversiDaResidenza.getElement(i,1)).equals("0")) && !residenza ) 
							|| 
							(!(((String)iImmobiliDiversiDaResidenza.getElement(i,1)).equals("0")) && residenza ) ) {
						sommaValore = true;
					}
				}
				
				if(sommaValore)
				{
					try {
						total += getValoreICIRiga2(i,residenza);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						total = 0;
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
		//getTotalOfMaxUsofruttoForDich();
		if(usaDetrazioneMaxValoreNudaProprieta)
		{
			//rimosso luigi 19/07/2018
			//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
			//total = total - getTotalOfMaxUsofruttoForDich();
		}
		
		return total;
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

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2012);
		calendar.set(Calendar.MONTH, 2-1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		System.out.println(lastDay);
	}


}
