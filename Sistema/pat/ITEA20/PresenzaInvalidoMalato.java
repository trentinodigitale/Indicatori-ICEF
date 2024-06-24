package c_elab.pat.ITEA20;

/** legge l'ICEF dell'anno precedente
 *
 * @author s_largher
 */
public class PresenzaInvalidoMalato extends QPublisherOnly {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	String verificaPresenzaValore = (String)(records.getElement(1,5));
        	if(verificaPresenzaValore==null)
        	{
        		return -1;
        	}
        	
        	//valore == -1 corrisponde a nessuna forzatura
        	return new Double(verificaPresenzaValore).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return -1.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return -1.0;
        }
    }
}
