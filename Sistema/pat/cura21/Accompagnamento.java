package c_elab.pat.cura21;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

public class Accompagnamento
  extends ElainNode
{
  protected void reset() {}
  
  public void init(RunawayData dataTransfer)
  {
    super.init(dataTransfer);
    StringBuffer sql = new StringBuffer();
    
    sql.append("SELECT sanitest.L6_Assistiti.importo_indennita_accompagnamento, sanitest.L6_Assistiti.importo_indennita_accompagnamento_altri ");
    sql.append("FROM sanitest.L6_Assistiti ");
    sql.append("WHERE sanitest.L6_Assistiti.Id_assistito = ");
    sql.append(this.IDdomanda);
    
    doQuery(sql.toString());
  }
  
  public double getValue()
  {
    try
    {
      return Double.parseDouble((String)this.records.getElement(1, 1)) + Double.parseDouble((String)this.records.getElement(1, 2));
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
