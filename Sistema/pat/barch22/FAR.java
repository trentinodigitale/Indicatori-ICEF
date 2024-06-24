package c_elab.pat.barch22;

/**
 * legge i valori del quadro E 
 * 
 * @author s_largher 
 */
public class FAR extends QTerreniBar {
	
	
	//CAMBIAMI: la franchigia sulla abitazione di residenza va verificata ogni anno
	private static double FAR = 150000.0;
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {
		
		
		for (int i = 1; i <= records.getRows(); i++) {
			try {
				// se c'è un terreno edificabile oggetto dell'intervento si azzera la franchigia sulla residenza 				
				if (records.getElement(i, 14) != null) {
					FAR = 0.0;
				}
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}		
		return FAR;
	}
}