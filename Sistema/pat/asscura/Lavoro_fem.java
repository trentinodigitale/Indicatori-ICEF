package c_elab.pat.asscura;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** 
 * Torna Lavoro_fem.<BR>
 * Dal servizio (30500 - periodo 30502) e (30602 - periodo 30602) e per AI (>=61000) torna DLF_NEW = 1500.0,
 * per servizi precedenti torna DLF_OLD = 1000.0. 
 */
public class Lavoro_fem extends ElainNode {
 
	// Deduzione individuale per lavoro femminile fissata a 1000 € (Art. 13 comma 5)
	private final double DLF_1000 = 1000.0;
	// Nuova Deduzione su ICEF 15
	private final double DLF_1500 = 1500.0;
	// Nuova Deduzione su ICEF 18
	private final double DLF_3000 = 3000.0;
	// Nuova Deduzione su ICEF 19 e ICEF 20
	private final double DLF_4000 = 4000.0;

	private final int						ID_SERVIZIO_30500	= 30500;
	private final int						ID_SERVIZIO_30602	= 30602;
	private final int						ID_SERVIZIO_30618	= 30618;
	private final int						ID_SERVIZIO_30619	= 30619;
	private final int						ID_SERVIZIO_30620	= 30620;
	private final int						ID_SERVIZIO_30621	= 30621;
	private final int						ID_SERVIZIO_30622	= 30622;
	private final int						ID_SERVIZIO_30623	= 30623;
	private final int						ID_SERVIZIO_61000	= 61000;

	private final int						ID_PERIODO_30503	= 30503;
	private final int						ID_PERIODO_30518	= 30518;
	private final int						ID_PERIODO_30519	= 30519;
	private final int						ID_PERIODO_30520	= 30520;
	private final int						ID_PERIODO_30521	= 30521;
	private final int						ID_PERIODO_30522	= 30522;
	private final int						ID_PERIODO_30523	= 30523;
	
	private final int						ZERO_INT			= 0;

	private int								idServizio;
	private int								idPeriodo;

	/** Lavoro_fem constructor */
	public Lavoro_fem() {
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

//		if(!IDdomanda.startsWith("*")) {
			//modalità normale con domanda
			sql.append("SELECT Domande.lavoro_femminile, Domande.id_servizio, Domande.id_periodo ");
			sql.append("FROM Domande ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
//		} else {
//			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...
//			String id_dichiarazione = IDdomanda.substring(1);
//			sql.append("SELECT     0 AS Lavoro_fem, 0, 0 ");
//			sql.append("FROM         Dich_icef ");
//			sql.append("WHERE     ID_dichiarazione = ");
//			sql.append(id_dichiarazione);
//		}

		doQuery(sql.toString());

		if (records != null && records.getRows() > 0) {
			setIdServizio(records.getInteger(1, 2));
			setIdPeriodo(records.getInteger(1, 3));
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

		try {

			if ((getIdServizio() == ID_SERVIZIO_30623)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() == ID_PERIODO_30523)) {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_4000;
			}
			if ((getIdServizio() == ID_SERVIZIO_30622)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() == ID_PERIODO_30522)) {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_4000;
			}
			if ((getIdServizio() == ID_SERVIZIO_30621)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() == ID_PERIODO_30521)) {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_4000;
			}
			if ((getIdServizio() == ID_SERVIZIO_30620)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() == ID_PERIODO_30520)) {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_4000;
			}			
			if ((getIdServizio() == ID_SERVIZIO_30619)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() == ID_PERIODO_30519)) {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_4000;
			}
			if ((getIdServizio() == ID_SERVIZIO_30618)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() == ID_PERIODO_30518)) {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_3000;
			}
			if ( (getIdServizio() >= ID_SERVIZIO_61000)
					|| (getIdServizio() >= ID_SERVIZIO_30602)
					|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() >= ID_PERIODO_30503) ) {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_1500;
			} else {
				return java.lang.Math.abs(records.getDouble(1, 1)) * DLF_1000;
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
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
