/*
 * Created on 25-mag-2006 
 *
 */
package c_elab.pat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import it.clesius.clesius.util.Sys;
import it.clesius.db.util.Table;

/**
 * @author g_barbieri 
 *
 */
public class Util_apapi {

	public static boolean[] get_array_figli(Table t, Calendar da_limite, Calendar a_limite, int lunghezza, int periodicita) {
		
    	Calendar data_nascita  = Calendar.getInstance();
    	Calendar data_18anni  = Calendar.getInstance();
    	Calendar data_invalido_da  = Calendar.getInstance();
    	Calendar data_invalido_a  = Calendar.getInstance();
    	Calendar da = Calendar.getInstance();
    	da = (Calendar)da_limite.clone();
    	Calendar a  = Calendar.getInstance();
    	a = (Calendar)a_limite.clone();
    	boolean invalido = false;

    	boolean figli_array[] = new boolean[lunghezza];
        boolean figli_array_tmp[] = new boolean[lunghezza];
    	boolean nato_array[] = new boolean[lunghezza];
    	boolean minore_array[] = new boolean[lunghezza];
    	boolean nel_nucleo_array[] = new boolean[lunghezza];
    	boolean invalido_array[] = new boolean[lunghezza];
    	
        for (int i = 1; i <= t.getRows(); i++) {
        	
        	//nascita e maggiorenne
            try {
            	data_nascita = t.getCalendar(i,1);
            	data_18anni = (Calendar)data_nascita.clone();
            	data_18anni.add(Calendar.YEAR, 18);
            } catch (Exception e) {
    			data_nascita = (Calendar)da_limite.clone();
    			data_18anni = (Calendar)a_limite.clone();
            }
            
            nato_array = get_t_array(da_limite, data_nascita, a_limite, a_limite, periodicita, true, lunghezza);
            minore_array = get_t_array(da_limite, data_nascita, data_18anni, a_limite, periodicita, true, lunghezza);

            // da.. a.. nel nucleo
            try {
            	da = t.getCalendar(i,2);
            } catch (Exception e) {
            	da = (Calendar)da_limite.clone();
            }
            try {
            	a  = t.getCalendar(i,3);
            } catch (Exception e) {
            	a = (Calendar)a_limite.clone();
            }
            
            nel_nucleo_array = get_t_array(da_limite, da, a, a_limite, periodicita, true, lunghezza);

            // da.. a.. invalido
            try {
            	invalido = !t.getBoolean(i,6);  //il campo si chiama invalido_no per cui invalido è invertito!!!!
            } catch (Exception e) {
            	invalido = false;
            }
            if (invalido) {
                try {
                	data_invalido_da = t.getCalendar(i,4);
                } catch (Exception e) {
                	data_invalido_da = (Calendar)da_limite.clone();
                }
                try {
                	data_invalido_a = t.getCalendar(i,5);
                } catch (Exception e) {
                	data_invalido_a = (Calendar)a_limite.clone();
                }
                invalido_array = get_t_array(da_limite, data_invalido_da, data_invalido_a, a_limite, periodicita, true, lunghezza);
            }
            
            // minore or disabile
            figli_array_tmp = Util_apapi.or(minore_array, invalido_array);
            // (minore or disabile) and nato
            figli_array_tmp = Util_apapi.and(figli_array_tmp, nato_array);
            // (minore or disabile) and nato and nel nucleo
            figli_array_tmp = Util_apapi.and(figli_array_tmp, nel_nucleo_array);
            
        	figli_array = Util_apapi.or(figli_array, figli_array_tmp);
        }
        return figli_array;
	}

