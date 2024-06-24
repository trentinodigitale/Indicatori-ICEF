package c_elab.pat.asscura;

import java.util.Hashtable;

import it.clesius.clesius.util.Sys;

/**
 * Legge i valori del quadro C1 dove tipo reddito vale:<BR>
 *  - ANP (assimilati)<BR>
 *  - DIV (diversi)<BR>
 * Dal servizio (30500 - periodo 30502) e (30602 - periodo 30602) e AI (servizio >= 61000) torna tot,
 * per servizi precedenti torna il 50 % di tot. 
 */
public class C1_aut_ast extends QC10 {

	public static final String			ANP					= "ANP";
	public static final String			DIV					= "DIV";
	
	private final int					ID_SERVIZIO_30500	= 30500;
	private final int					ID_SERVIZIO_30602	= 30602;
	private final int					ID_SERVIZIO_61000	= 61000;

	private final int					ID_PERIODO_30503	= 30503;
	
	private double						ROUND				= 1.0;
	private double						AGGIUSTA			= 0.01;
	
	private Hashtable<String, Double[]>	table_spese;
	
	/** ritorna il valore double da assegnare all'input node
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
				if ( records.getString(i, 1).equals(ANP) 
						|| records.getString(i, 1).equals(DIV) ) {
					
					double peso_par		= records.getDouble(i, 2);
					double importo		= records.getDouble(i, 3);
					String id_dich		= records.getString(i, 4);
					
					if ( table_spese.containsKey(id_dich) ) {
						double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
						table_spese.remove(id_dich);
						table_spese.put( id_dich, new Double[]{(Double)(imp+importo), (Double)peso_par} );
					}else {
						table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
					}
					
					//importo = importo * peso_par / 100.0;
					
					tot = tot +  Sys.round( importo - AGGIUSTA, ROUND ) * peso_par / 100.0;
				}
			}
			
			if ( (getIdServizio() >= ID_SERVIZIO_61000)
					|| (getIdServizio() >= ID_SERVIZIO_30602)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() >= ID_PERIODO_30503) ) {
				return tot;
			} else {
				// toglie il 50% dei redditi dell'assistito
				return tot/2;
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
}