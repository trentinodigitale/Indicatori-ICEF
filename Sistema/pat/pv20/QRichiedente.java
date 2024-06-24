package c_elab.pat.pv20;

import java.util.*;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import c_elab.pat.Util_apapi;


/** legge i dati del richiedente 
 *
 * @author g_barbieri 
 * 
 * 
 */
public abstract class QRichiedente extends ElainNode {
	

	
	/**
	 * SELECT ID_riferimento, riferimento FROM   PNS_tp_riferimenti	WHERE  (ID_servizio = 9290) AND (riferimento = N'1 trimestre')
	   SELECT D_relazione_parentela, parentela FROM R_Relazioni_parentela	WHERE (ID_servizio = 9290) AND (parentela IN (N'FE', 'FR', 'FC', 'AR', 'AC')) 
	   SELECT * FROM R_Relazioni_parentela	WHERE (ID_servizio = 9290) AND (parentela IN (N'Richiedente'))	
	 */
		int pv_IDServizio = -1;
		int pv_IDRiferimento_1Trimestre = -1; //vedi 1 trimestre nella tabella PNS_tp_riferimenti per il servizio in questione
		
		String pv_lista_IDRelazioneParentela_figli = ""; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
		
		String pv_lista_IDRelazioneParentela_richiedente = ""; 
		//NB aggiornare la classe Util_apapi per gli importi colf
		// dal 2017 sono cambiate le regole di calcolo
	
	
	protected Hashtable 		 inputValues = new Hashtable();
	
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QRichiedente.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {

            super.init(dataTransfer);
            
            //** modifiche 2017
            Table tbServizio;
			
			super.init(dataTransfer);
            
	        StringBuffer sql = new StringBuffer();
	        
	        sql.append("SELECT ID_servizio ");
			sql.append("FROM Domande ");
			sql.append("WHERE (Domande.id_domanda = ");
			sql.append(IDdomanda);
			sql.append(")");
			
			
			try {

				doQuery(sql.toString());
				tbServizio = records;
				pv_IDServizio = tbServizio.getInteger(1, 1);
				//CAMBIAMI ANNUALMENTE
				
				if(pv_IDServizio==Pv20_Params.pv_IDServizio_casalinghe){
					pv_IDRiferimento_1Trimestre = Pv20_Params.pv_casa_IDRiferimento_1Trimestre; 
					pv_lista_IDRelazioneParentela_figli = Pv20_Params.pv_casa_lista_IDRelazioneParentela_figli;
					pv_lista_IDRelazioneParentela_richiedente = Pv20_Params.pv_casa_lista_IDRelazioneParentela_richiedente; 
				}else{
					pv_IDRiferimento_1Trimestre = Pv20_Params.pv_dis_IDRiferimento_1Trimestre; 
					pv_lista_IDRelazioneParentela_figli = Pv20_Params.pv_dis_lista_IDRelazioneParentela_figli;
					pv_lista_IDRelazioneParentela_richiedente = Pv20_Params.pv_dis_lista_IDRelazioneParentela_richiedente;
				}

	        } catch (Exception e) {
	        	System.out.println("Errore in Check.init: " + e.toString()) ; 
	        }
			
            //** end modifiche 2017
        	int settimane = 0;
            int check = 0;
            int n_anni = 0;
            Table anni;

            
            inputValues.clear();
            
            // ------------------------------ conta anni
            sql = new StringBuffer();
            //                         1
            sql.append(
            "SELECT DISTINCT PNS_versamenti.anno ");
            sql.append("FROM PNS_versamenti ");
            sql.append("WHERE PNS_versamenti.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(" ORDER BY PNS_versamenti.anno DESC ");
            
            System.out.println(sql.toString());
            doQuery(sql.toString());
            anni = records;
            n_anni = anni.getRows();
            
            if (n_anni > 3) {
            	n_anni = 3;
    			System.out.println("ERRORE GRAVE: non possono essere gestiti piu' di 3 anni di contributi da c_elab.pat.pv08.QRichiedente");
            }

            // per ogni anno di versamento
            for (int k = 1; k <= n_anni; k++) {
            	int periodicita = -1;
            	int settimane_tot = 0;
            	int mesi = 0;
            	int mesi_tot = 0;
            	double importo = 0.0;
            	double importo_tot = 0.0;
            	int trimestre = 0;
            	double importo_colf = 0.0;
            	Calendar da = Calendar.getInstance();
            	Calendar a  = Calendar.getInstance();
            	Calendar da_limite = Calendar.getInstance();
            	Calendar a_limite  = Calendar.getInstance();
            	Calendar decorrenza_volontaria  = Calendar.getInstance();
            	Calendar decorrenza_pagamento  = Calendar.getInstance();
            	Calendar pensione_diretta  = Calendar.getInstance();
            	Calendar eta_pensionabile = Calendar.getInstance();
            	Calendar anni_contribuzione  = Calendar.getInstance();
            	Calendar data_versamento  = Calendar.getInstance();
            	Calendar data_scadenza  = Calendar.getInstance();
            	int t_periodo = 0;
            	int t_periodo_tot = 0;
            	int t_autorizzato = 0;
            	int t_autorizzato_tot = 0;
            	int t_assistenza = 0;
            	int t_assistenza_tot = 0;
            	int t_coperto = 0;
            	int t_coperto_tot = 0;
            	//boolean t_periodo_array[];
            	boolean t_autorizzato_array[];
            	boolean t_assistenza_array[];
            	boolean t_assistenza_arrayFINTA[];
            	boolean t_coperto_array[];
                int n_record = 0;

                int anno_servizio = anni.getInteger(k, 1);

            	// ------------------------------ conta versamenti
            	sql = new StringBuffer();
            	//                         1
            	sql.append(
            	"SELECT PNS_versamenti.importo ");
            	sql.append("FROM PNS_versamenti ");
            	sql.append("WHERE PNS_versamenti.ID_domanda = ");
            	sql.append(IDdomanda);
            	sql.append(" AND PNS_versamenti.anno = ");
            	sql.append(anno_servizio);
            	doQuery(sql.toString());
            	n_record = records.getRows();

            	// per ogni periodo di versamento all'interno dell'anno "anno_servizio"
            	for (int i = 1; i <= n_record; i++) {

            		// ------------------------------ versamenti
            		sql = new StringBuffer();
            		//                         1                      2                        3                        4                          5                      6                       7
            		sql.append(
            		"SELECT PNS_versamenti.importo, PNS_versamenti.settimane, PNS_versamenti.mesi, PNS_versamenti.ID_riferimento, PNS_versamenti.anno, PNS_versamenti.dataVers, PNS_versamenti.scadenza ");
            		sql.append("FROM PNS_versamenti ");
            		sql.append("WHERE PNS_versamenti.ID_domanda = ");
            		sql.append(IDdomanda);
                	sql.append(" AND PNS_versamenti.anno = ");
                	sql.append(anno_servizio);
            		sql.append(" ORDER BY PNS_versamenti.ID_versamento");
            		
            		System.out.println(sql.toString());
            		doQuery(sql.toString());

            		settimane = super.getInteger(i,2);
            		mesi =  super.getInteger(i,3);
            		if (servizio==pv_IDServizio)
            			trimestre = super.getInteger(i,4) - (pv_IDRiferimento_1Trimestre - 1);
            		else
            			System.out.println("ERRORE GRAVE: il servizio " + servizio + " non puo' essere gestito da c_elab.pat.pv08.QRichiedente");
            		
            		importo = super.getDouble(i,1);
            		//anno_servizio = super.getInteger(i,5);

            		// la valutazione settimane-mesi è fatta
            		// in base all'input nei rispettivi campi
            		// e non in base alla combo dipendente-autonomo
            		if ( settimane > mesi )
            			periodicita = 2;
            		else
            			periodicita = 3;

            		// inizializzazione estremi dei periodi
            		if (trimestre == 1) {
            			da.set(anno_servizio, 0, 1, 0, 0);				// 01/01
            			a.set(anno_servizio, 2, 31, 23, 59);			// 31/03
            			da_limite.set(anno_servizio, 0, 1, 0, 0);		// 01/01
            			a_limite.set(anno_servizio, 2, 31, 23, 59);		// 31/03
            		} else if (trimestre == 2) {
            			da.set(anno_servizio, 3, 1, 0, 0);				// 01/04
            			a.set(anno_servizio, 5, 30, 23, 59);			// 30/06
            			da_limite.set(anno_servizio, 3, 1, 0, 0);		// 01/04
            			a_limite.set(anno_servizio, 5, 30, 23, 59);		// 30/06
            		} else if (trimestre == 3) {
            			da.set(anno_servizio, 6, 1, 0, 0);				// 01/07
            			a.set(anno_servizio, 8, 30, 23, 59);			// 30/09
            			da_limite.set(anno_servizio, 6, 1, 0, 0);		// 01/07
            			a_limite.set(anno_servizio, 8, 30, 23, 59);		// 30/09
            		} else if (trimestre == 4) {
            			da.set(anno_servizio, 9, 1, 0, 0);				// 01/10
            			a.set(anno_servizio, 11, 31, 23, 59);			// 31/12
            			da_limite.set(anno_servizio, 9, 1, 0, 0);		// 01/10
            			a_limite.set(anno_servizio, 11, 31, 23, 59);	// 31/12
            		} else {  // intero anno
            			da.set(anno_servizio, 0, 1, 0, 0);				// 01/01
            			a.set(anno_servizio, 11, 31, 23, 59);			// 31/12
            			da_limite.set(anno_servizio, 0, 1, 0, 0);		// 01/01
            			a_limite.set(anno_servizio, 11, 31, 23, 59);	// 31/12
            		}

            		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da.getTime()));
            		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a.getTime()));

