package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import java.util.Calendar;


/** ritorna il valore del patrimonio mobiliare per il rendimento titoli di stato
 * @author g_barbieri
 */
public class X extends QDichiarazioneISEE {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		String str_data_dich = "1999-01-01";

		Calendar data_dich = Calendar.getInstance();

		Calendar uno_1_2000 = Calendar.getInstance();
		uno_1_2000.set(2000, 0, 1, 0, 0); // 1 gennaio 2000

		Calendar uno_1_2001 = Calendar.getInstance();
		uno_1_2001.set(2001, 0, 1, 0, 0); // 1 gennaio 2001

		Calendar trentuno_1_2002 = Calendar.getInstance();
		trentuno_1_2002.set(2002, 0, 31, 0, 0); // 31 gennaio 2002

		Calendar trentuno_1_2003 = Calendar.getInstance();
		trentuno_1_2003.set(2003, 0, 31, 0, 0); // 31 gennaio 2003

		Calendar venti_1_2004 = Calendar.getInstance();
		venti_1_2004.set(2004, 0, 20, 0, 0); // 20 gennaio 2004

		try {
			str_data_dich = (String) (records.getElement(1, 6));
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return -1.0;
		}

		try {
			data_dich.set(
				new Integer(str_data_dich.substring(0, 4)).intValue(),
				new Integer(str_data_dich.substring(5, 7)).intValue() - 1,
				new Integer(str_data_dich.substring(8, 10)).intValue(), 12,	0);
		} catch (NullPointerException npe) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ npe.toString());
			return -1.0;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("---> ERRORE IN " + getClass().getName()	+ ": " + iobe.toString());
			return -1.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return -1.0;
		}

		if (data_dich.before(uno_1_2000)) { // se dich prima dell'1-1-2000
			return 0.0495;
		} else if (
			data_dich.before(uno_1_2001)) { // se dich prima dell'1-1-2001
			return 0.0452;
		} else if (
			data_dich.before(
				trentuno_1_2002)) { // se dich prima dell'31-1-2002
			return 0.0557;
		} else if (
			data_dich.before(
				trentuno_1_2003)) { // se dich prima dell'31-1-2003
			return 0.0513;
		} else if (
			data_dich.before(venti_1_2004)) { // se dich prima dell'20-1-2004
			return 0.0504;
		} else {
			return 0.042;
		}
	}
}