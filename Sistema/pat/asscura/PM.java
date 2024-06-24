package c_elab.pat.asscura;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;


/** 
 * query per il quadro E della dichiarazione ICEF.
 * Attenzione questa classe è usata anche da net AI! pat/ai/ai15.net.html
 * @author g_barbieri
 */
public class PM extends DefComponentiDich {

	//VERIFICAMI: se periodo = 30500 (quello iniziale) calcola PM con regole icef12 altrimenti calcola con regole icef13

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

		//modalità normale con domanda
		/* 01. Patrimoni_finanziari.ID_tp_investimento,
		 * 02. R_Relazioni_parentela.peso_patrimonio,
		 * 03. Patrimoni_finanziari.consistenza, 
		 * 04. Familiari.ID_dichiarazione, 
		 * 05. Patrimoni_finanziari.consistenza_31_03, 
		 * 06. Patrimoni_finanziari.consistenza_30_06, 
		 * 07. Patrimoni_finanziari.consistenza_30_09, 
		 * 08. Domande.ID_periodo,
		 * 09. Domande.ID_servizio
		 */            
		sql.append("SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Familiari.ID_dichiarazione, Patrimoni_finanziari.consistenza_31_03, Patrimoni_finanziari.consistenza_30_06, Patrimoni_finanziari.consistenza_30_09, Domande.ID_periodo, Domande.ID_servizio  ");
		sql.append("FROM Familiari ");
		sql.append("INNER JOIN Patrimoni_finanziari ON Familiari.ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
		sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
		sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);

		//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
		String defDichType = DefDichElabConst.E;
		String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
		if(componenti != null && componenti.length()>0) {
			sql.append(" AND Familiari.ID_dichiarazione in ");
			sql.append(componenti);

			testPrintln(sql.toString());
		}
		//Fine aggiunta eventuale definizione di componenti		
		sql.append(" ORDER BY Familiari.ID_dichiarazione");


		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if (records == null) {
			return 0.0;
		}
		double tot			= 0.0;
		double round		= 1.0;
		double aggiusta		= 0.01;
		double fpim			= 0.0;

		try {
			String familiare="";
			for (int i = 1; i <= records.getRows(); i++)  {
				if (familiare.equals("")) {
					familiare=(String)records.getElement(i,4);
				} else {
					String nextFam=(String)records.getElement(i,4);
					if (!nextFam.equals(familiare)) {
						//togliere franchigia(5000) e sommare se positivo
						fpim = fpim - FIM;
						if (fpim>0) {
							tot = tot + fpim;
						}
						familiare = nextFam;
						fpim = 0.0;
					}//if diverso familiare
				}//else familiare vuoto

				if (records.getInteger(1, 8) == 30500){

					// -----------------------------
					// CALCOLO PM con le regole della dich ICEF 2012: media dei 4 trimestri per BAN
					// -----------------------------

					// se depositi bancari media della somma delle consistenze * peso patrimonio
					if ( ((String) records.getElement(i, 1)).equals("BN3") ) {
						double consistenza_31_03 = records.getDouble(i, 5);
						double consistenza_30_06 = records.getDouble(i, 6);
						double consistenza_30_09 = records.getDouble(i, 7);
						double consistenza_31_12 = records.getDouble(i, 3);

						double numSomme = 4.0;
						double mediaConsistenza = (consistenza_31_03 + consistenza_30_06 + consistenza_30_09 + consistenza_31_12)/numSomme;
						// consistenza
						fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * records.getDouble(i, 2) / 100.0);
						// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
					} else if ( ((String) records.getElement(i, 1)).equals("BN6") ) {

						double consistenza_30_06 = records.getDouble(i, 6);
						double consistenza_31_12 = records.getDouble(i, 3);

						double numSomme = 2.0;
						double mediaConsistenza = (consistenza_30_06 + consistenza_31_12)/numSomme;
						// consistenza
						fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * records.getDouble(i, 2) / 100.0);
						// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
					} else if ( ((String) records.getElement(i, 1)).equals("TIT")
							|| ((String) records.getElement(i, 1)).equals("PNQQ")
							|| ((String) records.getElement(i, 1)).equals("ALT") ) 
					{
						fpim = fpim + Sys.round(records.getDouble(i, 3) - aggiusta, round) * records.getDouble(i, 2) / 100.0;
					}

				} else {

					// -----------------------------
					// VERIFICAMI - CALCOLO PM con le regole della dich ICEF 2013: giacenza media per BAN
					// ATTENZIONE USATA ANCHE DA SERVIZIO 61000
					// -----------------------------

					// consistenza tipi di patrimonio valevoli per l'ICEF * peso patrimonio
					fpim = fpim + Sys.round(records.getDouble(i, 3) - aggiusta, round) * records.getDouble(i, 2) / 100.0;
				}

			}//for
			//ultimo familiare se presente: togliere franchigia(5000) e sommare se positivo
			fpim = fpim - FIM;
			if (fpim>0) {
				tot = tot + fpim;
			}
			return tot;
		} catch (NullPointerException n)  {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}  catch (NumberFormatException nfe)  {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}