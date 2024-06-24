/** 
 *Created on 03-nov-2005 
 */

package c_elab.pat.prestOn08;

/** 
 * @author a_pichler
 */
public class Canone_mese extends QRichiedente {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
    	
		if (records == null)
			return 0.0;

		try {
			return new Double((String) records.getElement(1, 3)).doubleValue();
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
    }
	
}
