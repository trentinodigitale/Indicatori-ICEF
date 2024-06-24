package c_elab.pat.odont19;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


public class Familiari extends ElainNode {

	/**
	 * 
	 */
	public String getString() {
		
		Calendar		data_presentazione	= Calendar.getInstance();
		Calendar		data_nascita		= Calendar.getInstance();
		StringBuffer	result				= new StringBuffer(); 
		result.append("<table align='center' style='text-align: left; width: 600px; height: 60px;' border='1' cellpadding='1' cellspacing='1'><tbody>");
		result.append("<tr style='background-color: rgb(204, 204, 204); vertical-align: middle; text-align: center;'><td>Cognome</td><td>Nome</td><td>Data Nascita</td><td>Codice fiscale</td><td>Residenza triennale</td></tr>");
		String res = "";
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
	            
	            int val = records.getInteger(i, 5);
	            if(val==0) {
	            	res = "No";
	            }else {
	            	res = "Sì";
	            }
	            
            	SimpleDateFormat	format		= new SimpleDateFormat("dd/MM/yyyy");
                String				timestamp	= format.format(data_nascita.getTime());
				result.append("<tr>");
				result.append("<td>"+records.getString(i, 1)+"</td>");
				result.append("<td>"+records.getString(i, 2)+"</td>");
				result.append("<td>"+timestamp+"</td>");
				result.append("<td>"+records.getString(i, 4)+"</td>");
				result.append("<td>"+res+"</td>");
				result.append("</tr>");
			}
			result.append("</tbody></table>");
			return(result.toString());
	    } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return "Errore nel caricamento dei soggetti del nucleo. Contattare Clesius";
        }
    }
	
	/**
	 * 
	 */
    protected void reset() {
    }
    
    /**
     * Soggetti.cognome,<BR>
     * Soggetti.nome,<BR>
     * Soggetti.data_nascita,<BR>
     * Soggetti.codice_fiscale,<BR> 
     * familiari.residenza_storica<BR>
     */
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        doQuery(
        "SELECT Soggetti.cognome, "
        		+ " Soggetti.nome, "
        		+ " Soggetti.data_nascita, "
        		+ " Soggetti.codice_fiscale, " 
        		+ " familiari.residenza_storica "
        		+ " FROM Familiari "
        		+ " INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione "
        		+ " INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto "
        		+ " INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID "
        		+ " WHERE Familiari.ID_domanda = "
        		+ IDdomanda
        );
    }
    
    /**
     * 
     */
    public Familiari(){
    }
}
