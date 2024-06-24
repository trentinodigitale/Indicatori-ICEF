package c_elab.pat.barch17;

public class N_condomini extends QDati {
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			double n_condomini = records.getDouble(1, records.getIndexOfColumnName("n_condomini"));
			return n_condomini;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
