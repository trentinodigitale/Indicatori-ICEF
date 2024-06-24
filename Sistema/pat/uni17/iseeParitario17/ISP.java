package c_elab.pat.uni17.iseeParitario17;


public class ISP extends QStudente  {
    
	boolean hasIseeConnesso = false;
	
    public double getValue() {
        try {
        	
        	Double isp = records.getDouble(1, records.getIndexOfColumnName("ISP"));
        	
        	return isp;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9999999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 9999999.0;
        }
    }
    
}
