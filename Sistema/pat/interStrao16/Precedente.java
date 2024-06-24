package c_elab.pat.interStrao16;

import it.clesius.clesius.util.Sys;

public class Precedente extends QDichiarazioneAttualizzata {
	
	//VERIFICAMI: verificare che siano la stesse ogni anno che cambia la dichiarazione ICEF
	private String idTpErogazioneMinimoVitale = "101";
	private String idTpErogazioneSussidioStraordinario = "102";
	private String idTpErogazioneRedditoGaranzia = "113";
	private String idTpErogazioneRedditoGaranziaAPAPI_2011 = "027";

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
				if(idTpErogazione.equals(idTpErogazioneMinimoVitale))
				{
					tot = tot + Sys.round(new Double((String) datiC5.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) datiC5.getElement(i, 1)).doubleValue() / 100.0;
				}
				else if(idTpErogazione.equals(idTpErogazioneSussidioStraordinario))
				{
					tot = tot + Sys.round(new Double((String) datiC5.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) datiC5.getElement(i, 1)).doubleValue() / 100.0;
				}
				else if(idTpErogazione.equals(idTpErogazioneRedditoGaranzia))
				{
					tot = tot + Sys.round(new Double((String) datiC5.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) datiC5.getElement(i, 1)).doubleValue() / 100.0;
				}
				else if(idTpErogazione.equals(idTpErogazioneRedditoGaranziaAPAPI_2011))
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
