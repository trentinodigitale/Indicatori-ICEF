package c_elab.pat.reddgar14;


public class Mesi extends QRedditoGaranzia {
	
	//VERIFICAMI: da modificare se cambiano il numero di mesi dell'automatismo (Reddito di Garanzia)
	private int numeroMesiDefaultRedditoGaranzia = 4;
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double numeroMesi = 0.0;
		
		try 
		{
			int durataMesi =  0;
			if(servizio == idServizioRedditoGaranzia)
			{
				durataMesi = numeroMesiDefaultRedditoGaranzia; 
			}
			else if(servizio == idServizioRedditoGaranziaSociale)
			{
				durataMesi = datiRedditoGaranzia.getInteger(1, 8); 
				
			}
			numeroMesi = durataMesi;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		
		return numeroMesi;
	}
		
}
