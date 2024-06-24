package c_elab.pat.icef17;

import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import it.clesius.evolservlet.icef.PassaValoriIcef;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/** query per i dati della domanda
 * @author g_barbieri
 */
public abstract class QParticolarita extends DefComponentiDich {

	/** QParticolarita constructor */
	public QParticolarita() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QParticolarita.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QParticolarita.class.getName(), IDdomanda );

		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda                              1                        2                          3                     4                             5                        6                               7
				//              							1                          2                         3                     4                             5
				sql.append("SELECT     tp_invalidita.coeff_invalidita, Familiari.spese_invalidita, Soggetti.data_nascita, Familiari.studente, Dich_icef.anno_produzione_redditi, "); 
                //         6                               7                                         8								9
				sql.append("Doc.data_presentazione, Familiari.ID_relazione_parentela, R_Relazioni_parentela.peso_reddito,tp_invalidita.coeff_invalidita2016 ");
				sql.append("FROM Soggetti INNER JOIN ");
				sql.append("Familiari INNER JOIN ");
				sql.append("tp_invalidita ON Familiari.ID_tp_invalidita = tp_invalidita.ID_tp_invalidita INNER JOIN ");
				sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN ");
				sql.append("Doc ON Familiari.ID_domanda = Doc.ID INNER JOIN ");
				sql.append("R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("WHERE     Familiari.ID_domanda =  ");
                sql.append(IDdomanda);
				
				//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
				String defDichType = DefDichElabConst.Particolarita;
				String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
				if(componenti != null && componenti.length()>0)
				{
					sql.append(" AND Familiari.ID_dichiarazione in ");
					sql.append(componenti);
					
					testPrintln(sql.toString());
				}
				String componentiPV =  PassaValoriIcef.getID_dichiarazioni(IDdomanda); //classe metodo DefComponentiDich
				if(componentiPV != null && componentiPV.length()>0){
					sql.append(" AND Familiari.ID_dichiarazione in ");
					sql.append(componentiPV);
				}
				//Fine aggiunta eventuale definizione di componenti
				
				sql.append(" ORDER BY Dich_icef.ID_dichiarazione");
			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);

				//              	  1                      2						3					4							  5							6						7							8
				sql.append(
					"SELECT 0 AS coeff_invalidita, 0 AS spese_invalidita, Soggetti.data_nascita, 0 AS studente, Dich_icef.anno_produzione_redditi,"
					+ " Doc.data_presentazione, 0 AS ID_relazione_parentela, 100 AS peso_reddito, 0 as coeff_invalidita2016 ");
				sql.append("FROM Dich_icef INNER JOIN ");
				sql.append("Soggetti ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN ");
				sql.append("Doc ON Dich_icef.ID_dichiarazione = Doc.ID ");
				sql.append("WHERE (Dich_icef.ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(")");
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
