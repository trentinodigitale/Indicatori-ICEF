/**
 *Created on 28-mag-2004
 */

package c_elab.pat.regione;

import java.util.Calendar;
import java.util.Hashtable;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** query per i dati della domanda
 * @author g_barbieri
 */
public abstract class QDomanda extends ElainNode {

	protected boolean monogenitore = true;
	protected boolean con_figli_minori = false;
	
	/** QDomanda constructor */
	public QDomanda() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QDomanda.class.getName(), IDdomanda, "records" );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDomanda.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
	    	Calendar data_nascita  = Calendar.getInstance();
	    	Calendar data_18anni  = Calendar.getInstance();
	    	Calendar data_presentazione  = Calendar.getInstance();
	    	String parentela;
			
			StringBuffer sql = new StringBuffer();
			//                                    1                    2                   3
			sql.append(
				"SELECT R_Relazioni_parentela.parentela, Soggetti.data_nascita, Doc.data_presentazione ");
			sql.append("FROM Doc INNER JOIN ((Familiari INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela) INNER JOIN (Dich_icef INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione) ON Doc.ID = Familiari.ID_domanda ");
			sql.append("WHERE Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			
	        for (int i = 1; i <= records.getRows(); i++) {
	        	parentela = records.getString(i,1);
	        	if(parentela.startsWith("Con")) // Coniuge, Convivente
	        		monogenitore = false;
	        	
	        	if(parentela.startsWith("F") || parentela.startsWith("A")) {  //FE, FR, FC, AC, AR
		            try {
		            	data_presentazione = records.getCalendar(i,3);
		            	data_nascita = records.getCalendar(i,2);
		            	data_18anni = (Calendar)data_nascita.clone();
		            	data_18anni.add(Calendar.YEAR, 18);
		            } catch (Exception e) {
		            }
		            if(data_18anni.after(data_presentazione))
		            	con_figli_minori = true;
	        	}
	        }
			
            sql = new StringBuffer();
			//                       1                        2                            3                      4
			sql.append(
				"SELECT Domande.genitori_lavoratori, Domande.n_invalidi_75 ");
			sql.append("FROM Domande ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
			
			session.setInitialized(true);
			session.setRecords( records );
			session.setAttribute("monogenitore", new Boolean(monogenitore));
			session.setAttribute("con_figli_minori", new Boolean(con_figli_minori));
		} else {
			records = session.getRecords();
			monogenitore = ((Boolean)session.getAttribute("monogenitore")).booleanValue();
			con_figli_minori = ((Boolean)session.getAttribute("con_figli_minori")).booleanValue();
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
