package c_elab.pat.barch20;

public class Spesa extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			double spesa = records.getDouble(1, records.getIndexOfColumnName("importo"));
			return spesa;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
