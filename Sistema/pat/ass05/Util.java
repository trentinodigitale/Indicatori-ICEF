package c_elab.pat.ass05;

/** 
 *Created on 11-ott-2005
 */
 
import java.util.Calendar;
import java.util.Vector;

/**
 * legge tutti i dati per il calcolo dell'assegno
 * 
 * @author g_barbieri
 */
public class Util {

	// NB - dipendono dal servizio!!!
	public static String getCodiceRichiedente() {
		return "145";		
	}
	public static String getCodiceConiuge() {
		return "146";		
	}
	public static String getCodiceConvivente_gen() {
		return "147";		
	}
	public static String getCodiceConvivente_no_gen() {
		return "148";		
	}
	public static Calendar getData_transizione() {
		Calendar data_transizione = Calendar.getInstance();
		data_transizione.set(2006, 5, 30, 23, 59); // 30 giugno 2006
		return data_transizione; 
	}
	
	/**
	 * converte una data in formato string tipo "2000-21-31" in un oggetto Calendar
	 * 
	 * @return Calendar
	 */
	public static Calendar stringdate2date(String date) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.set(new Integer(date.substring(0, 4)).intValue(), new Integer(
					date.substring(5, 7)).intValue() - 1, new Integer(date
					.substring(8, 10)).intValue(), 12, 0);
			return cal;
		} catch (NullPointerException npe) {
			return null;
		} catch (IndexOutOfBoundsException iobe) {
			return null;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	/**
	 * Ritorna un array di 12 valori. I mesi che vanno da 1 a mese_init sono false
	 * 
	 * @return array boolean
	 */
	public static boolean[] get_ha_requisiti_da_a(String data_da, String data_a,
			int mese_init, int anno, boolean date_vuote_is_no_requisiti) {

		boolean result[] = new boolean[12];
		String str_mese_init = null;

		if (mese_init < 10)
			str_mese_init = "0" + mese_init;
		Calendar da = stringdate2date(data_da);
		Calendar a = stringdate2date(data_a);
		Calendar da_rif = stringdate2date(anno + "-" + str_mese_init + "-01");
		Calendar a_rif = stringdate2date(anno + "-12-31");

		// non ha i requisiti per tutto il periodo
		if (date_vuote_is_no_requisiti && da == null && a == null) {
			for (int i = 0; i < 12; i++) {
				result[i] = false;
			}
			return result;
		}
		
		// gli effetti del cambiamento decorrono dal primo giorno del mese successivo
		if (da != null) {
			da.add(Calendar.MONTH, 1);
			da.set(Calendar.DAY_OF_MONTH, 1);
		}

		// se da è null o è prima della data di riferimento iniziale prende la
		// data di riferimento iniziale
		if (da == null || da.before(da_rif)) {
			da = da_rif;
		}
		// se a è null o è dopo la data di riferimento finale prende la data di
		// riferimento finale
		if (a == null || a.after(a_rif)) {
			a = a_rif;
		}
		
		// se da è dopo a_rif o se a è prima di da_rif è sempre false 
		if (da.after(a_rif) || a.before(da_rif)) {
			for (int i = 0; i < 12; i++) {
				result[i] = false;
			}
			return result;
		}

		for (int i = 0; i < 12; i++) {
			if (i < mese_init - 1) {
				result[i] = false;
			} else {
				if ((da.get(Calendar.MONTH) <= da_rif.get(Calendar.MONTH) + i
						- mese_init + 1)
						&& (a.get(Calendar.MONTH) >= a_rif.get(Calendar.MONTH)
								- 11 + i)) {
					result[i] = true;
				} else {
					result[i] = false;
				}
			}
		}
		return result;
	}

	/**
	 * Fa un AND tra due boolean array di 12 valori
	 * 
	 * @return array boolean
	 */
	public static boolean[] and(boolean a1[], boolean a2[]) {

		for (int i = 0; i < a1.length; i++) {
			a1[i] = a1[i] && a2[i];
		}
		return a1;
	}

	/**
	 * Fa un OR tra due boolean array di 12 valori
	 * 
	 * @return array boolean
	 */
	public static boolean[] or(boolean a1[], boolean a2[]) {

		for (int i = 0; i < a1.length; i++) {
			a1[i] = a1[i] || a2[i];
		}
		return a1;
	}

	/**
	 * Fa un NOT di un boolean array di 12 valori a partire da beginIndex
	 * 
	 * @return array boolean
	 */
	public static boolean[] not(boolean a1[], int beginIndex) {

		for (int i = 0; i < a1.length; i++) {
			if (i >= beginIndex)
				a1[i] = !a1[i];
		}
		return a1;
	}

	/**
	 * Fa un NOT di un boolean array di 12 valori
	 * 
	 * @return array boolean
	 */
	public static boolean[] not(boolean a1[]) {

		return not(a1, 0);
	}

	/**
	 * Fa la somma di un integer array di 12 valori. Ciascun valore può essere al massimo 9
	 * 
	 * @return array int
	 */
	public static int[] add(int a1[], boolean a2[]) {

		return add(a1, a2, 1);
	}

	/**
	 * Fa la somma di un integer array di 12 valori. Ciascun valore può essere al massimo 9
	 * 
	 * @return array int
	 */
	public static int[] add(int a1[], boolean a2[], int coeff) {

		for (int i = 0; i < a1.length; i++) {
			if (a2[i]) {
				a1[i] = Math.min(9, a1[i] + coeff); // la somma è limitata a 9
			}
		}
		return a1;
	}

	/**
	 * Converte un boolean array di 12 valori in un double di 13 cifre mettendo 
	 * il valore 1 all'inizio per non perdere gli zeri iniziali
	 * 
	 * @return double
	 */
	public static double toDouble(boolean a1[]) {

		double result = 0.0;

		for (int i = 0; i < a1.length; i++) {
			if (a1[i])
				result = result + java.lang.Math.pow(10, (a1.length - 1) - i);
		}

		// mette un 1 all'inizio del double per non perdere gli zeri iniziali
		result = result + java.lang.Math.pow(10, a1.length);

		return result;
	}

	/**
	 * Converte un integer array di 12 valori in un double di 13 cifre mettendo 
	 * il valore 1 all'inizio per non perdere gli zeri iniziali
	 * 
	 * @return double
	 */
	public static double toDouble(int a1[]) {

		double result = 0.0;

		for (int i = 0; i < a1.length; i++) {
			if (a1[i] != 0)
				result = result + a1[i]
						* java.lang.Math.pow(10, (a1.length - 1) - i);
		}

		// mette un 1 all'inizio del double per non perdere gli zeri iniziali
		result = result + java.lang.Math.pow(10, a1.length);

		return result;
	}

	/**
	 * Converte una stringa ("0", "1", "-1") in un boolean
	 * 
	 * @return boolean
	 */
	public static boolean stringToBoolean(String str) {
		if (str.equals("0"))
			return false;
		else
			return true;
	}

	/**
	 * Ritorna un array di 12 valori che indica se il soggetto è a carico a partire da beginIndex
	 * 
	 * @return array boolean
	 */
	public static boolean[] get_a_carico(int is_a_carico, int beginIndex) {

		boolean a_carico[] = new boolean[12];

		if (is_a_carico != 0) {
			for (int j = beginIndex; j < 12; j++) {
				a_carico[j] = true;
			}
		} else {
			for (int j = beginIndex; j < 12; j++) {
				a_carico[j] = false;
			}
		}
		return a_carico;
	}
	
//	/Wolfgang
	
	/**
	 * Fa un AND tra due boolean array di 12 valori
	 * e lo ritorna nel Boolean Vector v a partire da ia*12, ia=0,1,
	 */
	public static void and(boolean a1[], boolean a2[], int ia, Vector v) {

		for (int i = 0; i < a1.length; i++) {
			if ( a1[i] && a2[i] )
				v.set(ia*12+i, Boolean.TRUE );
		}
	}

	/**
	 * prende boolean array di 12 valori
	 * e lo ritorna nel Boolean Vector v a partire da ia*12, ia=0,1,
	 */
	public static void and(boolean a1[], int ia, Vector v) {

		for (int i = 0; i < a1.length; i++) {
			if ( a1[i] )
				v.set(ia*12+i, Boolean.TRUE );
		}
	}
}