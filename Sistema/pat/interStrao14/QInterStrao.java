package c_elab.pat.interStrao14;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QInterStrao extends ElainNode {

	protected Table datiInterStrao;
	protected Table canone_is;
	protected Table particolarita;

	//CAMBIAMI: va cambiata se vengono introdotti nuovi servizi
	protected long idServizioInterStrao = 29000;

	/** QProvvisorio constructor */
	public QInterStrao() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QInterStrao.class.getName(), IDdomanda );
		datiInterStrao = null;
		particolarita = null;
		canone_is = null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QInterStrao.class.getName(), IDdomanda  );
		super.init(dataTransfer);

		if (!session.isInitialized()) {

			StringBuffer sql = new StringBuffer();
			//              	  		   1       					2  	 	
			sql.append("SELECT Doc.data_presentazione, Domande.escludi_ufficio "); 
			sql.append("FROM Domande INNER JOIN Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			datiInterStrao = records;
			
			sql = new StringBuffer();
			//						1										2								3								4
			sql.append("SELECT IS_Dati.canone_mensile_lordo, IS_Dati.canone_contributo_mensile, IS_Dati.interessi_mutuo_mensile, IS_Dati.ID_tp_scelta_sociale_icef "); 
			sql.append("FROM IS_Dati ");
			sql.append("WHERE IS_Dati.ID_Domanda = ");
			sql.append(IDdomanda);
			
			doQuery(sql.toString());
			canone_is = records;

			sql = new StringBuffer();
			//              							1                          2                         				3						 4						5
			sql.append("SELECT     tp_invalidita.coeff_invalidita, Familiari.spese_invalidita, R_Relazioni_parentela.peso_reddito, Familiari.ID_dichiarazione, IS_Familiari.spese_rsa  ");
			sql.append("FROM Soggetti INNER JOIN ");
			sql.append("Familiari INNER JOIN ");
			sql.append("tp_invalidita ON Familiari.ID_tp_invalidita = tp_invalidita.ID_tp_invalidita INNER JOIN ");
			sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN ");
			sql.append("Doc ON Familiari.ID_domanda = Doc.ID INNER JOIN ");
			sql.append("R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela LEFT OUTER JOIN ");
			sql.append("IS_Familiari ON Dich_icef.ID_soggetto = IS_Familiari.ID_soggetto AND Familiari.ID_domanda = IS_Familiari.ID_Domanda ");
			sql.append("WHERE     Familiari.ID_domanda =  ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			particolarita = records;
			
			
			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("datiInterStrao", datiInterStrao);
			session.setAttribute("particolarita", particolarita);
			session.setAttribute("canone_is", canone_is);

		} else {
			records = session.getRecords();
			datiInterStrao = (Table)session.getAttribute("datiInterverntoStraordinario");
			particolarita = (Table)session.getAttribute("particolarita");
			canone_is = (Table)session.getAttribute("canone_is");
		}
	}
	
	public static int getEta(Calendar calNascita, Calendar calRif) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int eta = 0;
		if (calRif == null)
			calRif = Calendar.getInstance();

		eta = calRif.get(Calendar.YEAR) - calNascita.get(Calendar.YEAR);

		if (calRif.get(Calendar.MONTH) - calNascita.get(Calendar.MONTH) < 0) {
			eta = eta - 1;
		}

		if (calRif.get(Calendar.MONTH) == calNascita.get(Calendar.MONTH)
				&& calRif.get(Calendar.DAY_OF_MONTH)
				< calNascita.get(Calendar.DAY_OF_MONTH)) {
			eta = eta - 1;
		}

		//        if(dataNascita.getYear()>= 90) { // l'utente è nato tra il 1890  e 1900
		//            eta += 100;
		//        }

		//System.out.println("usufrutto = " + eta);

		return eta;
	}

	public static int getMesi(Calendar calDa, Calendar calRif) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int mesi = 0;
		if (calRif == null || calDa == null)
			return 0;

		if (calDa.getTime().getTime()>calRif.getTime().getTime())
			return 0;

		mesi = 12 * (calRif.get(Calendar.YEAR) - calDa.get(Calendar.YEAR));
		mesi += calRif.get(Calendar.MONTH) - calDa.get(Calendar.MONTH);

		if (calRif.get(Calendar.DAY_OF_MONTH) - calDa.get(Calendar.DAY_OF_MONTH) < 0) {
			mesi = mesi - 1;
		}

		return mesi;
	}

	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}

	public static void main(String[] args) throws Exception {
		String dataDaStr = "31/12/2011";
		String dataPresentazioneStr = "31/01/2010";


		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataDa = Calendar.getInstance();
		dataDa.setTime((Date)formatter.parse(dataDaStr));
		dataDa.set(Calendar.HOUR_OF_DAY, 0);
		dataDa.set(Calendar.MINUTE, 0);
		dataDa.set(Calendar.SECOND, 0);
		dataDa.set(Calendar.MILLISECOND, 0);

		Calendar dataPresentazione = Calendar.getInstance();
		dataPresentazione.setTime((Date)formatter.parse(dataPresentazioneStr));
		dataPresentazione.set(Calendar.HOUR_OF_DAY, 0);
		dataPresentazione.set(Calendar.MINUTE, 0);
		dataPresentazione.set(Calendar.SECOND, 0);
		dataPresentazione.set(Calendar.MILLISECOND, 0);

		System.out.println("data da: "+dataDa.getTime().toString());
		System.out.println("data pres: "+dataPresentazione.getTime().toString());

		int mesi = getMesi(dataDa, dataPresentazione);
		System.out.println("numero mesi: "+mesi);
	}
}
