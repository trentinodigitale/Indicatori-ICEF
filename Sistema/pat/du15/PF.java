package c_elab.pat.du15;
/**
 * CAMBIAMI PassaValoriDu2015 con PassaValoriDu2015
 */
import it.clesius.apps2core.ElainNode;
import it.clesius.evolservlet.icef.du.PassaValoriDu2015;

public class PF extends ElainNode {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
        try {
            return PassaValoriDu2015.getDatiDatiCondizioneEconomica(IDdomanda).getPF();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
	
	/** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    }
}