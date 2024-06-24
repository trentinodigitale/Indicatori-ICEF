package c_elab.pat.ass10;

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

    //CAMBIAMI: va cambiata ogni anno
	public static String getCodiceRichiedente() {
		return "1765";		
	}
	public static String getCodiceConiuge_gen() {
		return "1766";		
	}
	public static String getCodiceConiuge_no_gen() {
		return "1767";		
	}
	public static String getCodiceConvivente_gen() {
		return "1768";		
	}
	public static String getCodiceConvivente_no_gen() {
		return "1769";		
	}
	
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(2009, 11, 31, 23, 59); // 31 dic 2009
		return data_transizione; 
	}
}