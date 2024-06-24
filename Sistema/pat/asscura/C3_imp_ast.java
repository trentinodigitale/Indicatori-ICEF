package c_elab.pat.asscura;

import java.util.Hashtable;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;

/** 
 * Query per il quadro C3 della dichiarazione ICEF.<BR>
 * Dal servizio (30500 - periodo 30502) e (30602 - periodo 30602) e AI (servizio >= 61000) torna tot,
 * per servizi precedenti torna il 50 % di tot. 
 */
public class C3_imp_ast extends ElainNode {

	//CAMBIAMI: va cambiata ogni anno
//	private static String assistitoMag="6200";  
//	private static String assistitoMin="6220";  
	//sostituiti da R_Relazioni_parentela.ID_tp_relazione_parentela=1
	
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
		
		if(!IDdomanda.startsWith("*")) {
			//modalità normale con domanda
			/*
			 * 01. R_Relazioni_parentela.peso_reddito, 
			 * 02. sum(Redditi_impresa.reddito_dichiarato) as reddito,
			 * 03. Redditi_impresa.ID_dichiarazione, 
			 * 04. Domande.id_servizio, 
			 * 05. Domande.id_periodo
			 */
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, sum(Redditi_impresa.reddito_dichiarato) as reddito, Redditi_impresa.ID_dichiarazione, Domande.id_servizio, Domande.id_periodo  ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_impresa ON "+tableFamiliari+".ID_dichiarazione = Redditi_impresa.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append(" WHERE R_Relazioni_parentela.ID_tp_relazione_parentela=1 AND Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(" GROUP BY Redditi_impresa.ID_tp_impresa, R_Relazioni_parentela.peso_reddito, Redditi_impresa.ID_dichiarazione, Domande.id_servizio, Domande.id_periodo");
		} else {
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
		if (records != null && records.getRows() > 0) {
			setIdServizio(records.getInteger(1, 4));
			setIdPeriodo(records.getInteger(1, 5));
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
				
				tot = tot +  Sys.round( importo - AGGIUSTA, ROUND ) * peso_par / 100.0;
			}
			
			if ( (getIdServizio() >= ID_SERVIZIO_61000)
					|| (getIdServizio() >= ID_SERVIZIO_30602)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() >= ID_PERIODO_30503) ) {
				
				return Sys.round(tot - AGGIUSTA, ROUND);
			} else {
				// toglie il 50% dei redditi dell'assistito
				return Sys.round(tot - AGGIUSTA, ROUND)/2;
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
