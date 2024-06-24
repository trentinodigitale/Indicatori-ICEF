package c_elab.pat.du16.anf;



/**
 * Assegno ricevuto all'estero allo stesso titolo
 * 
 */
public class Assegno_estero  extends Q_assegno{
		
  
	public double getValue() {

		if (records == null)
			return 0.0; 
		
		// NB l'idoneità alla data di presentazione si valuta il mese successivo
		try {
			double anf_estero = tb_richiedente_lavoro.getDouble(1, 14);
			return anf_estero; //NB
				
		} catch (NullPointerException n) {
			//System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}