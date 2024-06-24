/* 
 * Created on Nov 16, 2005
 *
 */
package c_elab.pat.ass08.ce;

import java.util.*; 
import it.clesius.apps2core.ElainNode;
import it.clesius.evolservlet.icef.anf.PassaValoriANF08;

/**
 * @author w_irler
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QCE extends ElainNode {

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
		Vector ce = (Vector)(PassaValoriANF08.getCE( id_domanda ) );
		try{
			double val = ((Double)ce.get(mese)).doubleValue();
			return val;
		}catch(Exception e){
System.out.println("\n**ERROR in QCE.getValue("+id_domanda+", "+mese+"\n");
			return mese;			
		}
	}
	
}
