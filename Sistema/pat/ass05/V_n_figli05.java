package c_elab.pat.ass05;

/** 
 *Created on 11-ott-2005
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;


/** legge se ci sono figli minori o equiparati per il periodo lug-dic 2005
 * @author g_barbieri
 */
public class V_n_figli05 extends Q_assegno {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		try {
			int n_figli[] = new int[12];
			
			// init n_figli per ogni mese a 0
			for (int j = 0; j < 12; j++) {
				n_figli[j] = 0;
			}
			
			// per ogni componente indicato come figlio o equiparato
			for (int i = 1; i <= records.getRows(); i++) {
				
				// e' nato?
				boolean nato[] = isNato(i, 7, 2005);

				// e' nel nucleo?
				boolean nel_nucleo[] = isNelNucleo(i, 7, 2005);

				// e' a carico nel 2005?
				boolean a_carico[] = isACarico(i, 2005);
				
				// e' un minore?
				boolean minore[] = isMinore(i, 7, 2005);

				// e' disabile?
				boolean disabile[] = isDisabile(i, 7, 2005);
				
				// l'assegno è concesso se il figlo è: 
				// (minore OR disabile) AND a_carico AND nel_nucleo AND nato
				minore = Util.or(minore, disabile);
				minore = Util.and(minore, a_carico);
				minore = Util.and(minore, nel_nucleo);
				minore = Util.and(minore, nato);

				// se il figlio è idoneo viene aggiunto agli altri
				n_figli = Util.add(n_figli, minore);
			}

			try {
				return Util.toDouble(n_figli);
			} catch (NumberFormatException nfe) {
				System.out.println("ERRORE NumberFormatException in "
						+ getClass().getName() + ": " + nfe.toString());
				return 1000000000000.0;
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "
					+ n.toString());
			return 1000000000000.0;
		}
	}
}