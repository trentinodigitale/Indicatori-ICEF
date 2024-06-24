package c_elab.pat.interStrao21; 



public class Decisione_ufficio extends QInterStrao { 

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double decisioneUfficio = 0.0;
		
		try 
		{
			int idTpSceltaSocialeIcef = canone_is.getInteger(1, 4);  //la tabella non ha un nome appropriato, ma per comodità ho aggiunto solo il campo che mi interessava
			//Servizi Sociali hanno scelto di forzare icef del reddito
			if(idTpSceltaSocialeIcef == 1){
				decisioneUfficio = 1.0;
			}else if (idTpSceltaSocialeIcef == 2){ //Servizi Sociali hanno icef dei consumi
				decisioneUfficio = 2.0;
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
		
		return decisioneUfficio;
	}
}