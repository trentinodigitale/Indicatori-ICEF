/**
 *Created on 31-mag-2005
 */

package c_elab.pat.cura09;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge l'affitto dell'assistito dalla dichiarazione ICEF
 * @author g_barbieri
 */
public class Affitto_assistito extends ElainNode {
	
	//CAMBIAMI: va cambiata ogni anno
	String assist1="1626";  // nuova dom
	String assist2="1635";  // riaccertam

	
	/** Accompagnamento constructor */
	public Affitto_assistito() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();
		//                               1                      2
		sql.append("SELECT Detrazioni.importo , sanitest.L6_Assistiti.ID_tp_grado_parentela ");
		sql.append("FROM Familiari ");
		sql.append("INNER JOIN Detrazioni ON Familiari.ID_dichiarazione = Detrazioni.ID_dichiarazione INNER JOIN sanitest.L6_Assistiti ON Familiari.ID_domanda = sanitest.L6_Assistiti.Id_assistito ");
		sql.append("WHERE (Familiari.ID_relazione_parentela = "+assist1+" OR Familiari.ID_relazione_parentela = "+assist2+") AND (Detrazioni.ID_tp_detrazione='CNL' OR Detrazioni.ID_tp_detrazione='IMR')  AND Familiari.ID_domanda = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());
                
                
        }

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
        try {
            if(((String)(records.getElement(1,2))).equals("3")){
                
                return new Double((String)(records.getElement(1,1))).doubleValue();
                
            }else {
                return 0.0;
                    }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
