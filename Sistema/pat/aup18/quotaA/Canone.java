package c_elab.pat.aup18.quotaA;


/** legge i valori del quadro D dove tipo detrazione vale 
 * - CNL (canone di locazione)
 * e applica la deduzione massima individuale per canone e mutuo
 * @author g_barbieri
 * 
 */
public class Canone extends c_elab.pat.icef18.QCanoneMutuo {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		double comp_alloggio_sociale=0.0;

		// legge la quota di compartecipazione pagata per alloggio messo a disposizione nell’ambito di un intervento socio-assistenziale
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT AUP_Dati.nucleo_sociale_spesa ");
			sql.append("FROM AUP_Dati ");
			sql.append("WHERE AUP_Dati.ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			if(records.getElement(1, 1)==null){
				return 0.0f;
			}
			comp_alloggio_sociale = records.getDouble(1, 1);
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
		}
		
		try {
			boolean usaDeduzioneMassima = true;
			return getValue("CNL",usaDeduzioneMassima) + comp_alloggio_sociale;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}