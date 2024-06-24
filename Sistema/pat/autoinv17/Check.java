package c_elab.pat.autoinv17;

import c_elab.pat.icef.util.ElabContext;
import it.clesius.apps2core.ElainNode;


public class Check extends ElainNode {

	public Check() {
		
	}
	
	public void reset() {
		ElabContext.getInstance().resetSession(  Check.class.getName(), IDdomanda );
	}
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			return 10000000;
		} catch (Exception e) {
			e.printStackTrace();
			return 11234067;
		}
		
	}
}
