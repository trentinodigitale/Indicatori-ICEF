package c_elab.pat.socUtil11;
 
public class Retribuzione extends QAttSocialmenteUtili {
    
    public double getValue() {
        try {
        	return new Double((String)(records.getElement(1,2))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public Retribuzione(){
    }
}
