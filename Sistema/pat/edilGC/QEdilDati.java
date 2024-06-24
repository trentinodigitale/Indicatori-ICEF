package c_elab.pat.edilGC;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** 
 * @author s_largher
 */
public class QEdilDati extends ElainNode {

	/** QEdilDati constructor */
	public QEdilDati() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QEdilDati.class.getName(), IDdomanda, "records" );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QEdilDati.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();
/*
			edil_dati	pm
			edil_dati	pi
			edil_dati	prefinanziamento_anno_prec
			edil_dati	pm
			edil_dati	pi
			edil_dati	prefinanziamento
*/			
			sql.append("SELECT EDIL_dati.pm, EDIL_dati.pi, EDIL_dati.prefinanziamento ");//, EDIL_dati.pm_anno_prec, EDIL_dati.pi_anno_prec, EDIL_dati.prefinanziamento_anno_prec ");
			sql.append("FROM EDIL_dati ");
			sql.append("WHERE EDIL_dati.ID_domanda = ");
			sql.append(IDdomanda);
			
			doQuery(sql.toString());

			//System.out.println(sql.toString());

			session.setInitialized(true);
			session.setRecords( records );
        } else {
			records = session.getRecords();
        }
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
		return 0.0;
    }
}
