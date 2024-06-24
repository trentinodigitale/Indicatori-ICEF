package c_elab.pat.du12.anf.ce;

import it.clesius.apps2core.ElainNode;
import it.clesius.evolservlet.icef.du.DatiCondizioneEconomica;
import it.clesius.evolservlet.icef.du.PassaValoriDu2012;

import java.util.Vector;

/**
 */
public class QPF extends ElainNode {

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
		Vector<DatiCondizioneEconomica> ce = (Vector<DatiCondizioneEconomica>)(PassaValoriDu2012.getCE( id_domanda ) );
		try{
			double val = ((DatiCondizioneEconomica)ce.get(mese)).getPF();
			return val;
		}catch(Exception e){
			System.out.println("\n**ERROR in QPF.getValue("+id_domanda+", "+mese+"\n");
			return mese;			
		}
	}
	
}
