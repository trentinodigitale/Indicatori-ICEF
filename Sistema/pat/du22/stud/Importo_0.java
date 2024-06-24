package c_elab.pat.du22.stud;


/** 
 * legge i motivi di non idoneità 
 * 
 * 1 resindenza in TAA
 *
 * @author l_leonardi
 */
public class Importo_0 extends QTariffa {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
		if (records == null)
			return 0.0;
    
		try {
    	   return new Double((String)(records.getElement(1,7))).doubleValue();
    
    	} catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}