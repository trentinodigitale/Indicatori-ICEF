package c_elab.pat.uni13;

/** legge se lo studente fouri sede prende alloggio in sede
 *
 * @author s_largher
 */
public class Preso_alloggio extends QStudente {

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	//0=sconosciuto, 1=Si, 2=No
        	String valore = (String)(records.getElement(8,2));
        	if(valore!=null)
        	{
        		return new Double(valore).doubleValue();
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
