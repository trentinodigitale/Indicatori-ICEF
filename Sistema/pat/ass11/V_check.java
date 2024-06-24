package c_elab.pat.ass11;

/** 
 *Created on 11-ott-2005
 */
import java.util.Calendar; 

/**
 * legge se ci sono i requisiti di presenza nel nucleo, residenza e lavoro
 * 
 */
public class V_check extends Q_assegno {

    //CAMBIAMI: va cambiata ogni anno
    private static int anno = 2011;

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
		// check=10 significa domanda esclusa dufficio
		int check[] = new int[12];

		// init check per ogni mese a 0
		for (int j = 0; j < 12; j++) {
			check[j] = 0;
		}

		//requisiti presente nel nucleo
		boolean nel_nucleo[] = c_elab.pat.Util_apapi.get_ha_requisiti_da_a(
				(String) tb_richiedente_da_a.getElement(1, 1),
				(String) tb_richiedente_da_a.getElement(1, 2), 1, anno, false);

		//requisiti residenza
		boolean residente[] = new boolean[12];
		for (int i = 0; i < 12; i++) {
			try {
				if (c_elab.pat.Util_apapi.stringToBoolean((String) (tb_richiedente_residenza
						.getElement(1, i + 2)))
						&& c_elab.pat.Util_apapi.stringToBoolean((String) (tb_richiedente_residenza
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
				if (c_elab.pat.Util_apapi.stringToBoolean((String) (tb_richiedente_lavoro.getElement(
						1, i + 2)))
						&& c_elab.pat.Util_apapi.stringToBoolean((String) (tb_richiedente_lavoro
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
		//escludi ufficio
		boolean escludi_ufficio[]=new boolean[12];;
		{
		try {
			if(tb_dati_dom.getElement(1, 3)!= null && c_elab.pat.Util_apapi.stringToBoolean((String) tb_dati_dom.getElement(1, 3)))
			{		
				for (int i = 0; i < 12; i++) {
					escludi_ufficio[i] = false;
				}
				
			}else{
				for (int i = 0; i < 12; i++) {
					escludi_ufficio[i] = true;
				}
					
			}
		
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName()
						+ ": " + n.toString());
			
		}
		}
		//requisiti complessivi = presente & lavoratore & residente 
		check = c_elab.pat.Util_apapi.add(check, c_elab.pat.Util_apapi.not(nel_nucleo), 1);
		check = c_elab.pat.Util_apapi.add(check, c_elab.pat.Util_apapi.not(residente), 2);
		check = c_elab.pat.Util_apapi.add(check, c_elab.pat.Util_apapi.not(lavoratore), 4);
		check = c_elab.pat.Util_apapi.add(check, c_elab.pat.Util_apapi.not(escludi_ufficio), 10);
		
		// correzione check nel caso in cui la domanda sia presentata dopo il 31-12 
		try {
			Calendar data_dom = c_elab.pat.Util_apapi.stringdate2date((String)(tb_data_beneficio.getElement(1, 1))); 
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
		
		try {
			return c_elab.pat.Util_apapi.toDouble(check);
		} catch (NumberFormatException nfe) {
			System.out.println("ERRORE NumberFormatException in "
					+ getClass().getName() + ": " + nfe.toString());
			return 1777777777777.0;
		}
	}
}