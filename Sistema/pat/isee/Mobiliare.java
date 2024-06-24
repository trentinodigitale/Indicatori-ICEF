package c_elab.pat.isee;

/** 
 *Created on 03-giu-2004 
 */

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** query per il quadro E della dichiarazione ICEF
 * @author g_barbieri
 */
public class Mobiliare extends ElainNode {

	/** Mobiliare constructor */
	public Mobiliare() {
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
		//                                    1                         2                                   3
		sql.append(
			"SELECT Patrimoni_finanziari.consistenza, Patrimoni_finanziari.interessi, Patrimoni_finanziari.ID_tp_investimento ");
		sql.append("FROM Familiari ");
		sql.append(
			"INNER JOIN Patrimoni_finanziari ON Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
		sql.append(
			"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);

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
		double mobiliare_consistenza=0.0;
		double mobiliare_interessi=0.0;
                double mobiliare_importo=0.0;
                String mobiliare_ID_tp_investimento="";
                
                
		double round = 500.0;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				//arrotondamento ai 500 euro
				mobiliare_consistenza = new Double((String)records.getElement(i,1)).doubleValue();
                                mobiliare_interessi =  new Double((String)records.getElement(i,2)).doubleValue();
                                mobiliare_ID_tp_investimento = ((String)records.getElement(i,3));
                                
                                if(mobiliare_ID_tp_investimento.equals("BAN")){
                                    mobiliare_importo = java.lang.Math.max(0.0,(mobiliare_consistenza-mobiliare_interessi));
                                    mobiliare_importo = java.lang.Math.floor(mobiliare_importo/round);
                                    mobiliare_importo = mobiliare_importo * round;
                                }
                                else{
                                    mobiliare_importo = mobiliare_consistenza;
                                }
                                    
                                    tot += mobiliare_importo;
                                   
                                 
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
