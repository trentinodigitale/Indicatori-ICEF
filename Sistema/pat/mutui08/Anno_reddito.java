// Decompiled using: fernflower
// Took: 7ms

package c_elab.pat.mutui08;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

public class Anno_reddito extends ElainNode {
   protected void reset() {
   }

   public void init(RunawayData dataTransfer) {
      super.init(dataTransfer);
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT     Dich_icef.anno_produzione_redditi ");
      sql.append("FROM Dich_icef INNER JOIN ");
      sql.append("Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione INNER JOIN ");
      sql.append("Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
      sql.append("WHERE     (Familiari.ID_relazione_parentela = 1506) AND (Domande.ID_domanda = ");
      sql.append(this.IDdomanda);
      sql.append(")");
      this.doQuery(sql.toString());
   }

   public double getValue() {
      try {
         return new Double((String)this.records.getElement(1, 1));
      } catch (NullPointerException var2) {
         System.out.println("Null pointer in " + this.getClass().getName() + ": " + var2.toString());
         return 0.0D;
      } catch (NumberFormatException var3) {
         System.out.println("ERROR NumberFormatException in " + this.getClass().getName() + ": " + var3.toString());
         return 0.0D;
      }
   }
}