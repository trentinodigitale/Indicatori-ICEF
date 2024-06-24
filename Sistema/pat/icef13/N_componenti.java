package c_elab.pat.icef13;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;

/** legge dalla domanda il n. componenti
 * @author g_barbieri
 */
public class N_componenti extends DefComponentiDich {

	/** N_componenti constructor */
	public N_componenti() {
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
			//                        1
			sql.append(
				"SELECT Familiari.familiare, R_Relazioni_parentela.peso_componente ");
			sql.append("FROM Familiari INNER JOIN ");
			sql.append("R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			
			//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
			String defDichType = DefDichElabConst.N_componenti;
			String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
			if(componenti != null && componenti.length()>0)
			{
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componenti);

				testPrintln(sql.toString());
			}
			//Fine aggiunta eventuale definizione di componenti
		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

			String id_dichiarazione = IDdomanda.substring(1);

			//   			  	 1
			sql.append(
				"SELECT     0 AS familiare, 100 AS peso_componente ");
			sql.append(
				"FROM         Dich_icef ");
			sql.append(
				"WHERE     (ID_dichiarazione = ");
			sql.append(id_dichiarazione);
			sql.append(
				")");
		}

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		try {
			
			double tot=0;
			double round = 1.0;
			double aggiusta = 0.01;
			
			for (int i = 1; i <= records.getRows(); i++) {
					// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
					double value = 1.0;
					double pesoComponente = records.getDouble(i, 2);
					value =	Sys.round( value - aggiusta, round) * pesoComponente / 100.0;
					tot = tot + value;
			}
			
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}
