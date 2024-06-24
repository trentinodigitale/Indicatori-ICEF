package c_elab.pat.famNumeroseIsee;


/*****************************************************
                classe Mesi

******************************************************/


public class Mesi extends QDati {
    
    public Mesi(){		//{{INIT_CONTROLS
    }

    protected void reset() {
    }

    public double getValue() {
        int mese_start = 1;
        int mese_end = 12;

	    try {
 	        mese_start = new Integer ( records.getInteger(1, records.getIndexOfColumnName("mese_start")));
	        mese_end = new Integer ( records.getInteger(1, records.getIndexOfColumnName("mese_end")));
        } catch(NumberFormatException nfe) {
        	System.out.println("NumberFormatException in Table Assegni in " + getClass().getName() + ": " + nfe.toString());
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in Table Assegni in " + getClass().getName() + ": " + n.toString());
        }
        
        return mese_end - mese_start + 1;
   }
    
}