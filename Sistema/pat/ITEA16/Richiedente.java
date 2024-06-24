package c_elab.pat.ITEA16;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/*****************************************************
 * classe Richiedente 
 ******************************************************/

public class Richiedente extends ElainNode {

	public String getString() {
		
		Calendar data_presentazione = Calendar.getInstance();
		Calendar data_nascita = Calendar.getInstance();
		StringBuffer result = new StringBuffer(); 
		result.append("<table align='center' style='text-align: left; width: 600px; height: 60px;' border='1' cellpadding='1' cellspacing='1'><tbody>");
		result.append("<tr style='background-color: rgb(204, 204, 204); vertical-align: middle; text-align: center;'><td>Cognome</td><td>Nome</td><td>Data Nascita</td></tr>");
		try {
            try {
            	data_presentazione = records.getCalendar(1, 4);
            } catch (Exception e) {
            	data_presentazione.set(1900, 0, 1, 0, 0);
            }
			for (int i = 1; i <= records.getRows(); i++) {
	            try {
	            	data_nascita = records.getCalendar(i, 3);
	            } catch (Exception e) {
	            	data_nascita.set(1900, 0, 1, 0, 0);
	            }
            	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String timestamp = format.format(data_nascita.getTime());
				result.append("<tr>");
				result.append("<td>"+records.getString(i, 1)+"</td>");
				result.append("<td>"+records.getString(i, 2)+"</td>");
				result.append("<td>"+timestamp+"</td>");
				result.append("</tr>");
			}
			result.append("</tbody></table>");
			return(result.toString());
	    } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return "Errore nel caricamento dei soggetti del nucleo. Contattare Clesius";
        }
    }
    protected void reset() {
    }
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        //                  1               2                3                   4
        doQuery(
        "SELECT Soggetti.cognome, Soggetti.nome, Soggetti.data_nascita, Doc.data_presentazione FROM ((Familiari INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione) INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto) INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID WHERE Familiari.ID_domanda="
        + IDdomanda
        );
    }
    
    public Richiedente(){
    }
}
