package c_elab.pat.asscura;

import java.util.Calendar;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import c_elab.pat.icef12.DefComponentiDich;
import c_elab.pat.icef12.QParticolarita;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;

/** legge dalla domanda la detrazione per gli invalidi
 * Attenzione usata anche per pat/ai/pat.ai15.net.html
 * @author g_barbieri
 */
public class Invalidi extends DefComponentiDich{
	//NB questa classe estende la classe standard icef12.QParticolarita

	double QBI = 5400.0;  
	double DMI = 0.0;
	public static final double QBI_2017 = 2700.0; 
	
	
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

			/*
			 * 01. tp_invalidita.coeff_invalidita, 
			 * 02. Familiari.spese_invalidita, 
			 * 03. Soggetti.data_nascita, 
			 * 04. Familiari.studente, 
			 * 05. Dich_icef.anno_produzione_redditi,
			 * 06. Doc.data_presentazione, 
			 * 07. Familiari.ID_relazione_parentela, 
			 * 08. R_Relazioni_parentela.peso_reddito,
			 * 09. R_Relazioni_parentela.ID_tp_relazione_parentela
			 */		
			//									1							2							3					4					5								
			sql.append("SELECT     tp_invalidita.coeff_invalidita, Familiari.spese_invalidita, Soggetti.data_nascita, Familiari.studente, Dich_icef.anno_produzione_redditi, "); 
			//				6							7									8								9												10
			sql.append("Doc.data_presentazione, Familiari.ID_relazione_parentela, R_Relazioni_parentela.peso_reddito,R_Relazioni_parentela.ID_tp_relazione_parentela,tp_invalidita.coeff_invalidita2016 ");
			sql.append("FROM Soggetti INNER JOIN ");
			sql.append("Familiari INNER JOIN ");
			sql.append("tp_invalidita ON Familiari.ID_tp_invalidita = tp_invalidita.ID_tp_invalidita INNER JOIN ");
			sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN ");
			sql.append("Doc ON Familiari.ID_domanda = Doc.ID INNER JOIN ");
			sql.append("R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append(" WHERE  Familiari.ID_domanda = ");
			sql.append(IDdomanda);

			//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
			String defDichType = DefDichElabConst.Particolarita;
			String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
			if(componenti != null && componenti.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componenti);

				testPrintln(sql.toString());
			}
			//Fine aggiunta eventuale definizione di componenti

			sql.append(" ORDER BY Dich_icef.ID_dichiarazione");

			doQuery(sql.toString());

			session.setInitialized( true );
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}
	}


	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null) {
			return 0.0;
		}
		
		double tot		= 0.0;
		double round	= 1.0;
		double aggiusta	= 0.01;

		try {
			
			Calendar comapre= Calendar.getInstance();
			comapre.set(Calendar.YEAR, 2016);
			comapre.set(Calendar.DAY_OF_MONTH, 1);
			comapre.set(Calendar.MONTH, 6);
			comapre.set(Calendar.HOUR, 1);
			
			for (int i = 1; i <= records.getRows(); i++) {
				int val=0;
				try {
					val=records.getInteger(i, 9);
				} catch(Exception e) {
					val=0;
				}
				if ( val!=1 ) {
					
					Calendar data_presentazione=records.getCalendar(i, 6);
					
					double coeff_invalidita= 0.0;
					double qbi =0.0;
					if(data_presentazione.after(comapre)){
						 qbi=QBI_2017;
						try{
							coeff_invalidita= records.getDouble(i, 10);
						}catch(Exception e){
							coeff_invalidita=0;
						}
					}else{
						qbi=QBI;
						coeff_invalidita= records.getDouble(i, 1);
					}
					// max perché la deduzione massima non viene più applicata
					double value = java.lang.Math.max( DMI, java.lang.Math.max( qbi *coeff_invalidita, records.getDouble(i, 2) ) );

					
					double pesoReddito = 100;  //records.getDouble(i, 8); NB da rivedere!!!!!!!!
					value =	Sys.round( value - aggiusta, round) * pesoReddito / 100.0;	

					tot = tot + value;
				}
			}
			return tot;
		} catch (Exception n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} 
	}
}