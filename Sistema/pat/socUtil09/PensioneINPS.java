package c_elab.pat.socUtil09;


public class PensioneINPS extends QAttSocialmenteUtili {
    
    public double getValue() {
        try {
        	return new Double((String)(records.getElement(1,1))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public PensioneINPS(){
    }
}
