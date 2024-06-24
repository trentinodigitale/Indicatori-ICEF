package c_elab.pat.vittime16;

public class Idonea extends QVittime {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	double valore_residenza = Math.abs(records.getDouble(1,1));
        	double valore_gratuito_patrocinio = Math.abs(records.getDouble(1,2));
        	
        	String id_tp_sex = records.getString(1,8);
			
        	if( !id_tp_sex.equals("F") ) {
        		return 0.0;
        	}
        	if( (valore_gratuito_patrocinio+valore_residenza) == 2 ) {
        		return 1.0;
        	}
        	return 0.0;
        	
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}