package c_elab.pat.cura22;
import it.clesius.clesius.util.Sys;
import c_elab.pat.icef22.QParticolarita;

public class Invalidi extends QParticolarita {
	
	
  double QBI = 5400.0;
  double DMI = 0.0;
  
  public double getValue()
  {
    if (this.records == null) {
      return 0.0;
    }
    double tot = 0.0;
    double round = 1.0;
    double aggiusta = 0.01;
    try
    {
      for (int i = 1; i <= this.records.getRows(); i++) {
        if (!this.records.getString(i, 7).equals(cura22_Params.assist2))
        {
          double value = Math.max(this.DMI, Math.max(this.QBI * this.records.getDouble(i, 1), this.records.getDouble(i, 2)));
          double pesoReddito = 100;  //records.getDouble(i, 8); NB da rivedere!!!!!!!!
          value = Sys.round(value - aggiusta, round) * pesoReddito / 100.0;
          tot += value;
        }
      }
      return tot;
    }
    catch (NullPointerException n)
    {
      System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
      return 0.0;
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
    }
    return 0.0;
  }
}
