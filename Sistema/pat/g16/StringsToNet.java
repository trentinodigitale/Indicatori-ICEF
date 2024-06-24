package c_elab.pat.g16;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import java.util.Hashtable;

public class StringsToNet extends ElainNode
{

    public StringsToNet()
    {
    }

    public Hashtable<String,String> getStringsHashtable()
    {
        Hashtable<String,String> stringsToNet = new Hashtable<String,String>();
        for(int i = 1; i <= records.getRows(); i++)
        {
            try
            {
                String resultNode = records.getString(i, 2).toUpperCase();
                stringsToNet.put(resultNode, records.getString(i, 1) );
            }
            catch(NullPointerException n)
            {
                System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
                stringsToNet = new Hashtable<String,String>();
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
