package c_elab.pat.du16.anf;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.sql.servlet.RDServlet;

import java.util.Calendar;
import java.util.Hashtable;


/** 
 *Created on 11-ott-2005
 */

/**
 * legge se ci sono figli minori o equiparati 
 * 
 */
public class V_n_figli_esclusi extends Q_assegno {

    //CAMBIAMI: va cambiata ogni anno - FATTO
    private static int anno = 2017;

    

    public static void main(String[] a){


    	Hashtable h = new Hashtable();
    	h.put("servletName", "http://127.0.0.1:8080/clesius/icef/servlet/data");
    	h.put("applAut","clesio");
    	h.put("serialization","true");

    	try{
    		RunawayData    hrun= new RDServlet(); 
    		hrun.init(h);
    		V_n_figli_esclusi qd=new V_n_figli_esclusi();

    		qd.setVariables("9218295", 2016, 9420, 9420, ""+107, true);
    		qd.init(hrun);
    		//System.out.println("CCCCCCCCCCCC");
    		qd.getValue();

    	} catch (Exception e){

    	}
    }
	
    
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		try {
			int presenti[] = new int[12];
			int figli_esclusi[] = new int[12];
			int minori7[] = new int[12];
			int minori18[] = new int[12];
			int maggiorenni[] = new int[12];
			int disabili[] = new int[12];
			int minoriDisabili[] = new int[12];
			int maggiorenniDisabili[] = new int[12];
			int esonerati[] = new int[12];

			// init figli_esclusi per ogni mese a 0
			for (int j = 0; j < 12; j++) {
				figli_esclusi[j] = 0;
			}

			
			// per ogni componente indicato come figlio o equiparato
			for (int i = 1; i <= records.getRows(); i++) {
				
				// e' nato?
				boolean nato[] = isNato(i, 1, anno);

				// e' nel nucleo?
				boolean nel_nucleo[] = isNelNucleo(i, 1, anno);
				
				// e' presente ovvero è nato AND nel_nucleo?
				boolean presente[] = c_elab.pat.Util_apapi.and(nato, nel_nucleo);
				presenti = c_elab.pat.Util_apapi.add(presenti, presente);

				// e' un minore di 7 anni o adottato da meno di 7 anni presente nel nucleo?
				boolean minore7[] = isMinore(i, 1, anno, 7, true);
				minore7 = c_elab.pat.Util_apapi.and(minore7, presente);
				minori7 = c_elab.pat.Util_apapi.add(minori7, minore7);
				
				// e' un minore di 18 anni presente nel nucleo?
				boolean minore18[] = isMinore(i, 1, anno, 18, false);
				minore18 = c_elab.pat.Util_apapi.and(minore18, presente);
				minori18 = c_elab.pat.Util_apapi.add(minori18, minore18);
				
				// e' un maggiorenne presente nel nucleo?
				boolean maggiorenne[] = minore18.clone();
				maggiorenne = c_elab.pat.Util_apapi.not(maggiorenne);
				maggiorenne = c_elab.pat.Util_apapi.and(maggiorenne, presente);
				maggiorenni = c_elab.pat.Util_apapi.add(maggiorenni, maggiorenne);

				// e' un disabile presente nel nucleo?
				boolean disabile[] = isDisabile(i, 1, anno);
				disabile = c_elab.pat.Util_apapi.and(disabile, presente);
				disabili = c_elab.pat.Util_apapi.add(disabili, disabile);
				
				// e' un minorenne disabile presente nel nucleo?
				boolean minoreDisabile[] = c_elab.pat.Util_apapi.and(disabile.clone(), minore18.clone());
				minoriDisabili = c_elab.pat.Util_apapi.add(minoriDisabili, minoreDisabile);
				
				// e' un maggiorenne disabile presente nel nucleo?
				boolean maggiorenneDisabile[] = c_elab.pat.Util_apapi.and(disabile.clone(), maggiorenne.clone());
				maggiorenniDisabili = c_elab.pat.Util_apapi.add(maggiorenniDisabili, maggiorenneDisabile);
				
				// e' un minore di 6 anni o tra 6 e 8 anni esonerato dall'obbligo scolastico
				boolean esonerato[] = isEsonerato(i, anno);
				esonerato = c_elab.pat.Util_apapi.and(esonerato, presente);
				esonerati = c_elab.pat.Util_apapi.add(esonerati, esonerato);
			}

			for (int j = 0; j < 12; j++) { //per ogni mese
				
				// *********************************
				// se non c'è nessun figlio disabile
				// *********************************
				if ( disabili[j] == 0 ) {
					// figlio unico ***************************
					if ( presenti[j] == 1 ) {
						if ( minori7[j] == 1 ) {
							if (esonerati[j] == 1 ) {
								figli_esclusi[j] = 1;
							} else {
								figli_esclusi[j] = 0;
							}
						} else {
							figli_esclusi[j] = 0;
						}
					// più figli ***************************
					} else {
						// più figli di cui un solo minore
						if ( minori18[j] == 1 ){
							if (esonerati[j] == 1 ) {
								figli_esclusi[j] = 2;
							} else {
								figli_esclusi[j] = 1;
							}
						// più figli di cui almeno 2 minori
						} else {
							figli_esclusi[j] = esonerati[j];
						}
					}
				}
				
				// *******************************
				// se c'è almeno 1 figlio disabile
				// *******************************
				else {
					// figlio unico ***************************
					if (presenti[j] == 1) {
						figli_esclusi[j] = 1;
					// più figli ***************************
					} else {
						// un solo minore disabile con maggiorenni non disabili
						if (minori18[j] == 1 && minoriDisabili[j] == 1 && maggiorenniDisabili[j] == 0) {
							figli_esclusi[j] = 2;
						// più figli minori di cui almeno un minore disabile con maggiorenni non disabili
						} else if (minori18[j] > 1 && minoriDisabili[j] >= 1 && maggiorenniDisabili[j] == 0) {
							figli_esclusi[j] = minori18[j];
						// minori (anche disabili) e/o maggiorenni disabili
						} else {
							figli_esclusi[j] = minori18[j] + maggiorenniDisabili[j];
						}
					}
				}
			}
			
			try {
				return c_elab.pat.Util_apapi.toDouble(figli_esclusi);
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