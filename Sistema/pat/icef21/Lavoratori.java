package c_elab.pat.icef21;

/** legge dalla domanda se ci sono i genitori lavoratori 
 * 
 * @author g_barbieri
 */
public class Lavoratori extends QDomanda {

	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		try {
			return java.lang.Math.abs(records.getDouble(1, 2)) * LocalVariables.DGL;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}