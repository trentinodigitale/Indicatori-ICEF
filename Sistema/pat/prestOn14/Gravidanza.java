package c_elab.pat.prestOn14;

/** 
 * @author s_largher
 */
public class Gravidanza extends QRichiedente {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
    	
		if (records == null)
			return 0.0;
 
		try {
			return java.lang.Math.abs(new Double((String) records.getElement(1, 2)).doubleValue());
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
    }
	
}
