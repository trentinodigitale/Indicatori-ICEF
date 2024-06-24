package c_elab.pat.ITEA21_prov;

import it.clesius.clesius.util.Sys;

public class PM extends QProvvisorio{
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		// Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3)
//		double FIM = 5000.0;
		
		double round = 1.0;
		double aggiusta = 0.01;

		try {
//			double PM = 0;
//			PM = new Double((String) records.getElement(1, 3)).doubleValue() - aggiusta;
//			PM = PM - FIM;
//			if(PM < 0) {
//				PM = 0;
//			}
//			return Sys.round(PM, round);
			return Sys.round(new Double((String) records.getElement(1, 3)).doubleValue() - aggiusta, round);
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
