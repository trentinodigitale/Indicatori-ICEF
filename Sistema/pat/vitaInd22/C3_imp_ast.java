package c_elab.pat.vitaInd22;

import java.util.Hashtable;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** query per il quadro C3 della dichiarazione ICEF
 * @author s_largher
 */
public class C3_imp_ast extends ElainNode {

	private String tableFamiliari = "Familiari";

	private Hashtable table_spese;
	
	/** C3_imp constructor */
	public C3_imp_ast() {
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
			//                                      1                              2
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, sum(Redditi_impresa.reddito_dichiarato) as reddito, Redditi_impresa.ID_dichiarazione ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_impresa ON "+tableFamiliari+".ID_dichiarazione = Redditi_impresa.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			//sql.append("WHERE ("+tableFamiliari+".ID_relazione_parentela = "+assistitoMag+" OR "+tableFamiliari+".ID_relazione_parentela = "+assistitoMin+") AND Domande.ID_domanda = ");
			sql.append(" WHERE R_Relazioni_parentela.ID_tp_relazione_parentela=1 AND Domande.ID_domanda = ");
		
			sql.append(IDdomanda);

			sql.append(" GROUP BY Redditi_impresa.ID_tp_impresa, R_Relazioni_parentela.peso_reddito, Redditi_impresa.ID_dichiarazione");
		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

			String id_dichiarazione = IDdomanda.substring(1);

			//              				1                			2
			sql.append("SELECT     100 AS peso_reddito, sum(Redditi_impresa.reddito_dichiarato) as reddito, Redditi_impresa.ID_dichiarazione ");
			sql.append("FROM         Redditi_impresa ");
			sql.append("WHERE     (ID_dichiarazione = ");
			sql.append(id_dichiarazione);
			sql.append(")");
			sql.append(" GROUP BY Redditi_impresa.ID_tp_impresa, Redditi_impresa.ID_dichiarazione");
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
		
		table_spese = new Hashtable();

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				
				double peso_par = records.getDouble(i, 1);
				double importo = records.getDouble(i, 2);
				String id_dich = records.getString(i, 3);
				
				if ( table_spese.containsKey(id_dich) ) {
					double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
					table_spese.remove(id_dich);
					table_spese.put( id_dich, new Double[]{ (Double)(imp+importo), (Double)peso_par} );
				}else {
					table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
				}
				
				//importo = importo * peso_par / 100.0;
				
				tot = tot +  Sys.round( importo - aggiusta, round ) * peso_par / 100.0;
			}
			
			// toglie il 50% dei redditi dell'assistito
			//return tot/2;
			return tot;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
	
	
	public Hashtable getTable_spese() {
		return table_spese;
	}

	public void setTable_spese(Hashtable table_spese) {
		this.table_spese = table_spese;
	}
}
