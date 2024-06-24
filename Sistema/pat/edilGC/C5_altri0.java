/** 
 *Created on 03-giu-2004 
 */

package c_elab.pat.edilGC;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** query per il quadro C5 della dichiarazione ICEF
 * @author s_largher
 */
public class C5_altri0 extends ElainNode {

	private String tableFamiliari = "Edil_Familiari";
	
	/** C5_altri constructor */
	public C5_altri0() {
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

		if(!IDdomanda.startsWith("*"))
		{
			//modalità normale con domanda
			//                                      1                              2
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_altri.importo ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_altri ON "+tableFamiliari+".ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

			String id_dichiarazione = IDdomanda.substring(1);

			//              	1           	2
			sql.append("SELECT     100 AS peso_reddito, importo ");
			sql.append("FROM         Redditi_altri ");
			sql.append("WHERE     (ID_dichiarazione = ");
			sql.append(id_dichiarazione);
			sql.append(")");
		}

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
				tot = tot + Sys.round(records.getDouble(i, 2) - aggiusta, round) * records.getDouble(i, 1) / 100.0;
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
