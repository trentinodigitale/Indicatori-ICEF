/** 
 *Created on 23-nov-2005 
 */

package c_elab.pat.ass05.ce;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.anf.PassaValoriANF05;
import it.clesius.evolservlet.icef.anf.PassaValoriANF07;


/** query per il quadro C5 della dichiarazione ICEF
 * @author g_barbieri 
 */
public class Assegno_precedente extends ElainNode {

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
		
		// NB !!!!! -------||| PROVVISORIO in attesa di decidere cosa fare con gli ID inferiori!!!
		//if (new Integer(IDdomanda).intValue() > 424700) {
			// calcolo corretto
			//                                      1                               2
			sql.append(
				"SELECT ANF_erogazioni.importo AS pagato, Redditi_altri.importo AS dichiarato ");
			sql.append("FROM (((Familiari INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione) INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN ANF_erogazioni ON Soggetti.codice_fiscale = ANF_erogazioni.codice_fiscale) INNER JOIN Redditi_altri ON Dich_icef.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append("WHERE ANF_erogazioni.anno=2004 AND ANF_erogazioni.codice_intervento=8 AND Familiari.ID_domanda= ");
			sql.append(IDdomanda);
			sql.append(" AND Familiari.ID_dichiarazione in ");
			sql.append(PassaValoriANF05.getID_dichiarazioni(IDdomanda));
		//} else {
			// calcolo iniziale errato - vale ancora per le vecchie domande in attesa di decisione
			//                                      1                               2
		//	sql.append(
		//		"SELECT ANF_erogazioni.importo AS pagato, Redditi_altri.importo AS dichiarato ");
		//	sql.append("FROM (((Familiari INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione) INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN ANF_erogazioni ON Soggetti.codice_fiscale = ANF_erogazioni.codice_fiscale) INNER JOIN Redditi_altri ON Dich_icef.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
		//	sql.append("WHERE ANF_erogazioni.anno=2004 AND Familiari.ID_domanda= ");
		//	sql.append(IDdomanda);
		//	sql.append(" AND Familiari.ID_dichiarazione in ");
		//	sql.append(PassaValoriANF05.getID_dichiarazioni(IDdomanda));
		//}

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;
		double importo = 0;
		
		try {
			// somma tutti gli importi del quadro C5
			for (int i = 0; i <records.getRows(); i++){
				importo = importo + new Double((String) records.getElement(1, 2)).doubleValue();
			}
			// se l'importo della dich ICEF è superiore a quello letto dall'ark pagamenti 
			// ritorna quest'ultimo come importo da sottrarre
			if (importo >= new Double((String) records.getElement(1, 1)).doubleValue()){
				return new Double((String) records.getElement(1, 1)).doubleValue();
			} else {
				return 0.0;
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
