/**
 *Created on 23-mag-2006
 */
package c_elab.pat.pc11; 

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
	
	//CAMBIAMI: va cambiata ogni anno - fatto
	int pc_IDServizio = 9350;
	int pc_IDRiferimento_gennaio = 160; //vedi mese di gennaio nella tabella PNS_tp_riferimenti per il servizio in questione
	String pc_lista_IDRelazioneParentela_figli = "6064, 6065, 6066, 6067, 6068"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	String pc_lista_IDRelazioneParentela_parenti = "6069, 6070, 6071, 6072"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	String pc_lista_IDRelazioneParentela_richiedente = "6060"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	
    protected Hashtable 		 inputValues = new Hashtable();
	
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData 
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QRichiedente.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {

            super.init(dataTransfer);

            int check = 0;
            int n_anni = 0;
            Table anni;
        	int t_autorizzato = 0;
        	int t_autorizzato_tot = 0;
        	int t_assistenza_tot = 0;
        	int t_coperto_tot = 0;
        	int t_accettato_tot = 0;
        	int t_versato_tot = 0;
        	double importo_tot = 0.0;

            
            inputValues.clear();
            
            // ------------------------------ conta anni
            StringBuffer sql = new StringBuffer();
            //                         1
            sql.append(
            "SELECT DISTINCT PNS_versamenti.anno ");
            sql.append("FROM PNS_versamenti ");
            sql.append("WHERE PNS_versamenti.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(" ORDER BY PNS_versamenti.anno DESC ");
            doQuery(sql.toString());
            anni = records;
            n_anni = anni.getRows();
            
            if (n_anni > 3) {
            	n_anni = 3;
    			System.out.println("ERRORE GRAVE: non possono essere gestiti piu' di 3 anni di contributi da c_elab.pat.pc08.QRichiedente");
            }

            // per ogni anno di versamento
            for (int k = 1; k <= n_anni; k++) {
            
	        	int giorni = 0;
	        	double importo = 0.0;
            	double importo_anno_tot = 0.0;
	        	Calendar da = Calendar.getInstance();
	        	Calendar a  = Calendar.getInstance();
	        	Calendar da_limite = Calendar.getInstance();
	        	Calendar a_limite  = Calendar.getInstance();
	        	Calendar pensione_diretta  = Calendar.getInstance();
	        	GregorianCalendar cal = new GregorianCalendar();
	        	int t_assistenza = 0;
            	int t_assistenza_anno_tot = 0;
	        	int t_coperto = 0;
            	int t_coperto_anno_tot = 0;
	        	int t_accettato = 0;
	        	int t_accettato_anno_tot = 0;
	        	int t_versato = 0;
	        	int t_versato_anno_tot = 0;
	        	int riferimento = 0;
	        	boolean t_assistenza_array[];
	        	boolean t_coperto_array[];
	        	boolean t_accettato_array[];
	        	boolean t_versato_array[];
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
		            //                         1                      2                            3
		            sql.append(
		            "SELECT PNS_versamenti.importo, PNS_versamenti.ID_riferimento, PNS_versamenti.anno ");
		            sql.append("FROM PNS_versamenti ");
		            sql.append("WHERE PNS_versamenti.ID_domanda = ");
		    		sql.append(IDdomanda);
		        	sql.append(" AND PNS_versamenti.anno = ");
		        	sql.append(anno_servizio);
		    		sql.append(" ORDER BY PNS_versamenti.ID_versamento");
		    		doQuery(sql.toString());
		
		        	// trova i giorni totali dell'anno del servizio 
		        	if (cal.isLeapYear(anno_servizio))
		       			giorni = 366;
		        	else
		    			giorni = 365;
		            
	            	importo = super.getDouble(i, 1);
	            	
		        	// inizializza il vettore del periodo in giorni
		        	t_versato_array = new boolean[giorni];
	
		            if (servizio==pc_IDServizio)
		                riferimento = super.getInteger(i, 2) - (pc_IDRiferimento_gennaio - 1);
		            else
		            	System.out.println("ERRORE GRAVE: il servizio " + servizio + " non puo' essere gestito da c_elab.pat.pc08.QRichiedente");
	                
		            if (riferimento == 13) {  //tutto l'anno
	                	for (int j = 0; j < giorni; j++) {
	                		t_versato_array[j] = true;  //versa per tutti i giorni dell'anno
	                	}
	                } else {  // singoli mesi
	                	cal.set(anno_servizio, riferimento - 1, 1);
	                	int ini = cal.get(Calendar.DAY_OF_YEAR) - 1;
	                	int end = ini + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	                	for (int j = ini; j < end; j++) {
	                		t_versato_array[j] = true;  //versa per tutti i giorni del mese corrente
	                	}
	                }
		            t_versato = Util_apapi.count_true(t_versato_array);
		            t_accettato_array = t_versato_array;
		            
		            // inizializzazione estremi dei periodi
		    		da.set(anno_servizio, 0, 1, 0, 0);				// 01/01
					a.set(anno_servizio, 11, 31, 23, 59);			// 31/12
		    		da_limite.set(anno_servizio, 0, 1, 0, 0);		// 01/01
					a_limite.set(anno_servizio, 11, 31, 23, 59);	// 31/12
					
		    		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da.getTime()));
		    		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a.getTime()));
		        	
		        	// ------------------------------ richiedente
		            sql = new StringBuffer();
		            //                          1                              2                            3                           4                                 5
		            sql.append(
		            "SELECT PNS_richiedente.pensDir_data, PNS_richiedente.resTAA_5anni, PNS_richiedente.resTAA_1anno, PNS_richiedente.no_previdenza, PNS_richiedente.esclusa_ufficio ");
		            sql.append(" FROM PNS_richiedente ");
		            sql.append("WHERE PNS_richiedente.ID_domanda = ");
		            sql.append(IDdomanda);
		            doQuery(sql.toString());
		
		            if( !(records.getBoolean(1,2) || records.getBoolean(1,3)) )
		            	check = 2;  //escluso per mancanza requisiti residenza
		            
		            if( !(records.getBoolean(1,4)))
		            	check = 12;  //escluso per essere stato iscritto a forme di previdenza obbligatoria nel periodo dei versamenti
		
		            // NEW 2007 -->>
		            if( records.getBoolean(1,5) )
		            	check = 14;  //escluso d'ufficio
		            
		        	t_autorizzato = giorni;
		
		            try {
		            	pensione_diretta = super.getCalendar(1,1);
		            } catch (Exception e) {
		        		pensione_diretta = (Calendar)a_limite.clone();
		        		pensione_diretta.add(Calendar.DAY_OF_YEAR, 1);
		            }
		        	
		        	// trova il periodo di autorizzazione
		        	// esiste solo la fine (data pensione)
		        	// poichè non c'è un inizio di autorizzazione
		        	if (pensione_diretta.get(Calendar.YEAR) == anno_servizio) {
		        		t_autorizzato = pensione_diretta.get(Calendar.DAY_OF_YEAR) - 1;            		
		            	for (int j = t_autorizzato; j < giorni; j++) {
		            		// a partire dalla data di pensionamento
		            		// la 'autorizzazione è false
		            		t_accettato_array[j] = false; 
		            	}
		        	}
		
		    		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da.getTime()));
		    		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a.getTime()));
		
		        	// ------------------------------ copertura previdenziale obbligatoria
		            sql = new StringBuffer();
		            //                        1                    2               3
		            sql.append(
		            "SELECT PNS_periodi.ID_tp_periodo, PNS_periodi.dal, PNS_periodi.al ");
		            sql.append(" FROM PNS_periodi ");
		            sql.append("WHERE PNS_periodi.ID_domanda = ");
		            sql.append(IDdomanda);
		            doQuery(sql.toString());
		
		            t_coperto_array = new boolean[giorni];
		            boolean t_coperto_array_tmp[] = new boolean[giorni];
		            
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
			            
			            if (!Util_apapi.check_date(da_limite, da, a, a_limite)) {
			        		if (da.before(da_limite)) {
			        			da = (Calendar)da_limite.clone();
			        		}
			        		if (a.after(a_limite)) {
			        			a = (Calendar)a_limite.clone();
			        		}
				            // determina i giorni di copertura
			            	for (int m = da.get(Calendar.DAY_OF_YEAR) - 1; m < a.get(Calendar.DAY_OF_YEAR); m++) {
			            		t_coperto_array_tmp[m] = true;
			            	}
			            }
			        	t_coperto_array = Util_apapi.or(t_coperto_array, t_coperto_array_tmp);
		            }
		            
		            t_assistenza_array = new boolean[giorni];
		            boolean t_assistenza_array_tmp[] = new boolean[giorni];
		            
		        	// --------------------- educazione e cura dei figli nel nucleo ICEF  
		            sql = new StringBuffer(); 
		            //                     1                        2                           3                         4                            5                          6
		            sql.append(
		            "SELECT Soggetti.data_nascita, PNS_familiari.nucleo_dal, PNS_familiari.nucleo_al, PNS_familiari.nucleo_dal_inv, PNS_familiari.nucleo_al_inv, PNS_familiari.invalido_no ");
		            sql.append(" FROM ((Dich_icef INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione) INNER JOIN PNS_familiari ON (Familiari.ID_dichiarazione = PNS_familiari.ID_dichiarazione) AND (Familiari.ID_domanda = PNS_familiari.ID_domanda) ");
		            sql.append("WHERE Familiari.ID_relazione_parentela IN ");
		            sql.append("("+pc_lista_IDRelazioneParentela_figli+")");             //Figli
		            sql.append(" AND Familiari.ID_domanda = ");
		            sql.append(IDdomanda);
		            doQuery(sql.toString());
		
		            t_assistenza_array_tmp = Util_apapi.get_array_figli(records, da_limite, a_limite, giorni, 1);
		        	//cumula le assistenze
		        	t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);
		
		        	
		            // -------------------- educazione e cura dei figli non presenti nel nucleo ICEF
		            sql = new StringBuffer();
		            //                   1                                        2                                   3               	                   4                          5                            6
		            sql.append(
		            "SELECT Soggetti.data_nascita, PNS_assistiti_dati.educazAssistenza_dal, PNS_assistiti_dati.educazAssistenza_al, PNS_assistiti_dati.inv_dal, PNS_assistiti_dati.inv_al, PNS_assistiti_dati.invalido_no ");
		            sql.append(" FROM PNS_assistiti_dati INNER JOIN Soggetti ON PNS_assistiti_dati.ID_soggetto = Soggetti.ID_soggetto INNER JOIN PNS_assistiti ON PNS_assistiti_dati.ID_domanda = PNS_assistiti.ID_domanda ");
		            sql.append("WHERE PNS_assistiti.ID_relazioneParentela IN ");
		            sql.append("("+pc_lista_IDRelazioneParentela_figli+")");             //Figli
		            sql.append(" AND PNS_assistiti_dati.ID_domanda = ");
		            sql.append(IDdomanda);
		            doQuery(sql.toString());
		
		            t_assistenza_array_tmp = Util_apapi.get_array_figli(records, da_limite, a_limite, giorni, 1);
		        	//cumula le assistenze
		        	t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);
		
		        	
		        	// -------------------- assistenza parenti non autosufficienti 
		            sql = new StringBuffer();
		            //                             1                          2
		            sql.append(
		            "SELECT PNS_assistiti_dati.educazAssistenza_dal, PNS_assistiti_dati.educazAssistenza_al ");
		            sql.append(" FROM PNS_assistiti_dati INNER JOIN PNS_assistiti ON PNS_assistiti_dati.ID_domanda = PNS_assistiti.ID_domanda ");
		            sql.append("WHERE PNS_assistiti.ID_relazioneParentela IN ");
		            sql.append("("+pc_lista_IDRelazioneParentela_parenti+")");       //Parenti
		            sql.append(" AND PNS_assistiti_dati.ID_domanda = ");
		            sql.append(IDdomanda);
		            doQuery(sql.toString());
		            for (int j = 1; j <= records.getRows(); j++) {
			            try {
			            	da = super.getCalendar(j,1);
			            } catch (Exception e) {
			            	da = (Calendar)da_limite.clone();
			            }
			            try {
			            	a  = super.getCalendar(j,2);
			            } catch (Exception e) {
			            	a = (Calendar)a_limite.clone();
			            }
			            if (!Util_apapi.check_date(da_limite, da, a, a_limite)) {
			        		if (da.before(da_limite)) {
			        			da = (Calendar)da_limite.clone();
			        		}
			        		if (a.after(a_limite)) {
			        			a = (Calendar)a_limite.clone();
			        		}
			            	// riempie il periodo di assitenza per l'i-seimo familiare
			            	for (int m = da.get(Calendar.DAY_OF_YEAR) - 1; m < a.get(Calendar.DAY_OF_YEAR); m++) {
			            		t_assistenza_array_tmp[m] = true;
			            	}
			            }
		            	//cumula le assistenze
		            	t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);
		            }
		
		        	// -------------------- eta richiedente ( NEW da 2007 ->> ) 
		            sql = new StringBuffer();
		            //                    1
		            sql.append(
		            "SELECT Soggetti.data_nascita, PNS_familiari.nucleo_dal, PNS_familiari.nucleo_al ");
		            sql.append(" FROM ((Dich_icef INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione) INNER JOIN PNS_familiari ON (Familiari.ID_dichiarazione = PNS_familiari.ID_dichiarazione) AND (Familiari.ID_domanda = PNS_familiari.ID_domanda) ");
		            sql.append("WHERE Familiari.ID_relazione_parentela IN ");
		            sql.append("("+pc_lista_IDRelazioneParentela_richiedente+")");  	//Richiedente
		            sql.append(" AND Familiari.ID_domanda = ");
		            sql.append(IDdomanda);
		            doQuery(sql.toString());
		            
		            t_assistenza_array_tmp = Util_apapi.get_array_ultra55(records, da_limite, a_limite, giorni, 1);
		        	//cumula le assistenze con l'età del richiedente
		        	t_assistenza_array = Util_apapi.or(t_assistenza_array, t_assistenza_array_tmp);
		            
		        	// il periodo di assistenza è intersecato con il periodo accettato
		            t_assistenza_array = Util_apapi.and(t_assistenza_array, t_accettato_array);
		            t_assistenza = Util_apapi.count_true(t_assistenza_array);
		        	
		        	// il periodo coperto è intersecato con il periodo di assistenza
		        	t_coperto_array = Util_apapi.and(t_coperto_array, t_assistenza_array);
		        	t_coperto = Util_apapi.count_true(t_coperto_array);
		
		        	// il periodo accettato è la differenza tra il periodo di assistenza e quello coperto
		        	t_accettato = t_assistenza - t_coperto;

		        	
            		// si cumulano i valori per ogni singolo versamento
            		importo_anno_tot = importo_anno_tot + importo;
            		t_assistenza_anno_tot = t_assistenza_anno_tot + t_assistenza;
            		t_coperto_anno_tot = t_coperto_anno_tot + t_coperto;
            		t_versato_anno_tot = t_versato_anno_tot + t_versato;
            		t_accettato_anno_tot = t_accettato_anno_tot + t_accettato;
		        	
	            }
            	
        		t_autorizzato_tot = t_autorizzato_tot + t_autorizzato;
        		
        		importo_tot = importo_tot + importo_anno_tot;
        		t_assistenza_tot = t_assistenza_tot + t_assistenza_anno_tot;
        		t_coperto_tot = t_coperto_tot + t_coperto_anno_tot;
        		t_versato_tot = t_versato_tot + t_versato_anno_tot;
        		t_accettato_tot = t_accettato_tot + t_accettato_anno_tot;
	        	
            	inputValues.put("Check", new Double(check));

            	// inserimento dei valori nella hashtable

	            //if (k == 1) {
                	inputValues.put("Anno", new Double(anni.getDouble(k, 1)));
                	inputValues.put("Importo", new Double(importo_tot));
                	inputValues.put("T_versato", new Double(t_versato_tot));
                	inputValues.put("T_autorizzato", new Double(t_autorizzato_tot));
                	inputValues.put("T_assistenza", new Double(t_assistenza_tot));
                	inputValues.put("T_coperto", new Double(t_coperto_tot));
    	            inputValues.put("T_accettato", new Double(t_accettato_tot));
            	//} else {
                //	inputValues.put("Anno"+k, new Double(anni.getDouble(k, 1)));
    	        //	inputValues.put("Importo"+k, new Double(importo_tot));
    	        //	inputValues.put("T_versato"+k, new Double(t_versato_tot));
    	        //	inputValues.put("T_autorizzato"+k, new Double(t_autorizzato_tot));
    	        //	inputValues.put("T_assistenza"+k, new Double(t_assistenza_tot));
                //	inputValues.put("T_coperto"+k, new Double(t_coperto_tot));
    	        //    inputValues.put("T_accettato"+k, new Double(t_accettato_tot));
            	//}
            
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
