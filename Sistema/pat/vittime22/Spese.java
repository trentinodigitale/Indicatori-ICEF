package c_elab.pat.vittime22;

public class Spese extends QVittime {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	Double spesa = records.getDouble(1,records.getIndexOfColumnName("spesa"));
        	if(spesa != null) {
        		return java.lang.Math.abs(spesa);
        	}else{
        		//se il valore non è presente passo 0 di default
        		return 0.0;
        	}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}