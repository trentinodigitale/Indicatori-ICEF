package c_elab.pat.ener08;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

/**
 * 
 * @author g_barbieri
 *
 * Recupera i dati utilizzati dalle classi di trasformazione
 */
public abstract class QEnergia extends ElainNode {

	public double getValue() {
		return 0.0;
	}

	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QEnergia.class.getName(), IDdomanda, "records" );
	}

	public QEnergia(){
	}

	/**
	 * @param RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QEnergia.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();

			//							          1							2								3						4					5					6						7
			sql.append("SELECT Energia_tp_fonti.fonte, Energia_comuni.fascia_climatica, Domande.ID_comune_residenza, Energia.alt_sup_431, Energia.valore_isee, Energia.residente_trento, Domande.escludi_ufficio ");
			sql.append("FROM Energia " +
					"INNER JOIN Domande ON Energia.id_domanda = Domande.ID_domanda " +
					"INNER JOIN Energia_comuni ON Domande.ID_comune_residenza = Energia_comuni.ID_luogo " +
			"INNER JOIN Energia_tp_fonti ON Energia.ID_tp_fonte = Energia_tp_fonti.ID_tp_fonte ");
			sql.append("WHERE Domande.ID_domanda =  ");
			sql.append(IDdomanda);

			doQuery(sql.toString());

			session.setInitialized(true);
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}
	}
}