            		// calcola la lunghezza del periodo in settimane o mesi
            		if (periodicita == 2)
            			// calcola il n. di sabati tra i limiti del periodo 
            			t_periodo = Util_apapi.getSettimane(da_limite,a_limite);
            		else {
            			if (trimestre <= 4)
            				t_periodo = 3;
            			else
            				t_periodo = 12;
            		}

            		// inizializza il vettore del periodo (in settimane o mesi)
            		// SERVE?????
            		//t_periodo_array = new boolean[t_periodo];
            		//for (int j = 0; j < t_periodo; j++) {
            		//	t_periodo_array[j] = true;
            		//}
            		
            		try {
            			data_versamento = super.getCalendar(i,6);
            		} catch (Exception e) {
            			data_versamento.set(2050,0,1);
            		}
            		try {
            			data_scadenza = super.getCalendar(i,7);
            		} catch (Exception e) {
            			data_scadenza.set(2040,0,1);
            		}
            		if(data_versamento.after(data_scadenza))
            			check = 11;  //escluso per versamento ritardato

                    
            		// ------------------------------ richiedente
                    sql = new StringBuffer();
            		//                              1                              2                            3                            4                              5                              6                                   7                             8                  		       9							10
            		sql.append(
            		"SELECT PNS_richiedente.ID_tp_categoria, PNS_richiedente.etaPens_dal, PNS_richiedente.anniContr_dal, PNS_richiedente.resTAA_5anni, PNS_richiedente.resTAA_1anno, PNS_richiedente.entePrev_decorrenza, PNS_richiedente.entePrev_data, PNS_richiedente.no_previdenza, PNS_richiedente.pensDir_data, PNS_richiedente.esclusa_ufficio ");
            		sql.append(" FROM PNS_richiedente ");
            		sql.append("WHERE PNS_richiedente.ID_domanda = ");
            		sql.append(IDdomanda);
            		doQuery(sql.toString());

