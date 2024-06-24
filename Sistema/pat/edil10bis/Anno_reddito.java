package c_elab.pat.edil10bis;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

public class Anno_reddito extends ElainNode {
	
	//CAMBIAMI: va cambiata ogni anno
	//String rich1= "4800"; // per edil normale
	
	public Anno_reddito() {
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
		//                                 1
		sql.append(
			"SELECT     Dich_icef.anno_produzione_redditi ");
		sql.append("FROM Dich_icef INNER JOIN ");
		sql.append("Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione INNER JOIN ");
		sql.append("r_relazioni_parentela ON r_relazioni_parentela.ID_relazione_parentela = Familiari.ID_relazione_parentela ");
		sql.append("WHERE (r_relazioni_parentela.ruolo = 1 ) AND (Familiari.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(")");

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		try {
            return new Double((String)(records.getElement(1,1))).doubleValue();
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
	}
}