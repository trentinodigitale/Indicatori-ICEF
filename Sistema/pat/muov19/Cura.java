package c_elab.pat.muov19;

/** 
 * 
 */
public class Cura extends QAssegnazioni {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
    	
		if (records == null || records.getRows()==0)
			return 0.0;

		try {
			return java.lang.Math.abs( records.getDouble(1, 3) );
		} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
    }
	
}
