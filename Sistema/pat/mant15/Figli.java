package c_elab.pat.mant15;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author a_pichler
 *
 */
public class Figli extends QFigli {

	private static Log log = LogFactory.getLog( Figli.class );
	
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
