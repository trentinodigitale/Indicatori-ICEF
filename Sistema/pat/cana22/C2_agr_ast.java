package c_elab.pat.cana22;

import java.util.Hashtable;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

/** query per il quadro C2 della dichiarazione ICEF
 * @author s_largher
 */
public class C2_agr_ast extends ElainNode {

	private String tableFamiliari = "Familiari";
	
	private Hashtable table_spese;

	/** C2_agr constructor */
	public C2_agr_ast() {
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
			//                                     1                            2                            3                             4                                 5                             6							7
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_agricoli.quantita, R_Importi_agricoli.importo, Redditi_agricoli.costo_locazione, Redditi_agricoli.costo_dipendenti, Redditi_agricoli.quota, Redditi_agricoli.ID_dichiarazione ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN Redditi_agricoli ON "+tableFamiliari+".ID_dichiarazione = Redditi_agricoli.ID_dichiarazione ");
			sql.append("INNER JOIN R_Importi_agricoli ON (Redditi_agricoli.ID_tp_agricolo = R_Importi_agricoli.ID_tp_agricolo) AND (Redditi_agricoli.ID_zona_agricola = R_Importi_agricoli.ID_zona_agricola) AND (Redditi_agricoli.ID_dich = R_Importi_agricoli.ID_dich) ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append(" WHERE R_Relazioni_parentela.ID_tp_relazione_parentela=1 AND Domande.ID_domanda = ");
			
			sql.append(IDdomanda);

		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

			String id_dichiarazione = IDdomanda.substring(1);

			//  							1                		2              				3									4									5							6						7
			sql.append("SELECT     100 AS peso_reddito, Redditi_agricoli.quantita, R_Importi_agricoli.importo, Redditi_agricoli.costo_locazione, Redditi_agricoli.costo_dipendenti, Redditi_agricoli.quota, Redditi_agricoli.ID_dichiarazione ");
			sql.append("FROM         Redditi_agricoli INNER JOIN ");
			sql.append("R_Importi_agricoli ON Redditi_agricoli.ID_tp_agricolo = R_Importi_agricoli.ID_tp_agricolo AND "); 
			sql.append("Redditi_agricoli.ID_zona_agricola = R_Importi_agricoli.ID_zona_agricola AND Redditi_agricoli.ID_dich = R_Importi_agricoli.ID_dich ");
			sql.append("WHERE     (Redditi_agricoli.ID_dichiarazione =  ");
			sql.append(id_dichiarazione);
			sql.append(")");

		}

		doQuery(sql.toString());

		//System.out.println(sql.toString());

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
				
				String id_dich = records.getString(i,7);
				double peso_par = records.getDouble(i, 1);
				
				double agricolo = (java.lang.Math.max (0,
						// quantità *
						records.getDouble(i, 2) *
						// importo -
						records.getDouble(i, 3) -
						// costo locazione terreni -
						records.getDouble(i, 4) -
						// costo dipendenti
						records.getDouble(i, 5) )); 
				// quota possesso
				double quota = records.getDouble(i, 6);
				
				double importo = agricolo * quota / 100.0;
				
				if ( table_spese.containsKey(id_dich) ) {
					double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
					table_spese.remove(id_dich);
					table_spese.put( id_dich, new Double[]{ (Double)(imp+importo), (Double)peso_par} );
				} else {
					table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
				}
				
				//importo = importo * peso_par / 100.0;
				
				tot = tot +  importo * peso_par / 100.0;
			}

			// toglie il 50% dei redditi dell'assistito
			return Sys.round(tot - aggiusta, round)/2;
			
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
