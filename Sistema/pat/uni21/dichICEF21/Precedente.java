package c_elab.pat.uni21.dichICEF21;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 * legge l'importo delle borse di studio percepite dall'OU nell'anno precedente
 *
 * @author s_largher
 */
public class Precedente extends ElainNode {

	// CAMBIAMI: va cambiata ogni anno
	String anno_erogazione = "2019";
	String tp_erogazione = "210";

	protected Table borsa_anno_prec_CUD;

	/** Borse_percepite constructor */
	public Precedente() {
	}

	/**
	 * resetta le variabili statiche
	 * 
	 * @see ElainNode#reset()
	 */
	protected void reset() {

		ElabContext.getInstance().resetSession(Precedente.class.getName(), IDdomanda);
		borsa_anno_prec_CUD = null;
		records = null;
	}

	/**
	 * inizializza la Table records con i valori letti dalla query sul DB
	 *
	 * @see ElainNode#init(RunawayData)
	 * @param dataTransfer
	 *            it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session = ElabContext.getInstance().getSession(Precedente.class.getName(),
				IDdomanda);
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();

			sql = new StringBuffer();
															// 1 								2
			sql.append("SELECT Redditi_altri.importo AS dichiarato, R_Relazioni_parentela.peso_reddito ");
			sql.append(" FROM Familiari "
					+ "INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione "
					+ "INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto "
//					+ "INNER JOIN PAT_erogazioni ON Soggetti.codice_fiscale = PAT_erogazioni.codice_fiscale "
					+ "INNER JOIN Redditi_altri ON Dich_icef.ID_dichiarazione = Redditi_altri.ID_dichiarazione "
					+ "INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE "
//					+ "PAT_erogazioni.anno=" + anno_erogazione
//						+ " AND PAT_erogazioni.ID_tp_erogazione='" + tp_erogazione
						+ " Redditi_altri.ID_tp_erogazione=" + tp_erogazione
						+ " AND Familiari.ID_domanda= ");
			sql.append(IDdomanda);

			doQuery(sql.toString());

			session.setInitialized(true);
			session.setRecords(records);

		} else {
			super.init(dataTransfer);
			records = session.getRecords();
		}
	}

	/**
	 * ritorna il valore double da assegnare all'input node
	 *
	 * @see ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double perc = 0.0;
		double dichiarato = 0.0;
		double result = 0.0;

		try {
			// somma tutti gli importi letti da PAT_erogazioni (ricevuti) e dal
			// quadro C5 (dichiarati)

			for (int i = 1; i <= records.getRows(); i++) {
				perc = getDouble(i, 2) / 100.0;
				dichiarato = (getDouble(i, 1) * perc);
				result = result + dichiarato;
			}
			
			return result;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": "
					+ nfe.toString());
			return 0.0;
		}
	}
}