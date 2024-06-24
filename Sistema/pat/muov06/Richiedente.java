package c_elab.pat.muov06;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/*****************************************************
 * classe Richiedente per servizio muoversi 06
 *
 * s largher
 ******************************************************/

public class Richiedente extends ElainNode {
    
    private Table recordsCognomeNome;
    
    public String getString() {
        return (String)(records.getElement(1,1)) + " " + (String)(records.getElement(1,2));
    }
    protected void reset() {
    }
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        
        
        //se MUOVERSI_Richiedente.richiedente_beneficiario <> 0; Domande.cognome & Domande.nome

        doQuery(             //1              2
            "SELECT Domande.cognome, Domande.nome "+
            "FROM MUOVERSI_Richiedente INNER JOIN Domande "+
            "ON MUOVERSI_Richiedente.ID_domanda = Domande.ID_domanda "+
            "WHERE (MUOVERSI_Richiedente.richiedente_beneficiario <> 0) "+
            "AND (MUOVERSI_Richiedente.ID_domanda = "+
            IDdomanda + ");"
        );        
        
        
        recordsCognomeNome = records;
        
        if(recordsCognomeNome==null) {
        
            if (this.servizio == 8001) {
            //Tariffa muoversi senza riduzione 06

                //se MUOVERSI_Richiedente.richiedente_non_beneficiario <> 0; Soggetti.cognome & Soggetti.nome
            
                doQuery(                //1             2
                    "SELECT Soggetti.cognome, Soggetti.nome "+
                    "FROM MUOVERSI_Richiedente INNER JOIN Domande "+
                    "ON MUOVERSI_Richiedente.ID_domanda = Domande.ID_domanda "+
                    "INNER JOIN MUOVERSI_Beneficiario "+
                    "ON Domande.ID_domanda = MUOVERSI_Beneficiario.ID_domanda "+
                    "AND MUOVERSI_Richiedente.ID_domanda = MUOVERSI_Beneficiario.ID_domanda "+
                    "INNER JOIN Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto "+
                    "AND MUOVERSI_Beneficiario.ID_soggetto = Soggetti.ID_soggetto "+
                    "WHERE (MUOVERSI_Richiedente.richiedente_non_beneficiario <> 0) AND "+
                    "(MUOVERSI_Richiedente.ID_domanda = "+
                    IDdomanda + ");"
                );
                recordsCognomeNome = records;
            }
            if (this.servizio == 8000) {    //oppure else?
                //Tariffa muoversi con riduzione 06
                
                //Soggetti.cognome, Soggetti.nome da Dich_Icef
                
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
                recordsCognomeNome = records;
            }
        }
        //                  1              2
        //doQuery(
        //"SELECT Domande.cognome, Domande.nome FROM Domande WHERE (((Domande.ID_domanda)="
        //+ IDdomanda + "));"
        //);
    }
    
    //public Richiedente(){
    //}
    
}