/*
 * Residenza.java
 *
 * Created on 7 febbraio 2005, 11.57
 */

package c_elab.pat.bserv05;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 * legge se lo studente richiedente ha residenza a Tn nella dichiarazione ICEF collegata
 * @author  a_pichler
 */
public class Requisiti extends ElainNode {
    
    protected void reset() {
    }
    
    public double getValue() {
        
        if (records == null)
            return 0.0;
        
        try {
            if (records.getRows() > 0) {
                //System.out.println("records.getRows() > 0");
                return 1.0;
            } else {
                return 0.0;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        super.init(dataTransfer);
        StringBuffer sql = new StringBuffer();
        	
        /*
         * prima versione
        sql.append(
        "SELECT DISTINCT Dich_icef.ID_provincia_residenza ");
        sql.append("FROM Dich_icef INNER JOIN ");
        sql.append(
        "Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione INNER JOIN ");
        sql.append(
        "R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
        sql.append(
        "WHERE (R_Relazioni_parentela.ruolo = 1) AND (Dich_icef.ID_provincia_residenza ='TN') ");
        sql.append("AND (Familiari.ID_domanda ='");
        sql.append(IDdomanda);
        sql.append("');");
        */
        
        
        //versione del 12 maggio 2008
        sql.append("SELECT Domande.ID_provincia_residenza ");
        sql.append("FROM Domande ");
        sql.append("WHERE (Domande.ID_provincia_residenza ='TN') ");
        sql.append("AND (Domande.ID_domanda =");
        sql.append(IDdomanda);
        sql.append(");");
        
        doQuery(sql.toString());
        System.out.println(sql.toString());

    }
    
    /** Creates a new instance of Residenza */
    public Requisiti() {
        
              
    }
    
}
