package c_elab.pat.coltiv18;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;
import it.clesius.db.util.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QDatiDomanda extends ElainNode {

	protected Table datiUnitaLavorative;
	protected Table datiContributiAnnui;
	protected Table datiRParamsAnniContributi;
	protected Table datiEta;
	protected int annoContributoAttuale = -1;
	protected int annoContributoPrecedente = -1;	

	/** QProvvisorio constructor */
	public QDatiDomanda() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 * 
	 * la data cancellato al non è stata inserita nel calcolo per scelta concordata con l'APAPI
	 * 
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QDatiDomanda.class.getName(), IDdomanda );
		datiUnitaLavorative = null;
		datiContributiAnnui = null;
		datiRParamsAnniContributi = null;
		datiEta = null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 * 
	 * inserire il controllo data cambio fascia o 2010 o 2011
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QDatiDomanda.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();
			// 		   						  1         	   		  2	    	   		       3   		 	  		  4						5					6								7
			sql.append("SELECT IVS_unita.iscritto_dal, IVS_unita.riduzione65_dal, IVS_unita.cancellato_dal, Soggetti.data_nascita, Soggetti.ID_soggetto,IVS_unita.esonerato_corrente,IVS_unita.esonderato_arretrato "); 
			sql.append("FROM         IVS_unita INNER JOIN "); 
			sql.append(" Soggetti ON IVS_unita.ID_soggetto = Soggetti.ID_soggetto "); 
			sql.append("WHERE     (IVS_unita.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") "); 
			sql.append("ORDER BY IVS_unita.ID_soggetto "); 
			doQuery(sql.toString());
			datiUnitaLavorative = records;

			sql = new StringBuffer();
			// 		   						1      			   2	 			3   	    		  4									5
			sql.append("SELECT IVS_tp_eta.ID_eta, IVS_tp_eta.anno, IVS_tp_eta.eta_min, IVS_tp_eta.eta_max, IVS_tp_eta.mese_raggiungimento_eta_min_compreso, ");
			//										6											7
			sql.append("IVS_tp_eta.mese_raggiungimento_eta_max_compreso, IVS_tp_eta.ID_eta_senza_flag_riduzione ");
			sql.append("FROM IVS_tp_eta ");
			doQuery(sql.toString());
			datiEta = records;

			sql = new StringBuffer();
			// 		   								1      					   2	 				  3							 	4
			sql.append("SELECT IVS_R_contributi.ID_fascia, IVS_R_contributi.ID_eta, IVS_R_contributi.anno, IVS_R_contributi.contributo_annuo ");
			sql.append("FROM         IVS_R_contributi ");
			doQuery(sql.toString());
			datiContributiAnnui = records;

			sql = new StringBuffer();
			// 		   							1					 2
			sql.append("SELECT     R_Params.param_value, R_Params.ID_ente ");
			sql.append("FROM         Domande INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID INNER JOIN ");
			sql.append("R_Params ON Domande.ID_servizio = R_Params.ID_servizio AND Domande.ID_periodo = R_Params.ID_periodo ");
			sql.append("AND Domande.ID_ente_erogatore = R_Params.ID_ente ");
			sql.append("WHERE     (R_Params.param = 'maskParam') AND (Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") "); 
			doQuery(sql.toString());
			datiRParamsAnniContributi = records;

			sql = new StringBuffer();
			// 		   									   1      					         2	 				                          3
			sql.append("SELECT     IVS_Aziende_Agricole.fascia, IVS_Aziende_Agricole.fascia_precedente_da, IVS_Aziende_Agricole.fascia_precedente_data, ");
			//						   			  4      					            5	 				                         6
			sql.append("IVS_Aziende_Agricole.importo_IVS, IVS_Aziende_Agricole.importo_IVS_233_90, IVS_Aziende_Agricole.importo_IVS_160_75, ");
			//			   								7      					         				8	 				                          9
			sql.append("IVS_Aziende_Agricole.importo_IVS_arretrati, IVS_Aziende_Agricole.importo_IVS_233_90_arretrati, IVS_Aziende_Agricole.importo_IVS_160_75_arretrati, ");
			//						10							11							12									13									
			sql.append("Doc.data_presentazione, Domande.id_ente_erogatore,IVS_Aziende_Agricole.valutato_apapi,IVS_Aziende_Agricole.contributo_calcolato_apapi ");
			
			sql.append(",Domande.escludi_ufficio ");
			sql.append(",IVS_Aziende_Agricole.ID_altitudine ");
			
			sql.append("FROM         IVS_Aziende_Agricole INNER JOIN ");
			sql.append("            Domande ON IVS_Aziende_Agricole.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("            Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE     (IVS_Aziende_Agricole.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") "); 

			doQuery(sql.toString());			

			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("datiContributiAnnui", datiContributiAnnui);
			session.setAttribute("datiUnitaLavorative", datiUnitaLavorative);
			session.setAttribute("datiRParamsAnniContributi", datiRParamsAnniContributi);
			session.setAttribute("datiEta", datiEta);

		} else {
			records = session.getRecords();
			datiUnitaLavorative = (Table)session.getAttribute("datiUnitaLavorative");
			datiContributiAnnui = (Table)session.getAttribute("datiContributiAnnui");
			datiRParamsAnniContributi = (Table)session.getAttribute("datiRParamsAnniContributi");
			datiEta = (Table)session.getAttribute("datiEta");
		}
		setRParamsAnniContributi();
	}

	protected void setRParamsAnniContributi()
	{
		int idEnteErogatore = new Integer(records.getInteger(1, 11));
		if( idEnteErogatore == 0 )
		{
			idEnteErogatore = -1;
		}
		for (int i = 1; i <= datiRParamsAnniContributi.getRows(); i++) {
			String paramValue = datiRParamsAnniContributi.getString(i, 1);
			int idEnteRParams = datiRParamsAnniContributi.getInteger(i, 2);

			if(idEnteRParams == idEnteErogatore)
			{
				String param = "anno_contributo";
				int j = paramValue.indexOf(param+"=");
				if(j>=0) {
					int x = paramValue.indexOf(";",j+1);
					String anno = null;
					if(x<=0) {
						anno = paramValue.substring(j+ param.length() + 1);
					}
					else
					{
						anno = paramValue.substring(j+ param.length() + 1,x);
					}
					if(anno!=null)
					{
						annoContributoAttuale = Integer.parseInt(anno);
					}
				}

				param = "anno_contributo_precedente";
				j = paramValue.indexOf(param+"=");
				if(j>=0) {
					int x = paramValue.indexOf(";",j+1);
					String anno = null;
					if(x<=0) {
						anno = paramValue.substring(j+ param.length() + 1);
					}
					else
					{
						anno = paramValue.substring(j+ param.length() + 1,x);
					}
					if(anno!=null)
					{
						annoContributoPrecedente = Integer.parseInt(anno);
					}
				}
				break;
			}
		}
	}

	protected double getImportoMensile(int anno, int mese) throws NullPointerException, ParseException
	{
		double ret = 0;

		for (int i = 1; i <= datiUnitaLavorative.getRows(); i++) {
			int esonerato_corrente =0; 
			int esonerato_arretrato =0;
			if(datiUnitaLavorative.getString(i, 6)!=null ) {
				try {
					esonerato_corrente=datiUnitaLavorative.getInteger(i, 6);
					esonerato_arretrato=datiUnitaLavorative.getInteger(i, 7);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			if((esonerato_corrente==0 && anno==annoContributoAttuale) ||
					(esonerato_arretrato==0 && anno==annoContributoPrecedente) ) {		
				String iscritto_dalString = datiUnitaLavorative.getString(i, 1);
				Calendar iscritto_dal = null;
				if(iscritto_dalString!=null)
				{
					iscritto_dal = datiUnitaLavorative.getCalendar(i, 1);
				}
				else
				{
					//messo fisso il 01/01/anno_contributo_attuale
					iscritto_dal = getDataRiferimento(annoContributoAttuale, 1);
					//iscritto_dal.add(Calendar.DATE, -1);
				}
				boolean riduzione65=false;
				String st_riduzione65 =datiUnitaLavorative.getString(i, 2);
				String dt_riduzione65=DateTimeFormat.toItDate(st_riduzione65);
				if (st_riduzione65!=null){
					riduzione65=true;
				}
				// = datiUnitaLavorative.getBoolean(i, 2);
				String cancellato_dalString = datiUnitaLavorative.getString(i, 3);
				Calendar cancellato_dal = null;
				if(cancellato_dalString!=null)
				{
					cancellato_dal = datiUnitaLavorative.getCalendar(i, 3);
				}
				Calendar dataNascita = datiUnitaLavorative.getCalendar(i, 4);
				int idSoggetto = datiUnitaLavorative.getInteger(i, 5);
	
				if(unitaAttiva(anno, mese, iscritto_dal, cancellato_dal))
				{
					String id_eta = getEta(anno, mese, dataNascita, riduzione65);
					int fascia = getFascia(anno, mese);
					double numeroMesi = 12.0;
					double importo = getImporto(id_eta,fascia,anno);
					importo = importo / numeroMesi;
				    ret += importo;
				}
			}	
		}//end for

		return ret;
	}

	private double getImporto(String idEta, int fascia, int anno) {
		double importo = 0;
		 
		for (int i = 1; i <= datiContributiAnnui.getRows(); i++) 
		{
			
			int ID_fascia = datiContributiAnnui.getInteger(i, 1);
			String ID_eta = datiContributiAnnui.getString(i, 2);
			int annoContributo = datiContributiAnnui.getInteger(i, 3);
			double contributo_annuo = datiContributiAnnui.getDouble(i, 4);
			
			if(ID_fascia == fascia && idEta.equals(ID_eta) && annoContributo == anno)
			{
				importo = contributo_annuo;
				//System.out.println("ID_eta: "+ID_eta+", ID_fascia: "+ID_fascia+", annoContributo: "+annoContributo + ", importo: "+importo);
				break;
			}
		}
		
		return importo;
	}

	protected boolean unitaAttiva(int anno, int mese, Calendar iscritto_dal, Calendar cancellato_dal)
	{
		boolean ret = false;

		Calendar dataRiferimento = getDataRiferimento(anno, mese);
		if(iscritto_dal.getTime().getTime()<=dataRiferimento.getTime().getTime())
		{
			ret = true;
		}

		if(ret && cancellato_dal!=null)
		{
			dataRiferimento = getDataRiferimento(anno, mese);
			dataRiferimento.add(Calendar.MONTH, 1);
			dataRiferimento.add(Calendar.DATE, -1);
			if(dataRiferimento.getTime().getTime()<=cancellato_dal.getTime().getTime())
			{
				ret = true;
			}
			else
			{
				ret = false;
			}
		}

		return ret;

	}

	protected int getFascia(int anno, int mese) throws NullPointerException, ParseException
	{
		int ret = -1;

		int fasciaAttuale = records.getInteger(1, 1);
		Integer fasciaPrecedente = null;
		String fasciaPrecedenteString = records.getString(1, 2);
		if(fasciaPrecedenteString!=null)
		{
			fasciaPrecedente = records.getInteger(1, 2);
		}
	
		Calendar fasciaPrecedenteData = null;
		if(records.getElement(1, 3)!=null)
		{
			fasciaPrecedenteData = records.getCalendar(1, 3);
		}
		if(fasciaPrecedente!=null && fasciaPrecedenteData!=null)
		{
			Calendar dataRiferimento = getDataRiferimento(anno, mese);
			
			if(fasciaAttuale<fasciaPrecedente)
			{
				dataRiferimento.add(Calendar.MONTH, 1);
				dataRiferimento.add(Calendar.DATE, -1);
				
				
				if(dataRiferimento.getTime().getTime() <= fasciaPrecedenteData.getTime().getTime())
				{
					ret = fasciaPrecedente;
				}
				else
				{
					ret = fasciaAttuale;
				}
			}
			else
			{
					
				if(dataRiferimento.getTime().getTime() <= fasciaPrecedenteData.getTime().getTime())
				{
					ret = fasciaPrecedente;
				}
				else
				{
					ret = fasciaAttuale;
				}
			}
			
			
			//System.out.println("fascia: "+ret+", fasciaPrecedente: "+fasciaPrecedente+", fasciaAttuale: "+fasciaAttuale+", dataRiferimento: "+getReadableDate(dataRiferimento) + ", fasciaPrecedenteData: "+getReadableDate(fasciaPrecedenteData));
			
		}
		else
		{
			ret = fasciaAttuale;
		}
		return ret;
	}

	protected String getEta(int anno, int mese, Calendar dataNascita, boolean riduzione65)
	{
		String ret = null;
		//System.out.println(it.clesius.db.util.DateTimeFormat.toSqlDate(dataNascita.getTime()));
		Calendar dataRiferimentoMese = getDataRiferimento(anno, mese);
		Calendar dataRiferimentoMeseSuccessivo = getDataRiferimento(anno, mese);
		dataRiferimentoMeseSuccessivo.add(Calendar.MONTH, 1);

		for (int i = 1; i <= datiEta.getRows(); i++) 
		{
			String ID_eta = datiEta.getString(i, 1);
			int annoEta = datiEta.getInteger(i, 2);
			int eta_min = datiEta.getInteger(i, 3);
			int eta_max = datiEta.getInteger(i, 4);
			boolean mese_raggiungimento_eta_min_compreso = datiEta.getBoolean(i, 5);
			boolean mese_raggiungimento_eta_max_compreso = datiEta.getBoolean(i, 6);
			String ID_eta_senza_flag_riduzione = datiEta.getString(i, 7);

			if(annoEta==anno)
			{
				Calendar dataEtaMin = (Calendar)dataRiferimentoMeseSuccessivo.clone();
				if(mese_raggiungimento_eta_max_compreso)
				{
					dataEtaMin = (Calendar)dataRiferimentoMese.clone();
				}
				dataEtaMin.add(Calendar.YEAR, -eta_max);

				Calendar dataEtaMax = (Calendar)dataRiferimentoMese.clone();
				if(mese_raggiungimento_eta_min_compreso)
				{
					dataEtaMax = (Calendar)dataRiferimentoMeseSuccessivo.clone();
				}
				dataEtaMax.add(Calendar.YEAR, -eta_min);
				
				//System.out.println("ID_eta: "+ID_eta+", dataNascita: "+getReadableDate(dataNascita)+", dataEtaMin: "+getReadableDate(dataEtaMin) + ", dataEtaMax: "+getReadableDate(dataEtaMax));
				
				if(dataNascita.getTime().getTime() >= dataEtaMin.getTime().getTime() &&
						dataNascita.getTime().getTime() < dataEtaMax.getTime().getTime())
				{
					if(ID_eta_senza_flag_riduzione!=null && !riduzione65)
					{
						ret = ID_eta_senza_flag_riduzione;
					}
					else
					{
						ret = ID_eta;
					}
					break;
				}

			}

		}

		return ret;
	}
	
	private String getReadableDate(Calendar calendar)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(calendar.getTime());
	}

	protected Calendar getDataRiferimento(int anno, int mese)
	{
		Calendar dataRiferimento = Calendar.getInstance();
		dataRiferimento.set(Calendar.YEAR, anno);
		dataRiferimento.set(Calendar.DAY_OF_MONTH, 1);
		dataRiferimento.set(Calendar.MONTH, mese-1);
		dataRiferimento.set(Calendar.HOUR_OF_DAY, 0);
		dataRiferimento.set(Calendar.MINUTE, 0);
		dataRiferimento.set(Calendar.SECOND, 0);
		dataRiferimento.set(Calendar.MILLISECOND, 0);
		return dataRiferimento;
	}


	public static int getEta(Calendar calNascita, Calendar calRif) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int eta = 0;
		if (calRif == null)
			calRif = Calendar.getInstance();

		eta = calRif.get(Calendar.YEAR) - calNascita.get(Calendar.YEAR);

		if (calRif.get(Calendar.MONTH) - calNascita.get(Calendar.MONTH) < 0) {
			eta = eta - 1;
		}

		if (calRif.get(Calendar.MONTH) == calNascita.get(Calendar.MONTH)
				&& calRif.get(Calendar.DAY_OF_MONTH)
				< calNascita.get(Calendar.DAY_OF_MONTH)) {
			eta = eta - 1;
		}

		//        if(dataNascita.getYear()>= 90) { // l'utente è nato tra il 1890  e 1900
		//            eta += 100;
		//        }

		//System.out.println("usufrutto = " + eta);

		return eta;
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}
