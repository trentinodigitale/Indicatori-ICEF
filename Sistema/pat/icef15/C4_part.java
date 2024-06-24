package c_elab.pat.icef15;

import java.util.Hashtable;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;


/** query per il quadro C4 della dichiarazione ICEF
 * @author g_barbieri
 */
public class C4_part extends DefComponentiDich {

	private Hashtable<String,Double[]> table_spese;
	
	/** C4_part constructor */
	public C4_part() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
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
			//                             1                                     2                                         3                                4							5				6
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_partecipazione.reddito_dichiarato, Redditi_partecipazione.utile_fiscale, Redditi_partecipazione.quota, ID_tp_impresa, Redditi_partecipazione.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_partecipazione ON Familiari.ID_dichiarazione = Redditi_partecipazione.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
			String defDichType = DefDichElabConst.C4;
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

			//              				1                2              	3		  	4			5				6
			sql.append("SELECT     100 AS peso_reddito, reddito_dichiarato, utile_fiscale, quota, ID_tp_impresa, ID_dichiarazione ");
			sql.append("FROM         Redditi_partecipazione ");
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
		
		table_spese = new Hashtable<String,Double[]>();

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				
				double peso_par = records.getDouble(i, 1);
				double utile = Sys.round(records.getDouble(i, 3) - aggiusta, round);
				double quota = records.getDouble(i, 4);
				double reddito_dichiarato = Sys.round(records.getDouble(i, 2) - aggiusta, round);
				
				//String impresa = records.getString(i, 5);
				
				String id_dich = records.getString(i, 6);
				
				double importo = java.lang.Math.max(reddito_dichiarato, (utile * quota / 100.0));
				
				if ( table_spese.containsKey(id_dich) ) {
					double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
					table_spese.remove(id_dich);
					table_spese.put( id_dich, new Double[]{ (Double)(imp+importo), (Double)peso_par} );
				}else {
					table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
				}
				
				//importo = importo * peso_par / 100.0;
				
				tot = tot + importo * peso_par / 100.0;
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
	
	public Hashtable<String,Double[]> getTable_spese() {
		return table_spese;
	}

	public void setTable_spese(Hashtable<String,Double[]> table_spese) {
		this.table_spese = table_spese;
	}
}
