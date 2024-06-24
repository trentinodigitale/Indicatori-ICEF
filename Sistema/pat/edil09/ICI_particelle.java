/**
 *Created on 17-mar-2006
 */

package c_elab.pat.edil09;

/**
 * legge i valori del quadro E 
 * 
 * @author g_barbieri
 */
public class ICI_particelle extends QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {
		
		double ICI_terreno_oggetto_intervento = 0.0;
		
		for (int i = 1; i <= records.getRows(); i++) {
			try {
				// se immobile oggetto dell'intervento 				
				if (records.getElement(i, 14) != null) {
					// se l'immobile oggetto dell'intervento è un terreno
					if ((records.getString(i, 12)).equals("TE")){
						ICI_terreno_oggetto_intervento += getValoreICIRiga(i);
					}
				}
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}		
		return ICI_terreno_oggetto_intervento;
	}
}