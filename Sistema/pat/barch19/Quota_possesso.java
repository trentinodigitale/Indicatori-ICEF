package c_elab.pat.barch19;

public class Quota_possesso extends QDati {
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		try {
			double quota_possesso = records.getDouble(1, records.getIndexOfColumnName("quota_possesso"));
			if( quota_possesso == 0 ) {
				quota_possesso = 1;
			}else {
				//quota possesso * 100 / 1000 = millesimi rapportati a percentuale
				//percentuale / 100 
				quota_possesso = quota_possesso * 0.001;
			}
			
			return quota_possesso;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
