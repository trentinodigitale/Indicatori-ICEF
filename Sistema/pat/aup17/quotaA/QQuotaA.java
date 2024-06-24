package c_elab.pat.aup17.quotaA;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QQuotaA extends ElainNode {

	protected Table dati1;
	protected Table dati2;
	protected Table dati3;

	/** QProvvisorio constructor */
	public QQuotaA() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QQuotaA.class.getName(), IDdomanda );
		dati1 = null;
		dati2 = null;
		dati3 = null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QQuotaA.class.getName(), IDdomanda  );
		super.init(dataTransfer);

		if (!session.isInitialized()) {

			StringBuffer sql = new StringBuffer();
			//              	  		   1	
			sql.append("SELECT 1 ");			
			sql.append("FROM Domande ");
			sql.append("WHERE ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			dati1 = records;

			sql = new StringBuffer();	
			//              	  		   1	
			sql.append("SELECT 1 ");			
			sql.append("FROM Domande ");
			sql.append("WHERE ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			dati2 = records;

			sql = new StringBuffer();
			//              	  		   1	
			sql.append("SELECT 1 ");			
			sql.append("FROM Domande ");
			sql.append("WHERE ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			dati3 = records;

			sql = new StringBuffer();
			//              	  		   1	
			sql.append("SELECT 1 ");			
			sql.append("FROM Domande ");
			sql.append("WHERE ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());			

			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("dati1", dati1);
			session.setAttribute("dati2", dati2);
			session.setAttribute("dati3", dati3);

		} else {
			records = session.getRecords();
			dati1 = (Table)session.getAttribute("dati1");
			dati2 = (Table)session.getAttribute("dati2");
			dati3 = (Table)session.getAttribute("dati3");
		}
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
	}
}
