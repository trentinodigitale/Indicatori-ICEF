package c_elab.pat.ass11;

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
	public static String getCodiceRichiedente() {
		return "4963";		
	}
	public static String getCodiceConiuge_gen() {
		return "4964";		
	}
	public static String getCodiceConiuge_no_gen() {
		return "4965";		
	}
	public static String getCodiceConvivente_gen() {
		return "4966";		
	}
	public static String getCodiceConvivente_no_gen() {
		return "4967";		
	}
	
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(2010, 11, 31, 23, 59); // 31 dic 2009
		return data_transizione; 
	}
}