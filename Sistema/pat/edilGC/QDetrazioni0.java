/**
 *Created on 03-giu-2004
 */

package c_elab.pat.edilGC;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** query per il quadro D (Detrazioni) della dichiarazione ICEF
 * @author s_largher
 */
public abstract class QDetrazioni0 extends ElainNode {
	
	private String tableFamiliari = "Edil_Familiari";
	
	protected Table tb_componenti;
	
	/** QDetrazioni constructor */
	public QDetrazioni0() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QDetrazioni0.class.getName(), IDdomanda );
		tb_componenti = null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QDetrazioni0.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			StringBuffer sqlComponenti = new StringBuffer();
			
			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda

				//                             1                                    2                     3						4
				sql.append(
					"SELECT Detrazioni.ID_tp_detrazione, R_Relazioni_parentela.peso_reddito, Detrazioni.importo, Detrazioni.ID_dichiarazione ");
				sql.append("FROM "+tableFamiliari+" ");
				sql.append(
					"INNER JOIN Detrazioni ON "+tableFamiliari+".ID_dichiarazione = Detrazioni.ID_dichiarazione ");
				sql.append(
					"INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
				sql.append(
					"INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("WHERE Domande.ID_domanda = ");
				sql.append(IDdomanda);

				
				//											1									  2
				sqlComponenti.append("SELECT "+tableFamiliari+".ID_dichiarazione, R_Relazioni_parentela.peso_reddito ");
				sqlComponenti.append("FROM "+tableFamiliari+" ");
				sqlComponenti.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
				sqlComponenti.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sqlComponenti.append("WHERE Domande.ID_domanda = ");
				sqlComponenti.append(IDdomanda);

			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);
				
				//              			1                		2            3						4
				sql.append("SELECT     ID_tp_detrazione, 100 AS peso_reddito, importo, Detrazioni.ID_dichiarazione ");
				sql.append("FROM         Detrazioni ");
				sql.append("WHERE     (ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(")");
				
				//							  				1							2
				sqlComponenti.append("SELECT     "+tableFamiliari+".ID_dichiarazione, 100 AS peso_reddito ");
				sqlComponenti.append("FROM         "+tableFamiliari+" ");
				sqlComponenti.append("WHERE     (ID_dichiarazione = ");
				sqlComponenti.append(id_dichiarazione);
				sqlComponenti.append(")");
			}
			
			doQuery(sqlComponenti.toString());
			tb_componenti = records;
			
			doQuery(sql.toString());
			
			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("tb_componenti", tb_componenti);

		} else {
			records = session.getRecords();
			tb_componenti = (Table)session.getAttribute("tb_componenti");
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
