package c_elab.pat.uni21.iseeParitario21;


public class VSE extends QStudente  {
    
	boolean hasIseeConnesso = false;
	
    public double getValue() {
        try {
        	
        	Double vse = records.getDouble(1, records.getIndexOfColumnName("VSE"));
        	
        	return vse;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
    
}
