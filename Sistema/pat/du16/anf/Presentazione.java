/**
 *Created on 05-dic-2005
 */

package c_elab.pat.du16.anf; 

import java.util.Calendar;

public class Presentazione extends Q_assegno{
		
    //CAMBIAMI: va cambiata ogni anno - FATTO
    private static int anno = 2016;
	
	public double getValue() {

		if (records == null)
			return 0.0; 
		
		
		/**
		 * il mese di presentazione viene utilizzato solamente per il nodo di output
		 *  v_check_ce per questo è stato modificato in data 22 marzo 2017
		 *  l'eslusione parte dal mese successivo alla data di presentazione
		 *  
		 * per la partenza del mese di contribuzione ci si basa sulla presenza di altri parametri
		 * vedi la classe v_check di input o v_n_figli
		 */
		try {
			Calendar data_dom = c_elab.pat.Util_apapi.stringdate2date((String)(tb_data_beneficio.getElement(1, 1)));
			if ( data_dom.get(Calendar.YEAR)== anno )
				return 0; //NB
			else
				return data_dom.get(Calendar.MONTH) + 1; //NB
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}