            		if( !(records.getBoolean(1,4) || records.getBoolean(1,5)) )
            			check = 2;  //escluso per mancanza requisiti residenza

            		if( !(records.getBoolean(1,8)))
            			check = 12;  //escluso per essere stato iscritto a forme di previdenza obbligatoria nel periodo dei versamenti

            		// NEW 2007 -->>
            		if( records.getBoolean(1,10) )
            			check = 14;  //escluso d'ufficio

            		try {
            			decorrenza_volontaria = super.getCalendar(1,6);
            		} catch (Exception e) {
            			decorrenza_volontaria = (Calendar)da_limite.clone();
            			decorrenza_volontaria.add(Calendar.DAY_OF_YEAR, -1);
            		}

            		try {
            			decorrenza_pagamento = super.getCalendar(1,7);
            		} catch (Exception e) {
            			decorrenza_pagamento = (Calendar)decorrenza_volontaria.clone();
            		}

            		if(decorrenza_pagamento.before(decorrenza_volontaria))
            			decorrenza_volontaria = (Calendar)decorrenza_pagamento.clone();

            		try {
            			pensione_diretta = super.getCalendar(1,9);
            		} catch (Exception e) {
            			pensione_diretta = (Calendar)a_limite.clone();
            			pensione_diretta.add(Calendar.DAY_OF_YEAR, 1);
            		}
            		try {
            			eta_pensionabile = super.getCalendar(1,2);
            		} catch (Exception e) {
            			eta_pensionabile = (Calendar)a_limite.clone();
            			eta_pensionabile.add(Calendar.DAY_OF_YEAR, 1);
            		}
            		try {
            			anni_contribuzione = super.getCalendar(1,3);
            		} catch (Exception e) {
            			anni_contribuzione = (Calendar)a_limite.clone();
            			anni_contribuzione.add(Calendar.DAY_OF_YEAR, 1);
            		}

