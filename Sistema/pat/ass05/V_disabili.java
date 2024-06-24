package c_elab.pat.ass05;

/** 
 *Created on 11-ott-2005
 */

/**
 * legge se ci sono figli disabili
 * 
 * @author g_barbieri
 */
public class V_disabili extends Q_assegno {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		try {
			boolean almeno_un_disabile[] = new boolean[12];
			
			// per ogni componente indicato come figlio o equiparato
			for (int i = 1; i <= records.getRows(); i++) {
				
				// e' nato?
				boolean nato[] = isNato(i, 1, 2006);

				// e' nel nucleo?
				boolean nel_nucleo[] = isNelNucleo(i, 1, 2006);

				// e' a carico nel 2006?
				boolean a_carico[] = isACarico(i, 2006);
				
				// e' disabile?
				boolean disabile[] = isDisabile(i, 1, 2006);

				// l'agevolazione per disabili è concessa se il figlo è: 
				// disabile AND a_carico AND nel_nucleo AND nato
				disabile = Util.and(disabile, a_carico);
				disabile = Util.and(disabile, nel_nucleo);
				disabile = Util.and(disabile, nato);

				// la famiglia ha almeno un disabile se il figlio è disabile OR se un altro figlio è disabile
				almeno_un_disabile = Util.or(almeno_un_disabile, disabile);
			}

			try {
				return Util.toDouble(almeno_un_disabile);
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