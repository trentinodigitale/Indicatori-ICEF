/*
 * Richiedente.java
 *
 * Created on 29 ottobre 2007, 13.31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package c_elab.pat.muov09;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** 
  *
 *  il richiedente della domanda è il portatore di minorazione
 *  il "Beneficiario" tra i componenti per il servizio muoverSI con riduzione
 *  il soggetto nella tab. MUOVERSI_beneficiario per il servizio muoverSI senza riduzione
 */

public class Richiedente extends ElainNode {
    private static long ID_servizio_con_riduz = 8006;
    private static long ID_servizio_senza_riduz = 8007;
	
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
        
        //se SERVIZIO CON RIDUZIONE AND MUOVERSI_Richiedente.richiedente_beneficiario <> 0; Domande.cognome & Domande.nome
        if(records.getInteger(1,1) != 0) {
            doQuery(             //1              2
                    "SELECT Domande.cognome, Domande.nome "+
                    "FROM Domande "+
                    "WHERE (Domande.ID_domanda = "+
                    IDdomanda + ");"
                );        
        } else {
        
            if (this.servizio == ID_servizio_senza_riduz) {
            //Tariffa muoversi senza riduzione 

                doQuery(                //1             2
                    "SELECT Soggetti.cognome, Soggetti.nome "+
                    "FROM  Domande INNER JOIN "+
                    "MUOVERSI_Beneficiario ON Domande.ID_domanda = MUOVERSI_Beneficiario.ID_domanda INNER JOIN "+
                    "Soggetti ON MUOVERSI_Beneficiario.ID_soggetto = Soggetti.ID_soggetto "+
                    "WHERE (Domande.ID_domanda = "+
                    IDdomanda + ");"
                );
            }
            if (this.servizio == ID_servizio_con_riduz) {    //oppure else?
                //Tariffa muoversi con riduzione 
                
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