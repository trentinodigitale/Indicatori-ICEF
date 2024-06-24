package c_elab.pat.asscura;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.joda.time.LocalDate;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


public abstract class QDati extends ElainNode {

	public static int					trace				= 0;
	public static int					kMinAnno			= 2010;
	public static int					kMAxAnno			= 2032;

	private final static int			ID_VAR_OSP			= 14;
	private final static int			ID_VAR_RSA_NON_DEF	= 15;
	private final static int			ID_VAR_CONGEDO		= 16;
	private final static int			ID_VAR_RSA_DEF		= 17;


	public Table						iAcConfigurazioni	= null;
	public Table						iAcVariazioni		= null;
	public Table						iAcDati				= null;
	public Table						iAcRiaccertamento	= null;
	public Table						iAcFascicolo		= null;
	public Table						iAcFascicoloParent	= null;
	public Table						iV90Giorni			= null;
	public Table						datiC5				= null;
	public Hashtable<Integer, Double>	vResultm			= null;

	private double						DOUBLE_25000		= 25000.00;
	private final int					ID_SERVIZIO_61000	= 61000;
	private int							idServizio;
	private int							idPeriodo;

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QDati.class.getName(), IDdomanda );
		iAcVariazioni		= null;
		iAcConfigurazioni	= null;
		iAcRiaccertamento	= null;
		iAcFascicolo		= null;
		iAcDati				= null;
		datiC5				= null;
		iV90Giorni			= null;
		idServizio			= 0;
		idPeriodo			= 0;
	}

	//id_domanda_uscita
	//Check_riaccertamento


	/*
		1	trasferimento fuori provincia						NULL	NULL	NULL	31		2       2	//fascicolo
		2	trasferimento tra USL fuori provincia				NULL	NULL	NULL	31		4       2	//fascicolo
		3	iscrizione AIRE										NULL	NULL	NULL	31		A       2	//fascicolo
		4	decesso												NULL	NULL	NULL	1		3       2	//soggetto
		5	incompatibilità con altri contributi				NULL	0		0		27		NULL	4	//fascicolo
		6	incompatibilità con altri contributi da				NULL	0		1		27		NULL	2	//fascicolo
		7	rinuncia ad altri contributi da						NULL	1		0		27		NULL	1	//fascicolo
		8	indennità di accompagnamento assente				NULL	0		0		28		NULL	4	//fascicolo
		9	revoca indennità di accompagnamento da				NULL	0		1		28		NULL	2	//fascicolo
		10	assegnazione indennità di accompagnamento da		NULL	1		0		28		NULL	1	//fascicolo
		11	esclusione d'Ufficio APAPI							NULL	0		0		29		NULL	4	//fascicolo
		12	esclusione d'Ufficio APSS							NULL	0		0		30		NULL	4	//fascicolo
		14	ricovero ospedaliero								NULL	NULL	NULL	50		NULL	3	//soggetto
		15	ricovero in casa di riposo							NULL	NULL	NULL	51		NULL	3	//soggetto
		16	contributo biennale retribuito D.Lgs. n. 151/2001	NULL	NULL	NULL	NULL	NULL	3	//soggetto
		18	esclusione d'Ufficio APAPI da						NULL	0		1		29		NULL	2	//fascicolo
		19	rientro d'Ufficio APAPI								NULL	1		0		29		NULL	1	//fascicolo non presente
		20	esclusione d'Ufficio APSS da						NULL	0		1		30		NULL	2	//fascicolo
		21	rientro d'Ufficio APSS da							NULL	1		0		30		NULL	1	//fascicolo non presente
		23	mancata presentazione riaccertamento				NULL	NULL	NULL	NULL	NULL	2	//fascicolo
		30	rinuncia formalizzata utente						NULL	0		0		32		NULL	4	//fascicolo
		31	rinuncia formalizzata utente da						NULL	0		1		32		NULL	2	//fascicolo
		32	revoca APSS											NULL	0		1		33		NULL	2 	//fascicolo
		35	incompatibilità per ricovero in casa di riposo		NULL	NULL	NULL	52		NULL	4	//fascicolo
		100	nuovo profilo										1		NULL	NULL	NULL	NULL	0	//fascicolo
		200	mancanza pre-requisito residenza					NULL	NULL	NULL	26		NULL	5	//fascicolo
		201	mancanza pre-requisito ICEF							NULL	NULL	NULL	25		NULL	5	//fascicolo
	 */
	//AC_configurazione
	// 	|id_servizio |id_periodo |start 		|end		|Sup 		|....
	// 	|30500		|30500		|01/06/2012 |30/06/2013 |0,28000	|...
	// 	|30500		|30500		|01/07/2013 |31/12/2013 |0,31000	|..
	// 	|30500		|30501		|01/07/2013 |31/12/2014 |0,31000	|.
	// 	|30600		|30600		|01/01/2014 |31/12/2014 |0,31000	|


	//AC_fascicoli
	//	|id_soggetto|id_fascicolo	|id_domanda	|
	//	|10			|100000			|100000		|
	//	|10			|100000			|100001		|
	//	|10			|100000			|100002		|
	//	|10			|200000			|200000		|
	//	|10			|200000			|200001		|
	//......

	/*
		insert into ac_variazioni_soggetti
		insert into ac_variazioni_soggetti
		SELECT
		      domande.id_soggetto
		      ,ID_tp_variazione
		      ,data_inizio
		      ,data_fine
		      ,data_variazione
		      ,valore
		      ,ID_evento_APSS
		      ,codice_ric_apss
		  from ac_variazioni
		  inner join domande on  domande.id_domanda=ac_variazioni.id_domanda
		   where id_tp_variazione=4 or id_tp_variazione=14 or id_tp_variazione=15 or id_tp_variazione=16

		  insert into ac_fascicoli
  			select id_soggetto,id_domanda,id_domanda from domande where id_servizio=30500 and id_periodo=30501

  		  insert into ac_fascicoli
  			select id_soggetto,id_domanda,id_domanda from domande where id_servizio=30500 and id_periodo=30500
	 */



	/*se alla prima visita è in rsa >30g allora contributo parte da primo giorno mese cucessivo alla visita
	 *
		[14.26.24] mazzon_andrea: 1. CASO 1: persona che presenta una nuova domanda AC e che nel periodo tra la data di
		presentazione di quest'ultima e la data della vista UVM entra e/o esce da una RSA:

		a) (rsa-rsa) se la persona è in RSA alla data di presentazione della domanda ed è in RSA anche alla data della visita UVM,
		 si suppone che sia un caso stabilmente in RSA. AC viene riconosciuto dal mese successivo all'uscita da RSA. Se la persona
		 poi non esce mai da RSA l'assegno continua a rimanere sospeso e la domanda verrà esclusa alla fine del periodo di competenza per mancata presentazione del riaccertamento ICEF;

		b) (casa-rsa>30) se la persona è a casa alla data di presentazione della domanda e in RSA alla data della visita UVM e se tra la data di presentazione della domanda
		 e la data della visita UVM rimane in RSA per più di 30 gg, si suppone che sia un caso che è entrato in maniera stabile in RSA. AC viene riconosciuto dal mese successivo
		  all'uscita da RSA. Se la persona poi non esce mai da RSA l'assegno continua a rimanere sospeso e la domanda verrà esclusa alla fine del periodo
		   di competenza per mancata presentazione del riaccertamento ICEF;

		c) (casa-rsa<30) se la persona è a casa alla data di presentazione della domanda e in RSA alla data della visita UVM e se tra la data di presentazione della domanda
		e la data della visita UVM rimane in RSA per non più di 30 gg, si suppone che tale ricovero sia per sollievo per cui AC viene riconosciuto dal mese successivo alla domanda
		 e il ricovero in RSA viene trattato come una normale sospensione (vedi caso 2);

		d) (casa-casa) se la persona è a casa alla data di presentazione della domanda ed è a casa anche alla data della visita UVM ma tra i due eventi viene ricoverato in RSA, si
		suppone che tale ricovero sia per sollievo per cui AC viene riconosciuto dal mese successivo alla domanda e il ricovero in RSA viene trattato come una normale sospensione (vedi caso 2);
		e) (rsa-casa) se la persona è in RSA alla data di presentazione della domanda ed è a casa alla data della visita UVM,
		 si suppone che il ricovero iniziale fosse stato per sollievo per cui AC viene riconosciuto dal mese successivo alla domanda e
		 il ricovero in RSA viene trattato come una normale sospensione (vedi caso 2).
	 */


	/** inizializza la Table iAcDati con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */

	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QDati.class.getName(), IDdomanda  );
		if (!session.isInitialized()) {
			//System.out.println("4 ########################################## :"+System.currentTimeMillis());
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			/*
			 * 1	> id_domanda
			 * 2	> ID_variazione
			 * 3	> ID_tp_variazione (100=profilo ricevuto)
			 * 4	> data_inizio
			 * 5	> data_fine
			 * 6	> data_variazione
			 * 7	> valore
			 * 8	> ID_tp_variazione_categoria
			 * 9	> data_decorrenza_ia
			 */

			sql = new StringBuffer();

			//proroga 21/12/2017
			//in questi case è gestita la proroga sulla data di presentazione: ad esempio domande di riacc 2020 presentate fino al 31/03/2021 vengono considerate come presentate il 31/12/2020
			sql.append("SELECT case when Domande.id_servizio=30617 and data_presentazione>'2017-12-31' and data_presentazione <'2018-03-01' then CAST('2017-12-31' As Date) else case when Domande.id_servizio=30619 and data_presentazione>'2019-12-31' and data_presentazione <'2020-03-01' then CAST('2019-12-31' As Date) else case when Domande.id_servizio=30620 and data_presentazione>'2020-12-31' and data_presentazione <'2021-04-01' then CAST('2020-12-31' As Date) else case when Domande.id_servizio=30621 and data_presentazione>'2021-12-31' and data_presentazione <'2022-04-01' then CAST('2021-12-31' As Date) else case when Domande.id_servizio=30622 and data_presentazione>'2022-12-31' and data_presentazione <'2023-04-01' then CAST('2022-12-31' As Date) else data_presentazione end  end end end end as data_presentazione,");
			//sql.append("SELECT Doc.data_presentazione,   ");
			sql.append("Soggetti.data_nascita,   ");
			sql.append("case when id_servizio=30500 then AC_dati.residenza_requisito else 1 end as residenza_requisito, ");
			sql.append("case when id_servizio=30500 then AC_dati.residenza_certificatore else 2 end as residenza_certificatore, ");
			sql.append("case when id_servizio=30500 then AC_dati.indennita_requisito else 1 end as indennita_requisito, ");
			sql.append("case when id_servizio=30500 then AC_dati.indennita_certificatore else 0 end as indennita_certificatore, ");
			sql.append("case when id_servizio=30500 then AC_dati.compatibilita_requisito else 1 end as compatibilita_requisito, ");
			sql.append("case when id_servizio=30500 then AC_dati.compatibilita_certificatore else 0 end as compatibilita_certificatore, ");
			sql.append("case when id_servizio=30500 then AC_dati.data_decorrenza_ia else null end as data_decorrenza_ia, ");
			sql.append("case when id_servizio=30500 then AC_dati.progressivo_elab else 0 end as progressivo_elab, ");
			sql.append("case when id_servizio=30500 then AC_dati.ICEF_prec else 0 end as ICEF_prec, ");
			sql.append("id_servizio,id_periodo,Soggetti.id_soggetto  ");
			sql.append("FROM Doc INNER JOIN ");
			sql.append("Domande ON Doc.ID = Domande.ID_domanda LEFT JOIN ");
			sql.append("AC_dati ON AC_dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			if(trace>0){
				System.out.println(sql.toString());
			}
			doQuery(sql.toString());

			iAcDati=records;
			if(trace>0){
				System.out.println("NUMERO RECORD:"+iAcDati.getRows());
			}

			setIdServizio(iAcDati.getInteger(1, 12));
			setIdPeriodo(iAcDati.getInteger(1, 13));

			sql = new StringBuffer();
			sql.append("select id_domanda_uscita from ac_fascicoli ");
			sql.append("inner join domande on domande.id_domanda=ac_fascicoli.id_domanda ");
			sql.append("inner join doc on domande.id_domanda=doc.id ");
			sql.append("where ac_fascicoli.id_fascicolo=(select id_fascicolo from ac_fascicoli where id_domanda=");
			sql.append(IDdomanda);
			sql.append(") and ac_fascicoli.id_domanda>=");
			sql.append(IDdomanda);
			sql.append(" and id_servizio <> 30700 and doc.crc IS NOT NULL and domande.id_ente_erogatore > 0 order by ac_fascicoli.id_domanda ");

			if(trace!=0){
				System.out.println(sql.toString());
			}
			doQuery(sql.toString());
			iAcRiaccertamento=records;


			sql = new StringBuffer();
			/*
			 * 01. Id_servizio,
			 * 02. Id_periodo,
			 * 03. Inizio_periodo,
			 * 04. Fine_periodo,
			 * 05. Sup,
			 * 06. Max1,
			 * 07. Max2,
			 * 08. Max3,
			 * 09. Max4,
			 * 10. Min1,
			 * 11. Min2,
			 * 12. Min3,
			 * 13. Min4,
			 * 14. Detraz_65,
			 * 15. Inizio_vettore,
			 * 16. progressivo_elab,
			 * 17. Check_riaccertamento,
			 * 18. Franchigia_ast
			 */
			sql.append("SELECT  Id_servizio,	Id_periodo,	Inizio_periodo,	Fine_periodo,	Sup,	Max1,	Max2,	Max3,	Max4,	Min1,	Min2,	Min3,	Min4,	Detraz_65,	Inizio_vettore,progressivo_elab,Check_riaccertamento, Franchigia_ast FROM AC_configurazione where "
					+" id_servizio="+iAcDati.getInteger(1, 12)
					+" and id_periodo="+iAcDati.getInteger(1, 13)+" order by Inizio_periodo asc"
					//+" and progressivo_elab="+records.getInteger(1, 10)
					);
			if(trace!=0){
				System.out.println(sql.toString());
			}
			doQuery(sql.toString());
			iAcConfigurazioni = records;
			if(trace>0){
				System.out.println("NUMERO RECORD:"+iAcConfigurazioni.getRows());
			}
			/*
			 * 1	ID_variazione
			 * 2	ID_type
			 * 3	ID_tp_variazione >3
			 * 4	data_inizio > 4
			 * 5	data_fine > 5
			 * 6	ID_rif
			 * 7	valore
			 * 8	ID_tp_variazione_categoria > 8
			 * 9 	id_fascicolo
			 * 10	pos
			 * 11	rivalutazione_eta
			 */

			sql = new StringBuffer();
			sql.append("SELECT ID_variazione ");
			sql.append("	,ID_type ");
			sql.append("	,ID_tp_variazione ");
			sql.append("	,data_inizio ");
			sql.append("	,data_fine ");
			sql.append("	,ID_rif ");
			sql.append("	,valore ");
			sql.append("	,ID_tp_variazione_categoria ");
			sql.append("	,id_fascicolo ");
			sql.append("	,pos ");
			sql.append("	,rivalutazione_eta ");
			sql.append(" FROM ( ");
			sql.append("	SELECT ID_variazione ");
			sql.append("		,ID_type ");
			sql.append("		,ID_tp_variazione ");
			sql.append("		,data_inizio ");
			sql.append("		,data_fine ");
			sql.append("		,ID_rif ");
			sql.append("		,valore ");
			sql.append("		,ID_tp_variazione_categoria ");
			sql.append("		,id_fascicolo ");
			sql.append("		,rivalutazione_eta ");
			sql.append("		,ROW_NUMBER() OVER ( ");
			sql.append("			ORDER BY data_inizio ");
			sql.append("			) AS pos ");
			sql.append("	FROM ( ");
			sql.append("		SELECT TOP 10000 ID_variazione ");
			sql.append("			,ID_type ");
			sql.append("			,ID_tp_variazione ");
			sql.append("			,data_inizio ");
			sql.append("			,data_fine ");
			sql.append("			,ID_rif ");
			sql.append("			,valore ");
			sql.append("			,ID_tp_variazione_categoria ");
			sql.append("			,id_fascicolo ");
			sql.append("			,rivalutazione_eta ");
			sql.append("		FROM ( ");
			sql.append("			SELECT ID_variazione ");
			sql.append("				,'D' AS ID_type ");
			sql.append("				,ac_variazioni.ID_tp_variazione ");
			sql.append("				,CASE WHEN rivalutazione_eta = 1 and ac_variazioni.ID_tp_variazione in (9, 20) then data_inizio else CASE WHEN rivalutazione_eta = 0 ");
			sql.append("				THEN data_inizio ");
			sql.append("				ELSE ");
			sql.append("					CASE WHEN DATEADD(year, 18, domande.data_nascita) > '2010/01/01' "); 
			sql.append("					AND DATEADD(year, 18, domande.data_nascita) < '2032/12/31' ");
			sql.append("					THEN DATEADD(year, 18, domande.data_nascita) ");
			sql.append("					ELSE ");
			sql.append("						CASE WHEN DATEADD(year, 65, domande.data_nascita) > '2010/01/01' ");
			sql.append("						AND DATEADD(year, 65, domande.data_nascita) < '2032/12/31' ");
			sql.append("						THEN DATEADD(year, 65, domande.data_nascita) ");
			sql.append("						ELSE domande.data_nascita ");
			sql.append("						END ");
			sql.append("					END ");
			sql.append("					END ");
			sql.append("				END AS data_inizio ");
			sql.append("				,data_fine ");
			sql.append("				,ac_fascicoli.id_fascicolo AS ID_rif ");
			sql.append("				,valore ");
			sql.append("				,AC_tp_variazioni.ID_tp_variazione_categoria ");
			sql.append("				,id_fascicolo ");
			sql.append("				,rivalutazione_eta ");
			sql.append("			FROM ac_variazioni ");
			sql.append("			INNER JOIN AC_tp_variazioni ON AC_tp_variazioni.ID_tp_variazione = AC_variazioni.ID_tp_variazione ");
			sql.append("			INNER JOIN ac_fascicoli ON ac_variazioni.id_domanda = ac_fascicoli.id_fascicolo ");
			sql.append("			INNER JOIN domande ON domande.id_domanda = ac_fascicoli.id_fascicolo ");			
			sql.append("			WHERE ac_fascicoli.id_domanda =  ");
			sql.append(IDdomanda);
			sql.append("				AND ID_tp_variazione_categoria <> 5 ");
			sql.append("				AND ac_variazioni.id_tp_variazione <> 4 ");
			sql.append("				AND ac_variazioni.id_tp_variazione <> 14 ");
			sql.append("				AND ac_variazioni.id_tp_variazione <> 15 ");
			sql.append("				AND ac_variazioni.id_tp_variazione <> 17 ");
			sql.append("				AND ID_tp_variazione_categoria <> 4 ");
			sql.append("			UNION ");
			sql.append("			SELECT ID_variazione ");
			sql.append("				,'S' AS ID_type ");
			sql.append("				,AC_variazioni_soggetti.ID_tp_variazione ");
			sql.append("				,data_inizio ");
			sql.append("				,data_fine ");
			sql.append("				,ac_fascicoli.id_soggetto AS ID_rif ");
			sql.append("				,valore ");
			sql.append("				,AC_tp_variazioni.ID_tp_variazione_categoria ");
			sql.append("				,id_fascicolo ");
			sql.append("				, 0 ");
			sql.append("			FROM AC_variazioni_soggetti ");
			sql.append("			INNER JOIN AC_tp_variazioni ON AC_tp_variazioni.ID_tp_variazione = AC_variazioni_soggetti.ID_tp_variazione ");
			sql.append("			INNER JOIN ac_fascicoli ON AC_variazioni_soggetti.id_soggetto = ac_fascicoli.id_soggetto ");
			sql.append("			WHERE ac_fascicoli.id_domanda =  ");
			sql.append(IDdomanda);
			sql.append("				AND ID_tp_variazione_categoria <> 5 ");
			sql.append("			) t1order ");
			sql.append("		ORDER BY data_inizio ASC ");
			sql.append("		) t1 ");
			sql.append("	UNION ");
			sql.append("	SELECT * ");
			sql.append("	FROM ( ");
			sql.append("		SELECT TOP 10000 ID_variazione ");
			sql.append("			,'D' AS ID_type ");
			sql.append("			,ac_variazioni.ID_tp_variazione ");
			sql.append("			,data_inizio ");
			sql.append("			,data_fine ");
			sql.append("			,ac_fascicoli.id_fascicolo AS ID_rif ");
			sql.append("			,valore ");
			sql.append("			,AC_tp_variazioni.ID_tp_variazione_categoria ");
			sql.append("			,id_fascicolo ");
			sql.append("			,ROW_NUMBER() OVER ( ");
			sql.append("				ORDER BY data_inizio ");
			sql.append("				) AS pos ");
			sql.append("			,rivalutazione_eta ");
			sql.append("		FROM ac_variazioni ");
			sql.append("		INNER JOIN AC_tp_variazioni ON AC_tp_variazioni.ID_tp_variazione = AC_variazioni.ID_tp_variazione ");
			sql.append("		INNER JOIN ac_fascicoli ON ac_variazioni.id_domanda = ac_fascicoli.id_fascicolo ");
			sql.append("		WHERE ac_fascicoli.id_domanda =  ");
			sql.append(IDdomanda);
			sql.append("			AND ID_tp_variazione_categoria <> 5 ");
			sql.append("			AND ID_tp_variazione_categoria = 4 ");
			sql.append("		ORDER BY ID_variazione ");
			sql.append("		) t2 ");
			sql.append("	) tall ");
			sql.append(" ORDER BY pos ASC");
			if(trace!=0){
				System.out.println(sql.toString());
			}
			doQuery(sql.toString());
			iAcVariazioni= records;
			if(trace>1){
				System.out.println("NUMERO RECORD:"+iAcVariazioni.getRows());
			}
			if(iAcVariazioni.getRows()>0){
				doQuery("SELECT Doc.data_presentazione  from doc where id="+iAcVariazioni.getInteger(1, 9));
			}else{
				doQuery("SELECT Doc.data_presentazione  from doc where id=0");
			}

			iAcFascicolo = records;

			/*Recupero dati del C5 delle dichiarazioni connesse
			 * 01. R_Relazioni_parentela.peso_reddito,
			 * 02. Redditi_altri.importo,
			 * 03. Redditi_altri.ID_tp_erogazione
			 */
			sql = new StringBuffer();
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_altri.importo, Redditi_altri.ID_tp_erogazione ");
			sql.append(" FROM Familiari ");
			sql.append(" INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append(" INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(" INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append(" WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			datiC5 = records;

			sql = new StringBuffer();
			sql.append("select ac_fascicoli.id_domanda,ac_fascicoli.id_fascicolo,c_elaout.numeric_value,'' + c_elaout.numeric_value as strval from ac_fascicoli ");
			sql.append("inner join c_elaout on c_elaout.id_domanda=ac_fascicoli.id_fascicolo ");
			sql.append("where ac_fascicoli.id_domanda="+IDdomanda+" and node='check1'");
			doQuery(sql.toString());
			iAcFascicoloParent= records;

			sql = new StringBuffer();
			sql.append("select ac_fascicoli.id_domanda,ac_fascicoli.id_fascicolo,c_elaout.numeric_value,'' + c_elaout.numeric_value as strval from ac_fascicoli ");
			sql.append("inner join c_elaout on c_elaout.id_domanda=ac_fascicoli.id_fascicolo ");
			sql.append("where ac_fascicoli.id_domanda="+IDdomanda+" and node='check1'");
			doQuery(sql.toString());
			iAcFascicoloParent= records;



			sql = new StringBuffer();
			sql.append("SELECT ac_fascicoli.id_domanda, inizio.input_value, fine.input_value ");
			sql.append("			FROM ac_fascicoli ");
			sql.append("			INNER JOIN c_elain inizio on inizio.id_domanda = ac_fascicoli.id_domanda ");
			sql.append("			INNER JOIN c_elain fine on fine.id_domanda = ac_fascicoli.id_domanda ");
			sql.append("			INNER JOIN c_elaout on c_elaout.id_domanda = AC_fascicoli.id_domanda ");
			sql.append("			WHERE ac_fascicoli.id_soggetto = ");
			sql.append("			( SELECT id_soggetto FROM domande WHERE id_domanda = ");
			sql.append(IDdomanda);
			sql.append("			) ");
			sql.append("			AND ac_fascicoli.id_fascicolo NOT IN (select distinct id_fascicolo from ac_fascicoli where id_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
			sql.append("			AND ac_fascicoli.id_fascicolo < (select distinct id_fascicolo from ac_fascicoli where id_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
			sql.append("			AND inizio.node = 'M_inizio' ");
			sql.append("			AND fine.node = 'M_fine' ");
			sql.append("			AND c_elaout.node = 'check1' ");
			sql.append("			AND (ac_fascicoli.id_domanda_uscita IS NULL OR ac_fascicoli.id_domanda <= ac_fascicoli.id_domanda_uscita) ");
			sql.append("			AND c_elaout.numeric_value = 0  ");
			sql.append(" ORDER BY ac_fascicoli.id_domanda");
			doQuery(sql.toString());
			iV90Giorni= records;


			records=iAcDati;
			session.setRecords( records);
			session.setAttribute( "iV90Giorni",			iV90Giorni );
			session.setAttribute( "iAcFascicoloParent",	iAcFascicoloParent );
			session.setAttribute( "iAcRiaccertamento",	iAcRiaccertamento );

			session.setAttribute( "datiC5",				datiC5 );
			session.setAttribute( "iAcDati",			iAcDati );
			session.setAttribute( "iAcVariazioni",		iAcVariazioni);
			session.setAttribute( "iAcConfigurazioni",	iAcConfigurazioni);
			session.setAttribute( "iAcFascicolo",		iAcFascicolo);

			session.setAttribute( "idServizio",			idServizio);
			session.setAttribute( "idPeriodo",			idPeriodo);

			try{
				if (getIdServizio() < ID_SERVIZIO_61000) {
					vResultm=initNumeroGiorniPresenzaMese();
					session.setAttribute( "vResultm",		vResultm);
				}

			}catch(Exception e){
				e.printStackTrace();
			}
			session.setInitialized( true );

			if(trace>0){
				try{
					System.out.println();
					System.out.println("############################################");
					System.out.println("Id servizo:"+iAcDati.getInteger(1, 12));
					System.out.println("Id periodo:"+iAcDati.getInteger(1, 13));
					System.out.println("Numero step configurazione:"+iAcConfigurazioni.getRows());
					System.out.println("Data inizio periodo:"+calendarToString(getInizioPeriodo()));
					System.out.println("Data fine periodo:"+calendarToString(getFinePeriodo()));
					System.out.println("Data inizio vettore:"+calendarToString(getInizioVettore()));
					System.out.println("Data presentazione :"+calendarToString(iAcDati.getCalendar(1, 1)));
					System.out.println("############################################");
					System.out.println();
				} catch(Exception e) {

				}

			}


			//System.out.println("5 ########################################## :"+System.currentTimeMillis());

		} else {
			records				= session.getRecords();
			iAcDati				= (Table)session.getAttribute( "iAcDati" );
			iAcRiaccertamento	= (Table)session.getAttribute( "iAcRiaccertamento" );
			iAcVariazioni		= (Table)session.getAttribute("iAcVariazioni");
			iAcConfigurazioni	= (Table)session.getAttribute("iAcConfigurazioni");
			iAcFascicolo		= (Table)session.getAttribute("iAcFascicolo");
			iAcFascicoloParent	= (Table)session.getAttribute("iAcFascicoloParent");
			datiC5				= (Table)session.getAttribute("datiC5");
			iV90Giorni			= (Table)session.getAttribute("iV90Giorni");

			try {
				vResultm			= (Hashtable)session.getAttribute("vResultm");
			} catch (Exception e) {
				vResultm = null;
			}
			idServizio			= ((Integer)session.getAttribute("idServizio")).intValue();
			idPeriodo			= ((Integer)session.getAttribute("idPeriodo")).intValue();
		}

	}

	/**
	 *
	 * @return sempre true
	 */
	public boolean icefFascicoloValida(){
		//Il controllo è stato disabilitato per permettere la gestione dal monitor esterno
		/*if(iAcFascicoloParent.getRows()==0) {
			return true;
		}
		int idf=iAcFascicoloParent.getInteger(1, 1);
		int idd=iAcFascicoloParent.getInteger(1, 2);
		double value=iAcFascicoloParent.getDouble(1, 3);

		BigDecimal bi=new BigDecimal(value);
		String valuestr=bi.toPlainString();
		// SI TRATTA DI UN FASCICOLO
		if(idf==idd){
			return true;
		}

		if(valuestr.indexOf("4")>-1){
			return false;
		}*/
		return true;
	}

	public String calendarToString(Calendar cal){
		if(cal==null){
			return "-";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = formatter.format(cal.getTime());
		return s;
	}

	public double getProgressivoElab(){
		try{
			return iAcDati.getInteger(1, 10);
		}catch(Exception e){
			return 0;
		}
	}
	//è gestito solo una doppia elaborazione per periodo se fossero di + dobbiamo aggiungere + elaba_prec o trovare un modo alternativo
	public double getICEF_prec(){
		try{
			if(getProgressivoElab()==0){
				return 0;
			}
			return iAcDati.getDouble(1, 11);
		}catch(Exception e){
			return 0;
		}
	}

	public int getRecordPosition(){
		for(int i=1;i<=iAcConfigurazioni.getRows();i++){
			if(iAcConfigurazioni.getInteger(i, 16)==iAcDati.getInteger(1, 10)){
				return i;
			}
		}
		return 1;
	}

	public double getSup(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 5);
		}else{
			return -1;
		}
	}
	public double getMax1(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 6);
		}else{
			return 0;
		}
	}
	public double getMax2(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 7);
		}else{
			return 0;
		}


	}
	public double getMax3(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 8);
		}else{
			return 0;
		}

	}
	public double getMax4(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 9);
		}else{
			return 0;
		}

	}
	public double getMin1(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 10);
		}else{
			return 0;
		}
	}
	public double getMin2(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 11);
		}else{
			return 0;
		}
	}
	public double getMin3(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 12);
		}else{
			return 0;
		}
	}
	public double getMin4(){
		if(icefFascicoloValida()){
			return iAcConfigurazioni.getDouble(getRecordPosition(), 13);
		}else{
			return 0;
		}
	}
	public double getDetraz65(){
		return iAcConfigurazioni.getDouble(getRecordPosition(), 14);
	}
	public int getAnnoMinDaConfigurazione() throws Exception{
		return iAcConfigurazioni.getCalendar(getRecordPosition(), 15).get(Calendar.YEAR);
	}
	public int getMeseMinDaConfigurazione() throws Exception{
		return iAcConfigurazioni.getCalendar(getRecordPosition(), 15).get(Calendar.MONTH);
	}
	private Calendar normalizzaDate(Calendar date){
		date.set( Calendar.AM_PM, Calendar.AM );
		date.set(Calendar.HOUR,0);
		date.set(Calendar.MINUTE,0);
		date.set(Calendar.SECOND,0);
		date.set(Calendar.MILLISECOND,0);
		if(date.get(Calendar.YEAR)<kMinAnno){
			date.set(Calendar.YEAR, kMinAnno);
		}
		if(date.get(Calendar.YEAR)>kMAxAnno){
			date.set(Calendar.YEAR, kMAxAnno);
		}
		return date;
	}

	private Calendar getInizioVettore() throws Exception{
		Calendar inizioPeriodo=normalizzaDate(iAcConfigurazioni.getCalendar(1, 15));
		return inizioPeriodo;
	}
	private int getInizioPeriodoDaConfigInt() throws Exception{
		Calendar inizioPeriodo=getInizioPeriodo();
		int ret= (inizioPeriodo.get(Calendar.YEAR)*100)+inizioPeriodo.get(Calendar.MONTH)+1; //201201 (concatenazione mese + anno)
		return ret;
	}

	private Calendar getInizioPeriodo() throws Exception{
		Calendar inizioPeriodo=normalizzaDate(iAcConfigurazioni.getCalendar(1, 3));
		return inizioPeriodo;
	}

	/**
	 * Torna il valore di AC_configurazioni.Franchigia_ast.<BR>
	 * @return
	 */
	public double getFranchigia_ast(){
		if(icefFascicoloValida()) {
			if (iAcConfigurazioni != null && iAcConfigurazioni.getRows() > 0){
				return iAcConfigurazioni.getDouble(getRecordPosition(), 18);
			} else {
				return DOUBLE_25000;
			}
		}else{
			return 0;
		}
	}

	private Calendar getFinePeriodo() throws Exception{
		Calendar finePeriodo=normalizzaDate(iAcConfigurazioni.getCalendar(iAcConfigurazioni.getRows(), 4));

		if(iAcDati.getInteger(1, 12)== 30500){
			int anno=iAcDati.getCalendar(1, 1).get(Calendar.YEAR);
			finePeriodo.set(Calendar.YEAR, anno+1);
			finePeriodo.set(Calendar.MONTH, 11);
			finePeriodo.set(Calendar.DAY_OF_MONTH, 31);
		}
		return finePeriodo;
	}


	public LinkedHashMap<Integer,Vector<Integer>> getMatriceProfili() throws Exception{
		LinkedHashMap<Integer,Vector<Integer>> vYears=new LinkedHashMap<Integer,Vector<Integer>>();
		for(int i=kMinAnno;i<=kMAxAnno;i++){
			Calendar cp=Calendar.getInstance();
			cp.set(Calendar.YEAR, i);
			cp=normalizzaDate(cp);
			Vector<Integer> vMonths=new Vector<Integer>();
			for(int x=0;x<12;x++){
				cp.set(Calendar.DAY_OF_MONTH, 1);
				cp.set(Calendar.MONTH, i);

				vMonths.add(0);
			}
			vYears.put(i, vMonths);
		}
		return vYears;
	}

	public void valorizzaMatriceProfilo(LinkedHashMap<Integer,Vector<Integer>> vp,Calendar daData,int valoreProfilo) throws Exception{
		Calendar inizioPeriodo=getInizioPeriodo();
		Calendar finePeriodo=getFinePeriodo();
		daData=normalizzaDate(daData);
		if(trace>1){
			System.out.println("Profilo applicato da:"+calendarToString(daData));
		}

		while(daData.getTimeInMillis()<=finePeriodo.getTimeInMillis()){
			Vector<Integer> vm=	vp.get(daData.get(Calendar.YEAR));

			if(daData.getTimeInMillis()>=inizioPeriodo.getTimeInMillis()){
				vm.set(daData.get(Calendar.MONTH), valoreProfilo);
			}
			daData.add(Calendar.MONTH, 1);
		}
	}

	public int getAnnoRiferimentoDaDataPresentazione(Calendar c) throws Exception{
		int anno=c.get(Calendar.YEAR);
		int mese=c.get(Calendar.MONTH);
		if(anno==2013 && mese==0){
			anno=anno-1;
		}
		return anno;
	}

	public double getAnnoRiferimentoDaDataPresentazione() throws Exception {
		return getInizioVettore().get(Calendar.YEAR);
	}

	public int getNumeroProfili(){
		int countProfili=0;
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
			if(ID_tp_variazione==100){
				countProfili++;
			}
		}
		return countProfili;
	}

	public LinkedHashMap<Integer,Vector<Integer>> riempiMatriceProfili() throws  Exception{
		LinkedHashMap<Integer,Vector<Integer>>	vYears			= getMatriceProfili();
		int										countProfili	= 0;
		
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int		rivalutazione_eta	= iAcVariazioni.getInteger(i, 11);
			int		ID_tp_variazione	= iAcVariazioni.getInteger(i, 3);
			double	valoreProfilo		= -1;
			try {
				valoreProfilo=iAcVariazioni.getDouble(i, 7);
			} catch(Exception e) {}

			if(ID_tp_variazione==100 && valoreProfilo>-1){
				countProfili++;
				Calendar data_inizio		= normalizzaDate(iAcVariazioni.getCalendar(i, 4));
				data_inizio.set(Calendar.DAY_OF_MONTH, 1);
				if (rivalutazione_eta == 0) {
					data_inizio.add(Calendar.MONTH, 1);
				} else {
					/*in caso di rivalutazione per età la data inizio è stata posta al compleanno 18 o 65  il profilo ha effetto pieno solo dal 25 mese */	
					 data_inizio.add(Calendar.MONTH, 25);
				}

				Calendar data_presentazione	= normalizzaDate(iAcDati.getCalendar(1, 1));
				data_presentazione.set(Calendar.DAY_OF_MONTH, 1);
				
				if(countProfili == 1){
					//valorizzo dalla data di presentazione della domanda o retroattivamente
					if(getAnnoRiferimentoDaDataPresentazione(iAcDati.getCalendar(1, 1))==2012){
						//01/09/2012 inizio retroattivo
						data_presentazione.set(Calendar.YEAR, 2012);
						data_presentazione.set(Calendar.MONTH, 8);
						valorizzaMatriceProfilo(vYears,data_presentazione,(int) valoreProfilo);
					}else{

						if(data_presentazione.getTimeInMillis()<getInizioPeriodo().getTimeInMillis()){
							data_presentazione=getInizioPeriodo();
						}

						valorizzaMatriceProfilo(vYears,data_presentazione,(int) valoreProfilo);
					}
				}else{
					//valorizzo dalla data del profilo
					valorizzaMatriceProfilo(vYears,data_inizio,(int) valoreProfilo);
				}
			}
		}
		return vYears;
	}

	private String convertiProfiliInStringa() throws Exception{
		String ret="";
		LinkedHashMap<Integer, Vector<Integer>> result=riempiMatriceProfili();
		Iterator<Integer> iy=result.keySet().iterator();
		Calendar inizioVettore=getInizioVettore();
		Calendar finePeriodo=getFinePeriodo();

		Calendar data=Calendar.getInstance();
		data.set(Calendar.DAY_OF_MONTH, 1);
		data=normalizzaDate(data);
		int countPositivi=0;
		while(iy.hasNext()){
			int y=iy.next();
			Vector<Integer> vm=result.get(y);
			for(int i=0;i<vm.size();i++){
				data.set(Calendar.YEAR, y);
				data.set(Calendar.DAY_OF_MONTH, 1);
				data.set(Calendar.MONTH, i);
				if(data.getTimeInMillis()>=inizioVettore.getTimeInMillis() && data.getTimeInMillis()<=finePeriodo.getTimeInMillis()){
					ret=ret+vm.get(i);
					if(vm.get(i)>0){
						countPositivi++;
					}
				}
			}
		}
		if(countPositivi==0){
			ret="";
		}
		if(trace>1){
			System.out.println("Matrice profili data inizio periodo:"+calendarToString(inizioVettore)+" fine periodo:"+calendarToString(finePeriodo));
			System.out.println("Matrice profili valore:"+ret);
		}
		return ret;
	}

	public double getV_1_6() throws Exception{
		String profili=convertiProfiliInStringa();
		if(profili.length()==0){
			return 0;
		}else{
			String p16="1";
			for(int i=0;i<6;i++){
				if(profili.length()>i){
					p16=p16+profili.charAt(i);
				}else{
					p16=p16+"0";
				}
			}
			if(new Double(p16.substring(1,p16.length()))==0){
				return 0;
			}
			return new Double(p16);
		}
	}

	public double getV_7_18() throws Exception{
		String profili=convertiProfiliInStringa();
		if(profili.length()==0){
			return 0;
		}else{
			String p718="1";
			for(int i=6;i<18;i++){
				if(profili.length()>i){
					p718=p718+profili.charAt(i);
				}else{
					p718=p718+"0";
				}
			}
			if(new Double(p718.substring(1,p718.length()))==0){
				return 0;
			}
			return new Double(p718);
		}
	}


	public double getV_19_30() throws Exception{
		String profili=convertiProfiliInStringa();
		if(profili.length()==0){
			return 0;
		}else{
			String p1930="1";
			for(int i=18;i<30;i++){
				if(profili.length()>i){
					p1930= p1930+profili.charAt(i);
				}else{
					p1930= p1930+"0";
				}
			}
			if(new Double( p1930.substring(1, p1930.length()))==0){
				return 0;
			}
			return new Double( p1930);
		}
	}


	public double getUltimoProfilo() throws Exception{
		double ret=0;
		double val=getV_19_30();
		if(val!=0){
			String sval=new BigDecimal(val).toPlainString();
			sval=sval.replace(".", "");
			sval=sval.replace(",", "");
			for(int i=sval.length()-1;i>=0;i--){
				if(sval.charAt(i)!='0'){
					return new Double(""+sval.charAt(i));
				}
			}
		}

		val=getV_7_18();
		if(val!=0){
			String sval=new BigDecimal(val).toPlainString();
			sval=sval.replace(".", "");
			sval=sval.replace(",", "");
			for(int i=sval.length()-1;i>=0;i--){
				if(sval.charAt(i)!='0'){
					return new Double(""+sval.charAt(i));
				}
			}
		}

		val=getV_1_6();
		if(val!=0){
			String sval=new BigDecimal(val).toPlainString();
			sval=sval.replace(".", "");
			sval=sval.replace(",", "");
			for(int i=sval.length()-1;i>=0;i--){
				if(sval.charAt(i)!='0'){
					return new Double(""+sval.charAt(i));
				}
			}
		}

		return ret;
	}


	/*
	 * prepara una matrice dinamica per inserire tutte le varizioni caricate prevalorizza a presente dalal data di inizio periodo a quella di fine
	 */
	public LinkedHashMap<Integer,Vector<Vector<Integer>>> getMatricePresenze() throws Exception{
		Calendar inizioPeriodo=getInizioPeriodo();
		Calendar finePeriodo=getFinePeriodo();

		LinkedHashMap<Integer,Vector<Vector<Integer>>> vYears=new LinkedHashMap<Integer,Vector<Vector<Integer>>>();
		for(int i=kMinAnno;i<=kMAxAnno;i++){
			Calendar cp=Calendar.getInstance();
			cp.set(Calendar.YEAR, i);
			cp=normalizzaDate(cp);
			Vector<Vector<Integer>> vMonths=new Vector<Vector<Integer>>();
			for(int x=0;x<12;x++){
				cp.set(Calendar.DAY_OF_MONTH, 1);
				cp.set(Calendar.MONTH, x);

				int days=cp.getActualMaximum(Calendar.DAY_OF_MONTH);
				Vector<Integer> vDays=new Vector<Integer>();
				for(int y=0;y<days;y++){
					cp.set(Calendar.DAY_OF_MONTH, y+1);
					if(cp.getTimeInMillis()<=finePeriodo.getTimeInMillis() && cp.getTimeInMillis()>=inizioPeriodo.getTimeInMillis()){
						vDays.add(0);
					}else{
						vDays.add(0);
					}
				}
				vMonths.add(vDays);
			}
			vYears.put(i, vMonths);
		}
		return vYears;
	}


	private void valorizzaMatriceGiorni(LinkedHashMap<Integer,Vector<Vector<Integer>>> matrice,Calendar dataInizio,Calendar dataFine,int valore,int ID_tp_variazione) throws Exception{
		Calendar finePeriodo=getFinePeriodo();
		Calendar corrente=Calendar.getInstance();
		corrente=normalizzaDate(corrente);

		if(trace>0){
			if(dataFine==null){
				System.out.println(IDdomanda+" ("+ID_tp_variazione+") "+getVariazioneDesc(ID_tp_variazione)+" "+calendarToString(dataInizio)+" nessuna fine >> "+calendarToString(finePeriodo));
			}else{
				System.out.println(IDdomanda+" ("+ID_tp_variazione+") "+getVariazioneDesc(ID_tp_variazione)+" "+calendarToString(dataInizio)+" "+calendarToString(dataFine));
			}
		}

		if(dataFine==null){
			dataFine=finePeriodo;
		}
		dataInizio=normalizzaDate(dataInizio);
		dataFine=normalizzaDate(dataFine);
		Iterator<Integer> iy=matrice.keySet().iterator();
		while(iy.hasNext()){
			int y=iy.next();
			corrente.set(Calendar.YEAR, y);
			Vector<Vector<Integer>> vm=matrice.get(y);
			for(int i=0;i<vm.size();i++){
				int m=i;
				corrente.set(Calendar.DAY_OF_MONTH, 1);
				corrente.set(Calendar.MONTH, m);
				Vector<Integer> vg=vm.get(i);
				for(int x=0;x<vg.size();x++){
					int d=x+1;
					corrente.set(Calendar.DAY_OF_MONTH, d);
					if(corrente.getTimeInMillis()<dataInizio.getTimeInMillis()){
						continue;
					}else{
						//System.out.println(calendarToString(corrente)+" "+corrente.getTimeInMillis()+" "+calendarToString(dataFine)+" "+dataFine.getTimeInMillis());
						if(corrente.getTimeInMillis()<=dataFine.getTimeInMillis()){
							vg.set(x, valore);
						}
					}
				}
			}
		}
		traceMatrix(matrice);
	}


	public double getNumeroGiorniPresenzaMese(int pos) throws Exception{
		return (Double) vResultm.get(pos);
	}


	//	public double getNumeroGiorniPresenzaMeseOld(int pos) throws Exception{
	//
	//
	//
	//
	//		int annoPresentazione=getAnnoRiferimentoDaDataPresentazione(iAcDati.getCalendar(1, 1));
	//		LinkedHashMap<Integer,Vector<Vector<Integer>>> vYears = getMatricePresenze();
	//
	//		Calendar data_inizio_presentazione=iAcDati.getCalendar(1, 1);
	//		data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
	//		data_inizio_presentazione.set(Calendar.MONTH, data_inizio_presentazione.get(Calendar.MONTH)+1);
	//
	//
	//		if(annoPresentazione==2012){
	//			data_inizio_presentazione.set(Calendar.YEAR, 2012);
	//			data_inizio_presentazione.set(Calendar.MONTH, 8);
	//			data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
	//			if(!isDataDecorrenzaPrecedenteUgualeInizio()){
	//				data_inizio_presentazione=iAcDati.getCalendar(1, 1);
	//				data_inizio_presentazione.set(Calendar.MONTH, 		data_inizio_presentazione.get(Calendar.MONTH)+1);
	//				data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
	//			}
	//		}else{
	//			StringBuffer return0=new StringBuffer();
	//			data_inizio_presentazione=ricalcolaDataInizioDaSospensioneRSA(data_inizio_presentazione,return0);
	//			if(return0.length()>0){
	//				return 0;
	//			}
	//		}
	//
	//		if(data_inizio_presentazione.getTimeInMillis()<getInizioPeriodo().getTimeInMillis()){
	//			data_inizio_presentazione=getInizioPeriodo();
	//		}
	//
	//		valorizzaMatriceGiorni(vYears,	data_inizio_presentazione,null,1,0);
	//
	//		long dataPrimoProfilo=0;
	//		long dataDecesso=0;
	//		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
	//			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
	//			if(ID_tp_variazione==100){
	//				Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
	//				dataPrimoProfilo=data_inizio.getTime().getTime();
	//				break;
	//			}
	//		}
	//
	//		int uscita4=0;
	//
	//		Calendar ultimaEntrataUtile=data_inizio_presentazione;
	//		Calendar ultimaUscitaUtile=null;
	//
	//		//gestiamo le entrate
	//		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
	//			int variazioneCorrente=iAcVariazioni.getInteger(i, 1);
	//			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
	//
	//			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
	//			//entrata da data
	//			if(ID_tp_variazione_categoria==1){
	//				Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
	//				//vale sempre dal primo giorno del mese dopo
	//				data_inizio.set(Calendar.DAY_OF_MONTH, 1);
	//				data_inizio.set(Calendar.MONTH, data_inizio.get(Calendar.MONTH)+1);
	//
	//				ultimaEntrataUtile=data_inizio;
	//				vYears = getMatricePresenze();
	//				valorizzaMatriceGiorni(vYears,data_inizio,null,1,ID_tp_variazione);
	//
	//			}
	//
	//		}
	//
	//		//gestiamo le uscite
	//		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
	//			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
	//			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
	//			//uscita da data
	//			if(ID_tp_variazione_categoria==2){
	//				Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
	//				//vale sempre dal primo giorno del mese dopo
	//				data_inizio.set(Calendar.DAY_OF_MONTH, 1);
	//				data_inizio.set(Calendar.MONTH, data_inizio.get(Calendar.MONTH)+1);
	//				if(ultimaUscitaUtile==null){
	//					ultimaUscitaUtile=data_inizio;
	//				}else{
	//					if(ultimaUscitaUtile.getTimeInMillis()>data_inizio.getTimeInMillis()){
	//						ultimaUscitaUtile=data_inizio;
	//					}
	//				}
	//				if(trace>0){
	//					System.out.println("Data inserita uscita:"+iAcVariazioni.getCalendar(i, 4).getTime());
	//				}
	//				valorizzaMatriceGiorni(vYears,data_inizio,null,0,ID_tp_variazione);
	//
	//				if(ID_tp_variazione==4){
	//					dataDecesso=data_inizio.getTimeInMillis();
	//				}
	//			}
	//
	//		}
	//		//	System.out.println(ultimaUscitaUtile.getTime());
	//
	//		//gestiamo i reset
	//		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
	//
	//			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
	//			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
	//			if(ID_tp_variazione_categoria==4){
	//				vYears = getMatricePresenze();
	//				if(trace>0){
	//					System.out.println(IDdomanda+" ("+ID_tp_variazione+") "+getVariazioneDesc(ID_tp_variazione) +" >> reset della matrice");
	//				}
	//				traceMatrix(vYears);
	//
	//				uscita4++;
	//				break;
	//			}
	//		}
	//		if(uscita4==0){
	//			//gestiamo le sospensioni
	//			for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
	//
	//				int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
	//				int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
	//				if(ID_tp_variazione==16){
	//					continue;
	//				}
	//				if(ID_tp_variazione_categoria==3){
	//					Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
	//					Calendar data_fine=null;
	//					try{
	//						data_fine=iAcVariazioni.getCalendar(i, 5);
	//					}catch(Exception e){
	//
	//					}
	//					if(data_fine==null){
	//						data_fine=getFinePeriodo();
	//					}
	//
	//					if(data_inizio.getTimeInMillis()>getFinePeriodo().getTimeInMillis()){
	//						if(trace>0){
	//							System.out.println("Individuata sospensione con inizio "+calendarToString(data_inizio)+" seguente alla fine del periodo ("+calendarToString(getFinePeriodo())+")");
	//						}
	//						continue;
	//					}
	//
	//					if(ultimaEntrataUtile!=null && data_inizio.getTimeInMillis()<ultimaEntrataUtile.getTimeInMillis()){
	//
	//						if(iAcDati.getInteger(1, 12)>= 30600){
	//							if(trace>0){
	//								System.out.println("Individuata sospensione con entrata precedente alla prima utile periodo "+iAcDati.getInteger(1, 12)+" nessuna operazione da eseguire:"+calendarToString(data_inizio));
	//							}
	//
	//						}else{
	//							if(trace>0){
	//								System.out.println("Individuata sospensione con entrata precedente alla prima utile >> spostamento data da:"+calendarToString(data_inizio) +" a:"+calendarToString(ultimaEntrataUtile));
	//							}
	//							data_inizio=ultimaEntrataUtile;
	//
	//						}
	//					}
	//					if(ultimaUscitaUtile!=null && data_fine!=null && data_fine.getTimeInMillis()>ultimaUscitaUtile.getTimeInMillis()){
	//						if(trace>0){
	//							System.out.println("Individuata sospensione con uscita posteriore al limite utile >> spostamento data da:"+calendarToString(data_fine) +" a:"+calendarToString(ultimaUscitaUtile));
	//						}
	//
	//						data_fine=Calendar.getInstance();
	//						data_fine.setTime(ultimaUscitaUtile.getTime());
	//						data_fine.add(Calendar.DATE, -1);
	//
	//					}
	//					if( data_fine!=null && data_inizio!=null && data_fine.getTimeInMillis()>data_inizio.getTimeInMillis()){
	//						valorizzaMatriceGiorni(vYears,data_inizio,data_fine,2,ID_tp_variazione);
	//					}else{
	//						if(trace>0){
	//							System.out.println("Sospensione con data inizio ("+calendarToString(data_inizio)+") posteriore a data fine ("+calendarToString(data_fine)+")");
	//						}
	//					}
	//
	//				}
	//
	//			}
	//
	//			int progressiv2=0;
	//			Iterator<Integer> iy=vYears.keySet().iterator();
	//			while(iy.hasNext()){
	//				int y=iy.next();
	//				Vector<Vector<Integer>> vm=vYears.get(y);
	//				for(int i=0;i<vm.size();i++){
	//					int m=i;
	//					Vector<Integer> vg=vm.get(i);
	//					for(int x=0;x<vg.size();x++){
	//						int val=vg.get(x);
	//						if(val==2){
	//							progressiv2=progressiv2+1;
	//						}else{
	//							progressiv2=0;
	//						}
	//						if(progressiv2>30){
	//							vg.set(x, 3);
	//						}
	//					}
	//				}
	//			}
	//			iy=vYears.keySet().iterator();
	//			while(iy.hasNext()){
	//				int y=iy.next();
	//				Vector<Vector<Integer>> vm=vYears.get(y);
	//				for(int i=0;i<vm.size();i++){
	//					int m=i;
	//					Vector<Integer> vg=vm.get(i);
	//					for(int x=0;x<vg.size();x++){
	//						int val=vg.get(x);
	//						if(val==2){
	//							vg.set(x, 1);
	//						}
	//						if(val==3){
	//							vg.set(x, 0);
	//						}
	//					}
	//				}
	//			}
	//
	//			//gestiamo le sospensioni
	//			for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
	////				int variazioneCorrente=iAcVariazioni.getInteger(i, 1);
	//				int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
	////				int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
	//
	//				if(ID_tp_variazione==16){
	//					Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
	//
	//					String startDateString = "01/07/2013";
	//					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	//					Calendar calmin=Calendar.getInstance();
	//					calmin.setTime(df.parse(startDateString));
	//
	//					if(data_inizio.before(calmin)){
	//						data_inizio=calmin;
	//					}
	//
	//					Calendar data_fine=null;
	//					try{
	//						data_fine=iAcVariazioni.getCalendar(i, 5);
	//					}catch(Exception e){
	//
	//					}
	//					if(ultimaEntrataUtile!=null && data_inizio.getTimeInMillis()<ultimaEntrataUtile.getTimeInMillis()){
	//						data_inizio=ultimaEntrataUtile;
	//					}
	//					if(ultimaUscitaUtile!=null && data_fine!=null && data_fine.getTimeInMillis()>ultimaUscitaUtile.getTimeInMillis()){
	//						data_fine=ultimaUscitaUtile;
	//					}
	//					System.out.println(data_inizio.getTime());
	//					valorizzaMatriceGiorni(vYears,data_inizio,data_fine,0,ID_tp_variazione);
	//				}
	//
	//
	//			}
	//
	//		}
	//
	//		//valutare se la data del primo profilo è seguente al decesso in tale caso azzerare
	//		if(dataPrimoProfilo!=0 && dataDecesso!=0 && dataPrimoProfilo>dataDecesso){
	//			vYears = getMatricePresenze();
	//
	//			if(trace>0){
	//				System.out.println(IDdomanda+" >> data primo profilo posteriore decesso azzero");
	//			}
	//		}
	//		if(iAcVariazioni.getRows()==0 || getNumeroProfili()==0){
	//			//reset
	//			vYears = getMatricePresenze();
	//			if(trace>0){
	//				System.out.println(IDdomanda+" >> nessun profilo identificato azzero");
	//			}
	//		}
	//
	//		if(isMotivoEsclusioneEsternoPresente()){
	//			//reset
	//			vYears = getMatricePresenze();
	//			if(trace>0){
	//				System.out.println(IDdomanda+" >> domanda di riaccertamento non rilavata");
	//			}
	//		}
	//
	//
	//		if(iAcDati.getInteger(1, 12)>= 30600){
	//			Calendar tmp=getInizioVettore();
	//			tmp.add(Calendar.MONTH, pos);
	//			if(tmp.getTimeInMillis()<=getInizioPeriodo().getTimeInMillis()){
	//				vYears = getMatricePresenze();
	//				if(trace>0){
	//					System.out.println(IDdomanda+" >> richiesto mese antecedente inizio periodo ");
	//				}
	//			}
	//		}
	//
	//
	//
	//		Calendar inizioVettore=getInizioVettore();
	//		inizioVettore.add(Calendar.MONTH, (pos-1));
	//
	//		Vector<Vector<Integer>> vm=vYears.get(inizioVettore.get(Calendar.YEAR));
	//		Vector<Integer> vDays=vm.get(inizioVettore.get(Calendar.MONTH));
	//		double countDays=0;
	//		for(int i=0;i<vDays.size();i++){
	//			countDays=countDays+vDays.get(i);
	//		}
	//
	//		traceMatrix(vYears);
	//
	//		return countDays;
	//
	//	}




	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public Hashtable<Integer, Double> initNumeroGiorniPresenzaMese() throws Exception{


		vResultm				= new Hashtable<Integer, Double>();

		int annoPresentazione	= getAnnoRiferimentoDaDataPresentazione(iAcDati.getCalendar(1, 1));

		LinkedHashMap<Integer,Vector<Vector<Integer>>> vYears		= getMatricePresenze();

		Calendar data_inizio_presentazione=iAcDati.getCalendar(1, 1);
		data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
		data_inizio_presentazione.set(Calendar.MONTH, data_inizio_presentazione.get(Calendar.MONTH)+1);

		if(annoPresentazione==2012){
			data_inizio_presentazione.set(Calendar.YEAR, 2012);
			data_inizio_presentazione.set(Calendar.MONTH, 8);
			data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
			if(!isDataDecorrenzaPrecedenteUgualeInizio()){
				data_inizio_presentazione=iAcDati.getCalendar(1, 1);
				data_inizio_presentazione.set(Calendar.MONTH, 		data_inizio_presentazione.get(Calendar.MONTH)+1);
				data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
			}
			//DAL 2013
		}else{
			// SPOSTAMENTO DATA INIZIO PER SOSPENSIONI
			Calendar unoGennaio2018 = GregorianCalendar.getInstance();
			unoGennaio2018.set(2018, Calendar.JANUARY, 1); 
			Calendar seiLuglio2018 = GregorianCalendar.getInstance();
			seiLuglio2018.set(2018, Calendar.JULY, 6); 

			if (data_inizio_presentazione.before(unoGennaio2018)) {
				StringBuffer return0=new StringBuffer();

				data_inizio_presentazione=ricalcolaDataInizioDaSospensioneRSA(data_inizio_presentazione,return0);
				if(return0.length()>0){
					for(int i=1;i<=30;i++){
						double xxx=0;
						vResultm.put(i, xxx);
					}
					return vResultm;
				}
			} else if (!data_inizio_presentazione.before(seiLuglio2018)) {
				StringBuffer return0=new StringBuffer();
				data_inizio_presentazione=ricalcolaDataInizioDaSospensioneRSALp8_2018(data_inizio_presentazione,return0);
				if(return0.length()>0){
					for(int i=1;i<=30;i++){
						double xxx=0;
						vResultm.put(i, xxx);
					}
					return vResultm;
				}
			}
		}

		if(data_inizio_presentazione.getTimeInMillis()<getInizioPeriodo().getTimeInMillis()){
			data_inizio_presentazione=getInizioPeriodo();
		}

		valorizzaMatriceGiorni(vYears, data_inizio_presentazione, null, 1, 0);

		long dataPrimoProfilo	= 0;
		long dataDecesso		= 0;
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
			if(ID_tp_variazione==100){
				Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
				dataPrimoProfilo=data_inizio.getTime().getTime();
				break;
			}
		}

		int uscita4=0;

		Calendar ultimaEntrataUtile=data_inizio_presentazione;
		Calendar ultimaUscitaUtile=null;

		//GESTIONE ENTRATE
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			//			int variazioneCorrente=iAcVariazioni.getInteger(i, 1);
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);

			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
			//entrata da data
			if(ID_tp_variazione_categoria==1){
				Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
				//vale sempre dal primo giorno del mese dopo
				data_inizio.set(Calendar.DAY_OF_MONTH, 1);
				data_inizio.set(Calendar.MONTH, data_inizio.get(Calendar.MONTH)+1);

				if(iAcDati.getInteger(1, 12) > 30500 || iAcDati.getInteger(1, 13)>30500){
					if(data_inizio.getTimeInMillis()<data_inizio_presentazione.getTimeInMillis()){
						data_inizio=data_inizio_presentazione;
					}
				}
				ultimaEntrataUtile=data_inizio;
				vYears = getMatricePresenze();
				valorizzaMatriceGiorni(vYears,data_inizio,null,1,ID_tp_variazione);

			}

		}

		//GESTIONE USCITE
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
			//uscita da data
			if(ID_tp_variazione_categoria==2){
				Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
				//vale sempre dal primo giorno del mese dopo
				data_inizio.set(Calendar.DAY_OF_MONTH, 1);
				data_inizio.set(Calendar.MONTH, data_inizio.get(Calendar.MONTH)+1);
				if(ultimaUscitaUtile==null){
					ultimaUscitaUtile=data_inizio;
				}else{
					if(ultimaUscitaUtile.getTimeInMillis()>data_inizio.getTimeInMillis()){
						ultimaUscitaUtile=data_inizio;
					}
				}
				if(trace>0){
					System.out.println("Data inserita uscita:"+iAcVariazioni.getCalendar(i, 4).getTime());
				}
				valorizzaMatriceGiorni(vYears,data_inizio,null,0,ID_tp_variazione);

				if(ID_tp_variazione==4){
					dataDecesso=data_inizio.getTimeInMillis();
				}
			}

		}


		//gestiamo i reset
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {

			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
			if(ID_tp_variazione_categoria==4){
				vYears = getMatricePresenze();
				if(trace>0){
					System.out.println(IDdomanda+" ("+ID_tp_variazione+") "+getVariazioneDesc(ID_tp_variazione) +" >> reset della matrice");
				}
//				traceMatrix(vYears);

				uscita4++;
				break;
			}
		}


		if(uscita4==0){
			//GESTIONE SOSPENSIONI
			//RSA NON DEFINITIVO E OSPEDALE
			for (int i = 1; i <= iAcVariazioni.getRows(); i++) {

				int ID_tp_variazione			= iAcVariazioni.getInteger(i, 3);
				int ID_tp_variazione_categoria	= iAcVariazioni.getInteger(i, 8);
				//il congedo biennale ID_tp_variazione==16 viene considerato in seguito
				if(ID_tp_variazione == ID_VAR_CONGEDO || ID_tp_variazione == ID_VAR_RSA_DEF){
					continue;
				}
				
				//titologia periodo
				if(ID_tp_variazione_categoria == 3 //&& !isEsclusioneUfficio
						){
					Calendar data_inizio	= iAcVariazioni.getCalendar(i, 4);
					Calendar data_fine		= null;

					try{
						data_fine			= iAcVariazioni.getCalendar(i, 5);
					}catch(Exception e){

					}
					if(data_fine==null){
						data_fine			= getFinePeriodo();
					}

					if(data_inizio.getTimeInMillis()>getFinePeriodo().getTimeInMillis()){
						if(trace>0){
							System.out.println("Individuata sospensione con inizio "+calendarToString(data_inizio)+" seguente alla fine del periodo ("+calendarToString(getFinePeriodo())+")");
						}
						continue;
					}

					if(ultimaEntrataUtile!=null && data_inizio.getTimeInMillis()<ultimaEntrataUtile.getTimeInMillis()){

						if(iAcDati.getInteger(1, 12)>= 30600){
							if(trace>0){
								System.out.println("Individuata sospensione con entrata precedente alla prima utile periodo "+iAcDati.getInteger(1, 12)+" nessuna operazione da eseguire:"+calendarToString(data_inizio));
							}

						}else{
							if(trace>0){
								System.out.println("Individuata sospensione con entrata precedente alla prima utile >> spostamento data da:"+calendarToString(data_inizio) +" a:"+calendarToString(ultimaEntrataUtile));
							}
							data_inizio=ultimaEntrataUtile;

						}
					}
					if(ultimaUscitaUtile!=null && data_fine!=null && data_fine.getTimeInMillis()>=ultimaUscitaUtile.getTimeInMillis()){
						if(trace>0){
							System.out.println("Individuata sospensione con uscita posteriore al limite utile >> spostamento data da:"+calendarToString(data_fine) +" a:"+calendarToString(ultimaUscitaUtile));
						}
						//						ultimaUscitaUtile.add(Calendar.DATE, -1);
						//						data_fine=ultimaUscitaUtile;

						data_fine=Calendar.getInstance();
						data_fine.setTime(ultimaUscitaUtile.getTime());
						data_fine.add(Calendar.DATE, -1);
					}
					if( data_fine!=null && data_inizio!=null && data_fine.getTimeInMillis()>data_inizio.getTimeInMillis()){
						valorizzaMatriceGiorni(vYears,data_inizio,data_fine,2,ID_tp_variazione);
					}else{
						if(trace>0){
							System.out.println("Sospensione con data inizio ("+calendarToString(data_inizio)+") posteriore a data fine ("+calendarToString(data_fine)+")");
						}
					}

				}

			}

			//RSA DEFINITIVO 
			for (int i = 1; i <= iAcVariazioni.getRows(); i++) {

				int ID_tp_variazione			= iAcVariazioni.getInteger(i, 3);
				int ID_tp_variazione_categoria	= iAcVariazioni.getInteger(i, 8);
				//il congedo biennale ID_tp_variazione==16 viene considerato in seguito
				if(ID_tp_variazione == ID_VAR_CONGEDO 
						|| ID_tp_variazione == ID_VAR_RSA_NON_DEF
						|| ID_tp_variazione == ID_VAR_OSP){
					continue;
				}

				//titologia periodo
				if(ID_tp_variazione_categoria == 3 //&& !isEsclusioneUfficio
						){
					Calendar data_inizio	= iAcVariazioni.getCalendar(i, 4);
					Calendar data_fine		= null;

					try{
						data_fine			= iAcVariazioni.getCalendar(i, 5);
					}catch(Exception e){

					}
					if(data_fine==null){
						data_fine			= getFinePeriodo();
					}
					if(data_inizio.getTimeInMillis()>getFinePeriodo().getTimeInMillis()){
						if(trace>0){
							System.out.println("Individuata sospensione con inizio "+calendarToString(data_inizio)+" seguente alla fine del periodo ("+calendarToString(getFinePeriodo())+")");
						}
						continue;
					}

					if(ultimaEntrataUtile!=null && data_inizio.getTimeInMillis()<ultimaEntrataUtile.getTimeInMillis()){

						if(iAcDati.getInteger(1, 12)>= 30600){
							if(trace>0){
								System.out.println("Individuata sospensione con entrata precedente alla prima utile periodo "+iAcDati.getInteger(1, 12)+" nessuna operazione da eseguire:"+calendarToString(data_inizio));
							}

						}else{
							if(trace>0){
								System.out.println("Individuata sospensione con entrata precedente alla prima utile >> spostamento data da:"+calendarToString(data_inizio) +" a:"+calendarToString(ultimaEntrataUtile));
							}
							data_inizio=ultimaEntrataUtile;

						}
					}
					if(ultimaUscitaUtile!=null && data_fine!=null && data_fine.getTimeInMillis()>=ultimaUscitaUtile.getTimeInMillis()){
						if(trace>0){
							System.out.println("Individuata sospensione con uscita posteriore al limite utile >> spostamento data da:"+calendarToString(data_fine) +" a:"+calendarToString(ultimaUscitaUtile));
						}
						//						ultimaUscitaUtile.add(Calendar.DATE, -1);
						//						data_fine=ultimaUscitaUtile;

						data_fine=Calendar.getInstance();
						data_fine.setTime(ultimaUscitaUtile.getTime());
						data_fine.add(Calendar.DATE, -1);
					}
					if( data_fine!=null && data_inizio!=null && data_fine.getTimeInMillis()>data_inizio.getTimeInMillis()){
						valorizzaMatriceGiorni(vYears,data_inizio,data_fine,4,ID_tp_variazione);
					}else{
						if(trace>0){
							System.out.println("Sospensione con data inizio ("+calendarToString(data_inizio)+") posteriore a data fine ("+calendarToString(data_fine)+")");
						}
					}

				}

			}

			Calendar dataReset30Rip=null;
			//la parte di valutazione decesso è interna al ciclio principale
			LocalDate dataRip = null;
			if(getIdServizio() >=30603 || (getIdServizio()==30500 && getIdPeriodo()>=30504)){
				for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
					int ID_tp_variazione			= iAcVariazioni.getInteger(i, 3);
					int ID_tp_variazione_categoria	= iAcVariazioni.getInteger(i, 8);
					//uscita da data
					if(ID_tp_variazione_categoria==2 && ID_tp_variazione==4){
						dataRip = new LocalDate(iAcVariazioni.getCalendar(i, 4).get(Calendar.YEAR), iAcVariazioni.getCalendar(i, 4).get(Calendar.MONTH) + 1, iAcVariazioni.getCalendar(i, 4).get(Calendar.DAY_OF_MONTH));
						Calendar	data_inizio				= iAcVariazioni.getCalendar(i, 4);
						Calendar	tmp						= Calendar.getInstance();
						boolean		incasadiripososempre	= true;
						tmp.setTime(data_inizio.getTime());
						
						if (data_inizio.get(Calendar.YEAR) < 2018) {

							for(int x=0;x<=30;x++){
								tmp.add(Calendar.DAY_OF_MONTH, -1);
								//System.out.println(""+tmp.getTime());
								//rilevato decesso, valutiamo se era in casa di riposo da 30 giorni
								Vector<Vector<Integer>>	vm	= vYears.get(tmp.get(Calendar.YEAR));
								Vector<Integer>			gg	= vm.get(tmp.get(Calendar.MONTH));
								if(gg.get(tmp.get(Calendar.DAY_OF_MONTH)-1)==2
										|| gg.get(tmp.get(Calendar.DAY_OF_MONTH)-1)==4){

								}else{
									incasadiripososempre	= false;
								}
							}
						} 

						if(incasadiripososempre){
							dataReset30Rip=Calendar.getInstance();
							dataReset30Rip.setTime(tmp.getTime());
							System.out.println("rilevato decesso in casa di riposo");
						}
					}

				}
			}

			HashMap<Integer, Integer>	contatore90PrecedentiMap	= getContatore90Precedenti(vYears);

			int			contatore30					= 0;
			int			contatore30Definitivi		= 0;
			LocalDate	dataInizioContatore30Def	= new LocalDate(2018, 6, 6);
			LocalDate	data20171231				= new LocalDate(2017, 12, 31);
			boolean		sospesoDefinitivoAl20171231	= false;

			Iterator<Integer> iy=vYears.keySet().iterator();
			//CICLO SUGLI ANNI
			while(iy.hasNext()){
				int y			= iy.next();
				int contatore90	= 0;
				if (contatore90PrecedentiMap.containsKey(y)) {
					contatore90 = contatore90PrecedentiMap.get(y);
				} 

				//CICLO SUI MESI
				Vector<Vector<Integer>> vm=vYears.get(y);
				for(int i=0;i<vm.size();i++){
					//					int m = i;
					//CICLO SUI GIORNI
					Vector<Integer> vg=vm.get(i);
					for(int x=0;x<vg.size();x++){
						int val=vg.get(x);

						LocalDate dataGiornoAnalizzato = new LocalDate(y, i+1, x+1);

						if (y < 2018) {
							//il soggetto è in ricovero definitivo al 31/12/2017
							if (dataGiornoAnalizzato.equals(data20171231) && val == 4) {
								sospesoDefinitivoAl20171231 = true;
							}

							if (val == 2 || val == 4) {
								contatore30=contatore30+1;
							} else {
								contatore30=0;
							}
							if (contatore30 > 30) {
								vg.set(x, 3);
							}

							//LP 8 / 2018
						} else {
							
							// gestione contatore 90 giorni
							if (val == 2 || val == 4) {
								contatore90++;
								if (sospesoDefinitivoAl20171231) {
									contatore30=contatore30+1;
								} else {
									contatore30 = 0;
								}
							} else {
								contatore30 = 0;
							}

							//interrotta la sospensione definitiva in essere al 31/12/2017
							if (val != 4) {
								sospesoDefinitivoAl20171231 = false;
							} 

							// gestione contatore 30 giorni
							if (!dataGiornoAnalizzato.isBefore(dataInizioContatore30Def)) {
								if (val == 4) {
									contatore30Definitivi = contatore30Definitivi + 1;
								} else {
									contatore30Definitivi = 0;
								}
							}

							//sospeso da contributo sia se il contatore ha superato i 90 giorni sia superato il contatore dei 30 giorni (sia lp8 che precedente)
							if ( contatore30 > 30 
									|| contatore30Definitivi > 30 
									|| (contatore90 > 90 && (val == 2 || val == 4) ) ) {
								vg.set(x, 3);
							}

							if (dataRip != null && dataGiornoAnalizzato.equals(dataRip) ) {
								if ( contatore30 > 30 
										|| contatore30Definitivi > 30 
										|| contatore90 > 90  ) {
									dataReset30Rip.set(y, i, x+1, 0, 0, 0);
									dataReset30Rip.add(Calendar.DAY_OF_MONTH, -1);
								}
							}
						}
					}
				}

			}
			traceMatrix(vYears);
			if(dataReset30Rip!=null){
				//resettiama la matrice una volta trovato il primo 3 dopo la data dataReset30Rip

				iy=vYears.keySet().iterator();
				boolean resetSucc=false;
				Calendar tmp=Calendar.getInstance();
				while(iy.hasNext()){
					int y=iy.next();
					Vector<Vector<Integer>> vm=vYears.get(y);
					for(int i=0;i<vm.size();i++){
						int m=i;
						Vector<Integer> vg=vm.get(i);
						for(int x=0;x<vg.size();x++){
							int val=vg.get(x);
							tmp.set(Calendar.YEAR, y);
							tmp.set(Calendar.MONTH, m);
							tmp.set(Calendar.DAY_OF_MONTH, x+1);
							if(tmp.after(dataReset30Rip)){
								if(val==3){
									vg.set(x, 0);
									resetSucc=true;
								}
								if(resetSucc){
									vg.set(x, 0);
								}
							}
						}
					}
				}
			}

			traceMatrix(vYears);
			iy=vYears.keySet().iterator();
			while(iy.hasNext()){
				int y=iy.next();
				Vector<Vector<Integer>> vm=vYears.get(y);
				for(int i=0;i<vm.size();i++){
					int m=i;
					Vector<Integer> vg=vm.get(i);
					for(int x=0;x<vg.size();x++){
						int val=vg.get(x);
						if(val==2 || val == 4){
							vg.set(x, 1);
						}
						if(val==3){
							vg.set(x, 0);
						}
					}
				}
			}

			traceMatrix(vYears);
			//gestiamo le sospensioni
			for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
				int variazioneCorrente=iAcVariazioni.getInteger(i, 1);
				int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
				int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);

				// CONGEDO BIENNALE
				if(ID_tp_variazione==16){
					Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
					Calendar data_fine=null;

					String startDateString = "01/07/2013";
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Calendar calmin=Calendar.getInstance();
					calmin.setTime(df.parse(startDateString));

					if(data_inizio.before(calmin)){
						data_inizio=calmin;
					}

					try{
						data_fine=iAcVariazioni.getCalendar(i, 5);
					}catch(Exception e){

					}
					if(ultimaEntrataUtile!=null && data_inizio.getTimeInMillis()<ultimaEntrataUtile.getTimeInMillis()){
						data_inizio=ultimaEntrataUtile;
					}
					if(ultimaUscitaUtile!=null && data_fine!=null && data_fine.getTimeInMillis()>=ultimaUscitaUtile.getTimeInMillis()){
						data_fine=ultimaUscitaUtile;
					}
					valorizzaMatriceGiorni(vYears,data_inizio,data_fine,0,ID_tp_variazione);
				}


			}

		}

		//valutare se la data del primo profilo è seguente al decesso in tale caso azzerare
		if(dataPrimoProfilo!=0 && dataDecesso!=0 && dataPrimoProfilo>dataDecesso){
			vYears = getMatricePresenze();

			if(trace>0){
				System.out.println(IDdomanda+" >> data primo profilo posteriore decesso azzero");
			}
		}
		if(iAcVariazioni.getRows()==0 || getNumeroProfili()==0){
			//reset
			vYears = getMatricePresenze();
			if(trace>0){
				System.out.println(IDdomanda+" >> nessun profilo identificato azzero");
			}
		}

		if(isMotivoEsclusioneEsternoPresente()){
			//reset
			vYears = getMatricePresenze();
			if(trace>0){
				System.out.println(IDdomanda+" >> domanda di riaccertamento non rilavata");
			}
		}

		for(int pos=1;pos<=30;pos++){

			if(iAcDati.getInteger(1, 12)>= 30600){
				Calendar tmp=getInizioVettore();
				tmp.add(Calendar.MONTH, pos);
				if(tmp.getTimeInMillis()<=getInizioPeriodo().getTimeInMillis()){

					vResultm.put(pos, (double)0);
					//vYears = getMatricePresenze();
					if(trace>0){
						//System.out.println(IDdomanda+" >> richiesto mese antecedente inizio periodo ");
					}
				}
			}



			Calendar inizioVettore=getInizioVettore();
			inizioVettore.add(Calendar.MONTH, (pos-1));

			Vector<Vector<Integer>> vm=vYears.get(inizioVettore.get(Calendar.YEAR));
			Vector<Integer> vDays=vm.get(inizioVettore.get(Calendar.MONTH));
			double countDays=0;
			for(int i=0;i<vDays.size();i++){
				countDays=countDays+vDays.get(i);
			}

			vResultm.put(pos, countDays);
		}
		traceMatrix(vYears);
		//TEST 
