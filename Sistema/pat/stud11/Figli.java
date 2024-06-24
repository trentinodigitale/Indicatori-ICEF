/**
 *Created on 21-giu-2007
 */

package c_elab.pat.stud11;

import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.stud.PassaValoriSTUD2011;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** legge il numero dei minori o i maggiorenni fino a 20 anni frequentanti
 * @author g_barbieri
 */
public class Figli extends ElainNode {

	private static Log log = LogFactory.getLog( Figli.class );
	private Calendar datarif  = Calendar.getInstance();
	private boolean calcolaFigli = true;
	
	/** Accompagnamento constructor */
	public Figli() {
		//CAMBIAMI: va cambiata ogni anno
		datarif.set(2012, 5, 9, 23, 59); // 9 giugno 2012
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		
		calcolaFigli = PassaValoriSTUD2011.isCalcolaFigli(IDdomanda);
		if(calcolaFigli)
		{
			StringBuffer sql = new StringBuffer();
			// alla data del 15/6/2010
			// minori di 10 anni OR (minori di 20 anni AND studenti)  
			//                                    1                     2
			sql.append("SELECT STUD_Studenti.ID_tp_scuola, Soggetti.data_nascita ");
			sql.append("FROM Soggetti INNER JOIN Dich_icef ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione LEFT OUTER JOIN STUD_Studenti ON Familiari.ID_domanda = STUD_Studenti.ID_domanda AND Dich_icef.ID_soggetto = STUD_Studenti.ID_soggetto ");
			sql.append("WHERE Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(" ORDER BY STUD_Studenti.ID_tp_scuola");
			
			doQuery(sql.toString());
		}

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	
    	if(calcolaFigli)
		{
			Calendar datanascita = Calendar.getInstance();
	    	int tot = 0;
	        try {
				for (int i = 1; i <= records.getRows(); i++) {
		            try {
		            	datanascita = records.getCalendar(i, 2);
		            } catch (Exception e) {
		            	log.error("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda, e);
		            	datanascita.set(1900, 0, 1, 0, 0);
		            }
		            if(records.getString(i, 1) == null || (records.getString(i, 1)).equals("")) {
		            	// minore non iscritto a scuola
		        		Calendar data18anni = (Calendar)datarif.clone();
		        		data18anni.add(Calendar.YEAR, -18);
		            	if (datanascita.after(data18anni)) {
		            		tot++;
		            	}
		            } else {
		            	// componente iscritto a scuola
		        		Calendar data20anni = (Calendar)datarif.clone();
		        		data20anni.add(Calendar.YEAR, -20);
		            	if (datanascita.after(data20anni)) {
		            		tot++;
		            	}
		            }
				}
	            return tot;
	        } catch(NullPointerException n) {
	            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
	            return 0.0;
	        } catch (NumberFormatException nfe) {
	            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
	            return 0.0;
	        }   
		}
    	else
    	{
    		return 1.0;
    	}
    }
}
