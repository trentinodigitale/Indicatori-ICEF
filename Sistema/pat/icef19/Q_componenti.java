package c_elab.pat.icef19;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import it.clesius.evolservlet.icef.PassaValoriIcef;

/** legge dalla domanda il n. componenti
 * @author g_barbieri
 */
public class Q_componenti extends DefComponentiDich {

	/** N_componenti constructor */
	public Q_componenti() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( Q_componenti.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( Q_componenti.class.getName(), IDdomanda );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				//                        1
				sql.append(
						"SELECT Familiari.familiare, R_Relazioni_parentela.peso_componente,case when  soggetti.data_nascita<DATEADD(year, -65,convert(datetime, '01/01/'+cast(anno_produzione_redditi_max+1 as varchar), 103)) then 1 else 0 end as ultra65 ");
				sql.append("FROM Familiari INNER JOIN ");
				sql.append("R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("inner join Dich_icef on Dich_icef.id_dichiarazione=Familiari.id_dichiarazione ");
				sql.append("inner join soggetti on soggetti.id_soggetto=Dich_icef.id_soggetto ");
				sql.append("inner join domande on domande.ID_domanda=Familiari.ID_domanda ");
				sql.append("inner join R_Servizi on domande.ID_servizio=R_Servizi.ID_servizio ");
				sql.append("WHERE Familiari.ID_domanda = ");
				sql.append(IDdomanda);

				//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
				String defDichType = DefDichElabConst.N_componenti;
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
			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);

				//   			  	 1
				sql.append(
						"SELECT     0 AS familiare, 100 AS peso_componente, 0 as ultra65 ");
				sql.append(
						"FROM         Dich_icef ");
				sql.append(
						"WHERE     (ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(
						")");
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
	public double getNComponenti() {
			double tot=0;
			double round = 1.0;
			double aggiusta = 0.01;

			for (int i = 1; i <= records.getRows(); i++) {
				// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
				double value = 1.0;
				double pesoComponente = records.getDouble(i, 2);
				value =	Sys.round( value - aggiusta, round) * pesoComponente / 100.0;
				tot = tot + value;
			}
			return tot;
		
	}
	
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getNComponentiUltra65() {

		try {

			
			double ultra=0;
			double tot=0;
			double round = 1.0;
			double aggiusta = 0.01;

			for (int i = 1; i <= records.getRows(); i++) {
				// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
				double value = 1.0;
				double pesoComponente = records.getDouble(i, 2);
				value =	Sys.round( value - aggiusta, round) * pesoComponente / 100.0;
				tot = tot + value;
				
			}
			
			if(tot==1) {
				for (int i = 1; i <= records.getRows(); i++) {
					// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
					double ultra65 = records.getDouble(i, 3);
					if(ultra65==1) {
						ultra = ultra+1;
					}
				}
			}

			return ultra;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}
