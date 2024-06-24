package c_elab.pat.uni14;

/** stabilisce la fase del calcolo: Fase=0 (solo icef e fascia tasse) Fase=1 (calcolo normale, con Merito. ecc)
 *
 * @author s_largher
 */
public class Fase extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        try {
        	//utilizzo il valore Studente_unitn per verificare in che fase sono
        	String studente_unitn = (String)(records.getElement(6,2));
        	if(studente_unitn!=null)
        	{
        		//Fase=1 (calcolo normale, con Merito. ecc)
        		return 1.0;
        	}
        	else
        	{
        		//Fase=0 (solo icef e fascia tasse)
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
