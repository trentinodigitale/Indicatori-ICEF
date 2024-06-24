package c_elab.pat.uni16;

/** legge l'ICEF dell'anno precedente
 *
 * @author s_largher
 */
public class Icef_forzato extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	String verificaPresenzaValore = (String)(records.getElement(10,2));
        	if(verificaPresenzaValore==null)
        	{
        		return 99;
        	}
        	
        	//valore == -1 corrisponde a nessuna forzatura
        	double valoreNoForzatura = -1.0;
            double valoreIcef =  new Double((String)(records.getElement(10,2))).doubleValue();
            if(valoreIcef==valoreNoForzatura)
            {
            	return 99;
            }
            else
            {
            	return valoreIcef;
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
