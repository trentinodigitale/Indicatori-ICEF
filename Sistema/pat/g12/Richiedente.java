package c_elab.pat.g12;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

public class Richiedente extends ElainNode
{

    public String getString()
    {
        String applicant = "";
        for(int i = 1; i <= records.getRows(); i++)
        {
            try
            {
                if(i == 1)
                {
                    applicant = applicant + "cognome=" + (String)records.getElement(i, 1) + ";nome=" + (String)records.getElement(i, 2) + ";";
                }
            }
            catch(NullPointerException n)
            {
                System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
                applicant = "Errore in Richiedente.getString";
            }
        }

        return applicant;
    }

    protected void reset()
    {
    }

    public void init(RunawayData dataTransfer)
    {
        System.out.println("init richiedente RunawayData = " + dataTransfer);
        super.init(dataTransfer);
        doQuery("SELECT Domande.cognome, Domande.nome FROM Domande WHERE (Domande.ID_domanda = " + IDdomanda + ");");
    }

    public Richiedente()
    {
    }
}
