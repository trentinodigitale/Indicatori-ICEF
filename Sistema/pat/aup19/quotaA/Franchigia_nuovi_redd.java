package c_elab.pat.aup19.quotaA;

import it.clesius.evolservlet.icef.PassaValoriIcef;


public class Franchigia_nuovi_redd extends QQuotaA {
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{		
		try {
			
			return super.getFranchigia_nuovi_redd();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
		
	}
}