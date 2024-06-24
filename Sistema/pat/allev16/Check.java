package c_elab.pat.allev16;

import it.clesius.db.util.DateTimeFormat;
import it.clesius.util.General1;

import java.util.Calendar;
/**
 * 
 * @author a_pichler
 *
 */
public class Check extends QDati {

	//va cambiato ogni anno
	protected int anno_riferimento = 2015;
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		double check = 0.0;

		try {
			if(records==null) {
				return check;
			}
			else if(records.getRows()==1) {

				String data_nascita = records.getString(1, records.getIndexOfColumnName("data_nascita"));
				Calendar dataNascita = General1.getStringToCalendar(DateTimeFormat.toItDate(data_nascita));
				

				int anno_nascita = dataNascita.get(Calendar.YEAR);
				
				if (anno_nascita<anno_riferimento-40) {
					check = 1;
				}
				
				return check;
			
			}else return check;
		} catch (Exception e) {
			e.printStackTrace();
			return 11234067.0;
		}
		
	}
}
