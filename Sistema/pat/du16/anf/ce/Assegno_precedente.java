/** 
 *Created on 23-nov-2005 
 */

package c_elab.pat.du16.anf.ce; 
/**
 * CAMBIAMI PassaValoriDu2016 con PassaValoriDu2016
 */
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

import it.clesius.evolservlet.icef.du.PassaValoriDu2016;



/** query per il quadro C5 della dichiarazione ICEF 
 * 
 * 
 * @author g_barbieri 
 */
public class Assegno_precedente extends ElainNode {

	//CAMBIAMI: va cambiata ogni anno FATTO
	//019 - ASSEGNO REGIONALE al nucleo familiare
	private static String anno_erogazione="2015";
	//questo codice non esiste più
	private static String tp_erogazione1="008";
	private static String tp_erogazione2="019";

	/** Assegno_precedente constructor */
	public Assegno_precedente() {
	}

	/** resetta le variabili statiche 
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);

		StringBuffer sql = new StringBuffer();
		//                                       1                                  2                                 3
		sql.append(
		"SELECT PAT_erogazioni.importo AS ricevuto, Redditi_altri.importo AS dichiarato, R_Relazioni_parentela.peso_reddito ");
		sql.append("FROM Familiari INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto INNER JOIN PAT_erogazioni ON Soggetti.codice_fiscale = PAT_erogazioni.codice_fiscale INNER JOIN Redditi_altri ON Dich_icef.ID_dichiarazione = Redditi_altri.ID_dichiarazione INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE PAT_erogazioni.anno="+anno_erogazione+" AND (PAT_erogazioni.ID_tp_erogazione='"+tp_erogazione1+"' OR PAT_erogazioni.ID_tp_erogazione='"+tp_erogazione2+"') AND (Redditi_altri.ID_tp_erogazione='"+tp_erogazione1+"' OR Redditi_altri.ID_tp_erogazione='"+tp_erogazione2+"') AND Familiari.ID_domanda= ");
		sql.append(IDdomanda);
		sql.append(" AND Familiari.ID_dichiarazione in ");
		sql.append(PassaValoriDu2016.getID_dichiarazioni(IDdomanda));

		doQuery(sql.toString());
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double perc = 0.0;
		double dichiarato = 0.0;
		double ricevuto = 0.0;
		double result = 0.0;

		try {
			// somma tutti gli importi letti da PAT_erogazioni (ricevuti) e dal quadro C5 (dichiarati)
			for (int i = 1; i <=records.getRows(); i++){
				perc = getDouble(i, 3) / 100.0;
				ricevuto = (getDouble(i, 1) * perc);
				dichiarato = (getDouble(i, 2) * perc);
				// se l'importo della dich ICEF è superiore a quello letto dall'ark pagamenti 
				// ritorna quest'ultimo come importo da sottrarre
				result = result + Math.min(ricevuto, dichiarato);
			}
			return result;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