	public static boolean[] get_array_ultra55(Table t, Calendar da_limite, Calendar a_limite, int lunghezza, int periodicita) {
		
    	Calendar data_nascita  = Calendar.getInstance();
    	Calendar data_55anni  = Calendar.getInstance();
    	Calendar data_155anni  = Calendar.getInstance();
    	Calendar da = Calendar.getInstance();
    	da = (Calendar)da_limite.clone();
    	Calendar a  = Calendar.getInstance();
    	a = (Calendar)a_limite.clone();

    	boolean rich_array[] = new boolean[lunghezza];
        boolean rich_array_tmp[] = new boolean[lunghezza];
    	boolean ultra55_array[] = new boolean[lunghezza];
    	boolean nel_nucleo_array[] = new boolean[lunghezza];
    	
        for (int i = 1; i <= t.getRows(); i++) {
        	
        	//nascita e ultra55
            try {
            	data_nascita = t.getCalendar(i,1);
            	data_55anni = (Calendar)data_nascita.clone();
            	data_55anni.add(Calendar.YEAR, 55);
            	data_155anni = (Calendar)data_nascita.clone();
            	data_155anni.add(Calendar.YEAR, 155);				 //età non raggiungibile
            } catch (Exception e) {
            	data_55anni = (Calendar)da_limite.clone();
    			data_155anni = (Calendar)a_limite.clone();
            }
            
            ultra55_array = get_t_array(da_limite, data_55anni, data_155anni, a_limite, periodicita, true, lunghezza);

            // da.. a.. nel nucleo
            try {
            	da = t.getCalendar(i,2);
            } catch (Exception e) {
            	da = (Calendar)da_limite.clone();
            }
            try {
            	a  = t.getCalendar(i,3);
            } catch (Exception e) {
            	a = (Calendar)a_limite.clone();
            }
            
            nel_nucleo_array = get_t_array(da_limite, da, a, a_limite, periodicita, true, lunghezza);
           
            // ultra55 and nel nucleo
            rich_array_tmp = Util_apapi.and(ultra55_array, nel_nucleo_array);
            
        	rich_array = Util_apapi.or(rich_array, rich_array_tmp);
        }
        return rich_array;
	}
	
	public static boolean[] get_array_trimestri(Table t, Calendar da_limite, Calendar a_limite, int lunghezza, int periodicita, int anno_servizio) {
    	boolean trimestri_array[] = new boolean[lunghezza];
        boolean trimestri_array_tmp[] = new boolean[lunghezza];
    	Calendar da = Calendar.getInstance();
    	Calendar a  = Calendar.getInstance();
        for (int i = 1; i <= t.getRows(); i++) {
            for (int j = 1; j <= 4; j++) {
            	if (t.getBoolean(i, j)) {
                	da.set(anno_servizio, j * 3 - 3, 1);  //primo giorno del trimestre j-esimo 
                	a.set(anno_servizio, j * 3 - 1, 1);
                	a.set(anno_servizio, j * 3 - 1, a.getActualMaximum(Calendar.DAY_OF_MONTH)); //ultimo giorno del trimestre j-esimo
                	trimestri_array_tmp = get_t_array(da_limite, da, a, a_limite, periodicita, true, lunghezza);
            	}
            	trimestri_array = Util_apapi.or(trimestri_array, trimestri_array_tmp);
            }
        }
        return trimestri_array;
	}
	
	public static boolean[] get_array_trimestri(Table t, Calendar da_limite, Calendar a_limite, int lunghezza, int periodicita) {
    	boolean trimestri_array[] = new boolean[lunghezza];
        boolean trimestri_array_tmp[] = new boolean[lunghezza];
    	Calendar da = Calendar.getInstance();
    	Calendar a  = Calendar.getInstance();
        for (int i = 1; i <= t.getRows(); i++) {
            for (int j = 1; j <= 4; j++) {
            	if (t.getBoolean(i, j)) {
                	da.set(t.getInteger(i, 5), j * 3 - 3, 1);  //primo giorno del trimestre j-esimo 
                	a.set(t.getInteger(i, 5), j * 3 - 1, 1);
                	a.set(t.getInteger(i, 5), j * 3 - 1, a.getActualMaximum(Calendar.DAY_OF_MONTH)); //ultimo giorno del trimestre j-esimo
                	trimestri_array_tmp = get_t_array(da_limite, da, a, a_limite, periodicita, true, lunghezza);
            	}
            	trimestri_array = Util_apapi.or(trimestri_array, trimestri_array_tmp);
            }
        }
        return trimestri_array;
	}
	
