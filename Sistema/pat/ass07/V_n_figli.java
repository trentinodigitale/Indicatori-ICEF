package c_elab.pat.ass07;

/** 
 *Created on 11-ott-2005
 */

/**
 * legge se ci sono figli minori o equiparati 
 * 
 * @author g_barbieri
 */
public class V_n_figli extends Q_assegno {

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
				boolean nato[] = isNato(i, 1, 2007);

				// e' nel nucleo?
				boolean nel_nucleo[] = isNelNucleo(i, 1, 2007);

				// e' a carico nel 2007?
				//boolean a_carico[] = isACarico(i, 2007);
				
				// e' un minore?
				boolean minore[] = isMinore(i, 1, 2007);

				// e' disabile?
				boolean disabile[] = isDisabile(i, 1, 2007);
				
				// l'assegno è concesso se il figlo è: 
				// (minore OR disabile) AND a_carico AND nel_nucleo
				minore = c_elab.pat.Util_apapi.or(minore, disabile);
				//minore = Util.and(minore, a_carico);
				minore = c_elab.pat.Util_apapi.and(minore, nel_nucleo);
				minore = c_elab.pat.Util_apapi.and(minore, nato);
				
				// se il figlio è idoneo viene aggiunto agli altri
				n_figli = c_elab.pat.Util_apapi.add(n_figli, minore);
			}

			try {
				return c_elab.pat.Util_apapi.toDouble(n_figli);
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