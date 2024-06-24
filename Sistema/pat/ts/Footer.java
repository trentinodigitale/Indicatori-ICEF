/*
 * footer.java
 *
 * Created on 3 novembre 2004, 13.15
 */

package c_elab.pat.ts;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
//import it.clesius.util.Sys;

/*****************************************************
                classe footer


    a.pichler
 * indennità oriaria - servizio 1002 BORSE FSE
******************************************************/


public class Footer extends ElainNode
{
    boolean agevola = true;

    public void init(RunawayData dataTransfer){

        super.init(dataTransfer);
        StringBuffer sql0 = new StringBuffer();
        sql0.append("SELECT belief FROM C_ElaOUT");
        sql0.append(" WHERE (ID_domanda =");
        sql0.append(IDdomanda);
        sql0.append(") AND (node = 'agevolazione');");
        doQuery(sql0.toString());
        String sAgevola = (String)records.getElement(1,1);
        System.out.println(" agevola "+sAgevola);
        
        // agevola = 1 - il richiedente ha diritto all'indennità
        if(!sAgevola.equals("0.0")){
            
            // indennità oraria corrisposta in relazione alla condizione familiare
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT FSE_tp_famiglie.indennita"); 
            sql.append(" FROM FSE_Studenti INNER JOIN FSE_tp_famiglie ON FSE_Studenti.ID_famiglia = FSE_tp_famiglie.ID_famiglia");
            sql.append(" WHERE (FSE_Studenti.ID_domanda =");
            sql.append(IDdomanda);
            sql.append(");");

            //System.out.println(sql);

            doQuery(sql.toString());
        }
        else{agevola = false;}
    }
    
    
    
    public String getString()
    {
        String footer = "";
        
        try {
            if(agevola){
                String sIndennita = (String)records.getElement(1,1);
                //System.out.println("***************  INDENNITA' *******: "+sIndennita);
                footer += "Il richiedente ha diritto ad un'indennità integrativa di conciliazione pari ad <b>euro " +
                        sIndennita +"</b>, in relazione alle ore di effettiva" +
                        " frequenza del corso e dell'eventuale tirocinio formativo, con il vincolo di aver " +
                        "frequentato almeno il 70% delle lezioni del corso ed almeno il 50% delle ore di stage " +
                        "e di aver superato con esito positivo l'esame di fine corso ";

            }
        } catch (Exception e) {
            footer += "Errore in Footer.getString:" + e;
        }
        
        //footer += "<HR><CENTER>Clesius S.r.l.</CENTER></BODY></HTML>";
        
        return footer ;
           
    }
 
    
    
    
    
    protected void reset() {
    }
    
   	
}

