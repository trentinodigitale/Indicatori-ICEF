/** 
 *Created on 03-nov-2005 
 */

package c_elab.pat.asilo11;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** 
 * a_pichler
 * 
 */
public class Imm_strumentali extends ElainNode {
	
	//CAMBIAMI: va cambiata, cancellata e valutato il da farsi OGNI ANNO, vedi sotto.
	//classe di trasformazione di passaggio per gestire correttamente la classe Imm_strumentali
	//dal momento che questo servizio si sviluppa su due anni di dichiarazione ICEF diversi
	//N.B.: Ogni anno, a meno che non cambi la normativa, vanno valutate tutte le classi di trasformazione dell'ICEF standard per vedere cos'è cambiato 
	//e o renderle retrocompatibili oppure, come adesso, creare delle classi ah hoc
	private static String ID_dich_precedente ="6";
	private static String ID_dich_attuale ="7";
	
	/** Precedente constructor */
	public Imm_strumentali() {
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
		
		sql.append("SELECT   Familiari.ID_dichiarazione, ID_tp_impresa, valore_ici, quota ");
		sql.append("FROM     Familiari ");
		sql.append("INNER JOIN ");
		sql.append("	(SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, CASE WHEN ID_dich = "+ID_dich_precedente+" THEN quota ELSE quota_capitale END AS quota ");
		sql.append("		FROM  Redditi_partecipazione ");
		sql.append("	 UNION ");
		sql.append("	 SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, 100 AS quota ");
		sql.append("		FROM  Redditi_impresa ");
		sql.append("	) AS redd ON Familiari.ID_dichiarazione = redd.ID_dichiarazione ");
		sql.append("WHERE     (Familiari.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(") ");
		
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
			for (int row = 1; row <= records.getRows(); row++) {
				tot +=  Sys.round( ( records.getDouble(row,3) * records.getDouble(row,4) / 100.0 ) - aggiusta, round );
			}
			return tot;
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
