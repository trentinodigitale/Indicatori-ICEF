package c_elab.pat.asscura;

import java.util.Calendar;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;

/** 
 *@author s_largher 
 */

public class FPM extends QImmobiliare { 
	
	//VERIFICAMI: se periodo = 30500 (quello iniziale) calcola PM con regole icef12 altrimenti calcola con regole icef13

	// Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3)
	double FIM = 5000.0;  
	private			int			annoAC							= 2011;
	private final	int			MONTH_JUNE						= 5;
	private			String		query							= null;
	
	/** PM constructor */
	public FPM() {
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
		double FPM			= 0.0;
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
			
			//ritorna gli immobili ceduti a titolo oneroso
			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*")) {
				//modalità normale con domanda
				if(query==null) {
					query = getDefaultQuery();
				}
				sql.append(query);
			}

			doQuery(sql.toString());
			
			//nuova richiesta: franchigia sugli immobili ceduti da applicare solo se anno redditi/patrimonio >= 2020
			int annoProduzionePatrimonio = 0;
			if(annoProduzionePatrimoni.getRows() > 0) {
				annoProduzionePatrimonio = new Integer((String)annoProduzionePatrimoni.getElement(1,1)).intValue();
			}
			if(annoProduzionePatrimonio >= 2021) {
				// devo togliere la franchigia di 20.000 una volta sola: O dagli immobili diversi da quelli di residenza, O dagli immobili ceduti
				double valoreImmobiliDiversiDaResidenza = getValoreImmobili2( false, false, true );
				
				boolean usaDetrazioneMaxValoreNudaProprieta = false;
				boolean gratuito = false;
				double valoreImmobiliCedutiATitoloOneroso = getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
				
				if(tot>valoreImmobiliCedutiATitoloOneroso) { // e se PM è maggiore di PI5_oner ma ci sono anche PI5_grat? cmq toglierò 40.000 da PM e nulla dagli immobili ceduti? o devo cmq togliere anche 20.000 dagli immobili ceduti? e in tal caso come faccio? metto FPM = 40000 + 20000?
					FPM = 40000d;
					// se ci sono anche immobili ceduti a titolo gratuito che hanno un valore > 20.000, innalzo la franchigia FPM di 20.000,
					// altrimenti la innalzo del valore degli immobili ceduti a titolo gratuito
					gratuito = true;
					double valoreImmobiliCedutiATitoloGratuito = getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
					if(valoreImmobiliCedutiATitoloGratuito >= 20000d) {
						FPM = FPM  + 20000d;
					}else {
						//FPM = FPM + valoreImmobiliCedutiATitoloGratuito; <-- non devo innalzarla ulteriormente perchè vengono già tolti dagli immobili gratuiti, se presenti
					}
					
				}else { 
					//vedo anche il valore degli immobili ceduti a titolo gratuito
					gratuito = true;
					double valoreImmobiliCedutiATitoloGratuito = getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
					
					//se il valore degli immobili ceduti a titolo gratuito è maggiore della franchigia, non devo innalzare ulteriormente la franchigia, perchè applico già la franchigia massima
					if(valoreImmobiliCedutiATitoloGratuito >= 20000d) {
						FPM = 0d;
					}else {
						FPM = Math.max(0, 20000d - valoreImmobiliCedutiATitoloGratuito - valoreImmobiliDiversiDaResidenza);
						FPM = Sys.round(FPM - aggiusta, round);
					}

					
				}				
			}else {
				FPM = 40000d;
			}
			
			return FPM;
		} catch (NullPointerException n)  {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}  catch (NumberFormatException nfe)  {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
	
	/**
	 * Torna la query per il recupero degli immobili ceduti (AC_immobili_ceduti):<BR>
	 * - valorizza l'anno con l'anno letto in ac_configurazione anno Inizio_vettore - 1 per AC;<BR>
	 * - valorizza l'anno con l'anno letto da doc.data_presentazione -2 se nel primo semestre e -1 nel secondo semestra per AI.<BR>
	 * @return
	 */
	protected String getDefaultQuery() {
		StringBuffer sql = new StringBuffer();
		try{
			Table t1 = dataTransfer.executeQuery("Select domande.id_periodo, domande.id_servizio, doc.data_presentazione "
					+ "	from domande "
					+ "	inner join doc on doc.id = domande.id_domanda "
					+ " where domande.id_domanda = "
					+ IDdomanda);

			int idServizio	= t1.getInteger(1, 2);
			int idPeriodo	= t1.getInteger(1, 1);

			sql.append("SELECT  Inizio_vettore FROM AC_configurazione where " 
					+" id_servizio="
					+ idServizio
					+" and id_periodo="
					+ idPeriodo
					+ " order by Inizio_periodo asc"
					);

			Table t= dataTransfer.executeQuery(sql.toString());
			if (t != null && t.getRows() > 0) {
				annoAC =	t.getCalendar(1, 1).get(Calendar.YEAR); 

				if(annoAC > 2013){
					annoAC = annoAC - 1;
				}else{
					//è fisso perche' abbiamo sbagliato nel 2012 -2013
					annoAC = 2011;
				}
			} else {
				if (t1.getCalendar(1, 3).get(Calendar.MONTH) <= MONTH_JUNE) {
					annoAC = t1.getCalendar(1, 3).get(Calendar.YEAR) - 2;
				} else {
					annoAC = t1.getCalendar(1, 3).get(Calendar.YEAR) - 1;
				};
			}

		}catch(Exception e){
			e.printStackTrace();
		}


		sql = new StringBuffer();

		//TODO fisso anno 2011 in query
		sql.append("SELECT AC_immobili_ceduti.ID_tp_cessione, ");
		sql.append(" 100 AS peso_patrimonio, ");
		sql.append(" AC_immobili_ceduti.valore_ici, ");
		sql.append(" AC_immobili_ceduti.ID_tp_cat_catastale, ");
		sql.append(" AC_immobili_ceduti.ID_tp_diritto, ");
		sql.append(" AC_immobili_ceduti.anni_usufrutto, ");
		sql.append(" AC_immobili_ceduti.data_nascita_usufruttuario, ");
		sql.append(" Doc.data_presentazione, ");
		sql.append(annoAC);
		sql.append(" AS anno_produzione_patrimoni, ");
		sql.append(" 0 AS ID_dichiarazione, ");
		sql.append(annoAC);
		sql.append(" AS anno_produzione_redditi, ");
		sql.append(" AC_immobili_ceduti.ID_tp_immobile, ");
		sql.append(" AC_immobili_ceduti.quota  ");
		sql.append(" FROM Doc ");
		sql.append(" INNER JOIN Domande ON Doc.ID = Domande.ID_domanda ");
		sql.append(" INNER JOIN AC_immobili_ceduti ON Domande.ID_domanda = AC_immobili_ceduti.ID_domanda ");
		sql.append(" WHERE (AC_immobili_ceduti.ID_tp_uso_immobile = 'RES' OR AC_immobili_ceduti.ID_tp_uso_immobile = 'ALT') ");
		sql.append(" AND Domande.ID_domanda = ");
		sql.append(IDdomanda);

		return sql.toString();
	}
}