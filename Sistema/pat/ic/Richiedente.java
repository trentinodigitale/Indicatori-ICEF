package c_elab.pat.ic;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*****************************************************
 * classe Richiedente 
 ******************************************************/

public class Richiedente extends ElainNode {

	public String getString() {
		
		Calendar data_nascita = Calendar.getInstance();
		StringBuffer result = new StringBuffer(); 
		result.append("<table align='center' style='text-align: left; width: 600px; height: 60px;' border='1' cellpadding='1' cellspacing='1'><tbody>");
		result.append("<tr style='background-color: rgb(204, 204, 204); vertical-align: middle; text-align: center;'><td>Cognome</td><td>Nome</td><td>Data Nascita</td><td>Codice invalidità</td><td>Validità beneficio</td></tr>");
		try {
            try {
            	data_nascita = records.getCalendar(1, 3);
            } catch (Exception e) {
            	data_nascita.set(1900, 0, 1, 0, 0);
            }
        	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String timestamp = format.format(data_nascita.getTime());
			result.append("<tr style='text-align: center;'>");
			result.append("<td>"+records.getString(1, 1)+"</td>");
			result.append("<td>"+records.getString(1, 2)+"</td>");
			result.append("<td>"+timestamp+"</td>");
			result.append("<td>"+records.getString(1, 4)+"</td>");
			result.append("<td>"+records.getString(1, 5)+"</td>");
			result.append("</tr>");
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
        //                 1             2                3 					4							5
        doQuery(
        "SELECT Domande.cognome, Domande.nome, Domande.data_nascita, IC_tp_invalidita.tp_invalidita, IC_dati.elab_anno_competenza "+
        " FROM Domande "+
        " INNER JOIN IC_dati ON Domande.ID_domanda=IC_dati.ID_domanda "+
        " INNER JOIN IC_tp_invalidita ON IC_dati.ID_tp_invalidita=IC_tp_invalidita.ID_tp_invalidita "+
        " WHERE Domande.ID_domanda = "
        + IDdomanda
        );
    }
    
    public Richiedente(){
    }
}
