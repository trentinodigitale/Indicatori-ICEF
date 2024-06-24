package c_elab.pat.uni21.icefParitario21;


public class PI extends QStudente  {
    
	boolean hasIseeConnesso = false;
	
    public double getValue() {
        try {
        	
        	Double pi = records.getDouble(1, records.getIndexOfColumnName("PI"));
        	
        	return pi;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9999999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 9999999.0;
        }
    }
    
}
