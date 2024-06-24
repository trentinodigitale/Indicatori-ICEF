// Decompiled using: fernflower
// Took: 14ms

package c_elab.pat.mutui08;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

public class QEdilDati extends ElainNode {
   protected void reset() {
      ElabStaticContext.getInstance().resetSession(QEdilDati.class.getName(), this.IDdomanda, "records");
   }

   public void init(RunawayData dataTransfer) {
      ElabStaticSession session = ElabStaticContext.getInstance().getSession(QEdilDati.class.getName(), this.IDdomanda, "records");
      if (!session.isInitialized()) {
         super.init(dataTransfer);
         StringBuffer sql = new StringBuffer();
         sql.append("SELECT EDIL_dati.prefinanziamento, EDIL_dati.indennizzi_a_minori_fin, EDIL_dati.indennizzi_a_minori_imm, EDIL_dati.mutui_no_det ");
         sql.append("FROM EDIL_dati ");
         sql.append("WHERE EDIL_dati.ID_domanda = ");
         sql.append(this.IDdomanda);
         this.doQuery(sql.toString());
         session.setInitialized(true);
         session.setRecords(this.records);
      } else {
         this.records = session.getRecords();
      }

   }

   public double getValue() {
      return 0.0D;
   }
}