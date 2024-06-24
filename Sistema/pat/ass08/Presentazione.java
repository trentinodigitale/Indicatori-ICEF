/**
 *Created on 05-dic-2005
 */

package c_elab.pat.ass08; 

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

import java.util.Calendar;



public class Presentazione extends ElainNode{

	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		//                 1
		sql.append("SELECT data_inizio_beneficio");
		sql.append(" FROM ANF_data_inizio_beneficio WHERE (ID_domanda =");
		sql.append(IDdomanda);
		sql.append(")");
		
		doQuery(sql.toString());

	}
	
	public double getValue() {
		/*
		 * nota bene: solo per ANF08 in origine si passava sempre 1 (contributo retroattivo da gennaio).
		 * Con questa scelta però non era più possibile inserire le doppie domande in caso di decesso del richedente.
		 * Dal memento che ANF_data_inizio_beneficio.data_inizio_beneficio è stato introdotto a partire da ANF09
		 * si è utilizzato questo campo per trattare questi casi.
		 * In particolare se data_inizio_beneficio è vuoto ritorna 1 come prima
		 * Invece se data_inizio_beneficio è compilato si prende questa data come data inizio beneficio per la 
		 * seconda domanda (quella senza la persona deceduta). Il campo data_inizio_beneficio viene compilato da APAPI
		 * mediante il modulo creato da Alessandra che serve per variare la data di inizio beneficio.
		*/
		if (records == null)
			return 0.0; 

		if (records.getRows() == 0)
			return 1.0; 

		// NB l'idoneità alla data di presentazione si valuta il mese successivo
		try {
			Calendar data_dom = c_elab.pat.Util_apapi.stringdate2date((String)(records.getElement(1, 1)));
			if ( data_dom.get(Calendar.YEAR)== 2007 )
				return 0; //NB
			else {
				int dd = data_dom.get(Calendar.MONTH) + 2; //NB
				if (dd == 13) dd = 1;
				return dd; 
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}