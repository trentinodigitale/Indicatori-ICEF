package c_elab.pat.cura15;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

public class Bisogno
  extends ElainNode
{
  protected void reset() {}
  
  public void init(RunawayData dataTransfer)
  {
    super.init(dataTransfer);
    StringBuffer sql = new StringBuffer();
    
    sql.append(
      "SELECT sanitest.L6_Profilo.assegno ");
    sql.append("FROM sanitest.L6_Profilo ");
    sql.append("WHERE sanitest.L6_Profilo.Id_assistito = ");
    sql.append(this.IDdomanda);
    
    doQuery(sql.toString());
  }
  
  public double getValue()
  {
    double aValue = 0.0;
    try
    {
      aValue = new Double((String)this.records.getElement(1, 1)).doubleValue();
    }
    catch (NullPointerException n)
    {
      System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
      return 9.0;
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
      return 1.0;
    }
    if (aValue < 2.0) {
      return 0.0;
    }
    if (aValue == 2.0) {
      return 1.0;
    }
    if (aValue == 3.0) {
      return 2.0;
    }
    System.out.println("ERRORE: bisogno per l'assegno di cura non previsto: " + aValue + ". Previsti valori tra 0 e 3!!");
    return 0.0;
  }
}
