package c_elab.pat.aup22.quotaA;

/**
 * legge i valori del quadro F dove residenza è false (0)
 * 
 * @author g_barbieri
 */
public class PIAP extends c_elab.pat.icef22.QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		// se residenza = false ritorna gli immobili oltre la residenza
		boolean usaDetrazioneMaxValoreNudaProprieta = false;
		boolean residenza = false;
		double valoreImmobili = getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
		
		// AUP 2020: attualizzazione redditi: tolgo 50.000 euro
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT causa_emergenza");
        sql.append(" FROM aup_dati");
        sql.append(" WHERE id_domanda = ");
        sql.append(IDdomanda);
		
        doQuery(sql.toString());
        
        String causaEmergenza = "";
        if(records!=null && records.getRows()>0) {
        	for (int i = 1; i <= records.getRows(); i++) {
        		causaEmergenza = records.getString(i, 1);
        	}
        }		
		if("1".equals(causaEmergenza)) {
			valoreImmobili = valoreImmobili-50000;
			if (valoreImmobili < 0) {
				valoreImmobili = 0;
			}
		}
		return valoreImmobili;
	}
}