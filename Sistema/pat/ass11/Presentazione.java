/**
 *Created on 05-dic-2005
 */

package c_elab.pat.ass11; 

import java.util.Calendar;

public class Presentazione extends Q_assegno{
		
    //CAMBIAMI: va cambiata ogni anno
    private static int anno = 2010;
	
	public double getValue() {

		if (records == null)
			return 0.0; 
		
		// NB l'idoneità alla data di presentazione si valuta il mese successivo
		try {
			Calendar data_dom = c_elab.pat.Util_apapi.stringdate2date((String)(tb_data_beneficio.getElement(1, 1)));
			if ( data_dom.get(Calendar.YEAR)== anno )
				return 0; //NB
			else
				return data_dom.get(Calendar.MONTH) + 2; //NB
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}