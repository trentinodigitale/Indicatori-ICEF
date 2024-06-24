package c_elab.pat.du12.acq;

import it.clesius.clesius.util.Sys;

/** legge i valori del quadro F dove residenza è true non di nuda proprietà e restituisce la rendita catastale
 * 
 * @author g_barbieri
 */
public class Rendita_catastale extends QImmobiliareRenditaCatastale {
	
	//VERIFICAMI: vanno verificate ogni anno
	double coefficienteTrasformazioneValoreIciInRenditaCatastale = 105;
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		double ret = 0.0;
		
		try 
		{
		
			if (records == null)
				return 0.0;
				
			// se residenza = true ritorna la residenza
			boolean usaDetrazioneMaxValoreNudaProprieta = false;
			boolean residenza = true;
			double valore = getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
			
			double round = 1.0;
			double aggiusta = 0.01;
			ret = Sys.round( (valore / coefficienteTrasformazioneValoreIciInRenditaCatastale) - aggiusta, round);
		
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
		return ret;
	}
}