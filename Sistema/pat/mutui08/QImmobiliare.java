// Decompiled using: fernflower
// Took: 18ms

package c_elab.pat.mutui08;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import c_elab.pat.icef.Usufrutto;
import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import java.util.Calendar;

public abstract class QImmobiliare extends ElainNode {
   private Calendar BirthDate(String strDataNascita, Calendar dataRif) {
      if (strDataNascita != null && !strDataNascita.equals("")) {
         int anno = new Integer(strDataNascita.substring(0, 4));
         int mese = new Integer(strDataNascita.substring(5, 7)) - 1;
         int giorno = new Integer(strDataNascita.substring(8, 10));
         Calendar rightNow = Calendar.getInstance();
         rightNow.set(anno, mese, giorno);
         return rightNow;
      } else {
         return dataRif;
      }
   }

   protected double getValoreICIRiga(int i) {
     
      int monthRif = 12;
      int dayRif = 31;
      double round = 1.0D;
      double aggiusta = 0.01D;
      double val_ici_pesato = 0.0D;

      try {
         int yearRif = new Integer((String)this.records.getElement(i, 9));
         Calendar dataRif = Calendar.getInstance();
         dataRif.set(yearRif, monthRif - 1, dayRif);
         double val_ici = Sys.round(this.records.getDouble(i, 3) - aggiusta, round);
         double peso_patrim = this.records.getDouble(i, 2) / 100.0D;
         String tipo = (String)this.records.getElement(i, 5);
         val_ici_pesato = val_ici * peso_patrim;
         if (!tipo.equals("PR")) {
            Calendar theDate;
            if (!tipo.equals("UV") && !tipo.equals("SV") && !tipo.equals("AV")) {
               int durata;
               if (!tipo.equals("UT") && !tipo.equals("ST") && !tipo.equals("AT")) {
                  if (tipo.equals("NV")) {
                     theDate = this.BirthDate((String)this.records.getElement(i, 7), dataRif);
                     val_ici_pesato = (double)Usufrutto.getValoreNudaProprieta_aVita(val_ici_pesato, theDate, dataRif, yearRif);
                  } else if (tipo.equals("NT")) {
                     durata = new Integer((String)this.records.getElement(i, 6));
                     val_ici_pesato = (double)Usufrutto.getValoreNudaProprieta_aTermine(val_ici_pesato, durata, yearRif);
                  } else {
                     System.out.println("ERRORE: Tipo diritto n. " + tipo + " non previsto nella classe Immobiliare");
                     val_ici_pesato = 0.0D;
                  }
               } else {
                  durata = new Integer((String)this.records.getElement(i, 6));
                  val_ici_pesato = (double)Usufrutto.getValoreUsuf_aTermine(val_ici_pesato, durata, yearRif);
               }
            } else {
               theDate = this.BirthDate((String)this.records.getElement(i, 7), dataRif);
               val_ici_pesato = (double)Usufrutto.getValoreUsuf_aVita(val_ici_pesato, theDate, dataRif, yearRif);
            }
         }

         return val_ici_pesato;
      } catch (NullPointerException var19) {
         System.out.println("Null pointer in " + this.getClass().getName() + ": " + var19.toString());
         return 0.0D;
      } catch (NumberFormatException var20) {
         System.out.println("ERROR NumberFormatException in " + this.getClass().getName() + ": " + var20.toString());
         return 0.0D;
      }
   }

   protected void reset() {
      ElabStaticContext.getInstance().resetSession(QImmobiliare.class.getName(), this.IDdomanda, "records");
   }

   public void init(RunawayData dataTransfer) {
      ElabStaticSession session = ElabStaticContext.getInstance().getSession(QImmobiliare.class.getName(), this.IDdomanda, "records");
      if (!session.isInitialized()) {
         super.init(dataTransfer);
         StringBuffer sql = new StringBuffer();
         sql.append("SELECT Patrimoni_immobiliari.residenza_nucleo, R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici * Patrimoni_immobiliari.quota / 100 AS valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, EDIL_immobile.immobile, Patrimoni_immobiliari.ID_tp_immobile, EDIL_immobile.mq * Patrimoni_immobiliari.quota / 100 AS mq_quota_terreno ");
         sql.append("FROM Familiari ");
         sql.append("INNER JOIN Patrimoni_immobiliari ON Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
         sql.append("INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
         sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
         sql.append("INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID ");
         sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
         sql.append("LEFT OUTER JOIN EDIL_immobile ON Patrimoni_immobiliari.immobile = EDIL_immobile.immobile AND Patrimoni_immobiliari.ID_dichiarazione = EDIL_immobile.ID_dichiarazione AND Familiari.ID_domanda = EDIL_immobile.ID_domanda AND Familiari.ID_dichiarazione = EDIL_immobile.ID_dichiarazione ");
         sql.append("WHERE Domande.ID_domanda = ");
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