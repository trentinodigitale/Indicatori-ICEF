package c_elab.pat.mutui08;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Indennizzo_M extends QEdilDati {
   private static Log log = LogFactory.getLog(Indennizzo_M.class);

   public double getValue() {
      try {
         return this.records.getDouble(1, 2);
      } catch (NullPointerException var2) {
         log.error("Null pointer in " + this.getClass().getName() + ": " + var2.toString());
         return 0.0D;
      } catch (NumberFormatException var3) {
         log.error("ERROR NumberFormatException in " + this.getClass().getName() + ": " + var3.toString());
         return 0.0D;
      }
   }
}