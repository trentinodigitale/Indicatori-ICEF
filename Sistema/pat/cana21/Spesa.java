package c_elab.pat.cana21;

import it.clesius.clesius.util.Sys;

public class Spesa extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		double round = 1.0;
		double spesa = 0d;
		
		try {

			double renditaCatastaleRes = records.getDouble(1, records.getIndexOfColumnName("rendita_catastale_res"));
			double speseTecniche = records.getDouble(1, records.getIndexOfColumnName("importo_spese_tecniche"));
			double speseNotarili = records.getDouble(1, records.getIndexOfColumnName("importo_spese_notarili"));
			spesa = (renditaCatastaleRes * 4) + Math.min(8000d, speseNotarili) + Math.min(6000d, speseTecniche);
			
			//non qui: la spesa totale calcolata cmq la faccio vedere
			//spesa = Math.min(60000d, spesa);
			spesa = Sys.round(spesa, round);
			
			return spesa;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
