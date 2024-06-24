package c_elab.pat.ic;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedHashMap;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

/*****************************************************
 * classe QDati
 * oltre a fornire i dati per i nodi di input, calcola anche i 4 benefici pensione, maggiorazione_pensione, indennita_accompagnamento, assegno_integrativo
 * per fornire un confronto di correttezza con quanto calcolato dalla rete in elaout.
 * I filtri della query sono parametrizzati e letti da IC_Dati
 * La servlet IcElabCaller si occupa di richiamare n volte la servlet di calcolo e setta, prima di lanciare il calcolo, i campi
 * IC_dati.elab_anno_competenza, IC_dati.elab_anno_reddito, IC_dati.elab_definitivi_calcolo, IC_dati.elab_azzera_importi
 ******************************************************/

public abstract class QDati extends ElainNode {
	
	private final static int PARAM_IDTPESCLUSIONE_DINIEGO = 1;
	private final static int PARAM_IDTPESCLUSIONE_REVOCA = 2;
	
	private final static int DINIEGO_NONRESIDENTEPROVINCIA = 1;
	private final static int DINIEGO_SOGGETTONONINVALIDO = 2;
	private final static int DINIEGO_REDDITIDEFINITIVISOPRASOGLIAPRESUNTISOTTOSOGLIA = 3;
	private final static int DINIEGO_REDDITIDEFINITIVINONPRESENTATIENTROTERMINI = 4;
	private final static int DINIEGO_INCOMPATIBILITAALTRIINTERVENTI	= 5;
	private final static int DINIEGO_SENZAPERMESSOSOGGIORNO	= 6;
	private final static int DINIEGO_DUFFICIO = 19;
	
	private final static int REVOCA_DECESSODBENEFICIARIO = 8;
	private final static int REVOCA_TRASFERIMENTORESIDENZAFUORIPROVINCIA = 9;
	private final static int REVOCA_PERDITAINVALIDITA = 10;
	private final static int REVOCA_INVALIDITACONSCADENZAMANCATAREVISIONE = 11;
	private final static int REVOCA_RIDUZIONEINVALIDITA = 12;
	private final static int REVOCA_COMPIMENTO18 = 13;
	private final static int REVOCA_COMPIMENTO65 = 14;
	private final static int REVOCA_PERDITAREQUISITIECONOMICIANNISUCCESSIVI = 15;
	private final static int REVOCA_INCOMPATIBILITAALTRIINTERVENTI = 16;
	private final static int REVOCA_SCADENZAPERMESSOSOGGIORNO = 17;
	private final static int REVOCA_IRREPERIBILITA = 18;
	private final static int REVOCA_DUFFICIO = 20;
	
	public Table tabEsclusioni = null;
	
	private double indennita = 0.0;
	private double pensione = 0.0;
	private double maggiorazionePensione = 0.0;
	private double assegnoIntegrativo = 0.0;
	private double reddito = 0.0;
	private boolean ricovero = false;
	private double pensione1 = 0.0;
	private double pensione2 = 0.0;
	private double indennita1 = 0.0;
	private double indennita2 = 0.0;
	private double indennita3 = 0.0;
	private double indennita4 = 0.0;
	private double integrativo1 = 0.0;
	private double integrativo2 = 0.0;
	private double maggiorazione1 = 0.0;
	private double maggiorazione2 = 0.0;
	private double maggiorazione3 = 0.0;
	private double reddito1 = 0.0;
	private double reddito2 = 0.0;
	private int azzera_importi = 0;
	private int id_tp_invalidita = 0;
	private Calendar data_decorrenza_economica = null;
    private Calendar data_nascita = null;
    private double mesiPensione = 0.0;
    private int etaBen = 0;
    
