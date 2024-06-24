package c_elab.pat.prestOn14;

/** 
 * 
	 Ale
 * è stata inserita una pezza per gestire il cambiamento del modo di valutare il canone di locazione (icef 2012)!
 * 
 * si applica la deduzione massima individuale e non più la deduzione familiare 
 * Nella domanda non è possibile, o meglio è complicato riprodurre questo calcolo, perciò è stato deciso di 
 * semplificare il canone sulla domanda e prendere il minimo tra LDC/12 e il canone mensile
 * 
 */
public class Canone_mese extends QRichiedente {
	//NB CAMBIAMI ANNULMENTE VERIIFICA A COSA CORRISPONDE QUESTO LDC - lo trovi in delibera
	double LDC=4000;
    /** ritorna il valore double da assegnare all'input node
     * @return double
     * 
     */
    public double getValue() {
    	
		if (records == null)
			return 0.0;

		try {
			double canone_mese=new Double((String) records.getElement(1, 3)).doubleValue();
			double canone_mese_decurtato=java.lang.Math.min(LDC/12,canone_mese); 
			return canone_mese_decurtato;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
    }
	
}
