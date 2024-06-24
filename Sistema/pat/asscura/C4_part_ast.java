/** 
 *Created on 03-giu-2004 
 */

package c_elab.pat.asscura;

import java.util.Hashtable;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** 
 * Query per il quadro C4 della dichiarazione ICEF.<BR>
 * Dal servizio (30500 - periodo 30502) e (30602 - periodo 30602) e AI (servizio >= 61000) torna tot,
 * per servizi precedenti torna il 50 % di tot. 
 */
public class C4_part_ast extends ElainNode {

	private String							tableFamiliari		= "Familiari";
	
	private Hashtable<String, Double[]>		table_spese;

	private final int						ID_SERVIZIO_30500	= 30500;
	private final int						ID_SERVIZIO_30602	= 30602;
	private final int						ID_SERVIZIO_61000	= 61000;

	private final int						ID_PERIODO_30503	= 30503;
	
	private final double					ROUND				= 1.0;
	private final double					AGGIUSTA			= 0.01;
	private final int						ZERO_INT			= 0;
	
	private int								idServizio;
	private int								idPeriodo;
	
	/** C4_part constructor */
	public C4_part_ast() {
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

		if(!IDdomanda.startsWith("*")) {
			//modalità normale con domanda
			/*
			 * 01. R_Relazioni_parentela.peso_reddito, 
			 * 02. Redditi_partecipazione.reddito_dichiarato, 
			 * 03. Redditi_partecipazione.utile_fiscale, 
			 * 04. Redditi_partecipazione.quota, 
			 * 05. ID_tp_impresa, 
			 * 06. Redditi_partecipazione.ID_dichiarazione, 
			 * 07. Domande.id_servizio, 
			 * 08. Domande.id_periodo
			 */
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_partecipazione.reddito_dichiarato, Redditi_partecipazione.utile_fiscale, Redditi_partecipazione.quota, ID_tp_impresa, Redditi_partecipazione.ID_dichiarazione, Domande.id_servizio, Domande.id_periodo  ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_partecipazione ON "+tableFamiliari+".ID_dichiarazione = Redditi_partecipazione.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				//sql.append("WHERE ("+tableFamiliari+".ID_relazione_parentela = "+assistitoMag+" OR "+tableFamiliari+".ID_relazione_parentela = "+assistitoMin+") AND Domande.ID_domanda = ");
			sql.append(" WHERE R_Relazioni_parentela.ID_tp_relazione_parentela=1 AND Domande.ID_domanda = ");
			sql.append(IDdomanda);

		} else {
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
		
		if (records != null && records.getRows() > 0) {
			setIdServizio(records.getInteger(1, 7));
			setIdPeriodo(records.getInteger(1, 8));
		} else {
			setIdServizio(ZERO_INT);
			setIdPeriodo(ZERO_INT);
		}
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if (records == null) {
			return 0.0;
		}
		
		double tot = 0.0;
		
		table_spese = new Hashtable<String, Double[]>();

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				
				double peso_par				= records.getDouble(i, 1);
				double utile				= Sys.round(records.getDouble(i, 3) - AGGIUSTA, ROUND);
				double quota				= records.getDouble(i, 4);
				double reddito_dichiarato	= Sys.round(records.getDouble(i, 2) - AGGIUSTA, ROUND);
				
//				String impresa = records.getString(i, 5);
				
				String id_dich = records.getString(i, 6);
				
				double importo = java.lang.Math.max(reddito_dichiarato, (utile * quota / 100.0));
				
				if ( table_spese.containsKey(id_dich) ) {
					double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
					table_spese.remove(id_dich);
					table_spese.put( id_dich, new Double[]{ (Double)(imp+importo), (Double)peso_par} );
				}else {
					table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
				}

				tot = tot + importo * peso_par / 100.0;
			}
			
			if ( (getIdServizio() >= ID_SERVIZIO_61000)
					|| (getIdServizio() >= ID_SERVIZIO_30602)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() >= ID_PERIODO_30503) ) {
				
				return tot;
			} else {
				// toglie il 50% dei redditi dell'assistito
				return tot / 2;
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
	
	public Hashtable<String, Double[]> getTable_spese() {
		return table_spese;
	}

	public void setTable_spese(Hashtable<String, Double[]> table_spese) {
		this.table_spese = table_spese;
	}

	public int getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(int idServizio) {
		this.idServizio = idServizio;
	}

	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
}
