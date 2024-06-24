package c_elab.pat.reddgar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

import java.util.Calendar;


/** query per il calcolo dei componenti con residenza triennale della domanda del reddito di garanzia ICEF
 * @author l_leonardi
 */
public class N_residenti extends QRedditoGaranzia {
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		double nResidenti = 0.0;

		try 
		{
			//il numero di residenti è dato dal numero di minorenni + il numero di adulti con 3 anni di residenza (non contando gli esclusi d'ufficio e quelli con grado di parentela "Coniuge non convivente")
			nResidenti = getNumeroResidenti(false,true);
			
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
		return nResidenti;
	}
}