package c_elab.pat.uni16;

/** legge la sede della residenza dello studente
 *
 * @author s_largher
 */
public class Sede extends QStudente  {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
        try {
        	//1=in sede; 2=fuori sede; 3=pendolare
        	String valore = (String)(records.getElement(4,2));
        	if(valore!=null)
        	{
        		double val=new Double(valore).doubleValue();
        		if (val>3 || val<1) return 0.0;
        		else return val;
        	}
        	else
        	{
        		//se il valore non è presente passo 0 di default
        		return 0.0;
        	}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
    
}
