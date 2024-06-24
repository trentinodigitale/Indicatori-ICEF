/**
 *Created on 17-mar-2006
 */

package c_elab.pat.edil10bis;

/**
 * 
 * @author g_barbieri
 */
public class ICI_fabbricati extends QImmobiliareEdil {
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	
	//CAMBIAMI: va cambiata ogni anno
	int idServizioNoFabbricati = 12510; // per mutui
	
	public double getValue() {
		
		if(servizio==idServizioNoFabbricati)
		{
			//se la domanda è dell'edil 10 MUTUI ICI_fabbricati deve sempre tornare 0
	 		return 0.0;
		}
		
		double ICI_fabbricati_oggetto_intervento = 0.0;
		
		for (int i = 1; i <= records.getRows(); i++) {
			try {
				// se immobile oggetto dell'intervento 				
				if (records.getElement(i, 14) != null) {
					// se l'immobile oggetto dell'intervento è un fabbricato
					if (!(records.getString(i, 12)).equals("TE")){
						ICI_fabbricati_oggetto_intervento += getValoreICIRiga(i);
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
		return ICI_fabbricati_oggetto_intervento;
	}
}