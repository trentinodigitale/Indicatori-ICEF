package c_elab.pat.du12.acq;


public class Check extends QContrPotAcq {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		double check = 0.0;
		
		if (datiContrPotAcq == null)
			return 9.0;
		
		if (datiContrPotAcq.getRows()<1)
			return 8.0;
		
		boolean residenzaTAA =  datiContrPotAcq.getBoolean(1, 2);
		
		if(!residenzaTAA)
		{
			check += 1.0;
		}
		//check 2: l'intera domanda è stata esclusa d'ufficio
		boolean domandaEscusaUfficio = datiContrPotAcq.getBoolean(1, 3);
		boolean domandaEscusaUfficioApapi = datiContrPotAcq.getBoolean(1, 7);
		if(domandaEscusaUfficio || domandaEscusaUfficioApapi)
		{
			check += 20000000;
		}
		//check 3: domanda di reddito di garanzia in conflitto
		if(datiContrPotAcq != null && datiContrPotAcq.getString(1,6)!=null && datiContrPotAcq.getString(1,6).length()>0)
		{
			check += 3000000;
		}
		
		return check;
	}
}
