/**
 *Created on 28-mag-2004
 */

package c_elab.pat.icef10;

import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** query per il quadro C1 della dichiarazione ICEF
 * @author g_barbieri
 */
public abstract class QAuto extends DefComponentiDich {

	/** QC1 constructor */
	public QAuto() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QAuto.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QAuto.class.getName(), IDdomanda );
		
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			
			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				sql.append("SELECT sum(autoveicoli) as autoveicoli ");
				sql.append("FROM Familiari ");
				sql.append("INNER JOIN Dich_icef ");
				sql.append("ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
				sql.append("WHERE Familiari.ID_domanda = ");
				sql.append(IDdomanda);
				
				//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
				String defDichType = DefDichElabConst.auto;
				String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
				if(componenti != null && componenti.length()>0)
				{
					sql.append(" AND Familiari.ID_dichiarazione in ");
					sql.append(componenti);
					
					testPrintln(sql.toString());
				}
				//Fine aggiunta eventuale definizione di componenti
			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);
				
				sql.append("SELECT 0 as autoveicoli ");
				sql.append("FROM Dich_icef ");
				sql.append("WHERE Dich_icef.ID_dichiarazione = ");
				sql.append(id_dichiarazione);
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