    private String revocheArray = "";
    private String dinieghiArray = "";

	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QDati.class.getName(), IDdomanda, "records" );
		pensione = 0.0;
		maggiorazionePensione = 0.0;
		indennita = 0.0;
		assegnoIntegrativo = 0.0;
		etaBen = 0;
		revocheArray = "";
		dinieghiArray = "";
	}

	public void init(RunawayData dataTransfer) {
	
		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDati.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			long time=System.currentTimeMillis();
			super.init(dataTransfer);
			
			try {
				
				//REVOCHE & DINIEGHI
				StringBuffer q = new StringBuffer("select id_domanda, id_tp_esclusione, id_tp_motivo_esclusione, data ");
				q.append("from ic_esclusioni ");
				q.append("where id_domanda="+this.IDdomanda+" ");
				q.append("order by data ");
				
				doQuery(q.toString());
				tabEsclusioni=records;
				
				String firstToken = "1";
				
				//REVOCHE
				LinkedHashMap<String, String> mapRevoche = new LinkedHashMap<String, String>();
				mapRevoche.put("decessoBeneficiario", "0");
				mapRevoche.put("trasferimentoResidenzaFuoriProvincia", "0");
				mapRevoche.put("perditaInvalidita", "0");
				mapRevoche.put("invaliditaConScadenzaMancataRevisione", "0");
				mapRevoche.put("riduzioneInvalidita", "0");
				mapRevoche.put("compimento18", "0");
				mapRevoche.put("compimento65", "0");
				mapRevoche.put("perditaRequisitiEconomiciAnniSuccessivi", "0");
				mapRevoche.put("incompatibilitaAltriInterventi", "0");
				mapRevoche.put("scadenzaPermessoSoggiorno", "0");
				mapRevoche.put("irreperibilita", "0");
				mapRevoche.put("revocaDufficio", "0");
				
				int countRevoche = 0;
				
				if(tabEsclusioni!=null && tabEsclusioni.getRows()>0){
					for(int i=1; i<=tabEsclusioni.getRows(); i++) {
						int id_tp_esclusione = tabEsclusioni.getInteger(i, tabEsclusioni.getIndexOfColumnName("id_tp_esclusione"));
						if(id_tp_esclusione==PARAM_IDTPESCLUSIONE_DINIEGO){
							break;
						}
						int id_tp_motivo_esclusione = tabEsclusioni.getInteger(i, tabEsclusioni.getIndexOfColumnName("id_tp_motivo_esclusione"));
			
						if(id_tp_motivo_esclusione==REVOCA_DECESSODBENEFICIARIO){
							mapRevoche.put("decessoBeneficiario", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_TRASFERIMENTORESIDENZAFUORIPROVINCIA){
							mapRevoche.put("trasferimentoResidenzaFuoriProvincia", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_PERDITAINVALIDITA){
							mapRevoche.put("perditaInvalidita", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_INVALIDITACONSCADENZAMANCATAREVISIONE){
							mapRevoche.put("invaliditaConScadenzaMancataRevisione", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_RIDUZIONEINVALIDITA){
							mapRevoche.put("riduzioneInvalidita", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_COMPIMENTO18){
							mapRevoche.put("compimento18", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_COMPIMENTO65){
							mapRevoche.put("compimento65", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_PERDITAREQUISITIECONOMICIANNISUCCESSIVI){
							mapRevoche.put("perditaRequisitiEconomiciAnniSuccessivi", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_INCOMPATIBILITAALTRIINTERVENTI){
							mapRevoche.put("incompatibilitaAltriInterventi", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_SCADENZAPERMESSOSOGGIORNO){
							mapRevoche.put("scadenzaPermessoSoggiorno", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_IRREPERIBILITA){
							mapRevoche.put("irreperibilita", "1");
							countRevoche++;
							continue;
						}else if(id_tp_motivo_esclusione==REVOCA_DUFFICIO){
							mapRevoche.put("revocaDufficio", "1");
							countRevoche++;
							continue;
						}
					}
				}
	
				if(countRevoche>0){
					for(String motivo:mapRevoche.keySet()){
						revocheArray = revocheArray + mapRevoche.get(motivo);
					}
					revocheArray = firstToken + revocheArray;
				}else{
					revocheArray = "0";
				}
				
				//DINIEGHI
				LinkedHashMap<String, String> mapDinieghi = new LinkedHashMap<String, String>();
				mapDinieghi.put("nonResidenteProvincia", "0");
				mapDinieghi.put("soggettoNonInvalido", "0");
				mapDinieghi.put("redditiDefinitiviSopraSogliaPresuntiSottoSoglia", "0");
				mapDinieghi.put("redditiDefinitiviNonEntroTermini", "0");
				mapDinieghi.put("incompatibilitaAltriInterventi", "0");
				mapDinieghi.put("senzaPermessoSoggiorno", "0");
				mapDinieghi.put("diniegoDufficio", "0");
				
				int countDinieghi = 0;
				
				if(tabEsclusioni!=null && tabEsclusioni.getRows()>0){
					for(int i=1; i<=tabEsclusioni.getRows(); i++) {
						int id_tp_esclusione = tabEsclusioni.getInteger(i, tabEsclusioni.getIndexOfColumnName("id_tp_esclusione"));
						if(id_tp_esclusione==PARAM_IDTPESCLUSIONE_REVOCA){
							break;
						}
						int id_tp_motivo_esclusione = tabEsclusioni.getInteger(i, tabEsclusioni.getIndexOfColumnName("id_tp_motivo_esclusione"));
			
						if(id_tp_motivo_esclusione==DINIEGO_NONRESIDENTEPROVINCIA){
							mapDinieghi.put("nonResidenteProvincia", "1");
							countDinieghi++;
							continue;
						}else if(id_tp_motivo_esclusione==DINIEGO_SOGGETTONONINVALIDO){
							mapDinieghi.put("soggettoNonInvalido", "1");
							countDinieghi++;
							continue;
						}else if(id_tp_motivo_esclusione==DINIEGO_REDDITIDEFINITIVISOPRASOGLIAPRESUNTISOTTOSOGLIA){
							mapDinieghi.put("redditiDefinitiviSopraSogliaPresuntiSottoSoglia", "1");
							countDinieghi++;
							continue;
						}else if(id_tp_motivo_esclusione==DINIEGO_REDDITIDEFINITIVINONPRESENTATIENTROTERMINI){
							mapDinieghi.put("redditiDefinitiviNonEntroTermini", "1");
							countDinieghi++;
							continue;
						}else if(id_tp_motivo_esclusione==DINIEGO_INCOMPATIBILITAALTRIINTERVENTI){
							mapDinieghi.put("incompatibilitaAltriInterventi", "1");
							countDinieghi++;
							continue;
						}else if(id_tp_motivo_esclusione==DINIEGO_SENZAPERMESSOSOGGIORNO){
							mapDinieghi.put("senzaPermessoSoggiorno", "1");
							countDinieghi++;
							continue;
						}else if(id_tp_motivo_esclusione==DINIEGO_DUFFICIO){
							mapDinieghi.put("diniegoDufficio", "1");
							countDinieghi++;
							continue;
						}
					}
				}
				
				if(countDinieghi>0){
					for(String motivo:mapDinieghi.keySet()){
						dinieghiArray = dinieghiArray + mapDinieghi.get(motivo);
					}
					dinieghiArray = firstToken + dinieghiArray;
				}else{
					dinieghiArray = "0";
				}
	
				
				//DATI
				StringBuffer q1 = new StringBuffer();
				q1.append("SELECT        Domande.ID_domanda, IC_dati.ID_tp_invalidita, Doc.data_presentazione, Domande.data_nascita, 0 AS ricovero, "); 
				q1.append("     COALESCE (tab_redd.redd_tot, 0) AS reddito, ");
				q1.append(" 	IC_dati.elab_anno_competenza, def1.default_value AS Indennita1, def2.default_value AS Indennita2, def3.default_value AS Indennita3, def4.default_value AS Indennita4, def5.default_value AS Integrativo1, ");
				q1.append("		def6.default_value AS Integrativo2, def7.default_value AS Maggiorazione1, def8.default_value AS Maggiorazione2, def9.default_value AS Maggiorazione3, def10.default_value AS Pensione1, ");
				q1.append("		def11.default_value AS Pensione2, def12.default_value AS Reddito1, def13.default_value AS Reddito2, IC_dati.elab_azzera_importi, IC_dati.data_decorrenza_economica, ");
				q1.append(" (SELECT        mesi_da_65 ");
				q1.append(" FROM            R_eta_ultra65 ");
				q1.append(" WHERE  IC_dati.data_decorrenza_economica between data_da and coalesce(data_a, '2100-01-01')) as mesi_65 ");
				q1.append(" FROM            Doc ");
				q1.append(" INNER JOIN      Domande ON Doc.ID = Domande.ID_domanda ");
				q1.append(" INNER JOIN 	  	IC_dati ON Domande.ID_domanda = IC_dati.ID_domanda ");
				q1.append(" LEFT OUTER JOIN (SELECT        anno_reddito AS max_anno, definitivi, ID_domanda, redd_tot, redd_dip, redd_autonomo, malattia, borse_lavoro, produttivita, indennita_inail ");
				q1.append("                  FROM          IC_redditi ");
				q1.append("	) AS tab_redd ON Domande.ID_domanda = tab_redd.ID_domanda AND IC_dati.elab_anno_reddito=tab_redd.max_anno AND IC_dati.elab_definitivi_calcolo=tab_redd.definitivi ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def1 ON IC_dati.elab_anno_competenza = def1.anno AND Domande.ID_servizio = def1.ID_servizio AND Domande.ID_periodo = def1.ID_periodo AND (def1.ID_ente = 0) AND (def1.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def2 ON IC_dati.elab_anno_competenza = def2.anno AND Domande.ID_servizio = def2.ID_servizio AND Domande.ID_periodo = def2.ID_periodo AND (def2.ID_ente = 0) AND (def2.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def3 ON IC_dati.elab_anno_competenza = def3.anno AND Domande.ID_servizio = def3.ID_servizio AND Domande.ID_periodo = def3.ID_periodo AND (def3.ID_ente = 0) AND (def3.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def4 ON IC_dati.elab_anno_competenza = def4.anno AND Domande.ID_servizio = def4.ID_servizio AND Domande.ID_periodo = def4.ID_periodo AND (def4.ID_ente = 0) AND (def4.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def5 ON IC_dati.elab_anno_competenza = def5.anno AND Domande.ID_servizio = def5.ID_servizio AND Domande.ID_periodo = def5.ID_periodo AND (def5.ID_ente = 0) AND (def5.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def6 ON IC_dati.elab_anno_competenza = def6.anno AND Domande.ID_servizio = def6.ID_servizio AND Domande.ID_periodo = def6.ID_periodo AND (def6.ID_ente = 0) AND (def6.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def7 ON IC_dati.elab_anno_competenza = def7.anno AND Domande.ID_servizio = def7.ID_servizio AND Domande.ID_periodo = def7.ID_periodo AND (def7.ID_ente = 0) AND (def7.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def8 ON IC_dati.elab_anno_competenza = def8.anno AND Domande.ID_servizio = def8.ID_servizio AND Domande.ID_periodo = def8.ID_periodo AND (def8.ID_ente = 0) AND (def8.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def9 ON IC_dati.elab_anno_competenza = def9.anno AND Domande.ID_servizio = def9.ID_servizio AND Domande.ID_periodo = def9.ID_periodo AND (def9.ID_ente = 0) AND (def9.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def10 ON IC_dati.elab_anno_competenza = def10.anno AND Domande.ID_servizio = def10.ID_servizio AND Domande.ID_periodo = def10.ID_periodo AND (def10.ID_ente = 0) AND (def10.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def11 ON IC_dati.elab_anno_competenza = def11.anno AND Domande.ID_servizio = def11.ID_servizio AND Domande.ID_periodo = def11.ID_periodo AND (def11.ID_ente = 0) AND (def11.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def12 ON IC_dati.elab_anno_competenza = ");
				q1.append(" 	case when IC_dati.elab_anno_competenza<=2010 then def12.anno+1 ");
				q1.append(" 	else def12.anno end ");
				q1.append(" 	AND Domande.ID_servizio = def12.ID_servizio ");
				q1.append(" 	AND Domande.ID_periodo = def12.ID_periodo ");
				q1.append(" 	AND (def12.ID_ente = 0) ");
				q1.append(" 	AND (def12.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" INNER JOIN IC_C_DefaultIn AS def13 ON IC_dati.elab_anno_competenza = "); 
				q1.append(" 	case when IC_dati.elab_anno_competenza<=2010 then def13.anno+1 ");
				q1.append(" 	else def13.anno end ");
				q1.append(" 	AND Domande.ID_servizio = def13.ID_servizio ");
				q1.append(" 	AND Domande.ID_periodo = def13.ID_periodo ");
				q1.append(" 	AND (def13.ID_ente = 0) ");
				q1.append(" 	AND (def13.net = 'c_elab/pat/ic/pat.ic.net.html') ");
				q1.append(" WHERE      (def1.node = 'Indennita1') AND (def2.node = 'Indennita2') AND (def3.node = 'Indennita3') AND (def4.node = 'Indennita4') AND (def5.node = 'Integrativo1') AND ");
				q1.append(" (def6.node = 'Integrativo2') AND (def7.node = 'Maggiorazione1') AND (def8.node = 'Maggiorazione2') AND (def9.node = 'Maggiorazione3') AND (def10.node = 'Pensione1') AND ");
				q1.append(" (def11.node = 'Pensione2') AND (def12.node = 'Reddito1') AND (def13.node = 'Reddito2') ");
				q1.append(" AND Domande.ID_domanda = "+ this.IDdomanda );
	
				doQuery(q1.toString());
				session.setRecords( records );

				if(records.getElement(1,records.getIndexOfColumnName("ID_tp_invalidita"))!= null){
					id_tp_invalidita = records.getInteger(1, records.getIndexOfColumnName("ID_tp_invalidita") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("data_decorrenza_economica"))!= null){
					data_decorrenza_economica = records.getCalendar(1, records.getIndexOfColumnName("data_decorrenza_economica") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("data_nascita"))!= null){
					data_nascita = records.getCalendar(1, records.getIndexOfColumnName("data_nascita") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("ricovero"))!= null){
					ricovero = records.getBoolean(1, records.getIndexOfColumnName("ricovero") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("reddito"))!= null){
					reddito = records.getDouble(1, records.getIndexOfColumnName("reddito") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Pensione1"))!= null){
					pensione1 = records.getDouble(1, records.getIndexOfColumnName("Pensione1") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Pensione2"))!= null){
					pensione2 = records.getDouble(1, records.getIndexOfColumnName("Pensione2") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Indennita1"))!= null){
					indennita1 = records.getDouble(1, records.getIndexOfColumnName("Indennita1") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Indennita2"))!= null){
					indennita2 = records.getDouble(1, records.getIndexOfColumnName("Indennita2") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Indennita3"))!= null){
					indennita3 = records.getDouble(1, records.getIndexOfColumnName("Indennita3") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Indennita4"))!= null){
					indennita4 = records.getDouble(1, records.getIndexOfColumnName("Indennita4") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Integrativo1"))!= null){
					integrativo1 = records.getDouble(1, records.getIndexOfColumnName("Integrativo1") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Integrativo2"))!= null){
					integrativo2 = records.getDouble(1, records.getIndexOfColumnName("Integrativo2") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Maggiorazione1"))!= null){
					maggiorazione1 = records.getDouble(1, records.getIndexOfColumnName("Maggiorazione1") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Maggiorazione2"))!= null){
					maggiorazione2 = records.getDouble(1, records.getIndexOfColumnName("Maggiorazione2") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Maggiorazione3"))!= null){
					maggiorazione3 = records.getDouble(1, records.getIndexOfColumnName("Maggiorazione3") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Reddito1"))!= null){
					reddito1 = records.getDouble(1, records.getIndexOfColumnName("Reddito1") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("Reddito2"))!= null){
					reddito2 = records.getDouble(1, records.getIndexOfColumnName("Reddito2") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("elab_azzera_importi"))!= null){
					azzera_importi = records.getInteger(1, records.getIndexOfColumnName("elab_azzera_importi") );
				}
				if(records.getElement(1,records.getIndexOfColumnName("mesi_65"))!= null){
	            	mesiPensione = records.getDouble(1, records.getIndexOfColumnName("mesi_65") );
	            }
				
				if(countDinieghi>0){
					azzera_importi = 1;
				}

				int anni = Years.yearsBetween(LocalDate.fromCalendarFields(data_nascita), LocalDate.fromCalendarFields(data_decorrenza_economica)).getYears();
	    		double mesi = Months.monthsBetween(LocalDate.fromCalendarFields(data_nascita), LocalDate.fromCalendarFields(data_decorrenza_economica)).getMonths();
	    		
	    		mesi = mesi - (anni*12);
	    		
	    		int anniAdd = 0;
	    		
	    		if(mesiPensione>=12){
	    			anniAdd = new Double(mesiPensione/12).intValue();
	    			mesiPensione = mesiPensione - (anniAdd*12);
	    		}
	    		
	    		int anniPensione = 65 + anniAdd;
	    		
	    		if(anni>=0 && anni<18){
	    			etaBen = anni;
	    		}else if(anni>=18 && anni<anniPensione){
	    			etaBen = 30;//forzo la rete a considerare il soggetto come se avesse eta fra 18 e 65
	    		}else if(anni==anniPensione && mesi<mesiPensione){
	    			etaBen = 30;//forzo la rete a considerare il soggetto come se avesse eta fra 18 e 65
	    		}else if(anni>=anniPensione){
	    			etaBen = anni;
	    		}

				switch (id_tp_invalidita) {
				case 1://03 - Invalidi civili parziali
					if(reddito<=reddito1){
						pensione = pensione1;
						maggiorazionePensione = maggiorazione1;
					}
					break;
				case 2://04 - Invalidi civili assoluti
					if(etaBen>=18 && etaBen<=65 && reddito<=reddito2){
						pensione = pensione1;
						maggiorazionePensione = maggiorazione1;
						if(!ricovero){
							assegnoIntegrativo = integrativo1;
						}
					}
					break;
				case 3://05 - Invalidi civili minorenni non autosufficienti
					indennita = indennita3;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 4://05 - Invalidi civili assoluti non autosufficienti
					if(etaBen>=18 && etaBen<=65 && reddito<=reddito2){
						pensione = pensione1;
						maggiorazionePensione = maggiorazione1;
					}
					indennita = indennita3;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 5://05 - Invalidi civili assoluti ultrasessantacinquenni non autosufficienti
					indennita = indennita3;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 6://11 - Sordomuti
					if(etaBen>=18 && etaBen<=65 && reddito<=reddito2){
						pensione = pensione1;
						maggiorazionePensione = maggiorazione1;
					}
					indennita = indennita2;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 7://09 - Cechi parziali
					if(reddito<=reddito2){
						pensione = pensione1;
						if(etaBen<65){
							maggiorazionePensione = maggiorazione1;
						}else if(etaBen>=65 && etaBen<75){
							maggiorazionePensione = maggiorazione2;
						}else if(etaBen>=75){
							maggiorazionePensione = maggiorazione3;
						}
					}
					indennita = indennita1;
					assegnoIntegrativo = integrativo1;
					break;
				case 8://10 - Cechi totali
					if(reddito<=reddito2){
						if(ricovero){
							pensione = pensione1;
						}else{
							pensione = pensione2;
						}
						if(etaBen<65){
							maggiorazionePensione = maggiorazione1;
						}else if(etaBen>=65 && etaBen<75){
							maggiorazionePensione = maggiorazione2;
						}else if(etaBen>=75){
							maggiorazionePensione = maggiorazione3;
						}
					}
					indennita = indennita4;
					assegnoIntegrativo = integrativo2;
					break;
				case 9://07 - Invalidi civili minorenni
					pensione = pensione1;
					maggiorazionePensione = maggiorazione1;
					break;
				case 10://06 - Invalidi civili minorenni non deambulanti
					indennita = indennita3;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 11://06 - Invalidi civili assoluti non deambulanti
					if(etaBen>=18 && etaBen<=65 && reddito<=reddito2){
						pensione = pensione1;
						maggiorazionePensione = maggiorazione1;
					}
					indennita = indennita3;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 12://06 - Invalidi civili assoluti ultrasessantacinquenni non deambulanti
					indennita = indennita3;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 13://07+09 - Invalidi civili minorenni e ciechi parziali
					pensione = pensione1;
					maggiorazionePensione = maggiorazione1;
					if(reddito<=reddito2){
						pensione = pensione + pensione1;
						maggiorazionePensione = maggiorazionePensione + maggiorazione1;
					}
					assegnoIntegrativo = integrativo1;
					break;
				case 14://11 - Sordomuti ultrasessantacinquenni
					indennita = indennita2;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 15://11 - Sordomuti minorenni
					indennita = indennita2;
					if(!ricovero){
						assegnoIntegrativo = integrativo1;
					}
					break;
				case 16://10 - Ciechi totali minorenni
					indennita = indennita4;
					assegnoIntegrativo = integrativo2;
					break;
				default:
					pensione = 0.0;
					indennita = 0.0;
					maggiorazionePensione = 0.0;
					assegnoIntegrativo = 0.0;
					break;
				}
				if(azzera_importi!=0){
					pensione = 0.0;
					indennita = 0.0;
					maggiorazionePensione = 0.0;
					assegnoIntegrativo = 0.0;
				}
			} catch(NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				pensione = 0.0;
				indennita = 0.0;
				maggiorazionePensione = 0.0;
				assegnoIntegrativo = 0.0;
				etaBen = -1;
			} catch (ParseException e) {
				System.out.println("ParseException in " + getClass().getName() + ": " + e.toString());
				pensione = 0.0;
				indennita = 0.0;
				maggiorazionePensione = 0.0;
				assegnoIntegrativo = 0.0;
				etaBen = -1;
			}
			
			session.setAttribute("etaBen", etaBen);
			session.setAttribute("pensione", pensione);
			session.setAttribute("maggiorazionePensione", maggiorazionePensione);
			session.setAttribute("indennita", indennita);
			session.setAttribute("assegnoIntegrativo", assegnoIntegrativo);
			session.setAttribute("revocheArray", revocheArray);
			session.setAttribute("dinieghiArray", dinieghiArray);
			
			session.setInitialized( true );
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+(System.currentTimeMillis()-time));
			
		} else {
			records = session.getRecords();
			etaBen = (int) session.getAttribute("etaBen");
			pensione = (double) session.getAttribute("pensione");
			maggiorazionePensione = (double) session.getAttribute("maggiorazionePensione");
			indennita = (double) session.getAttribute("indennita");
			assegnoIntegrativo = (double) session.getAttribute("assegnoIntegrativo");
			revocheArray = (String) session.getAttribute("revocheArray");
			dinieghiArray = (String) session.getAttribute("dinieghiArray");
		}

	} 
	
	public QDati(){		//{{INIT_CONTROLS
		//}}
	}
	
	public double getRevoche() throws Exception{
		return Double.parseDouble(revocheArray);
	}
	
	public double getDinieghi() throws Exception{
		return Double.parseDouble(dinieghiArray);
	}

	public double getValue() {
		return 0.0;
	}

	public double getIndennita() {
		return indennita;
	}

	public double getPensione() {
		return pensione;
	}

	public double getMaggiorazionePensione() {
		return maggiorazionePensione;
	}

	public double getAssegnoIntegrativo() {
		return assegnoIntegrativo;
	}
	
	public int getEtaBen() {
		return etaBen;
	}

	public void setEtaBen(int etaBen) {
		this.etaBen = etaBen;
	}

}