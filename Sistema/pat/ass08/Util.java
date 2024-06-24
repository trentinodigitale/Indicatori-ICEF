package c_elab.pat.ass08;

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

	// NB - dipendono dal servizio!!! TODO
	public static String getCodiceRichiedente() {
		return "582";		
	}
	public static String getCodiceConiuge_gen() {
		return "583";		
	}
	public static String getCodiceConiuge_no_gen() {
		return "591"; //AGGIUNTO GIU-08
	}
	public static String getCodiceConvivente_gen() {
		return "584";		
	}
	public static String getCodiceConvivente_no_gen() {
		return "585";		
	}
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(2007, 11, 31, 23, 59); // 31 dic 2007
		return data_transizione; 
	}
}