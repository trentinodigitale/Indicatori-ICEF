package c_elab.pat.aupBC22;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.PassaValoriIcef;

public class Decremento extends ElainNode {

	
	public double getValue() {
		return PassaValoriIcef.getDecremento(IDdomanda); 	
	}

	@Override
	protected void reset() {
	
	}
	
	public void init(RunawayData dataTransfer) {
		
	}
	
}
