// Decompiled using: fernflower
// Took: 51ms

package c_elab.pat.bserv10;

import it.clesius.db.sql.RunawayData;
import it.clesius.apps2core.ElainNode;

public class Requisiti extends ElainNode {
    protected void reset() {
    }
    
    public double getValue() {
        if (this.records == null) {
            return 0.0;
        }
        try {
            if (this.records.getRows() > 0) {
                return 1.0;
            }
            return 0.0;
        }
        catch (NullPointerException n) {
            System.out.println("Null pointer in " + this.getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public void init(final RunawayData dataTransfer) {
        super.init(dataTransfer);
        final StringBuffer sql = new StringBuffer();
        sql.append("SELECT Domande.ID_provincia_residenza ");
        sql.append("FROM Domande ");
        sql.append("WHERE (Domande.ID_provincia_residenza ='TN') ");
        sql.append("AND (Domande.ID_domanda =");
        sql.append(this.IDdomanda);
        sql.append(");");
        this.doQuery(sql.toString());
        System.out.println(sql.toString());
    }
    
    public Requisiti() {
        super();
    }
}