package c_elab.pat.reddgar12;



/** query per il calcolo dei componenti beneficiari dell'importo della domanda (esclusi i condannati) del reddito di garanzia ICEF
 * @author l_leonardi
 */
public class N_beneficiari_effettivi extends QRedditoGaranzia {
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		double nBeneficiariEffettivi = 0.0;

		try 
		{
			//Il numero dei soggetti beneficiari dell'importo è il numero di soggetti presenti nel nucleo ristretto (esclusi i condannati)			
			nBeneficiariEffettivi = getNumeroBeneficiari(true);
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return nBeneficiariEffettivi;
	}
}