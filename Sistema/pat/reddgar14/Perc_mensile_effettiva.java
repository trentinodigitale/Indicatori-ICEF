package c_elab.pat.reddgar14;



/** query per il calcolo della percentuale totale (esclusi i condannati) dell'importo mensile
 * @author l_leonardi
 */
public class Perc_mensile_effettiva extends QRedditoGaranzia {
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{		
		double percMensileEffettiva = 0.0;

		try 
		{
			//percentuale totale (esclusi i condannati) dell'importo mensile		
			percMensileEffettiva = percentualeSpettanteNonOccupati(true) * percentualeSpettanteDecuratazioneStrutturaCoperturaParziale();
			
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return percMensileEffettiva;
	}
}