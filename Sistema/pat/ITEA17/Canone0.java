package c_elab.pat.ITEA17; 



public class Canone0 extends QCanoneMutuo0 {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	
	public double getValue() {

		try {
			boolean usaDeduzioneMassima = true;
			return getValue("CNL",usaDeduzioneMassima);
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}