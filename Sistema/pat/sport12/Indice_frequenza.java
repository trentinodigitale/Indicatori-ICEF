package c_elab.pat.sport12;

/** 
 * @author s_largher
 */
public class Indice_frequenza extends QStudente {

      public double getValue() {
    	if (records == null)
			return 0.0;
    	
		try {
			return java.lang.Math.abs(new Double((String) records.getElement(1, 11)).doubleValue());
		
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
