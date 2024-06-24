package c_elab.pat.uni22.icefParitario22;


public class C1_pens extends QStudente  {
    
	boolean hasIseeConnesso = false;
	
    public double getValue() {
        try {
        	
        	Double redditi = records.getDouble(1, records.getIndexOfColumnName("redditi"));
        	
        	return redditi;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9999999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 9999999.0;
        }
    }
    
}
