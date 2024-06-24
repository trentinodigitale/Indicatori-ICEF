package c_elab.pat.interStrao19;


public class Check extends QInterStrao {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		double check = 0.0;

		try 
		{
			//Check 2: l'intera domanda è stata esclusa d'ufficio
			boolean domandaEsclusaUfficio = datiInterStrao.getBoolean(1, 2);
			if(domandaEsclusaUfficio)
			{
				check += 20000000;
			}
			
			//Check 5: ICEF sopra il limite
			//Questo check lo gestisce la rete

			//se c'è un errore costruisco l'array di motivi d'esclusione, altrimenti torno 0
			if(check!=0)
			{
				check += 1000000000;
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return -1.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return -1.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return -1.0;
		}
		return check;
	}
}
