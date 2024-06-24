package c_elab.pat.reddgar;

import it.clesius.clesius.util.Sys;

public class Borse_anticrisi extends QDichiarazioneAttualizzata {
	
	//VERIFICAMI: verificare che sia la stessa ogni anno che cambia la dichiarazione ICEF
	private String idTpErogazioneBorseAnticrisi = "701";

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		if (datiC5 == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= datiC5.getRows(); i++) {
				String idTpErogazione = (String) datiC5.getElement(i, 3);
				if(idTpErogazione.equals(idTpErogazioneBorseAnticrisi))
				{
					tot = tot + Sys.round(new Double((String) datiC5.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) datiC5.getElement(i, 1)).doubleValue() / 100.0;
				}
			}
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
		
}
