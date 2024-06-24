package c_elab.pat.cana22;

/**
 * legge i valori del quadro E 
 * 
 * @author s_largher 
 */
public class FAR extends QTerreniBar {
	
	
	//CAMBIAMI: la franchigia sulla abitazione di residenza va verificata ogni anno
	//private static double FAR = 150000.0;
	private static double FAR = 0.0;
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {
		
		
		for (int i = 1; i <= records.getRows(); i++) {
			try {
				// se c'è un'abitazione di residenza di lusso la franchigia viene impostata a 150.000, altrimenti vale 0			
				if (records.getElement(i, 1) != null) {
					FAR = 150000.0;
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