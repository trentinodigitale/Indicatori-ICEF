package c_elab.pat.g12;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import java.util.Hashtable;

public class StringsToNet extends ElainNode
{

    public StringsToNet()
    {
    }

    public Hashtable getStringsHashtable()
    {
        Hashtable stringsToNet = new Hashtable();
        for(int i = 1; i <= records.getRows(); i++)
        {
            try
            {
                String resultNode = ((String)records.getElement(i, 2)).toUpperCase();
                stringsToNet.put(resultNode, (String)records.getElement(i, 1));
            }
            catch(NullPointerException n)
            {
                System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
                stringsToNet = new Hashtable();
            }
        }

        return stringsToNet;
    }

    protected void reset()
    {
    }

    public void init(RunawayData dataTransfer)
    {
        super.init(dataTransfer);
        doQuery("SELECT R_Benefici.beneficio, R_Benefici.nodo_risultato FROM R_Benefici WHERE (ID" +
		"_servizio = "
		 + servizio + ");");
    }
}
