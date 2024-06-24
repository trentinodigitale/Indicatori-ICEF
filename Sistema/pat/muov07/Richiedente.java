package c_elab.pat.muov07;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/*****************************************************
 * classe Richiedente per servizio muoversi 07
 *
 * s largher
 ******************************************************/

public class Richiedente extends ElainNode {
    
    public String getString() {
        return (String)(records.getElement(1,1)) + " " + (String)(records.getElement(1,2));
    }
    protected void reset() {
    }
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);

        doQuery(                               //1             
            "SELECT MUOVERSI_Richiedente.richiedente_beneficiario "+
            "FROM MUOVERSI_Richiedente "+
            "WHERE (MUOVERSI_Richiedente.ID_domanda = "+
            IDdomanda + ");"
        );        
        
        //se MUOVERSI_Richiedente.richiedente_beneficiario <> 0; Domande.cognome & Domande.nome
        if(records.getInteger(1,1) != 0) {
            doQuery(             //1              2
                    "SELECT Domande.cognome, Domande.nome "+
                    "FROM Domande "+
                    "WHERE (Domande.ID_domanda = "+
                    IDdomanda + ");"
                );        
        } else {
        
            if (this.servizio == 8003) {
            //Tariffa muoversi senza riduzione 07

                doQuery(                //1             2
                    "SELECT Soggetti.cognome, Soggetti.nome "+
                    "FROM MUOVERSI_Richiedente INNER JOIN Domande "+
                    "ON MUOVERSI_Richiedente.ID_domanda = Domande.ID_domanda "+
                    "INNER JOIN MUOVERSI_Beneficiario "+
                    "ON Domande.ID_domanda = MUOVERSI_Beneficiario.ID_domanda "+
                    "INNER JOIN Soggetti ON MUOVERSI_Beneficiario.ID_soggetto = Soggetti.ID_soggetto "+
                    "WHERE (MUOVERSI_Richiedente.richiedente_non_beneficiario <> 0) AND "+
                    "(MUOVERSI_Richiedente.ID_domanda = "+
                    IDdomanda + ");"
                );
            }
            if (this.servizio == 8002) {    //oppure else?
                //Tariffa muoversi con riduzione 07
                
                doQuery(
                    "SELECT Soggetti.cognome, Soggetti.nome "+
                    "FROM Domande INNER JOIN Familiari "+
                    "ON Domande.ID_domanda = Familiari.ID_domanda INNER JOIN "+
                    "Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN "+
                    "Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto INNER JOIN "+
                    "R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela "+
                    "WHERE (R_Relazioni_parentela.parentela = N'Beneficiario') AND (Domande.ID_domanda = "+
                    IDdomanda + ");"
                );
            }
        }
    }
}