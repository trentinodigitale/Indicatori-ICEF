package c_elab.pat.aup18.quotaA;

import it.clesius.evolservlet.icef.PassaValoriIcef;

/** query per 
 * @author g_barbieri
 */
public class Forza_congruo extends QQuotaA {
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{		
		//da forzare se i servizi sociali hanno indicato congruo
		//solo se è indicato  caso sociale =1
		if(PassaValoriIcef.getForzaCongruo(IDdomanda)!=null &&  PassaValoriIcef.getForzaCongruo(IDdomanda)>0){
			return 1.0;
		}
		
		return 0;
		
	}
}