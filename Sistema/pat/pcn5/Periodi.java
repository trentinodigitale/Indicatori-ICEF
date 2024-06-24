/**
 *Created on 23-mag-2006
 */

package c_elab.pat.pcn5;

import it.clesius.apps2core.ElainNode;
import java.util.ArrayList;

import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;
import it.clesius.db.util.Table;
import it.clesius.util.General1;

import java.util.Calendar;
import java.util.GregorianCalendar;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import c_elab.pat.ass11.Q_assegno;

/**
  */
public class Periodi extends ElainNode {
	protected  Table ANTICRISI_sospensioni;
	protected  Table ANTICRISI_periodi;
	private int mesi_massimi_contributo=28; 
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( Periodi.class.getName(), IDdomanda, "records" );
		
		ANTICRISI_sospensioni=null;
		ANTICRISI_periodi = null;
 	}

	public void init(RunawayData dataTransfer) {
	
	    super.init(dataTransfer);
	    ElabStaticSession session =  ElabStaticContext.getInstance().getSession( Periodi.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {

	    
	    
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT periodo_dal, periodo_al,ore_cassa_integrazione,periodo,ID_tp_periodo,agenzia_lavoro ");
			sql.append("FROM  ANTICRISI_periodi ");
			sql.append("WHERE ID_domanda =  ");
			sql.append(IDdomanda);
			sql.append("ORDER BY periodo_dal ");		
			
			doQuery(sql.toString());
			ANTICRISI_periodi = records;
		}	
       
	}
	
	/**
	 * 
	 * CICLO PRIMA SUI PERIODI POI SUI MESI PER PERIODO
	 * il numero di mesi è dato dalla somma dei mesi di disoccupazione, iscrizione alle liste dei mobilità e dai mesi di cassa integrazione.
	 * I mesi di disoccupazione e di mobilità sono dati dal numero di giorni di disoccupazione o iscrizione alla mobilità meno i giorni di sospensionde diviso 30 e il resto è considerato un mese se pari o superiore a 16 giorni.
	 * I mesi di cassa integrazione sono dati dal numero di ore di cassa integrazione diviso 173.
	 * I mesi così ottenuti vengono moltiplicati per l'importo mensile
	 */
	
	public double getValue() {
		try {
        	int totGiorni=0;
        	int totGiorniCassaIntegrazione=0;
        	int totGiorniSospensione=0;
        	if(ANTICRISI_periodi!=null && ANTICRISI_periodi.getRows()>0){
	        	for(int i=1; i<=ANTICRISI_periodi.getRows(); i++){
	        		
	        		String data_dal = (String) ANTICRISI_periodi.getElement(i, 1);
	        		String data_al = (String) ANTICRISI_periodi.getElement(i, 2);
	        		int  ID_tp_periodo =  ANTICRISI_periodi.getInteger(i, 5);
	        		int giorni=0;
	        		int giorni_cassa_integrazione=0;
	        		int sospensione_periodo =  ANTICRISI_periodi.getInteger(i, 4);
	        			if(ANTICRISI_periodi.getElement(i, 3)!=null && !ANTICRISI_periodi.getElement(i, 3).equals("") && ID_tp_periodo==3){
	        			giorni_cassa_integrazione =  ANTICRISI_periodi.getInteger(i, 3);
	        		}else{
		        		GregorianCalendar dal = General1.getStringToCalendar(DateTimeFormat.toItDate(data_dal));
		        		GregorianCalendar al = General1.getStringToCalendar(DateTimeFormat.toItDate(data_al));
		        	
		        		giorni=(int)getGiorni(dal, al);
		        	}
	        		totGiorniCassaIntegrazione=totGiorniCassaIntegrazione+giorni_cassa_integrazione;
	        		totGiorni=giorni+totGiorni;
	        		
	        		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( Q_assegno.class.getName(), IDdomanda, "records" );
	        		if (!session.isInitialized()) {

						StringBuffer lQuery = new StringBuffer();
						lQuery.append("SELECT sospensione_dal, sospensione_al ");
						lQuery.append(" FROM ANTICRISI_sospensioni WHERE (ID_domanda =");
						lQuery.append(IDdomanda);
						lQuery.append(") AND (periodo=");
						lQuery.append(sospensione_periodo);
						lQuery.append(") ORDER BY sospensione_dal;");
	
						doQuery(lQuery.toString());
						ANTICRISI_sospensioni = records;
	        		}
					if(ANTICRISI_sospensioni!=null && ANTICRISI_sospensioni.getRows()>0){
						for(int Z=1; Z<=ANTICRISI_sospensioni.getRows(); Z++){
			        		
			        		String sospensione_data_dal = (String) ANTICRISI_sospensioni.getElement(Z, 1);
			        		String sospensione_data_al = (String) ANTICRISI_sospensioni.getElement(Z, 2);
			        	
			        		int sospensione_giorni=0;
			        		GregorianCalendar sospensione_dal = General1.getStringToCalendar(DateTimeFormat.toItDate(sospensione_data_dal));
			        		GregorianCalendar sospensione_al = General1.getStringToCalendar(DateTimeFormat.toItDate(sospensione_data_al));
		        	
			        		if(sospensione_dal!=null && sospensione_al!=null){
				        		
				        		sospensione_giorni=(int)getGiorni(sospensione_dal, sospensione_al);
			        		}
			        		totGiorniSospensione=sospensione_giorni+totGiorniSospensione;
						}
					}
				}
        	}
        	int risultato_cassa_integrazione=0;
        	
        	int risultato=(totGiorni-totGiorniSospensione)/30;
        	int resto=(totGiorni-totGiorniSospensione)%30;
        	
        	
        	if(resto>16){risultato=risultato+1;}
        	int mesi_max=mesi_massimi_contributo-risultato;
        	if(totGiorniCassaIntegrazione>0){
        		risultato_cassa_integrazione=Math.min(mesi_max, totGiorniCassaIntegrazione/173);        		
        	}
        	return risultato+risultato_cassa_integrazione;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
        
       
    }
	
	/**
	 *  Calcola la differenza tra due date compresi gli estremi
	 * @param dal
	 * @param al
	 * @return
	 */
	public double getGiorni(GregorianCalendar dal, GregorianCalendar al){
		
		//Differenza in giorni tra due date
		long milliseconds1 = dal.getTimeInMillis();
		long milliseconds2 = al.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		double diffDays = diff / (24 * 60 * 60 * 1000); //
		return  diffDays+1;
	}
	
	
}