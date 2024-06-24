package c_elab.pat.ass05;

/** 
 *Created on 11-ott-2005
 */
import java.util.Calendar;

/**
 * legge se ci sono i requisiti di presenza nel nucleo, residenza e lavoro
 * 
 * @author g_barbieri
 */
public class V_check extends Q_assegno {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		// check=0 significa idoneo
		// check=1 significa non idoneo perchè il richiedente non è nel nucleo
		// check=2 significa non idoneo perchè il richiedente non è residente
		// check=4 significa non idoneo perchè il richiedente non svolge le attività lavorative previste
		// le combinazioni di non idoneità sono rappresentate dalla somma dei rispettivi valori
		// check=8 significa mese non valido per domanda presentata dopo
		// check=9 significa domanda non idonea poichè è doppia
		int check[] = new int[12];

		// init check per ogni mese a 0
		for (int j = 0; j < 12; j++) {
			check[j] = 0;
		}

		//requisiti presente nel nucleo
		boolean nel_nucleo[] = Util.get_ha_requisiti_da_a(
				(String) tb_richiedente_da_a.getElement(1, 1),
				(String) tb_richiedente_da_a.getElement(1, 2), 1, 2006, false);

		//requisiti residenza
		boolean residente[] = new boolean[12];
		for (int i = 0; i < 12; i++) {
			try {
				if (Util.stringToBoolean((String) (tb_richiedente_residenza
						.getElement(1, i + 2)))
						&& Util.stringToBoolean((String) (tb_richiedente_residenza
								.getElement(1, i + 1))))
					residente[i] = true;
				else
					residente[i] = false;
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName()
						+ ": " + n.toString());
				residente[i] = false;
			}
		}

		//requisiti lavoro
		boolean lavoratore[] = new boolean[12];
		for (int i = 0; i < 12; i++) {
			try {
				if (Util.stringToBoolean((String) (tb_richiedente_lavoro.getElement(
						1, i + 2)))
						&& Util.stringToBoolean((String) (tb_richiedente_lavoro
								.getElement(1, i + 1))))
					lavoratore[i] = true;
				else
					lavoratore[i] = false;
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName()
						+ ": " + n.toString());
				lavoratore[i] = false;
			}
		}

		//requisiti complessivi = presente & lavoratore & residente
		check = Util.add(check, Util.not(nel_nucleo), 1);
		check = Util.add(check, Util.not(residente), 2);
		check = Util.add(check, Util.not(lavoratore), 4);

		// correzione check nel caso in cui la domanda sia presentata dopo il 30-6-06
		try {
			Calendar data_dom = Util.stringdate2date((String)(tb_dati_dom.getElement(1, 1))); 
			if ( data_dom.after(Util.getData_transizione()) ) {
				int a = data_dom.get(Calendar.MONTH) + 1;
				for (int j = 0; j < a; j++) {
					check[j] = 9;
				}
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName()
					+ ": " + n.toString());
		}

		// correzione check nel caso in cui la domanda sia doppia o esclusa d'ufficio
		//try {
		//	if ( new Integer((String)(tb_dati_dom.getElement(1, 2))).intValue() > 0 ) {
		//		for (int j = 0; j < 12; j++) {
		//			check[j] = 9;
		//		}
		//	}
		//} catch (NullPointerException n) {
		//	System.out.println("Null pointer in " + getClass().getName()
		//			+ ": " + n.toString());
		//}
		
		try {
			return Util.toDouble(check);
		} catch (NumberFormatException nfe) {
			System.out.println("ERRORE NumberFormatException in "
					+ getClass().getName() + ": " + nfe.toString());
			return 1777777777777.0;
		}
	}
}