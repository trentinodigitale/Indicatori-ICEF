package c_elab.pat.icef13;

import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** 
 * @author s_largher
 * 
 *  somma valori ICI da quadro C3 e quadro C4
 *	se redditi da partecipazione si moltiplica per la quota
 */
public abstract class QvaloriICI extends DefComponentiDich {
	
	/** QDetrazioni constructor */
	public QvaloriICI() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QvaloriICI.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QvaloriICI.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			
			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				//										1					 2				  3				 4									5
				sql.append("SELECT   Familiari.ID_dichiarazione, redd.ID_tp_impresa, redd.valore_ici, redd.quota, R_Relazioni_parentela.peso_patrimonio ");
				sql.append("FROM     Familiari ");
				sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("INNER JOIN ");
				sql.append("	(SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, quota_capitale as quota ");
				sql.append("		FROM  Redditi_partecipazione ");
				sql.append("	 UNION ");
				sql.append("	 SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, 100 AS quota ");
				sql.append("		FROM  Redditi_impresa ");
				sql.append("	) AS redd ON Familiari.ID_dichiarazione = redd.ID_dichiarazione ");
				sql.append("WHERE     (Familiari.ID_domanda = ");
				sql.append(IDdomanda);
				sql.append(") ");

				//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
				String defDichType = DefDichElabConst.ici;
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

				sql.append("SELECT   Dich_icef.ID_dichiarazione, ID_tp_impresa, valore_ici, quota, 100 as peso_patrimonio ");
				sql.append("FROM     Dich_icef  ");
				sql.append("INNER JOIN ");
				sql.append("	(SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, quota_capitale as quota ");
				sql.append("		FROM  Redditi_partecipazione ");
				sql.append("	 UNION ");
				sql.append("	 SELECT  ID_dichiarazione, ID_tp_impresa, valore_ici, 100 AS quota ");
				sql.append("		FROM  Redditi_impresa ");
				sql.append("	) AS redd ON Dich_icef.ID_dichiarazione = redd.ID_dichiarazione ");
				sql.append("WHERE     (Dich_icef.ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(") ");
			}
			
			doQuery(sql.toString());

			session.setInitialized( true );
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