//		if ("12919730".equals(IDdomanda)) {
//			vResultm.put(12, 0d);
//		}
		if("11188936".equals(IDdomanda)) {
			vResultm.put(12, 17d);
		}
		if ("13110199".equals(IDdomanda) || "12081551".equals(IDdomanda)) {
			vResultm.put(7, 31d);
			vResultm.put(8, 28d);
			vResultm.put(9, 31d);
			vResultm.put(10, 30d);
			vResultm.put(11, 31d);
			vResultm.put(12, 30d);
			vResultm.put(13, 31d);
			vResultm.put(14, 31d);
			vResultm.put(15, 30d);
			vResultm.put(16, 31d);
			vResultm.put(17, 30d);
			vResultm.put(18, 31d);
			if ("13110199".equals(IDdomanda)) {
				vResultm.put(8, 29d);
				vResultm.put(16, 0d);
				vResultm.put(17, 0d);
				vResultm.put(18, 0d);
				
			}

		}
		
		
		return vResultm;

		//return countDays;

	}


	public double getProfili() {
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
			if(ID_tp_variazione==100 ){
				return 0;
			}
		}
		return 1;
	}

	public double getCessazione() {
		/*for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
			if(ID_tp_variazione==4 ){
				return 1;
			}

		}
		if(iAcVariazioni.getRows()>0){
			int ID_tp_variazione=iAcVariazioni.getInteger(iAcVariazioni.getRows(), 3);
			if(ID_tp_variazione==1 || ID_tp_variazione==2 || ID_tp_variazione==3){
				return 2;
			}
			if(ID_tp_variazione==9){
				return 3;
			}
			if(ID_tp_variazione==6){
				return 4;
			}
		}*/
		return 0;

	}

	public boolean esisteProfilo(){
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);

			if(ID_tp_variazione==100){
				return true;
			}
		}
		return false;
	}

	public boolean isInPeriodo(Calendar data){
		try {
			if (data!=null) {
				Calendar inizioPeriodotLimit=getInizioPeriodo();
				Calendar finePeriodoLimit=getFinePeriodo();
				if(data.getTime().getTime()>=inizioPeriodotLimit.getTime().getTime() && data.getTime().getTime()<=finePeriodoLimit.getTime().getTime()){
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Calendar getDataRiaccertamento(){
		try{
			return iAcConfigurazioni.getCalendar(1, 17);
		}catch(Exception e){
			//disabilita in riaccertamento
			Calendar c=Calendar.getInstance();
			c.set(Calendar.YEAR, 2050);
			return c;
		}
	}

	private HashMap<Integer, Integer> getContatore90Precedenti(LinkedHashMap<Integer,Vector<Vector<Integer>>> vYears) {
		HashMap<Integer, Integer>	contatore90GiorniMap	= new HashMap<Integer, Integer>();
		Iterator<Integer>			iys						= vYears.keySet().iterator();

		if ( iV90Giorni != null && iV90Giorni.getRows() > 0 ) {
			for (int c = 1; c <= iV90Giorni.getRows(); c++) {
				String inizio		= String.valueOf((int)(iV90Giorni.getDouble(c, 2)));
				String fine			= String.valueOf((int)(iV90Giorni.getDouble(c, 3)));
				LocalDate ldInizio	= new LocalDate(Integer.parseInt(inizio.substring(0, 4)), 	Integer.parseInt(inizio.substring(4, 6)), 	1);
				LocalDate ldFine	= new LocalDate(Integer.parseInt(fine.substring(0, 4)), 	Integer.parseInt(fine.substring(4, 6)), 	1);

				while(iys.hasNext()){
					int y			= iys.next();
					if ( y >= 2018 ) {
						//CICLO SUI MESI
						Vector<Vector<Integer>> vm=vYears.get(y);
						for(int i=0;i<vm.size();i++){
							//CICLO SUI GIORNI
							Vector<Integer> vg=vm.get(i);
							for(int x=0;x<vg.size();x++){
								int val=vg.get(x);
								LocalDate dataGiornoAnalizzato = new LocalDate(y, i+1, x+1);

								if ( (val == 2 || val == 4) &&
										( !dataGiornoAnalizzato.isBefore(ldInizio) && !dataGiornoAnalizzato.isAfter(ldFine) ) ) {
									if (contatore90GiorniMap.containsKey(y)) {
										contatore90GiorniMap.put(y, contatore90GiorniMap.get(y).intValue()+1);
									} else {
										contatore90GiorniMap.put(y, 1);
									}
								}
							}
						}
					}
				}
			}
		}

		return contatore90GiorniMap;
	}


	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//ricordati di spostare la data check riaccertamento a + un anno per il record sotto.
	//30500	30501	2013-07-01 00:00:00.000	2013-07-01 00:00:00.000	2015-12-31 00:00:00.000	2015-01-01 00:00:00.000	0,32000	100,00000	300,00000	600,00000	1100,00000	100,00000	150,00000	300,00000	500,00000	5400,00000	0
	public boolean isRiaccertamentoAvailable() throws Exception{
		Calendar c=getDataRiaccertamento();




		if(Calendar.getInstance().getTimeInMillis()>=c.getTimeInMillis()){

			Calendar data_presentazione=normalizzaDate(iAcDati.getCalendar(1, 1));
			//per il servizio 30500 periodo 30503 dobbiamo saltare il controllo se l'anno di presentazione è il 2016
			//			if(idServizio==30500 && idPeriodo==30503 && data_presentazione.get(Calendar.YEAR)>=2016){
			//				System.out.println("Elaborazione ac id_domanda:"+IDdomanda+" Rimuovi questa riga il 28/02/2018");
			//				return true;
			//			}


			//gestice le esclusioni per icef

			if(data_presentazione.get(Calendar.YEAR)<=c.get(Calendar.YEAR)-2 /*&& getSameDomandaUscita()==0*/){
			// se confermato che dal 1 aprile 2021 devo chiudere riaccertamento economico 2019 e assegno di cura 2019 non basta chiudere quelle presentate nel 2019, ma anche quelle presentate fino al 30/06/2020
			//if((data_presentazione.get(Calendar.YEAR)<=c.get(Calendar.YEAR)-2) || (data_presentazione.get(Calendar.YEAR)<=c.get(Calendar.YEAR)-1 && data_presentazione.get(Calendar.MONTH) <= Calendar.JUNE)){	
				if(iAcRiaccertamento.getRows()>1){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}

	}

	public int getSameDomandaUscita(){
		try{
			if( iAcRiaccertamento.getInteger(1, 1)!=0){
				return iAcRiaccertamento.getInteger(1, 1);
			}
			return 0;
		}catch(Exception e){
			return 0;
		}
	}

	public int getIdDomandaUscita(){
		try{
			if( iAcRiaccertamento.getInteger(1, 1)!=new Integer(IDdomanda)){
				return iAcRiaccertamento.getInteger(1, 1);
			}
			return 0;
		}catch(Exception e){
			return 0;
		}
	}


	/*public boolean isEsclusaPerIcef(){
		try{
			if( iAcRiaccertamento.getRows()>0 && iAcRiaccertamento.getInteger(1, 2)==1){
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}*/

	boolean isMotivoEsclusioneEsternoPresente(){
		if(getIdDomandaUscita()>0){
			return true;
		}
		return false;
	}

	public double getRevoche() throws Exception{


		String ret="1";
		String morteDopoProfilo="0";
		String incompatibilitaAltricontributiDa="0";
		String indennitaAccompagnamentoDa="0" ;
		String riaccertemntoIcefAssente="0" ;
		String trasferimentoFuoriProvinciaDa="0";
		String rinunciaFormalizzataUtenteDa="0";
		String vuoto="0";
		String retEsclusoUfficioApapiDa="0";
		String retEsclusoUfficioApssDa="0";


		int count=0;

		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
			Calendar data_inizio = null;

			if (iAcVariazioni.getElement(i, 4) != null) {
				data_inizio = iAcVariazioni.getCalendar(i, 4);
			}
			if(esisteProfilo() && ID_tp_variazione==4 && isInPeriodo(data_inizio)){
				morteDopoProfilo="1";
				count++;
			}



			if(ID_tp_variazione==6 && isInPeriodo(data_inizio)){
				incompatibilitaAltricontributiDa="2";
				count++;
			}
			if(ID_tp_variazione==9 && isInPeriodo(data_inizio)){
				indennitaAccompagnamentoDa="3";
				count++;
			}

			if(esisteProfilo() && ID_tp_variazione==1 && isInPeriodo(data_inizio)){
				trasferimentoFuoriProvinciaDa="5";
				count++;
			}
			if(ID_tp_variazione==31 && isInPeriodo(data_inizio)){
				rinunciaFormalizzataUtenteDa="6";
				count++;
			}

			if(ID_tp_variazione==18 && isInPeriodo(data_inizio)){
				retEsclusoUfficioApapiDa="8";
				count++;
			}
			if((ID_tp_variazione==20 || ID_tp_variazione==32) && isInPeriodo(data_inizio)){
				retEsclusoUfficioApssDa="9";
				count++;
			}

		}
		if(count==0 && getCheck()==0 && getIdDomandaUscita()==0 /*&& !isEsclusaPerIcef()*/){
			//if(iAcDati.getInteger(1, 12)== 30500 && iAcDati.getInteger(1, 12)== 30600){
			if(!isRiaccertamentoAvailable() || IDdomanda.equals("11245352") || IDdomanda.equals("11239984") || IDdomanda.equals("13168462") || IDdomanda.equals("7634181")){
				count++;
				riaccertemntoIcefAssente="4";
			}
			//}
		}


		if(count==0){
			return 0;
		}
		ret=ret+
				morteDopoProfilo+
				incompatibilitaAltricontributiDa+
				indennitaAccompagnamentoDa+
				riaccertemntoIcefAssente+
				trasferimentoFuoriProvinciaDa+
				rinunciaFormalizzataUtenteDa+
				vuoto+
				retEsclusoUfficioApapiDa+
				retEsclusoUfficioApssDa;



		return new Double(ret);
	}

	public double getCheck(){
		String ret="1";
		String mortePrimaProfilo="0";
		String incompatibilitaAltricontributi="0";
		String indennitaAccompagnamento="0" ;
		String mancanzaIcef="0" ;
		if(IDdomanda.equals("11245352") || IDdomanda.equals("10651348")) {
			mancanzaIcef="4" ;
		}
		String trasferimentoFuoriProvinciaPrimaProfilo="0";
		String rinunciaFormalizzataUtente="0";
		String retResidenza="0";
		String retEsclusoUfficioApapi="0";
		String retEsclusoUfficioApss="0";
		int count=0;
		//i servizi di riaccertamento non hanno dati in ac dati
		try{
			int residenza_requisito=iAcDati.getInteger(1, 3);
			int residenza_certificatore=iAcDati.getInteger(1, 4);


			if(residenza_requisito==0 && residenza_certificatore==2){
				retResidenza="7";
				count++;
			}
		}catch(Exception e){
			retResidenza="0";
		}

		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);

			if(!esisteProfilo() && ID_tp_variazione==4){
				mortePrimaProfilo="1";
				count++;
			}
			if(ID_tp_variazione==5 || ID_tp_variazione==35){
				incompatibilitaAltricontributi="2";
				count++;
			}
			if(ID_tp_variazione==8){
				indennitaAccompagnamento="3";
				count++;
			}

			if(!esisteProfilo() && ID_tp_variazione==1){
				trasferimentoFuoriProvinciaPrimaProfilo="5";
				count++;
			}
			if(ID_tp_variazione==30){
				rinunciaFormalizzataUtente="6";
				count++;
			}

			if(ID_tp_variazione==11 ){
				retEsclusoUfficioApapi="8";
				count++;
			}
			if(ID_tp_variazione==12 ){
				retEsclusoUfficioApss="9";
				count++;
			}

		}
		if(count==0){
			return 0;
		}
		ret=ret+
				mortePrimaProfilo+
				incompatibilitaAltricontributi+
				indennitaAccompagnamento+
				mancanzaIcef+
				trasferimentoFuoriProvinciaPrimaProfilo+
				rinunciaFormalizzataUtente+
				retResidenza+
				retEsclusoUfficioApapi+
				retEsclusoUfficioApss;

		return new Double(ret);
	}


	public int getMeseDataPresentazione() throws Exception{
		Calendar c=iAcDati.getCalendar(1, 1);
		int mese=c.get(Calendar.MONTH);
		return mese+1;
	}

	/*
	 	per le domande con doc.data_presenzazione < 01/02/2013

		se ac_dati.data_decorrenza_ia is null oppure ac_dati.data_decorrenza_ia >= 01/09/2012
		allora la data decorrenza contributo AC è il primo giorno del mese successivo alla data_presentazione della domanda

		se ac_dati.data_decorrenza_ia < 01/09/2012
		allora la data decorrenza contributo AC è 01/09/2012

		per le domande con doc.data_presenzazione > 01/02/2013
		allora la data decorrenza contributo AC è il primo giorno del mese successivo alla data_presentazione della domanda
	 */

	//uscite ed entrate valgono dal primo giorno del mese successivo

	public boolean isDataDecorrenzaPrecedenteUgualeInizio(){
		try {
			Calendar c=iAcDati.getCalendar(1, 9);
			Date cd= c.getTime();
			Calendar ccopare=Calendar.getInstance();
			ccopare.set(Calendar.YEAR, 2012);
			ccopare.set(Calendar.DAY_OF_MONTH, 1);
			ccopare.set(Calendar.MONTH, 8);

			ccopare.set(Calendar.HOUR_OF_DAY, 23);
			ccopare.set(Calendar.MINUTE, 59);
			Date dCompare=ccopare.getTime();
			if(cd.getTime()<=dCompare.getTime()){
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public Calendar  ricalcolaDataInizioDaSospensioneRSA(Calendar data_iniziocorrente,StringBuffer return0) throws Exception{
		Calendar detaret=data_iniziocorrente;

		/*Regola di differimento dell'inizio per sospensione RSA.
		 * Entra solo per le domande iniziali (servizio 30500) con periodo diverso dal 30500
		 */
		if(iAcDati.getInteger(1, 12) == 30500 && iAcDati.getInteger(1, 13) != 30500){
			long dataPrimoProfilo	= 0;
			long dataPresentazione	= 0;

			for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
				int ID_tp_variazione	= iAcVariazioni.getInteger(i, 3);
				if(ID_tp_variazione==100){
					Calendar data_inizio	= iAcVariazioni.getCalendar(i, 4);
					dataPrimoProfilo		= data_inizio.getTime().getTime();
					break;
				}
			}

			if(dataPrimoProfilo==0){
				return detaret;
			}

			dataPresentazione=iAcDati.getCalendar(1, 1).getTime().getTime();

			long data_inizio_g	= 0;
			long data_fine_g	= 0;

			for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
				int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
				if(ID_tp_variazione!=ID_VAR_RSA_NON_DEF && ID_tp_variazione!=ID_VAR_RSA_DEF){
					continue;
				}
				Calendar	cdata_inizio	= iAcVariazioni.getCalendar(i, 4);
				long		data_inizio		= iAcVariazioni.getCalendar(i, 4).getTime().getTime();
				long		data_fine		= 0;
				Calendar	cdata_fine		= null;;
				try{
					data_fine=iAcVariazioni.getCalendar(i, 5).getTime().getTime();
					cdata_fine=iAcVariazioni.getCalendar(i, 5);
				}catch(Exception e){
					data_fine=0;
				}

				if(trace>0){
					System.out.println("Rilevata variazione:"+calendarToString(cdata_inizio) +" a:"+calendarToString(cdata_fine));
				}

				if(data_fine_g==0 && data_inizio_g==0){
					data_inizio_g=data_inizio;
					data_fine_g=data_fine;
				}else{
					if(data_fine_g!=0 && (data_inizio==data_fine_g || data_inizio<data_fine_g || data_inizio==(data_fine_g+(1000*60*60*24)))){
						data_inizio=data_inizio_g;
					}else{
						data_inizio_g=data_inizio;
						data_fine_g=data_fine;
					}
				}

				// se il ricovero inizia prima della data di presentazione e non c'è una data fine o c'è ed è superiore della data del primo profilo
				if(data_inizio<dataPresentazione && (data_fine>dataPrimoProfilo || data_fine==0)){
					if(data_fine==0){
						return0.append("exit");
						return null;
					}else{
						cdata_fine.set(Calendar.DAY_OF_MONTH, 1);
						cdata_fine.set(Calendar.MONTH, cdata_fine.get(Calendar.MONTH)+1);

						return cdata_fine;
					}
				}
				//se il ricovero in rsa dura da + di 30 giorni rispetto alla data della prima visita
				if(trace>0){
					System.out.println(new Date(data_inizio));
					System.out.println(new Date(dataPrimoProfilo));
					System.out.println(new Date(dataPresentazione));
				}
				if(data_inizio>dataPresentazione && data_inizio<dataPrimoProfilo && (data_fine==0 || data_fine>dataPrimoProfilo)){

					long diffDays=(dataPrimoProfilo-data_inizio)/(1000*60*60*24);

					if(diffDays>30){
						if(data_fine==0){
							return0.append("exit");
							return null;
						}else{
							cdata_fine.set(Calendar.DAY_OF_MONTH, 1);
							cdata_fine.set(Calendar.MONTH, cdata_fine.get(Calendar.MONTH)+1);



							try{

								Date data_inizioProssimaRsa=null;
								int x=-1;
								for (x = i+1; x <= iAcVariazioni.getRows(); x++) {
									int ID_tp_variazione2=iAcVariazioni.getInteger(x, 3);
									if(ID_tp_variazione2!=ID_VAR_RSA_NON_DEF && ID_tp_variazione2!=ID_VAR_RSA_DEF){
										continue;
									}
									data_inizioProssimaRsa=iAcVariazioni.getCalendar(x, 4).getTime();

									break;
								}

								if(data_fine!=0 && data_inizioProssimaRsa!=null && (data_fine==data_inizioProssimaRsa.getTime() || data_fine+(1000*60*60*24)==data_inizioProssimaRsa.getTime())){
									try{

										Calendar cdata_finesuc=iAcVariazioni.getCalendar(x, 5);
										cdata_finesuc.set(Calendar.DAY_OF_MONTH, 1);
										cdata_finesuc.set(Calendar.MONTH, cdata_finesuc.get(Calendar.MONTH)+1);

										return cdata_finesuc;
									}catch(Exception e){
										return0.append("exit");
										return null;
									}
								}

							}catch(Exception e){

							}


							return cdata_fine;
						}
					}
				}
			}
		}

		return detaret;

	}

	public Calendar  ricalcolaDataInizioDaSospensioneRSALp8_2018(Calendar data_iniziocorrente, StringBuffer return0) throws Exception {
		
		Calendar dataRet = data_iniziocorrente;
		
		LinkedHashMap<Integer,Vector<Vector<Integer>>> vYears		= getMatricePresenze();

		//RSA DEFINITIVO 
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {

			int ID_tp_variazione			= iAcVariazioni.getInteger(i, 3);
			int ID_tp_variazione_categoria	= iAcVariazioni.getInteger(i, 8);
			if(ID_tp_variazione == ID_VAR_CONGEDO 
					|| ID_tp_variazione == ID_VAR_RSA_NON_DEF
					|| ID_tp_variazione == ID_VAR_OSP){
				continue;
			}

			//titologia periodo
			if(ID_tp_variazione_categoria == 3){
				Calendar data_inizio	= iAcVariazioni.getCalendar(i, 4);
				Calendar data_fine		= null;

				try{
					data_fine			= iAcVariazioni.getCalendar(i, 5);
				}catch(Exception e){

				}
				if(data_fine==null){
					data_fine			= getFinePeriodo();
				}

				valorizzaMatriceGiorni(vYears,data_inizio,data_fine,1,ID_tp_variazione);
			}
		}

		
		LocalDate dataPresentazione = new LocalDate(data_iniziocorrente.get(Calendar.YEAR)
														, (data_iniziocorrente.get(Calendar.MONTH)+1)
														, data_iniziocorrente.get(Calendar.DAY_OF_MONTH));
		
		
		boolean presentazioneInSospensione = false;
		Iterator<Integer> iy=vYears.keySet().iterator();

		while(iy.hasNext()){
			int anno			= iy.next();
			//CICLO SUI MESI
			Vector<Vector<Integer>> vm=vYears.get(anno);
			for(int mese=0;mese<vm.size();mese++){
				//CICLO SUI GIORNI
				Vector<Integer> vg=vm.get(mese);
				for(int giorno=0;giorno<vg.size();giorno++){
					LocalDate dataGiornoAnalizzato = new LocalDate(anno, mese+1, giorno+1);
					int val=vg.get(giorno);
					if (dataGiornoAnalizzato.equals(dataPresentazione) && val == 1) {
						presentazioneInSospensione = true;
					}
					
					if (presentazioneInSospensione && val == 0) {
						if (giorno == 0) {
							dataRet.set(anno, mese, 1, 0, 0, 0);
						} else {
							dataRet.set(anno, mese, 1, 0, 0, 0);
							dataRet.set(Calendar.MONTH, dataRet.get(Calendar.MONTH)+1);
						}
						return dataRet;
					}
				}
			}
		}
		
		return dataRet;
		
		
		
		
//		Calendar	detaret				= data_iniziocorrente;
//		long		dataPresentazione	= iAcDati.getCalendar(1, 1).getTime().getTime();
//		long		data_inizio_g		= 0;
//		long		data_fine_g			= 0;
//		long		dataPrimoProfilo	= 0;
		
//		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
//			int ID_tp_variazione	= iAcVariazioni.getInteger(i, 3);
//			if(ID_tp_variazione==100){
//				Calendar data_inizio	= iAcVariazioni.getCalendar(i, 4);
//				dataPrimoProfilo		= data_inizio.getTime().getTime();
//				break;
//			}
//		}
//
//		if(dataPrimoProfilo==0){
//			return detaret;
//		}
		
//		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
//			int ID_tp_variazione=iAcVariazioni.getInteger(i, 3);
//			if(ID_tp_variazione!=ID_VAR_RSA_DEF){
//				continue;
//			}
//			Calendar	cdata_inizio	= iAcVariazioni.getCalendar(i, 4);
//			long		data_inizio		= iAcVariazioni.getCalendar(i, 4).getTime().getTime();
//
//			long		data_fine		= 0;
//			Calendar	cdata_fine		= null;
//			try{
//				data_fine	= iAcVariazioni.getCalendar(i, 5).getTime().getTime();
//				cdata_fine	= iAcVariazioni.getCalendar(i, 5);
//			}catch(Exception e){
//				data_fine	= 0;
//			}
//
//			if(trace>0){
//				System.out.println("Rilevata variazione:"+calendarToString(cdata_inizio) +" a:"+calendarToString(cdata_fine));
//			}
//
//
//			if(data_fine_g==0 && data_inizio_g==0) {
//				data_inizio_g		= data_inizio;
//				data_fine_g			= data_fine;
//			} else {
//				if(data_fine_g!=0 && (data_inizio==data_fine_g || data_inizio<data_fine_g || data_inizio==(data_fine_g+(1000*60*60*24)))){
//					data_inizio		= data_inizio_g;
//
//				}else{
//					data_inizio_g	= data_inizio;
//					data_fine_g		= data_fine;
//				}
//			}
//
//			if( data_inizio<dataPresentazione && (data_fine>dataPresentazione || data_fine==0) ){
//				if (data_fine == 0) {
//					return0.append("exit");
//					return null;
//				} else {
//					cdata_fine.set(Calendar.DAY_OF_MONTH, 1);
//					cdata_fine.set(Calendar.MONTH, cdata_fine.get(Calendar.MONTH)+1);
//
//					return cdata_fine;
//				}
//			}
//		}
//		return detaret;

	}

	public String getVariazioneDesc(int id){
		if(id==0)return	"Init matrice";
		if(id==1)return	"trasferimento fuori provincia";
		if(id==2)return	"trasferimento tra USL fuori provincia";
		if(id==3)return	"iscrizione AIRE";
		if(id==4)return	"decesso";
		if(id==5)return	"incompatibilità con altri contributi";
		if(id==6)return	"incompatibilità con altri contributi da";
		if(id==7)return	"rinuncia ad altri contributi da";
		if(id==8)return	"indennità di accompagnamento assente";
		if(id==9)return	"revoca indennità di accompagnamento da";
		if(id==10)return	"assegnazione indennità di accompagnamento da";
		if(id==11)return	"esclusione d'Ufficio APAPI";
		if(id==12)return	"esclusione d'Ufficio APSS";
		if(id==14)return	"ricovero ospedaliero";
		if(id==15)return	"ricovero in casa di riposo (sollievo)";
		if(id==16)return	"contributo biennale retribuito D.Lgs. n. 151/2001";
		if(id==17)return	"ricovero in casa di riposo (definitivo)";
		if(id==18)return	"esclusione d'Ufficio APAPI da";
		if(id==19)return	"rientro d'Ufficio APAPI";
		if(id==20)return	"esclusione d'Ufficio APSS da";
		if(id==21)return	"rientro d'Ufficio APSS da";
		if(id==23)return	"mancata presentazione riaccertamento";
		if(id==30)return	"rinuncia formalizzata utente";
		if(id==31)return	"rinuncia formalizzata utente da";
		if(id==35)return	"incompatibilità per ricovero in casa di riposo";
		if(id==100)return	"nuovo profilo";
		if(id==200)return	"mancanza pre-requisito residenza";
		if(id==201)return	"mancanza pre-requisito ICEF";
		return "Non trovata";
	}

	public void traceMatrix(LinkedHashMap<Integer,Vector<Vector<Integer>>> matrice) throws Exception{

		if(trace==0){
			return;
		}

		String[] mesi={"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

		Calendar inizioPeriodotLimit=getInizioPeriodo();
		Calendar finePeriodoLimit=getFinePeriodo();

		Calendar inizioPeriodo=getInizioPeriodo();inizioPeriodo.add(Calendar.MONTH,-2 );
		Calendar finePeriodo=getFinePeriodo();finePeriodo.add(Calendar.MONTH,+2 );
		Calendar corrente=Calendar.getInstance();
		corrente=normalizzaDate(corrente);

		/*System.out.println(calendarToString(inizioPeriodo));
		System.out.println(calendarToString(finePeriodo));
		System.out.println(calendarToString(corrente));*/

		Iterator<Integer> iy=matrice.keySet().iterator();
		while(iy.hasNext()){
			int y=iy.next();
			corrente.set(Calendar.YEAR, y);
			Vector<Vector<Integer>> vm=matrice.get(y);
			for(int i=0;i<vm.size();i++){
				// se non si setta il giorno prima del mese la classe calendar non va se è già settato un giorno maggiore del limite del mese settato
				corrente.set(Calendar.DAY_OF_MONTH, 1);
				corrente.set(Calendar.MONTH, i);
				if(inizioPeriodotLimit.getTimeInMillis()==corrente.getTimeInMillis()){

					System.out.println("");
					System.out.println("*******************************************************");
				}


				if(corrente.getTimeInMillis()>=inizioPeriodo.getTimeInMillis() && corrente.getTimeInMillis()<=finePeriodo.getTimeInMillis()){
					if(inizioPeriodotLimit.getTimeInMillis()!=corrente.getTimeInMillis()){
						System.out.println("");
					}
					System.out.print(y+" "+mesi[i]+" \t");
				}
				Vector<Integer> vg=vm.get(i);
				for(int x=0;x<vg.size();x++){
					corrente.set(Calendar.DAY_OF_MONTH, x+1);
					//System.out.println(inizioPeriodotLimit.getTime()+" "+corrente.getTime());

					if(corrente.getTimeInMillis()<inizioPeriodo.getTimeInMillis()){
						continue;
					}else{
						if(corrente.getTimeInMillis()<=finePeriodo.getTimeInMillis()){
							System.out.print(vg.get(x));
						}
					}

					if(finePeriodoLimit.getTimeInMillis()==corrente.getTimeInMillis()){
						System.out.println("");
						System.out.print("*******************************************************");
					}
				}
			}
		}
		System.out.println("");
		System.out.println("---------------------------------------------------");
		System.out.println("---------------------------------------------------");
		System.out.println("");
	}

	public Calendar getMeseDataInizioAlernativa() throws Exception{
		if(iAcFascicolo.getRows()>0){
			return iAcFascicolo.getCalendar(1, 1);
		}else{
			return iAcDati.getCalendar(1, 1);
		}
	}

	public double getMeseInizio() throws Exception {

		Calendar data_inizio_presentazione=getMeseDataInizioAlernativa();
		data_inizio_presentazione.set(Calendar.MONTH, data_inizio_presentazione.get(Calendar.MONTH)+1);
		data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
		if(getAnnoRiferimentoDaDataPresentazione(getMeseDataInizioAlernativa())==2012){
			data_inizio_presentazione.set(Calendar.YEAR, 2012);
			data_inizio_presentazione.set(Calendar.MONTH, 8);
			data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
			if(!isDataDecorrenzaPrecedenteUgualeInizio()){
				data_inizio_presentazione=getMeseDataInizioAlernativa();
				data_inizio_presentazione.set(Calendar.MONTH, 		data_inizio_presentazione.get(Calendar.MONTH)+1);
				data_inizio_presentazione.set(Calendar.DAY_OF_MONTH, 1);
			}
		}else{
			// SPOSTAMENTO DATA INIZIO PER SOSPENSIONI
			Calendar unoGennaio2018 = GregorianCalendar.getInstance();
			unoGennaio2018.set(2018, Calendar.JANUARY, 1); 
			Calendar seiLuglio2018 = GregorianCalendar.getInstance();
			seiLuglio2018.set(2018, Calendar.JULY, 6); 

			if (data_inizio_presentazione.before(unoGennaio2018)) {
				StringBuffer return0=new StringBuffer();
				data_inizio_presentazione=ricalcolaDataInizioDaSospensioneRSA(data_inizio_presentazione,return0);
				if(return0.length()>0){
					Calendar data_presentazione=normalizzaDate(iAcDati.getCalendar(1, 1));
					int ret= (data_presentazione.get(Calendar.YEAR)*100)+data_presentazione.get(Calendar.MONTH)+1; //201201 (concatenazione mese + anno)
					return ret;
				}
			} else if (!data_inizio_presentazione.before(seiLuglio2018)) {
				StringBuffer return0=new StringBuffer();
				data_inizio_presentazione=ricalcolaDataInizioDaSospensioneRSALp8_2018(data_inizio_presentazione,return0);
				if(return0.length()>0){
					Calendar data_presentazione=normalizzaDate(iAcDati.getCalendar(1, 1));
					int ret= (data_presentazione.get(Calendar.YEAR)*100)+data_presentazione.get(Calendar.MONTH)+1; //201201 (concatenazione mese + anno)
					return ret;
				}
			}
		}

		int ret= (data_inizio_presentazione.get(Calendar.YEAR)*100)+data_inizio_presentazione.get(Calendar.MONTH)+1; //201201 (concatenazione mese + anno)
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			//String valoreProfilo=iAcVariazioni.getString(i, 7);
			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
			//entrata da data
			if(ID_tp_variazione_categoria==1){
				Calendar data_inizio=iAcVariazioni.getCalendar(i, 4);
				data_inizio.set(Calendar.MONTH, data_inizio.get(Calendar.MONTH)+1);
				data_inizio.set(Calendar.DAY_OF_MONTH, 1);
				if(data_inizio.getTime().getTime()>data_inizio_presentazione.getTime().getTime()){
					//se trovo una variazione di entrata con data > ala data di inizio (presentazione) allora sposto avanti il mese di inizio contabile
					ret= (data_inizio.get(Calendar.YEAR)*100)+data_inizio.get(Calendar.MONTH)+1; //201201 (concatenazione mese + anno)
					break;
				}
			}
		}
		if(iAcDati.getInteger(1, 12)>= 30600){
			if(ret<getInizioPeriodoDaConfigInt()){
				return getInizioPeriodoDaConfigInt();
			}
		}
		return ret;
	}

	//inizio e fine sonoinclusi come estremi!!!
	public double getMeseFine() throws Exception {
		Calendar data_fine_ac=getFinePeriodo();
		int ret= (data_fine_ac.get(Calendar.YEAR)*100)+data_fine_ac.get(Calendar.MONTH)+1; //201201 (concatenazione mese + anno)
		for (int i = 1; i <= iAcVariazioni.getRows(); i++) {
			//String valoreProfilo=iAcVariazioni.getString(i, 7);
			int ID_tp_variazione_categoria =iAcVariazioni.getInteger(i, 8);
			//entrata da data
			if(ID_tp_variazione_categoria==2){
				Calendar data_fine=iAcVariazioni.getCalendar(i, 4);
				if(data_fine.getTime().getTime()<=data_fine_ac.getTime().getTime()){
					//se trovo una variazione di entrata con data > ala data di inizio (presentazione) allora sposto avanti il mese di inizio contabile
					ret= (data_fine.get(Calendar.YEAR)*100)+data_fine.get(Calendar.MONTH)+1; //201201 (concatenazione mese + giorno)
					break;
				}
			}
		}
		return ret;
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 * @throws Exception
	 */
	public double is19() throws Exception {
		return 0;
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}

	public int getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(int idServizio) {
		this.idServizio = idServizio;
	}

	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}





}
