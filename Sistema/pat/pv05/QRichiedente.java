/**
 *Created on 23-mag-2006
 */
package c_elab.pat.pv05;

import java.text.DateFormat;
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
 */
public abstract class QRichiedente extends ElainNode {
    
	protected Hashtable 		 inputValues = new Hashtable();
	
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QRichiedente.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {

            super.init(dataTransfer);
            
        	int periodicita = -1;
        	int anno_servizio = 0;
        	int settimane = 0;
        	int settimane_tot = 0;
        	int mesi = 0;
        	int mesi_tot = 0;
        	double importo = 0.0;
        	double importo_tot = 0.0;
        	double importo_colf = 0.0;
        	int trimestre = 0;
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
        	Calendar trentuno_dic_1995  = Calendar.getInstance();
        	trentuno_dic_1995.set(1995,11,31,23,59);  //31/12/1995
        	int t_periodo = 0;
        	int t_periodo_tot = 0;
        	int t_autorizzato = 0;
        	int t_autorizzato_tot = 0;
        	int t_assistenza = 0;
        	int t_assistenza_tot = 0;
        	int t_coperto = 0;
        	int t_coperto_tot = 0;
        	boolean t_periodo_array[];
        	boolean t_autorizzato_array[];
        	boolean t_assistenza_array[];
        	boolean t_coperto_array[];
            int n_record = 0;
            int check = 0;
            
            // ------------------------------ conta versamenti
        	StringBuffer sql = new StringBuffer();
            //                         1
            sql.append(
            "SELECT PNS_versamenti.importo ");
            sql.append("FROM PNS_versamenti ");
            sql.append("WHERE PNS_versamenti.ID_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());
            n_record = records.getRows();
            
            // per ogni periodo di versamento
            for (int i = 1; i <= n_record; i++) {
            	
                // ------------------------------ versamenti
            	sql = new StringBuffer();
                //                         1                      2                        3                        4                          5                      6                       7
                sql.append(
                "SELECT PNS_versamenti.importo, PNS_versamenti.settimane, PNS_versamenti.mesi, PNS_versamenti.ID_riferimento, PNS_versamenti.anno, PNS_versamenti.dataVers, PNS_versamenti.scadenza ");
                sql.append("FROM PNS_versamenti ");
                sql.append("WHERE PNS_versamenti.ID_domanda = ");
                sql.append(IDdomanda);
                sql.append(" ORDER BY PNS_versamenti.ID_versamento");
                doQuery(sql.toString());

                settimane = super.getInteger(i,2);
	            mesi =  super.getInteger(i,3);
	            trimestre = super.getInteger(i,4) - 4; //TODO generalizzare per anni successivi!!
	            importo = super.getDouble(i,1);
	            anno_servizio = super.getInteger(i,5);
	            
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
	        	
	            try {
	                data_versamento = super.getCalendar(i,6);
	            } catch (Exception e) {
	            	data_versamento.set(2050,0,1);
	            }
	            try {
	                data_scadenza = super.getCalendar(i,7);
	            } catch (Exception e) {
	            	data_scadenza.set(2010,0,1);
	            }
	            if(data_versamento.after(data_scadenza))
	            	check = 11;  //escluso per versamento ritardato

//_________________________________ !!!!!!!!!!!!!!!	            
	            
	            
	            // inizializza il vettore del periodo (in settimane o mesi)
	        	t_periodo_array = new boolean[t_periodo];
	        	for (int j = 0; j < t_periodo; j++) {
	        		t_periodo_array[j] = true;
	        	}
	        	
	        	// ------------------------------ richiedente
	            sql = new StringBuffer();
	            //                              1                              2                            3                            4                              5                              6                                   7                             8                                 9
	            sql.append(
	            "SELECT PNS_richiedente.ID_tp_categoria, PNS_richiedente.etaPens_dal, PNS_richiedente.anniContr_dal, PNS_richiedente.resTAA_5anni, PNS_richiedente.resTAA_1anno, PNS_richiedente.entePrev_decorrenza, PNS_richiedente.entePrev_data, PNS_richiedente.no_previdenza, PNS_richiedente.pensDir_data ");
	            sql.append(" FROM PNS_richiedente ");
	            sql.append("WHERE PNS_richiedente.ID_domanda = ");
	            sql.append(IDdomanda);
	            doQuery(sql.toString());
	        	
	            if( !(records.getBoolean(1,4) || records.getBoolean(1,5)) )
	            	check = 2;  //escluso per mancanza requisiti residenza
	            
	            if( !(records.getBoolean(1,8)))
	            	check = 12;  //escluso per essere stato iscritto a forme di previdenza obbligatoria nel periodo dei versamenti
	            
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
	            
	            //TODO generalizzare: vanno messi in una tabella poichè cambiano ogni anno
	            if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	                if (periodicita == 2)  //dip
	                	importo_colf = 25.56;
	                else
	                	importo_colf = 92.56;
	            } else {
	                if (periodicita == 2)  //dip
	                	importo_colf = 21.36;
	                else
	                	importo_colf = 92.56;
	            }

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
	            boolean t_assistenza_array_tmp[] = new boolean[t_periodo];
	            
	        	// --------------------- educazione e cura dei figli nel nucleo ICEF  
	            sql = new StringBuffer();
	            //                     1                        2                           3                         4                            5                          6
	            sql.append(
	            "SELECT Soggetti.data_nascita, PNS_familiari.nucleo_dal, PNS_familiari.nucleo_al, PNS_familiari.nucleo_dal_inv, PNS_familiari.nucleo_al_inv, PNS_familiari.invalido_no ");
	            sql.append(" FROM ((Dich_icef INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione) INNER JOIN PNS_familiari ON (Familiari.ID_dichiarazione = PNS_familiari.ID_dichiarazione) AND (Familiari.ID_domanda = PNS_familiari.ID_domanda) ");
	            sql.append("WHERE Familiari.ID_relazione_parentela IN ");
	            sql.append("(262, 263, 264, 265, 266)");             //TODO generalizzare
	            sql.append(" AND Familiari.ID_domanda = ");
	            sql.append(IDdomanda);
	            doQuery(sql.toString());

	            t_assistenza_array_tmp = Util_apapi.get_array_figli(records, da_limite, a_limite, t_periodo, periodicita);
	        	//cumula le assistenze
	        	t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);

	        	
	        	// -------------------- assistenza parenti non autosufficienti 
	            // -------------------- e educazione e cura dei figli non presenti nel nucleo ICEF
	            sql = new StringBuffer();
	            //                      1                          2                      3                         4
	            sql.append(
	            "SELECT PNS_assistiti.curaAss1Trim, PNS_assistiti.curaAss2Trim, PNS_assistiti.curaAss3Trim, PNS_assistiti.curaAss4Trim ");
	            sql.append(" FROM PNS_assistiti ");
	            sql.append("WHERE PNS_assistiti.ID_domanda = ");
	            sql.append(IDdomanda);
	            doQuery(sql.toString());

	            t_assistenza_array_tmp = Util_apapi.get_array_trimestri(records, da_limite, a_limite, t_periodo, periodicita, anno_servizio);
	        	//cumula le assistenze
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
            }
            
            // inserimento dei valori nella hashtable
            inputValues.put("Check", new Double(check));
            inputValues.put("Importo", new Double(importo_tot));
            if (periodicita == 2)
            	inputValues.put("Settimane", new Double(1.0));
            else
            	inputValues.put("Settimane", new Double(0.0));
            inputValues.put("Importo_colf", new Double(importo_colf));
            inputValues.put("T_versato", new Double(Math.max(settimane_tot,mesi_tot)));
            inputValues.put("T_periodo", new Double(t_periodo_tot));
            inputValues.put("T_autorizzato", new Double(t_autorizzato_tot));
            inputValues.put("T_assistenza", new Double(t_assistenza_tot));
            inputValues.put("T_coperto", new Double(t_coperto_tot));
            
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
