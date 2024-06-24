package c_elab.pat.ass07;

import java.util.Calendar;

/** 
 *Created on 11-ott-2005  
 */

/**
 * legge tutti i dati per il calcolo dell'assegno 
 * 
 * @author g_barbieri 
 */
public class Util {

	// NB - dipendono dal servizio!!!
	public static String getCodiceRichiedente() {
		return "323";		
	}
	public static String getCodiceConiuge() {
		return "324";		
	}
	public static String getCodiceConvivente_gen() {
		return "325";		
	}
	public static String getCodiceConvivente_no_gen() {
		return "326";		
	}
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(2006, 11, 31, 23, 59); // 31 dic 2006
		return data_transizione; 
	}
}