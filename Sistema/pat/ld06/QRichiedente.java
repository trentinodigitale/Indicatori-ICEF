/**
 *Created on 23-mag-2006 
 */
package c_elab.pat.ld06;

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
        	int mesi = 0;
        	int trimestre = 0;
        	double importo = 0.0;
        	double importo_max = 0.0;
        	int tipo_autorizzazione = 0;
        	int check = 0;
        	Calendar da = Calendar.getInstance();
        	Calendar a  = Calendar.getInstance();
        	Calendar da_limite = Calendar.getInstance();
        	Calendar a_limite  = Calendar.getInstance();
        	Calendar data_versamento  = Calendar.getInstance();
        	Calendar data_scadenza  = Calendar.getInstance();
        	Calendar pensione_diretta  = Calendar.getInstance();
        	Calendar eta_pensionabile = Calendar.getInstance();
        	int t_trimestre = 0;
        	int t_autorizzato = 0;
        	int t_disoccupato = 0;
        	int t_coperto = 0;
        	boolean t_trimestre_array[];
        	boolean t_autorizzato_array[];
        	boolean t_disoccupato_array[];
        	boolean t_coperto_array[];
            
            // ------------------------------ versamenti
        	StringBuffer sql = new StringBuffer();
            //                         1                      2                        3                        4                            5                       6                       7
            sql.append(
            "SELECT PNS_versamenti.importo, PNS_versamenti.settimane, PNS_versamenti.mesi, PNS_versamenti.ID_riferimento, PNS_versamenti.dataVers, PNS_versamenti.scadenza, PNS_versamenti.anno ");
            sql.append("FROM PNS_versamenti ");
            sql.append("WHERE PNS_versamenti.ID_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());
            
            // si efefttua un solo versamento per domanda e riguarda un trimestre
            // dell'anno di riferimento
            settimane = super.getInteger(1,2);
            mesi =  super.getInteger(1,3);
            trimestre = super.getInteger(1,4);
            importo = super.getDouble(1,1);
            anno_servizio = super.getInteger(1,7);
            
            try {
                data_versamento = super.getCalendar(1,5);
            } catch (Exception e) {
            	data_versamento.set(2050,0,1);
            }
            try {
                data_scadenza = super.getCalendar(1,6);
            } catch (Exception e) {
            	data_scadenza.set(2010,0,1);
            }
            if(data_versamento.after(data_scadenza))
            	check = 11;  //escluso per versamento ritardato
            
            // la valutazione settimane-mesi è fatta
            // in base all'input nei rispettivi campi
            // e non in base alla combo dipendente-autonomo
            if ( settimane > mesi )
            	periodicita = 2;
            else
            	periodicita = 3;
            
            if (servizio == 9105 )
            	trimestre = trimestre - 22;  	//TODO sistemare per servizi anni successivi al 2006
            									//vedi PNS_tp_riferimenti
            
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
        	} else {
        		da.set(anno_servizio, 9, 1, 0, 0);				// 01/10
    			a.set(anno_servizio, 11, 31, 23, 59);			// 31/12
        		da_limite.set(anno_servizio, 9, 1, 0, 0);		// 01/10
    			a_limite.set(anno_servizio, 11, 31, 23, 59);	// 31/12
        	}

    		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da.getTime()));
    		//System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a.getTime()));
        	
        	// calcola la lunghezza del periodo in settimane o mesi
        	if (periodicita == 2)
	        	// calcola il n. di sabati tra i limiti del periodo 
        		t_trimestre = Util_apapi.getSettimane(da_limite,a_limite);
			else
				t_trimestre = 3;
        	
        	// inizializza il vettore del periodo (in settimane o mesi)
        	t_trimestre_array = new boolean[t_trimestre];
        	for (int i = 0; i < t_trimestre; i++) {
        		t_trimestre_array[i] = true;
        	}
        	
        	// ------------------------------ richiedente
            sql = new StringBuffer();
            //                              1                              2                            3                            4                              5                             6
            sql.append(
            "SELECT PNS_richiedente.ID_contribuente, PNS_richiedente.copPrev_dal, PNS_richiedente.pensDir_data, PNS_richiedente.etaPens_dal, PNS_richiedente.resTAA_5anni, PNS_richiedente.resTAA_1anno ");
            sql.append(" FROM PNS_richiedente ");
            sql.append("WHERE PNS_richiedente.ID_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());

            if( !(records.getBoolean(1,5) || records.getBoolean(1,6)) )
            	check = 2;  //escluso per mancanza requisiti residenza
            
            tipo_autorizzazione = super.getInteger(1,1);
            if (tipo_autorizzazione == 1)
            	importo_max = 1780.0;
            else
            	importo_max = 1080.0;
            
            // calcola le date di inzio/fine dell'autorizzazione
            // se sono vuote corrispondono alle date limite del periodo
           try {
            	da = super.getCalendar(1,2);
            } catch (Exception e) {
            	da = (Calendar)da_limite.clone();
            }
            
            try {
            	pensione_diretta = super.getCalendar(1,3);
            } catch (Exception e) {
        		pensione_diretta = (Calendar)a_limite.clone();
        		pensione_diretta.add(Calendar.DAY_OF_YEAR, 1);
            }
            try {
            	eta_pensionabile = super.getCalendar(1,4);
            } catch (Exception e) {
        		eta_pensionabile = (Calendar)a_limite.clone();
        		eta_pensionabile.add(Calendar.DAY_OF_YEAR, 1);
            }

            // il Calendar a è il minimo delle due date
            a = (Calendar)pensione_diretta.clone();
            if(eta_pensionabile.before(pensione_diretta))
            	a = (Calendar)eta_pensionabile.clone();
            
        	t_autorizzato_array = new boolean[t_trimestre];
        	t_autorizzato_array = Util_apapi.get_t_array(da_limite, da, a, a_limite, periodicita, false, t_trimestre);
        	t_autorizzato = Util_apapi.count_true(t_autorizzato_array);
        	

        	// ------------------------------ disoccupazione e copertura previdenziale obbligatoria
            sql = new StringBuffer();
            //                        1                    2               3
            sql.append(
            "SELECT PNS_periodi.ID_tp_periodo, PNS_periodi.dal, PNS_periodi.al ");
            sql.append(" FROM PNS_periodi ");
            sql.append("WHERE PNS_periodi.ID_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());

            // disoccupazione senza contributi: è il periodo da finanziare
            t_disoccupato_array = new boolean[t_trimestre];
            // copertura previdenziale obbligatoria: va tolto dal periodo da finanziare
            t_coperto_array = new boolean[t_trimestre];
            boolean t_disoccupato_array_tmp[] = new boolean[t_trimestre];
            boolean t_coperto_array_tmp[] = new boolean[t_trimestre];
            
            for (int i = 1; i <= records.getRows(); i++) {
	            try {
	            	da = super.getCalendar(i,2);
	            } catch (Exception e) {
	            	da = (Calendar)da_limite.clone();
	            }
	            try {
	            	a  = super.getCalendar(i,3);
	            } catch (Exception e) {
	            	a = (Calendar)a_limite.clone();
	            }
	            //TODO sistemare per servizi anni successivi al 2006
            	if ( super.getInteger(i,1) == 1 || super.getInteger(i,1) == 8 ) { // disoccupazione
    	            t_disoccupato_array_tmp = Util_apapi.get_t_array(da_limite, da, a, a_limite, periodicita, false, t_trimestre);
    	        	t_disoccupato_array = Util_apapi.or(t_disoccupato_array, t_disoccupato_array_tmp);
            	} else { // copertura previdenziale obbligatoria
    	            t_coperto_array_tmp = Util_apapi.get_t_array(da_limite, da, a, a_limite, periodicita, true, t_trimestre);
    	        	t_coperto_array = Util_apapi.or(t_coperto_array, t_coperto_array_tmp);
            	}
            }
        	
        	// il periodo di disoccupazione è intersecato con il periodo autorizzato
        	t_disoccupato_array = Util_apapi.and(t_disoccupato_array, t_autorizzato_array);
        	t_disoccupato = Util_apapi.count_true(t_disoccupato_array);
        	
        	// il periodo di copertura è intersecato con il periodo di disoccupazione
        	t_coperto_array = Util_apapi.and(t_coperto_array, t_disoccupato_array);
        	t_coperto = Util_apapi.count_true(t_coperto_array);

            // inserimento dei valori nella hashtable
            inputValues.put("Check", new Double(check));
            inputValues.put("Importo", new Double(importo));
            inputValues.put("Importo_max", new Double(importo_max));
            inputValues.put("T_versato", new Double(Math.max(settimane,mesi)));
            inputValues.put("T_trimestre", new Double(t_trimestre));
            inputValues.put("T_autorizzato", new Double(t_autorizzato));
            inputValues.put("T_disoccupato", new Double(t_disoccupato));
            inputValues.put("T_coperto", new Double(t_coperto));
            
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
