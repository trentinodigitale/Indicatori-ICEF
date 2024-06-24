package c_elab.pat.ass09;

import java.util.Calendar;

/** 
 *Created on 11-ott-2005  
 */

/** 
 * legge tutti i dati per il calcolo dell'assegno 
 * 
 * @author s_largher  
 */
public class Util {

	// NB - dipendono dal servizio!!! TODO
	public static String getCodiceRichiedente() {
		return "1148";		
	}
	public static String getCodiceConiuge_gen() {
		return "1149";		
	}
	public static String getCodiceConiuge_no_gen() {
		return "1150";		
	}
	public static String getCodiceConvivente_gen() {
		return "1151";		
	}
	public static String getCodiceConvivente_no_gen() {
		return "1152";		
	}
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(2008, 11, 31, 23, 59); // 31 dic 2008
		return data_transizione; 
	}
}