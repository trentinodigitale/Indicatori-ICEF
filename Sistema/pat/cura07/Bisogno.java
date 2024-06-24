/**
 *Created on 31-mag-2005
 */

package c_elab.pat.cura07;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge il bisogno di assistenza dalle tabelle del sanitest
 * @author g_barbieri
 */
public class Bisogno extends ElainNode {
	
	/** Accompagnamento constructor */
	public Bisogno() {
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
		//                                1
		sql.append(
			"SELECT sanitest.L6_Profilo.assegno ");
		sql.append("FROM sanitest.L6_Profilo ");
		sql.append("WHERE sanitest.L6_Profilo.Id_assistito = ");
		sql.append(IDdomanda);

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	double aValue = 0.0;
        try {
            aValue = new Double((String)(records.getElement(1,1))).doubleValue();
        } catch(NullPointerException n) {
        	// se non è ancora stato fatto il test socio-saniterio non c'è il record per cui va in nullpointer
        	// in questo caso si ritorna convenzionalmente un bisogno di assistenza = 9, ovvero sconosciuto
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 1.0;
        }
        
        if (aValue < 2 )
        	return 0;  //non idoneo
        else if (aValue == 2 )
        	return 1;  // bisogno elevato
        else if (aValue == 3 )
        	return 2;  // bisogno molto elevato
        else {
            System.out.println("ERRORE: bisogno per l'assegno di cura non previsto: " + aValue + ". Previsti valori tra 0 e 3!!");
        	return 0;
        }
    }
}
