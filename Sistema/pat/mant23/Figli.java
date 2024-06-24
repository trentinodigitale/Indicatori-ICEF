package c_elab.pat.mant23;

/**
 * 
 * @author a_pichler
 *
 */
public class Figli extends QFigli {

	public Figli() {	}

    public double getValue() {
    	
    	int tot = 0;
        try {
			tot = records.getRows();
            return tot;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
