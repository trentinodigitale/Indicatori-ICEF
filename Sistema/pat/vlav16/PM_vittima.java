package c_elab.pat.vlav16;


/** 
 * legge il patr. finanziario della vittima alal fine del mese precedente la data dell'incidente 
 * 
 *
 * @author g_barbieri
 */
public class PM_vittima extends QCorrezioneCE {

	
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
		try {
			return records.getDouble(1,records.getIndexOfColumnName("patrimonio_finanziario"));
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}