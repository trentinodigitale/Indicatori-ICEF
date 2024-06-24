package c_elab.pat.aup19.quotaA;

import c_elab.pat.icef19.DefComponentiDich;
import c_elab.pat.icef19.LocalVariables;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import it.clesius.evolservlet.icef.PassaValoriIcef;


/** query per il quadro E della dichiarazione ICEF
 * @author g_barbieri
 */
public class PM2 extends DefComponentiDich {



	/** PM constructor */
	public PM2() {
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

		if(!IDdomanda.startsWith("*"))
		{
			//modalità normale con domanda
			//                                         1                                    2                                    3                      4
			sql.append(
			"SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Familiari.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append(
			"INNER JOIN Patrimoni_finanziari ON Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
			sql.append(
			"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(
			"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
			String defDichType = DefDichElabConst.E;
			String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
			if(componenti != null && componenti.length()>0)
			{
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componenti);

				testPrintln(sql.toString());
			}
			String componentiPV =  PassaValoriIcef.getID_dichiarazioni(IDdomanda); //classe metodo DefComponentiDich
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}
			//Fine aggiunta eventuale definizione di componenti		
			sql.append(" ORDER BY Familiari.ID_dichiarazione");
		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

			String id_dichiarazione = IDdomanda.substring(1);

			//              		1                		2              	3			4
			sql.append(
			"SELECT     ID_tp_investimento, 100 AS peso_patrimonio, consistenza, ID_dichiarazione ");
			sql.append(
			"FROM         Patrimoni_finanziari ");
			sql.append(
			"WHERE     (ID_dichiarazione = ");
			sql.append(id_dichiarazione);
			sql.append(
			")");
			sql.append(" ORDER BY ID_dichiarazione");
		}

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
					familiare=(String)records.getElement(i,4);
				}
				else 
				{
					String nextFam=(String)records.getElement(i,4);
					if (!nextFam.equals(familiare))
					{
						//togliere franchigia(5000) e sommare se positivo
						fpim = fpim - LocalVariables.FIM;
						if (fpim>0)
						{
							tot = tot + fpim;
						}
						familiare = nextFam;
						fpim = 0.0;
					}//if diverso familiare
				}//else familiare vuoto
				// consistenza tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				fpim = fpim + Sys.round(records.getDouble(i, 3) - aggiusta, round) * records.getDouble(i, 2) / 100.0;
			}//for
			//ultimo familiare se presente: togliere franchigia(5000) e sommare se positivo
			fpim = fpim - LocalVariables.FIM;
			if (fpim>0)
			{
				tot = tot + fpim;
			}

			//AUP 2020: attualizzazione dei redditi: PM diventa la somma delle consistenze nella AUP_PATRIMONIO_FINANZIARIO - 3000 euro
//			NUOVA RICHIESTA DI ZOPPI DEL 23/09/2020: PM SEMPRE A 0 IN CASO DI ATTUALIZZAZIONE COVID -> AGGIUNGERE ANCHE QUESTI AL REDDITO C5
			StringBuffer sql = new StringBuffer();
			sql.append("select causa_emergenza_2 from aup_dati where id_domanda = ");
			sql.append(IDdomanda);
			Table t;
			try {
				t = dataTransfer.executeQuery(sql.toString());
				String causaEmergenza2 = (String)t.getElement(1, 1);
				if("1".equals(causaEmergenza2)) {
//					sql = new StringBuffer();
//					sql.append("select sum(consistenza) from aup_patrimonio_finanziario where id_domanda = ");
//					sql.append(IDdomanda);
//					try {
//						Table t2 = dataTransfer.executeQuery(sql.toString());
//						tot = (double)t2.getDouble(1, 1);
//						tot = tot - 3000;
//						if(tot < 0d) {
//							tot = 0d;
//						}
//					} catch (DBException e) {
//						tot = 0d;
//						e.printStackTrace();
//					}		
//	NUOVA RICHIESTA DI ZOPPI DEL 23/09/2020: PM SEMPRE A 0 IN CASO DI ATTUALIZZAZIONE COVID -> AGGIUNGERE ANCHE QUESTI AL REDDITO C5
					tot = 0d;
				}					
			} catch (DBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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