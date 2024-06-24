package c_elab.pat.vlav16;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/** legge la percentuale di riduzione del contributo massimo in base ai parenti superstiti
 *
 * @author g_barbieri
 */
public class Perc_riduzione extends ElainNode {

	//CAMBIAMI: id_relazione_parentela va cambiata ogni anno
	Integer coniuge    = 15400;
	Integer convivente = 15401;
	Integer figlio     = 15402;
	Integer genitore   = 15403;
	Integer parente    = 15404;
	
	
	/** Borse_percepite constructor */
	public Perc_riduzione() {
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
		//                                    1
		sql.append("SELECT Familiari.ID_relazione_parentela ");
		sql.append("FROM Familiari ");
		sql.append("WHERE Familiari.ID_domanda= ");
		sql.append(IDdomanda);
		sql.append(" ORDER BY Familiari.ID_relazione_parentela");

		doQuery(sql.toString());
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;
		
		int n_familiari = 0; //n. componenti contando coniuge/convivente e figli
		int n_genitori = 0;  //n. genitori
		int n_parenti = 0;   //n. parenti secondo grado
		
		try {
			// conteggio componenti per categoria
			for (int i = 1; i <=records.getRows(); i++){
				if (records.getInteger(i, 1) == coniuge ||
						records.getInteger(i, 1) ==convivente ||
						records.getInteger(i, 1) == figlio)
					n_familiari++;
				else if (records.getInteger(i, 1) == genitore)
					n_genitori++;
				else if (records.getInteger(i, 1) == parente)
					n_parenti++;
			}
			
			// assegnazione percentuali di riduzione come da DGP 1725/12 art.5
			if (n_familiari >= 4)
				return 0.0;
			else if (n_familiari == 3)
				return 0.1;
			else if (n_familiari == 2)
				return 0.2;
			else if (n_familiari == 1)
				return 0.3;
			else if (n_genitori >= 1)
				return 0.4;
			else if (n_parenti >= 3)
				return 0.5;
			else if (n_parenti == 2)
				return 0.6;
			else if (n_parenti == 1)
				return 0.7;
			else
				return 1.0;  // domanda non idonea 
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 1.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 1.0;
		}
	}
}