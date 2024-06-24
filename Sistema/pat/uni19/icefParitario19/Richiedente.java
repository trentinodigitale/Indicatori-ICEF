package c_elab.pat.uni19.icefParitario19;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/*****************************************************
 * classe Richiedente
 ******************************************************/

public class Richiedente extends ElainNode {
    
public String getString() {
		
		Calendar data_nascita = Calendar.getInstance();
		StringBuffer result = new StringBuffer(); 
		result.append("<table align='center' style='text-align: left; width: 600px; height: 60px;' border='1' cellpadding='1' cellspacing='1'><tbody>");
		result.append("<tr style='background-color: rgb(204, 204, 204); vertical-align: middle; text-align: center;'><td>Cognome</td><td>Nome</td><td>Data Nascita</td></tr>");
		try {
            try {
            	data_nascita = records.getCalendar(1, 3);
            } catch (Exception e) {
            	data_nascita.set(1900, 0, 1, 0, 0);
            }
        	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String timestamp = format.format(data_nascita.getTime());
			result.append("<tr>");
			result.append("<td>"+records.getString(1, 1)+"</td>");
			result.append("<td>"+records.getString(1, 2)+"</td>");
			result.append("<td>"+timestamp+"</td>");
			result.append("</tr>");
			result.append("</tbody></table>");
			return(result.toString());
	    } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return "Errore nel caricamento del richiedente. Contattare Clesius";
        }
    }


    protected void reset() {
    }
    
    
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        //               1            2                3     
        doQuery(
        "SELECT domande.cognome, domande.nome, domande.data_nascita "
        + " FROM Domande "
        + " WHERE Domande.ID_domanda= "+
        IDdomanda
        );
    }
    
    
    public Richiedente(){
    }
}

