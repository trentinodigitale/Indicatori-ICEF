/**
 *Created on 28-mag-2004
 */

package c_elab.pat.asscura;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** 
 * Query per il quadro C1 della dichiarazione ICEF
 */
public abstract class QC10 extends ElainNode {

	private String			tableFamiliari		= "Familiari";
	
	private int				idServizio;
	private int				idPeriodo;
	private final int		ZERO_INT			= 0;
	
	/** QC10 constructor */
	public QC10() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession( QC10.class.getName(), IDdomanda );
		idServizio			= 0;
		idPeriodo			= 0;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QC10.class.getName(), IDdomanda );
		
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			
			if(!IDdomanda.startsWith("*")) {
				//modalità normale con domanda
				/*
				 * 01. Redditi_dipendente.ID_tp_reddito, 
				 * 02. R_Relazioni_parentela.peso_reddito, 
				 * 03. Redditi_dipendente.importo, 
				 * 04. Redditi_dipendente.ID_dichiarazione,
				 * 05. Domande.id_servizio, 
				 * 06. Domande.id_periodo
				 */                                 
				sql.append("SELECT Redditi_dipendente.ID_tp_reddito, R_Relazioni_parentela.peso_reddito, Redditi_dipendente.importo, Redditi_dipendente.ID_dichiarazione, Domande.id_servizio, Domande.id_periodo ");
				sql.append("FROM ");
				sql.append(tableFamiliari);
				sql.append(" INNER JOIN Redditi_dipendente ON ");
				sql.append(tableFamiliari);
				sql.append(".ID_dichiarazione = Redditi_dipendente.ID_dichiarazione ");
				sql.append(" INNER JOIN Domande ON ");
				sql.append(tableFamiliari);
				sql.append(".ID_domanda = Domande.ID_domanda ");
				sql.append(" INNER JOIN R_Relazioni_parentela ON ");
				sql.append(tableFamiliari);
				sql.append(".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append(" WHERE R_Relazioni_parentela.ID_tp_relazione_parentela=1 ");
				sql.append(" AND Domande.ID_domanda = ");
				sql.append(IDdomanda);
				
			} else {
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);
				
				sql.append("SELECT  ID_tp_reddito, 100 AS peso_reddito, importo, ID_dichiarazione ");
				sql.append("FROM  Redditi_dipendente ");
				sql.append("WHERE ID_dichiarazione = ");
				sql.append(id_dichiarazione);

			}
			
			doQuery(sql.toString());
			
			session.setInitialized( true );
			session.setRecords( records );
			
			session.setAttribute( "idServizio",			idServizio);
			session.setAttribute( "idPeriodo",			idPeriodo);
		} else {
			//records = theRecords;
			records = session.getRecords();
			idServizio			= ((Integer)session.getAttribute("idServizio")).intValue();
			idPeriodo			= ((Integer)session.getAttribute("idPeriodo")).intValue();
		}
		if (records.getRows() > 0) {
			setIdServizio(records.getInteger(1, 5));
			setIdPeriodo(records.getInteger(1, 6));
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
		return 0.0;
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
