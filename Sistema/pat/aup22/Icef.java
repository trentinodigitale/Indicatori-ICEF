package c_elab.pat.aup22;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.PassaValoriIcef;

public class Icef extends ElainNode {

	
	public double getValue() {
		return PassaValoriIcef.getIcef(IDdomanda); 	
	}

	@Override
	protected void reset() {
	
	}
	
	public void init(RunawayData dataTransfer) {
		
	}
	
}