            		// calcola le date di inzio/fine dell'autorizzazione
            		da = (Calendar)decorrenza_volontaria.clone();

            		// il Calendar a è il minimo delle tre date
            		a = (Calendar)pensione_diretta.clone();
            		if(eta_pensionabile.before(pensione_diretta))
            			a = (Calendar)eta_pensionabile.clone();
            		if(anni_contribuzione.before(a))
            			a = (Calendar)anni_contribuzione.clone();

            		// NEW 2017
            		importo_colf = Util_apapi.getImporto_colf(decorrenza_volontaria, periodicita, anno_servizio);

            		t_autorizzato_array = new boolean[t_periodo];
            		t_autorizzato_array = Util_apapi.get_t_array(da_limite, da, a, a_limite, periodicita, false, t_periodo);
            		t_autorizzato = Util_apapi.count_true(t_autorizzato_array);

            		// ------------------------------ copertura previdenziale obbligatoria
            		sql = new StringBuffer();
            		//                        1                    2               3
            		sql.append(
            		"SELECT PNS_periodi.ID_tp_periodo, PNS_periodi.dal, PNS_periodi.al ");
            		sql.append(" FROM PNS_periodi ");
            		sql.append("WHERE PNS_periodi.ID_domanda = ");
            		sql.append(IDdomanda);
            		doQuery(sql.toString());

            		t_coperto_array = new boolean[t_periodo];
            		boolean t_coperto_array_tmp[] = new boolean[t_periodo];

            		for (int j = 1; j <= records.getRows(); j++) {
            			try {
            				da = super.getCalendar(j,2);
            			} catch (Exception e) {
            				da = (Calendar)da_limite.clone();
            			}
            			try {
            				a  = super.getCalendar(j,3);
            			} catch (Exception e) {
            				a = (Calendar)a_limite.clone();
            			}
            			t_coperto_array_tmp = Util_apapi.get_t_array(da_limite, da, a, a_limite, periodicita, true, t_periodo);
            			t_coperto_array = Util_apapi.or(t_coperto_array, t_coperto_array_tmp);
            		}

            		t_assistenza_array = new boolean[t_periodo];
            		t_assistenza_arrayFINTA= new boolean[t_periodo];
            		
            		boolean t_assistenza_array_tmp[] = new boolean[t_periodo];

            		// --------------------- educazione e cura dei figli nel nucleo ICEF  
            		sql = new StringBuffer();
            		//                     1                        2                           3                         4                            5                          6
            		sql.append(
            		"SELECT Soggetti.data_nascita, PNS_familiari.nucleo_dal, PNS_familiari.nucleo_al, PNS_familiari.nucleo_dal_inv, PNS_familiari.nucleo_al_inv, PNS_familiari.invalido_no ");
            		sql.append(" FROM ((Dich_icef INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione) INNER JOIN PNS_familiari ON (Familiari.ID_dichiarazione = PNS_familiari.ID_dichiarazione) AND (Familiari.ID_domanda = PNS_familiari.ID_domanda) ");
            		sql.append("WHERE Familiari.ID_relazione_parentela IN ");
            		sql.append("("+pv_lista_IDRelazioneParentela_figli+")");             //Figli
            		sql.append(" AND Familiari.ID_domanda = ");
            		sql.append(IDdomanda);
            		doQuery(sql.toString());

