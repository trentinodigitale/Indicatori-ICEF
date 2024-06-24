package c_elab.pat.coltivdir19;



public class Percentuale  extends QDatiDomanda {
	
	private static final int id_alt_sotto900			=0;
	private static final int id_alt_sopra900			=1;
	private static final int id_alt_sopra1200			=2;
	
	private static final double perc_alt_sotto900			=0.499999999999;
	private static final double perc_alt_sopra900			=0.599999999999;
	private static final double perc_alt_sopra1200			=0.699999999999;
	
	/** ritorna il valore double da assegnare all'input node
	 * 
- 50 per le aziende operanti in zone svantaggiate al di sotto dei 900 metri di altitudine
- 60 per le aziende operanti tra i 900 e i 1200 metri di altitudine
- 70 per le aziende operatni sopra i 1200 metri di altitudine.

	 * @return double
	 */
	public double getValue() {
		double percentuale = 0.0;
		int idAlt=0;
		if(records.getElement(1,records.getIndexOfColumnName("ID_altitudine"))!=null){
			
			try {
				idAlt =records.getInteger(1,records.getIndexOfColumnName("ID_altitudine"));
				switch (idAlt) {
		        case id_alt_sotto900:  
		        	percentuale=perc_alt_sotto900;
		            break;
		        case id_alt_sopra900:  
		        	percentuale=perc_alt_sopra900;
		            break;
		        case id_alt_sopra1200:  
		        	percentuale=perc_alt_sopra1200;
		            break;
				}
			} catch (Exception e) {
				
			}
			
		}
		return percentuale;
	}
	protected void reset() {
	}
}
