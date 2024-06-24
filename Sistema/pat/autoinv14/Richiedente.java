package c_elab.pat.autoinv14;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/*****************************************************
 * classe Richiedente
 ******************************************************/

public class Richiedente extends ElainNode {
    public String getString() {
        return (String)(records.getElement(1,1)) + " " + (String)(records.getElement(1,2));
    }
    protected void reset() {
    }
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        //                  1              2
        doQuery(
        "SELECT CASE WHEN Richiedenti.ID_Domanda IS NOT NULL THEN Soggetti.cognome ELSE Domande.cognome END AS cognome, " +
        "CASE WHEN Richiedenti.ID_Domanda IS NOT NULL THEN Soggetti.nome ELSE Domande.nome END AS nome " +
        "FROM Domande " +
        "LEFT OUTER JOIN Richiedenti ON Domande.ID_domanda = Richiedenti.ID_domanda "+
        "LEFT OUTER JOIN Soggetti ON Richiedenti.ID_soggetto = Soggetti.ID_soggetto "+
        "WHERE (((Domande.ID_domanda)="
        + IDdomanda + "));"
        );
    }
    
    public Richiedente(){
    }
}
