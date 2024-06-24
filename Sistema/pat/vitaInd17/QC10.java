/**
 *Created on 28-mag-2004
 */

package c_elab.pat.vitaInd17;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** query per il quadro C1 della dichiarazione ICEF
 * @author s_largher
 */
public abstract class QC10 extends ElainNode {

	//CAMBIAMI: va cambiata ogni anno
	private static String assistitoMag="26860";  
	private static String assistitoMin="26880";  

	private String tableFamiliari = "Familiari";
	
	/** QC1 constructor */
	public QC10() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession( QC10.class.getName(), IDdomanda );
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
			
			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				//                                  1                                     2                            3							4
				sql.append("SELECT Redditi_dipendente.ID_tp_reddito, R_Relazioni_parentela.peso_reddito, Redditi_dipendente.importo, Redditi_dipendente.ID_dichiarazione ");
				sql.append("FROM "+tableFamiliari+" ");
				sql.append("INNER JOIN Redditi_dipendente ON "+tableFamiliari+".ID_dichiarazione = Redditi_dipendente.ID_dichiarazione ");
				sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
				sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				
				sql.append("WHERE (R_Relazioni_parentela.ID_tp_relazione_parentela=1) AND Domande.ID_domanda = ");
				
		
				sql.append(IDdomanda);
				
			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);
				
				//              		1                2              		3							4
				sql.append("SELECT     ID_tp_reddito, 100 AS peso_reddito, importo, ID_dichiarazione ");
				sql.append("FROM         Redditi_dipendente ");
				sql.append("WHERE     (ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(")");
			}
			
			doQuery(sql.toString());
		
			session.setInitialized( true );
			session.setRecords( records );
			
		} else {
			//records = theRecords;
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
