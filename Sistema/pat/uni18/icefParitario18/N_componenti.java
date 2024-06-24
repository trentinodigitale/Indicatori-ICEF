package c_elab.pat.uni18.icefParitario18;


public class N_componenti extends QStudente  {
    
	boolean hasIseeConnesso = false;
	
    public double getValue() {
        try {
        	
        	Double n_componenti = records.getDouble(1, records.getIndexOfColumnName("n_componenti"));
        	
        	return n_componenti;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9999999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 9999999.0;
        }
    }
    
}
