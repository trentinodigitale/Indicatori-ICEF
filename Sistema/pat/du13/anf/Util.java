package c_elab.pat.du13.anf;

import java.util.Calendar;

/** 
 *Created on 11-ott-2005  
 */

/** 
 * legge tutti i dati per il calcolo dell'assegno 
 * 
 */
public class Util {

    //CAMBIAMI: va cambiata ogni anno - FATTO
	private static int anno = 2013;
			
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(anno, 11, 31, 23, 59); // 31 dic anno
		return data_transizione; 
	}
}