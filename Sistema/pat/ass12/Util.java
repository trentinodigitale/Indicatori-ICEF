package c_elab.pat.ass12;

import java.util.Calendar;

/** 
 *Created on 11-ott-2005  
 */

/** 
 * legge tutti i dati per il calcolo dell'assegno 
 * 
 */
public class Util {

    //CAMBIAMI: va cambiata ogni anno
	private static int anno = 2011;
		
	public static String getCodiceRichiedente() {
		return "5335";		
	}
	public static String getCodiceConiuge_gen() {
		return "5336";		
	}
	public static String getCodiceConiuge_no_gen() {
		return "5337";		
	}
	public static String getCodiceConvivente_gen() {
		return "5338";		
	}
	public static String getCodiceConvivente_no_gen() {
		return "5339";		
	}
	
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(anno, 11, 31, 23, 59); // 31 dic 2011
		return data_transizione; 
	}
}