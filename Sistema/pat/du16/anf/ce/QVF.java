package c_elab.pat.du16.anf.ce;
/**
 * CAMBIAMI PassaValoriDu2016 con PassaValoriDu2016
 */
import it.clesius.apps2core.ElainNode;
import it.clesius.evolservlet.icef.du.DatiCondizioneEconomica;
import it.clesius.evolservlet.icef.du.PassaValoriDu2016;
import java.util.Vector;

/**
 */
public class QVF extends ElainNode {

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	protected double getValue(String id_domanda, int mese) {
		Vector<DatiCondizioneEconomica> ce = (Vector<DatiCondizioneEconomica>)(PassaValoriDu2016.getCE( id_domanda ) );
		try{
			double val = ((DatiCondizioneEconomica)ce.get(mese)).getVF();
			return val;
		}catch(Exception e){
			System.out.println("\n**ERROR in QVF.getValue("+id_domanda+", "+mese+"\n");
			return mese;			
		}
	}
	
}
