// Decompiled using: fernflower
// Took: 7ms

package c_elab.pat.mutui08;

public class Mq_particelle extends QImmobiliare {
   public double getValue() {
      double MQ_terreno_oggetto_intervento = 0.0D;

      for(int i = 1; i <= this.records.getRows(); ++i) {
         try {
            if (this.records.getElement(i, 11) != null && this.records.getString(i, 12).equals("TE")) {
               MQ_terreno_oggetto_intervento += this.records.getDouble(i, 13);
            }
         } catch (NullPointerException var5) {
            System.out.println("Null pointer in " + this.getClass().getName() + ": " + var5.toString());
            return 0.0D;
         } catch (NumberFormatException var6) {
            System.out.println("ERROR NumberFormatException in " + this.getClass().getName() + ": " + var6.toString());
            return 0.0D;
         }
      }

      return MQ_terreno_oggetto_intervento;
   }
}