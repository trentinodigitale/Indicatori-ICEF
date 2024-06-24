package c_elab.pat.reddgar10;

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
			//Il numero dei soggetti residenti è dato dalla somma dei soggetti maggiorenni residenti da almeno 3 anni in Trentino e
			//dei soggetti minorenni non contando:
			//	- gli esclusi d'ufficio, 
			//	- quelli detenuti in istituto di pena, 
			//	- ospitati presso strutture residenziali socio-sanitarie o socio-assistenziali,
			//	- che non coabitano, 
			//	- non residente, convivente e genitore di minori residenti e 
			//	- soggetti con grado di parentela coniuge non residente			
			nResidenti = getNumeroResidenti();
			
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