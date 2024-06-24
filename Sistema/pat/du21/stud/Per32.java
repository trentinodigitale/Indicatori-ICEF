package c_elab.pat.du21.stud;

public class Per32 extends QTariffa2 {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
        try {
        	return new Double((String)(records.getElement(1,11))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}