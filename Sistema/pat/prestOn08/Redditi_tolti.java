package c_elab.pat.prestOn08;

import it.clesius.apps2core.ElainNode;

public class Redditi_tolti extends ElainNode {
    public Redditi_tolti() {
        super();
    }
    
    protected void reset() {
    }
    
    public double getValue() {
        try {
            return 0.0;
        }
        catch (NullPointerException n) {
            System.out.println("Null pointer in " + this.getClass().getName() + ": " + n.toString());
            return 0.0;
        }
        catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + this.getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}