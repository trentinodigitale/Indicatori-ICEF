package c_elab.pat.ass09;


/** 
 *Created on 11-ott-2005
 */

/**
 * legge se ci sono figli minori o equiparati 
 * 
 * @author s_largher
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
			boolean disabili[] = new boolean[12];
			boolean minori7[] = new boolean[12];
			// boolean concesso[] = new boolean[12];

			// init n_figli per ogni mese a 0
			for (int j = 0; j < 12; j++) {
				n_figli[j] = 0;
				//concesso[j] = false;
			}

			// per ogni componente indicato come figlio o equiparato
			for (int i = 1; i <= records.getRows(); i++) {
				
				// e' nato?
				boolean nato[] = isNato(i, 1, 2009);

				// e' nel nucleo?
				boolean nel_nucleo[] = isNelNucleo(i, 1, 2009);
				
				// e' presente ovvero è nato AND nel_nucleo?
				boolean presente[] = c_elab.pat.Util_apapi.and(nato, nel_nucleo);

				// e' a carico nel 2009?
				//boolean a_carico[] = isACarico(i, 2009);
				
				// e' un minore di 18 anni presente nel nucleo?
				boolean minore18[] = isMinore(i, 1, 2009, 18, false);
				minore18 = c_elab.pat.Util_apapi.and(minore18, presente);

				// e' un minore di 7 anni o adottato da meno di 7 anni presente nel nucleo?
				boolean minore7[] = isMinore(i, 1, 2009, 7, true);
				minore7 = c_elab.pat.Util_apapi.and(minore7, presente);
				
				// e' disabile presente nel nucleo?
				boolean disabile[] = isDisabile(i, 1, 2009);
				disabile = c_elab.pat.Util_apapi.and(disabile, presente);
				
				// il figlio è ammissibile se è minore18 OR disabile  
				boolean ammissibile[] = c_elab.pat.Util_apapi.or(minore18, disabile);
				
				// se il figlio è ammissibile viene aggiunto agli altri
				n_figli = c_elab.pat.Util_apapi.add(n_figli, ammissibile);

				// informazioni da portarsi al di fuori del ciclo for sui figli:
				// minori7 = esiste almeno un figlio minore di 7 anni o adottato da meno di 7 anni 
				// disabili = esiste almeno un figlio disabile 
				minori7 = c_elab.pat.Util_apapi.or(minori7, minore7);
				disabili = c_elab.pat.Util_apapi.or(disabili, disabile);
				
			}

			// correzione in caso di figlio unico non disabile di età compresa tra i 7 e 18 anni
			for (int j = 0; j < 12; j++) { //per ogni mese
				if (n_figli[j] == 1) {
					if (!disabili[j]) {
						if (!minori7[j]) {
							// se nel mese j c'è: 
							// - un solo figlio 
							// - non disabile 
							// - non di età inferiore a 7 anni (ovvero tra i 7 e 18 anni)
							// allora in quel mese non ci sono figli ammissibili per cui n_figli diventa 0 
							n_figli[j] = 0;
						}
					}
				}
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