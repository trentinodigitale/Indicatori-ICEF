/**  
 *Created on 03-giu-2004 
 */

package c_elab.pat.ass08.ce; 

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.anf.PassaValoriANF08;
import it.clesius.clesius.util.Sys;


/** query per il quadro C3 della dichiarazione ICEF
 * @author g_barbieri 
 */
public class C3_imp extends ElainNode {

	/** C3_imp constructor */
	public C3_imp() {
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
		//                                      1                              2
		sql.append(
			"SELECT R_Relazioni_parentela.peso_reddito, Redditi_impresa.reddito_dichiarato ");
		sql.append("FROM Familiari ");
		sql.append(
			"INNER JOIN Redditi_impresa ON Familiari.ID_dichiarazione = Redditi_impresa.ID_dichiarazione ");
		sql.append(
			"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append(
			"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(" AND Familiari.ID_dichiarazione in ");
		sql.append(PassaValoriANF08.getID_dichiarazioni(IDdomanda));

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				tot = tot + Sys.round(new Double((String) records.getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 1)).doubleValue() / 100.0;
			}
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