	// schiaccia le date all'interno del periodo
	public static boolean check_date(Calendar da_limite, Calendar da, Calendar a, Calendar a_limite) {
		
		if (a_limite.before(da_limite)) {
			return true;
		}

		if (a.before(da)) {
			return true;
		}

		if ( (da.before(da_limite) && a.before(da_limite)) ) {
			return true;
		} 
		
		if ( (da.after(a_limite) && a.after(a_limite)) ) {
			return true;
		} 
		
		return false;
	}
	
	public static boolean[] get_t_array(Calendar da_limite, Calendar da, Calendar a, Calendar a_limite, int periodicita, boolean estremi_inclusi, int lunghezza) {
		
		boolean result[];
		int n_da_limite = 0;
		int n_da = 0;
		int n_a = 0;
		int n_a_limite = 0; 
		System.out.println("da_limite "+DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da_limite.getTime()));
		System.out.println("da "+DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da.getTime()));
		System.out.println("a "+DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a.getTime()));
		System.out.println("a_limite "+DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a_limite.getTime()));
		
		result = new boolean[lunghezza];

		// Controllo consistenza date
		if ( !check_date(da_limite, da, a , a_limite) ) {
		
			
			if (da.before(da_limite)) {
				da = (Calendar)da_limite.clone();
			}
			if (a.after(a_limite)) {
				a = (Calendar)a_limite.clone();
			}

//			System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da_limite.getTime()));
//			System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(da.getTime()));
//			System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a.getTime()));
//			System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.ITALY).format(a_limite.getTime()));
			
			//------------ giornaliero
			if (periodicita == 1) {
				n_da_limite = da_limite.get(Calendar.DAY_OF_YEAR);
				n_da = da.get(Calendar.DAY_OF_YEAR) - n_da_limite + 1;
				n_a = a.get(Calendar.DAY_OF_YEAR) - n_da_limite + 1;
				n_a_limite = a_limite.get(Calendar.DAY_OF_YEAR) - n_da_limite + 1;
				n_da_limite = 1;
				
				//------------ settimanale
			} else if (periodicita == 2) {
				
				// offset è il numero di giorni che passano 
				// tra 1 gennaio e il primo sabato del periodo
				int offset = 0;
				Calendar cal = Calendar.getInstance();
				cal = (Calendar)da_limite.clone();
				while ( cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
					cal.add(Calendar.DATE, + 1);
					offset++;
				}
				offset = offset + da_limite.get(Calendar.DAY_OF_YEAR) - 1;
				
				// se da_limite è 1 gennaio ed è sabato
				if (offset == 0 && da_limite.get(Calendar.DAY_OF_YEAR) == 1) {
					n_da_limite = 0;
				} else {
					n_da_limite = count_week_floor(da_limite, offset);
				}
				
				// se da è 1 gennaio ed è sabato
				if (offset == 0 && da.get(Calendar.DAY_OF_YEAR) == 1) {
					n_da = 0;
				} else {
					n_da = count_week_da(da, offset, estremi_inclusi);
				}
				
				n_a = count_week_a(a, offset, estremi_inclusi);
				n_a_limite = count_week_floor(a_limite, offset);
			
			//------------ mensile
			} else if (periodicita == 3) {  
				n_da_limite = 1;
				n_da = da.get(Calendar.MONTH) - da_limite.get(Calendar.MONTH) + 1;
				n_a = a.get(Calendar.MONTH) - da_limite.get(Calendar.MONTH) + 1;
				n_a_limite = 3;
				
				if (!estremi_inclusi){
					if (da.get(Calendar.DAY_OF_MONTH) != 1) {  // se non è il primo del mese
						n_da++;
					}
					if (a.get(Calendar.DAY_OF_MONTH) != a.getActualMaximum(Calendar.DAY_OF_MONTH)) { // se non è l'ultimo del mese
						n_a--;
					}
				}
			} else {
				System.out.println("ERRORE: periodicità non prevista: " + periodicita);
				result = null;
			}
			
			if (n_a_limite != lunghezza)
				System.out.println("ERRORE GRAVE: n_a_limite != lunghezza");
		
			// tra n_da e n_a l'array è true
			for (int j = Math.max(0, n_da - 1); j < Math.min(n_a, n_a_limite); j++) {
				result[j] = true;
			}
		} else {
			System.out.println("ERRORE: date inconsistenti");
		}
		
		return result;
	}
	
	public static int count_week_da(Calendar cal, int offset, boolean estremi_inclusi) {
		//if (estremi_inclusi)
		//	return count_week_floor(cal, offset);
		//else
			return count_week_ceil(cal, offset); 
	}
	
	public static int count_week_a(Calendar cal, int offset, boolean estremi_inclusi) {
		if (estremi_inclusi)
			return count_week_ceil(cal, offset);
		else
			return count_week_floor(cal, offset); 
	}
	
	public static int count_week_floor(Calendar cal, int offset) {
		return new Double(Math.floor((cal.get(Calendar.DAY_OF_YEAR) - offset + 6.0) / 7.0)).intValue();
	}
	
	public static int count_week_ceil(Calendar cal, int offset) {
		return new Double(Math.ceil((cal.get(Calendar.DAY_OF_YEAR) - offset + 6.0) / 7.0)).intValue();
	}
	
	/**
	 * Somma i valori true di un boolean array 
	 * 
	 * @return int
	 */
	public static int count_true(boolean a1[]) {
		int val = 0;
		for (int i = 0; i < a1.length; i++) {
			if (a1[i])
			 val++;
		}
		return val;
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

	public static int getSettimane(Calendar da, Calendar a) {
		
		Calendar cal = Calendar.getInstance();
		cal = (Calendar)da.clone();

		int settimane = 0;
		
		// trova il n. dei sabati estremi compresi
		do {
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				settimane++;
			}
			cal.add(Calendar.DATE, +1);
		} while (a.after(cal));

		return settimane;		
	}
	
	public static boolean[] get_ha_requisiti_da_a_comp(Calendar da, Calendar a,
			int mese_ini_ben, int anno_ini_ben, boolean compresoA) {
		
		mese_ini_ben=mese_ini_ben-1;
		boolean result[] = new boolean[12];
		//il beneficio ha inizio dal primo giorno del mese
		Calendar da_ini_ben =  Calendar.getInstance();
		da_ini_ben.set(Calendar.YEAR,anno_ini_ben);
		da_ini_ben.set(Calendar.MONTH,mese_ini_ben);
		da_ini_ben.set(Calendar.DAY_OF_MONTH, 1);
		
		da_ini_ben.set(Calendar.HOUR_OF_DAY, 0);
		da_ini_ben.set(Calendar.MINUTE, 0);
		da_ini_ben.set(Calendar.SECOND, 0); 
		da_ini_ben.set(Calendar.MILLISECOND, 0);
		
		Calendar a_fin_ben=(Calendar) da_ini_ben.clone();
		a_fin_ben.add(Calendar.MONTH, 12);
		//se non c'è una data nel nucleo dal si presuppone sia presente dall'inizio
		if (da == null) {
			da = (Calendar)da_ini_ben.clone();
		}else{
			//il giorno è ininfluente
			da.set(Calendar.DAY_OF_MONTH,1);
		}
		// se a è null o è dopo la data di riferimento finale prende la data di
		// riferimento finale
		if (a == null) {
			a = (Calendar)a_fin_ben.clone();
		}else{
			//il giorno è ininfluente
			a.set(Calendar.DAY_OF_MONTH,1);
		}
		
		if(compresoA){
			a.add(Calendar.MONTH, 1);
		}

		
		// se da è dopo a_fin_beneficio o se a è prima di da_ini_beneficio è sempre false 
		if (da.after(a_fin_ben) || (a.before(da_ini_ben))) {
			for (int i = 0; i < 12; i++) {
				result[i] = false;
			}
			return result;
		}

		//System.out.println("ini ben - da "+formatter.format(da_ini_ben.getTime())+" a "+formatter.format(a_fin_ben.getTime()));
		a_fin_ben.add(Calendar.MONTH,-11);
		for (int meseProgressivo = 0 ; meseProgressivo < 12; meseProgressivo++) {
			//System.out.println(meseProgressivo+"req da - da "+formatter.format(da.getTime())+" a "+formatter.format(a.getTime()));
			//System.out.println("ini ben - da "+formatter.format(da_ini_ben.getTime())+" a "+formatter.format(a_fin_ben.getTime()));
			
			if ((da.before(da_ini_ben) || da.equals(da_ini_ben)) && 
					(a.after(a_fin_ben) || a.equals(a_fin_ben))) {
				result[meseProgressivo] = true;
			} else {
				result[meseProgressivo] = false;
			}
			da_ini_ben.add(Calendar.MONTH,1);
			a_fin_ben.add(Calendar.MONTH,1);
			}
		return result;
	}
	
	
	public static double getImporto_colf(Calendar decorrenza_volontaria, int periodicita, int anno) {
		/**
		 * CAMBIAMI
		 *  OGNI ANNO devi aggiungere in fondo l'importo della colf 
		 *  else if (anno==anno_nuovo){}
		 *  l'anno di riferimento è in PNS_versamenti.anno
		 *  zoppi annulamente ci invia una mail con i nuovi importi
		 *  con la speranza che un giorno sia più facile da trovare e da ricordare
		 *  ale 
		 *  
		 *  
		 *  SELECT        TOP (200) ID_servizio, anno
			FROM            PNS_tp_anni
			WHERE        (ID_servizio = 9280)
		 */
    	Calendar trentuno_dic_1995  = Calendar.getInstance();
    	trentuno_dic_1995.set(1995,11,31,23,59);  //31/12/1995
    	
    	if (anno == 2005) {
    		if (decorrenza_volontaria.after(trentuno_dic_1995)) {
    		    if (periodicita == 2)  //dip
    		    	return 25.56;
    		    else
    		    	return 92.56;
    		} else {
    		    if (periodicita == 2)  //dip
    		    	return 21.36;
    		    else
    		    	return 92.56;
    		}
    	} else if (anno == 2006) {
    		if (decorrenza_volontaria.after(trentuno_dic_1995)) {
    		    if (periodicita == 2)  //dip
    		    	return 26.0;
    		    else
    		    	return 94.12;
    		} else {
    		    if (periodicita == 2)  //dip
    		    	return 21.72;
    		    else
    		    	return 94.12;
    		}
		} else if (anno == 2007) {
    		if (decorrenza_volontaria.after(trentuno_dic_1995)) {
    		    if (periodicita == 2)  //dip
    		    	return 27.91;
    		    else
    		    	return 98.28;
    		} else {
    		    if (periodicita == 2)  //dip
    		    	return 22.68;
    		    else
    		    	return 98.28;
    		}
		} else if (anno == 2008) {
    		if (decorrenza_volontaria.after(trentuno_dic_1995)) {
    		    if (periodicita == 2)  //dip
    		    	return 28.39;
    		    else
    		    	return 99.97;
    		} else {
    		    if (periodicita == 2)  //dip
    		    	return 23.07;
    		    else
    		    	return 99.97;
    		}
    		
		} else if (anno == 2009) {
	        if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	            if (periodicita == 2)  //dip
	                return 30.21; // 28.39;
	            else
	                return 103.13; // 99.97;
	        } else {
	            if (periodicita == 2)  //dip
	                return 23.80; // 23.07;
	            else
	                return 103.13; // 99.97;
	        } 
		} else if (anno == 2010) {
	        if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	            if (periodicita == 2)  //dip
	                return 30.42; // 30.21;
	            else
	                return 103.87; // 103.13;
	        } else {
	            if (periodicita == 2)  //dip
	                return 23.97; // 23.80;
	            else
	                return 103.87; // 103.13;
	        } 
		} else if (anno == 2011) {
	        if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	            if (periodicita == 2)  //dip
	                return 31.85; // 30.42;
	            else
	                return 105.52; // 103.87;
	        } else {
	            if (periodicita == 2)  //dip
	                return 24.35; // 23.97;
	            else
	                return 105.52; // 103.87;
	        } 
		}else if (anno == 2012) {
			
			
	        if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	        	//autorizzati dopo il 31/12/1995		          
	        	// periodicità settimanale = 2
	            if (periodicita == 2)  
	            	return 32.71;  
	            else
	            // altrimenti mensile
	            	return 108.38; 
	        } else {
	        	//autorizzati prima del 31/12/1995		          
	          if (periodicita == 2)  
	        	  	return 25.01; 
	            else
	            	return 108.38; 
	        } 
		}else if (anno == 2013) {
						
	        if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	        	//autorizzati dopo il 31/12/1995		          
	        	// periodicità settimanale = 2
	            if (periodicita == 2)  
	            	return 34.54;  
	            else
	            // altrimenti mensile
	            	return 111.63; 
	        } else {
	        	//autorizzati prima del 31/12/1995		          
	          if (periodicita == 2)  
	        	  	return 25.76; 
	            else
	            	return 111.63; 
	        } 
	        
		}else if (anno == 2014) {
						
	        if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	        	//autorizzati dopo il 31/12/1995		          
	        	//retribuzione minima settimanale
	            if (periodicita == 2)  
	            	return 34.92;  
	            else
	//equivalente mensile teorico del versamento volontario si ottiene dividendo per tre l'importo del corrispondente versamento trimestrale 
	            // autorizzati prima del 31.12.1995
	            	return 112.88; 
	        } else {
	        	//autorizzati prima del 31.12.1995          
	          if (periodicita == 2)  
	        	  	return 26.05; 
	            else
	//equivalente mensile teorico del versamento volontario si ottiene dividendo per tre l'importo del corrispondente versamento trimestrale 
	            // autorizzati prima del 31.12.1995
          	return 112.88; 
	        } 
	        
		}else if (anno == 2015) {
						
	        if (decorrenza_volontaria.after(trentuno_dic_1995)) {
	        	//autorizzati dopo il 31/12/1995		          
	        	//retribuzione minima settimanale
	            if (periodicita == 2)  
	            	return 34.99;  
	            else
	//equivalente mensile teorico del versamento volontario si ottiene dividendo per tre l'importo del corrispondente versamento trimestrale 
	            // autorizzati prima del 31.12.1995
	            	return 113.10; 
	        } else {
	        	//autorizzati prima del 31.12.1995          
	          if (periodicita == 2)  
	        	  	return 26.10; 
	            else
	//equivalente mensile teorico del versamento volontario si ottiene dividendo per tre l'importo del corrispondente versamento trimestrale 
	            // autorizzati prima del 31.12.1995
          	return 113.10; 
	        } 
	        
		}else if (anno==2016){
			// i mesi e le settimane devono essere forniti dall'APAPI
			double importo_intero_anno=4000.0;
			int settimane_anno=53;
			int mesi_anno=12;
			double imp=0.0;
			if (periodicita == 2){ 
				imp=importo_intero_anno/settimane_anno;
			}else{
				imp=importo_intero_anno/mesi_anno;
			}
			BigDecimal bdC = new BigDecimal(imp);
			//il totale non può essere maggiore ai 4000 come da regolamento
			bdC = bdC.setScale(2,BigDecimal.ROUND_HALF_DOWN);					    
			imp = bdC.doubleValue();
		    
			return imp; 
		}else if (anno==2017){
			// i mesi e le settimane devono essere forniti dall'APAPI
			double importo_intero_anno=4000.0;
			int settimane_anno=52;
			int mesi_anno=12;
			double imp=0.0;
			if (periodicita == 2){ 
				imp=importo_intero_anno/settimane_anno;
			}else{
				imp=importo_intero_anno/mesi_anno;
			}
			BigDecimal bdC = new BigDecimal(imp);
			//il totale non può essere maggiore ai 4000 come da regolamento
			bdC = bdC.setScale(2,BigDecimal.ROUND_HALF_DOWN);					    
			imp = bdC.doubleValue();
		    
			return imp;
			
			
		}else if (anno==2018){
			// i mesi e le settimane devono essere forniti dall'APAPI
			double importo_intero_anno=4000.0;
			int settimane_anno=52;
			int mesi_anno=12;
			double imp=0.0;
			if (periodicita == 2){ 
				imp=importo_intero_anno/settimane_anno;
			}else{
				imp=importo_intero_anno/mesi_anno;
			}
			BigDecimal bdC = new BigDecimal(imp);
			//il totale non può essere maggiore ai 4000 come da regolamento
			bdC = bdC.setScale(2,BigDecimal.ROUND_HALF_DOWN);					    
			imp = bdC.doubleValue();
		    
			return imp;

		}else if (anno==2019){
			// i mesi e le settimane devono essere forniti dall'APAPI
			double importo_intero_anno=4000.0;
			int settimane_anno=52;
			int mesi_anno=12;
			double imp=0.0;
			if (periodicita == 2){ 
				imp=importo_intero_anno/settimane_anno;
			}else{
				imp=importo_intero_anno/mesi_anno;
			}
			BigDecimal bdC = new BigDecimal(imp);
			//il totale non può essere maggiore ai 4000 come da regolamento
			bdC = bdC.setScale(2,BigDecimal.ROUND_HALF_DOWN);					    
			imp = bdC.doubleValue();
		    
			return imp;
			
		}else if (anno==2020){
			// i mesi e le settimane devono essere forniti dall'APAPI
			double importo_intero_anno=4000.0;
			int settimane_anno=52;
			int mesi_anno=12;
			double imp=0.0;
			if (periodicita == 2){ 
				imp=importo_intero_anno/settimane_anno;
			}else{
				imp=importo_intero_anno/mesi_anno;
			}
			BigDecimal bdC = new BigDecimal(imp);
			//il totale non può essere maggiore ai 4000 come da regolamento
			bdC = bdC.setScale(2,BigDecimal.ROUND_HALF_DOWN);					    
			imp = bdC.doubleValue();
		    
			return imp;
			
		}else if (anno==2021){
			// i mesi e le settimane devono essere forniti dall'APAPI
			double importo_intero_anno=4000.0;
			int settimane_anno=52;
			int mesi_anno=12;
			double imp=0.0;
			if (periodicita == 2){ 
				imp=importo_intero_anno/settimane_anno;
			}else{
				imp=importo_intero_anno/mesi_anno;
			}
			BigDecimal bdC = new BigDecimal(imp);
			//il totale non può essere maggiore ai 4000 come da regolamento
			bdC = bdC.setScale(2,BigDecimal.ROUND_HALF_DOWN);					    
			imp = bdC.doubleValue();
		    
			return imp;
			
		}else if (anno==2022){
			// i mesi e le settimane devono essere forniti dall'APAPI
			double importo_intero_anno=4000.0;
			int settimane_anno=52;
			int mesi_anno=12;
			double imp=0.0;
			if (periodicita == 2){ 
				imp=importo_intero_anno/settimane_anno;
			}else{
				imp=importo_intero_anno/mesi_anno;
			}
			BigDecimal bdC = new BigDecimal(imp);
			//il totale non può essere maggiore ai 4000 come da regolamento
			bdC = bdC.setScale(2,BigDecimal.ROUND_HALF_DOWN);					    
			imp = bdC.doubleValue();
		    
			return imp;
			
		}else {
			System.out.println("ERRORE GRAVE: AGGIORNARE GLI IMPORTI COLF IN Util_apapi.class");
			return 0;
		}
	}
}