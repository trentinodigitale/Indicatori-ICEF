package c_elab.pat.edil09;


/** legge i valori del quadro E dove residenza è true (<>0)
 * 
 * @author g_barbieri
 */
public class RES extends c_elab.pat.icef09.RES {
	
	//CAMBIAMI: va cambiata ogni anno
	int idServizioNoRES = 12505; // per mutui
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		if(servizio==idServizioNoRES)
		{
			//se la domanda è dell'edil 09 MUTUI RES deve sempre tornare 0
	 		return 0.0;
		}
		
		return super.getValue();
	}
}