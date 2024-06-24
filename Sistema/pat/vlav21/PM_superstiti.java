package c_elab.pat.vlav21;


/** 
 * legge le erogazioni una tantum ricevute dai superstiti a seguito dell'incidente 
 * 
 *
 * @author g_barbieri
 */
public class PM_superstiti extends QCorrezioneCE {

	
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
		try {
			return records.getDouble(1,records.getIndexOfColumnName("altre_erogazioni"));
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}