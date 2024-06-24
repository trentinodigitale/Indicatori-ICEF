/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef;


/** se tra gli immobili indicati come residenza c'è un A1,A7,A8,A9 ritorna 
 * il coefficiente di valutazione 0.5 (valore dell'immobile al 50%)
 * altrimenti 0 (l'immobile non conta)
 * Questo valore viene moltiplicato nella rete con il valore di RES.
 * 
 * @author g_barbieri
 */
public class ALV extends QImmobiliare {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;
		
		boolean residenzaDiLusso = false;
		
		for (int i = 1; i <= records.getRows(); i++ ){
			try {
				// se la riga è fleggata come "residenza"
				if ( !(((String)records.getElement(i,1)).equals("0")) ) {
					String cat_cat = (String)records.getElement(i,4);
					if ( cat_cat.equals("A1") || cat_cat.equals("A7") || cat_cat.equals("A8") || cat_cat.equals("A9") ) {
						residenzaDiLusso = true;
					} 
				}
			} catch(NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
			}
		}
		
		if (residenzaDiLusso)
			return 0.5;
		else
			return 0.0;
	}
}