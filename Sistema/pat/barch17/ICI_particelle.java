package c_elab.pat.barch17;

/**
 * legge i valori del quadro E 
 * 
 * @author s_largher 
 */
public class ICI_particelle extends QTerreniBar {
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
					ICI_terreno_oggetto_intervento += getValoreICIRiga(i);
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