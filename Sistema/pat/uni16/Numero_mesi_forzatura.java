package c_elab.pat.uni16;

/** legge il numero di mesi da forzare
 *
 */
public class Numero_mesi_forzatura extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	String verificaPresenzaValore = (String)(records.getElement(9,2));
        	if(verificaPresenzaValore==null)
        	{
        		return 99;
        	}        	
        	
        	//valore == -1 corrisponde a nessuna forzatura
        	double valoreNoForzatura = -1.0;
        	double valoreNumeroMesiForzatura = new Double((String)(records.getElement(9,2))).doubleValue();
            if(valoreNumeroMesiForzatura==valoreNoForzatura)
            {
            	return 99;
            }
            else
            {
            	return valoreNumeroMesiForzatura;
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
