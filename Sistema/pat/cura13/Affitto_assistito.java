package c_elab.pat.cura13;

import it.clesius.db.sql.RunawayData;
import it.clesius.apps2core.ElainNode;

public class Affitto_assistito extends ElainNode {
    String assist1;
    String assist2;
    
    public Affitto_assistito() {
        super();
        this.assist1 = "6007";
        this.assist2 = "6017";
    }
    
    protected void reset() {
    }
    
    public void init(final RunawayData dataTransfer) {
        super.init(dataTransfer);
        final StringBuffer sql = new StringBuffer();
        sql.append("SELECT Detrazioni.importo , sanitest.L6_Assistiti.ID_tp_grado_parentela ");
        sql.append("FROM Familiari ");
        sql.append("INNER JOIN Detrazioni ON Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione INNER JOIN sanitest.L6_Assistiti ON Familiari.ID_domanda = sanitest.L6_Assistiti.Id_assistito ");
        sql.append("WHERE (Familiari.ID_relazione_parentela = " + this.assist1 + " OR Familiari.ID_relazione_parentela = " + this.assist2 + ") AND (Detrazioni.ID_tp_detrazione='CNL' OR Detrazioni.ID_tp_detrazione='IMR')  AND Familiari.ID_domanda = ");
        sql.append(this.IDdomanda);
        this.doQuery(sql.toString());
    }
    
    public double getValue() {
        try {
            if (((String)this.records.getElement(1, 2)).equals("3")) {
                return new Double((String)this.records.getElement(1, 1));
            }
            return 0.0;
        }
        catch (NullPointerException n) {
            System.out.println("Null pointer in " + this.getClass().getName() + ": " + n.toString());
            return 0.0;
        }
        catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + this.getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}