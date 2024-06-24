package c_elab.pat.reddgar12;



/** query per il calcolo dei componenti beneficiari (compresi i condannati) dell'importo della domanda del reddito di garanzia ICEF
 * @author l_leonardi
 */
public class N_beneficiari extends QRedditoGaranzia {
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		double nBeneficiari = 0.0;

		try 
		{
			//Il numero dei soggetti beneficiari dell'importo è il numero di soggetti presenti nel nucleo ristretto (compresi i condannati)			
			nBeneficiari = getNumeroBeneficiari(false);
			
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
		return nBeneficiari;
	}
}