            		t_assistenza_array_tmp = Util_apapi.get_array_figli(records, da_limite, a_limite, t_periodo, periodicita);
            		//cumula le assistenze
            		t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);


            		// -------------------- assistenza parenti non autosufficienti 
            		// -------------------- e educazione e cura dei figli non presenti nel nucleo ICEF
            		sql = new StringBuffer();
            		//                              1                               2                                3                               4                              5
            		sql.append(
            		"SELECT PNS_assistiti_dati.curaAss1Trim, PNS_assistiti_dati.curaAss2Trim, PNS_assistiti_dati.curaAss3Trim, PNS_assistiti_dati.curaAss4Trim, PNS_assistiti_dati.anno ");
            		sql.append(" FROM PNS_assistiti_dati ");
            		sql.append("WHERE PNS_assistiti_dati.ID_domanda = ");
            		sql.append(IDdomanda);
            		doQuery(sql.toString());

            		t_assistenza_array_tmp = Util_apapi.get_array_trimestri(records, da_limite, a_limite, t_periodo, periodicita);
            		//cumula le assistenze
            		t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);


            		// -------------------- eta richiedente ( NEW da 2007 ->> ) 
            		sql = new StringBuffer();
            		//                    1
            		sql.append(
            		"SELECT Soggetti.data_nascita, PNS_familiari.nucleo_dal, PNS_familiari.nucleo_al ");
            		sql.append(" FROM ((Dich_icef INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione) INNER JOIN PNS_familiari ON (Familiari.ID_dichiarazione = PNS_familiari.ID_dichiarazione) AND (Familiari.ID_domanda = PNS_familiari.ID_domanda) ");
            		sql.append("WHERE Familiari.ID_relazione_parentela IN ");
            		sql.append("("+pv_lista_IDRelazioneParentela_richiedente+")");     //Richiedente
            		sql.append(" AND Familiari.ID_domanda = ");
            		sql.append(IDdomanda);
            		doQuery(sql.toString());

            		t_assistenza_array_tmp = Util_apapi.get_array_ultra55(records, da_limite, a_limite, t_periodo, periodicita);
            		//cumula le assistenze con l'età del richiedente
            		t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);


            		// il periodo di assistenza è intersecato con il periodo autorizzato
            		t_assistenza_array = Util_apapi.and(t_assistenza_array, t_autorizzato_array);
            		t_assistenza = Util_apapi.count_true(t_assistenza_array);

            		// il periodo coperto è intersecato con il periodo di assistenza
            		t_coperto_array = Util_apapi.and(t_coperto_array, t_assistenza_array);
            		t_coperto = Util_apapi.count_true(t_coperto_array);

            		// si cumulano i valori per ogni singolo versamento
            		importo_tot = importo_tot + importo;
            		settimane_tot = settimane_tot + settimane;
            		mesi_tot = mesi_tot + mesi;
            		t_periodo_tot = t_periodo_tot + t_periodo;
            		t_autorizzato_tot = t_autorizzato_tot + t_autorizzato;
            		t_assistenza_tot = t_assistenza_tot + t_assistenza;
            		t_coperto_tot = t_coperto_tot + t_coperto;
            		
            		// barbieri in ferie --- io speriamo di capirci qualcosa ---- poi in ferie ci vado io
            		if(pv_IDServizio==Pv20_Params.pv_IDServizio_disoccupati){
            			t_assistenza_tot=t_periodo_tot;

            		}
            	}//end for
            	
            	
            	// inserimento dei valori nella hashtable
            	if (periodicita == 2)
            		inputValues.put("Settimane", new Double(1.0));
            	else
            		inputValues.put("Settimane", new Double(0.0));

            	inputValues.put("Check", new Double(check));

            	if (k == 1) {
                	inputValues.put("Anno", new Double(anni.getDouble(k, 1)));
                	inputValues.put("Importo", new Double(importo_tot));
                	inputValues.put("Importo_colf", new Double(importo_colf));
                	inputValues.put("T_versato", new Double(Math.max(settimane_tot,mesi_tot)));
                	inputValues.put("T_periodo", new Double(t_periodo_tot));
                	inputValues.put("T_autorizzato", new Double(t_autorizzato_tot));
                	inputValues.put("T_assistenza", new Double(t_assistenza_tot));
                	inputValues.put("T_coperto", new Double(t_coperto_tot));
            	} else {
                	inputValues.put("Anno"+k, new Double(anni.getDouble(k, 1)));
                	inputValues.put("Importo"+k, new Double(importo_tot));
                	inputValues.put("Importo_colf"+k, new Double(importo_colf));
                	inputValues.put("T_versato"+k, new Double(Math.max(settimane_tot,mesi_tot)));
                	inputValues.put("T_periodo"+k, new Double(t_periodo_tot));
                	inputValues.put("T_autorizzato"+k, new Double(t_autorizzato_tot));
                	inputValues.put("T_assistenza"+k, new Double(t_assistenza_tot));
                	inputValues.put("T_coperto"+k, new Double(t_coperto_tot));
            	}
            }
            
            session.setInitialized(true);
			session.setRecords( records );
			session.setAttribute("inputValues", inputValues);			
        } else {
			records = session.getRecords();
			inputValues = (Hashtable)session.getAttribute("inputValues");
        }
    }
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QRichiedente.class.getName(), IDdomanda, "records" );
    	inputValues = null;
    }
    
    /** QRichiedente constructor */
    public QRichiedente(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
