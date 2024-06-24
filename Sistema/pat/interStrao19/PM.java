package c_elab.pat.interStrao19;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;


/** query per il calcolo dei patrimoni finanziari della dichiarazione ICEF attualizzata
 * @author l_leonardi
 */
public class PM extends ElainNode {

	// Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3)
	double FIM = 5000.0;  

	/** PM constructor */
	public PM() {
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
		//												1										2									3
		sql.append("SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, ");
		//									 4									 5	
		sql.append("Patrimoni_finanziari.interessi, Patrimoni_finanziari.ID_dichiarazione ");
		sql.append("FROM Familiari INNER JOIN  ");
		sql.append("Domande ON Familiari.ID_domanda = Domande.ID_domanda INNER JOIN  ");
		sql.append("R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela INNER JOIN  ");
		sql.append("Familiari_ext ON Familiari.ID_domanda = Familiari_ext.ID_domanda AND Familiari.ID_dichiarazione = Familiari_ext.ID_dichiarazione INNER JOIN  ");
		sql.append("Patrimoni_finanziari ON Familiari_ext.ID_dichiarazione_ext = Patrimoni_finanziari.ID_dichiarazione ");
		sql.append("WHERE (Domande.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(") ");
		sql.append("ORDER BY Patrimoni_finanziari.ID_dichiarazione ");
		
		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		double fpim = 0.0;

		try 
		{
			String familiare="";
			for (int i = 1; i <= records.getRows(); i++) 
			{
				if (familiare.equals(""))
				{
					familiare=(String)records.getElement(i,5);
				}
				else 
				{
					String nextFam=(String)records.getElement(i,5);
					if (!nextFam.equals(familiare))
					{
						//togliere franchigia(5000) e sommare se positivo
						fpim = fpim - FIM;
						if (fpim>0)
						{
							tot = tot + fpim;
						}
						familiare = nextFam;
						fpim = 0.0;
					}//if diverso familiare
				}//else familiare vuoto
				// se depositi bancari media della somma delle consistenze * peso patrimonio
				if ( ((String) records.getElement(i, 1)).equals("BAN") 
						|| ((String) records.getElement(i, 1)).equals("TIT")
						|| ((String) records.getElement(i, 1)).equals("PNQQ")
						|| ((String) records.getElement(i, 1)).equals("ALT") ) 
				{
					fpim = fpim + Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) records.getElement(i, 2)).doubleValue() / 100.0;
				}
			}//for
			//ultimo familiare se presente: togliere franchigia(5000) e sommare se positivo
			fpim = fpim - FIM;
			if (fpim>0)
			{
				tot = tot + fpim;
			}
			return tot;
		} 
		catch (NullPointerException n) 
		{
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} 
		catch (NumberFormatException nfe) 
		{
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}