package c_elab.pat.reddgar13;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QRedditoGaranzia extends ElainNode {

	protected Table datiRedditoGaranzia;
	protected Table datiRinnovo;
	protected Table particolarita;

	//CAMBIAMI: va cambiata se vengono introdotti nuovi servizi
	protected long idServizioRedditoGaranzia = 30000;
	protected long idServizioRedditoGaranziaSociale = 30001;

	//CAMBIAMI: va cambiata ogni anno
	protected int reddGar_IDRelazioneParentela_ConiugeNonResidente = 7102;
	private int reddGarSociale_IDRelazioneParentela_ConiugeNonResidente = 7112;


	/** QProvvisorio constructor */
	public QRedditoGaranzia() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QRedditoGaranzia.class.getName(), IDdomanda );
		datiRedditoGaranzia = null;
		datiRinnovo = null;
		particolarita = null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QRedditoGaranzia.class.getName(), IDdomanda  );
		super.init(dataTransfer);

		if (!session.isInitialized()) {

			StringBuffer sql = new StringBuffer();
			//              	  		   1         	   			2	    	   		   3   		 	  		  4					  		  5			  		   6					  7	
			sql.append("SELECT GR_Dati.locazione, GR_Dati.imp_canone_mensile, GR_Dati.sociale_no, GR_Dati.sociale_attestazione, GR_Dati.sociale_data, GR_Dati.sociale_si, GR_Dati.sociale_inizio_mese, "); 
			//				   		   8				      9					   10						     11								12					
			sql.append("GR_Dati.sociale_durata, GR_Dati.esclusa_ufficio, Doc.data_presentazione, GR_Dati.sociale_inizio_anno, GR_Dati.imp_int_mutuo_mensile, ");
			//						13								14								15								16						17
			sql.append("GR_Dati.senza_fissa_dimora, GR_Dati.ID_tp_scelta_incongrua, GR_Dati.ID_tp_scelta_sociale_icef, GR_Dati.ID_tp_esclusione, GR_Dati.mesi_revoca, ");
			//								18								19										 20									21
			sql.append("GR_Dati.incongrua_almeno_una_volta, GR_Dati.attesa_scelta_sociale_icef, GR_Dati.nucleo_struttura_coperturaParz,");
			//								21										 		22										23
			sql.append("GR_Dati.nucleo_struttura_coperturaParz_decurt, GR_Dati.deroga_incompatibilita_automatismo,  GR_Dati.imp_canone_mensile_contributo   ");			
			sql.append("FROM GR_Dati INNER JOIN ");
			sql.append("Domande ON GR_Dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE GR_Dati.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			datiRedditoGaranzia = records;


			sql = new StringBuffer();	

			//							   1									  2			  			   3			  		  4			  			 5	
			sql.append("SELECT GR_Rinnovi_12.ID_attestazione_insuss, GR_Rinnovi_12.ID_soggetto, GR_Rinnovi_12.ciclo, GR_Rinnovi_12.rinnovo, GR_Rinnovi_12.tutti_lavorano, ");
			//			   					6			  			7			   			8		  			  9		      			10	
			sql.append("GR_Rinnovi_12.importo_mensile, GR_Rinnovi_12.anno_inizio, GR_Rinnovi_12.mese_inizio, GR_Rinnovi_12.per_mesi, GR_Rinnovi_12.data_rinnovo, ");
			//	    						11							        12												13
			sql.append("GR_Rinnovi_12.rinnovo_con_beneficio, GR_Att_dati.sana_anche_incongruita, GR_Rinnovi_12.rinnovo_con_beneficio_precedente ");
			sql.append("FROM GR_Rinnovi_12 LEFT OUTER JOIN ");
			sql.append("GR_Att_dati ON GR_Att_dati.ID_domanda = GR_Rinnovi_12.ID_attestazione_insuss ");
			sql.append("WHERE GR_Rinnovi_12.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			datiRinnovo = records;

			sql = new StringBuffer();
			//              							1                          2                         				3						 4
			sql.append("SELECT     tp_invalidita.coeff_invalidita, Familiari.spese_invalidita, R_Relazioni_parentela.peso_reddito, Familiari.ID_dichiarazione ");
			sql.append("FROM Soggetti INNER JOIN ");
			sql.append("Familiari INNER JOIN ");
			sql.append("tp_invalidita ON Familiari.ID_tp_invalidita = tp_invalidita.ID_tp_invalidita INNER JOIN ");
			sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN ");
			sql.append("Doc ON Familiari.ID_domanda = Doc.ID INNER JOIN ");
			sql.append("R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE     Familiari.ID_domanda =  ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			particolarita = records;

			sql = new StringBuffer();
			//									 1							2				  			3						4						  		5				   					6				  
			sql.append("SELECT  GR_Familiari.esclusa_ufficio, GR_Familiari.ID_dichiarazione, GR_Familiari.subentrato, GR_Familiari.occupato, GR_Familiari.dimissioni_non_giusta_causa, GR_Familiari.dimissioni_giusta_causa, ");
			//								  7								8									9											10										11				
			sql.append("GR_Familiari.senzaReddito_24m, GR_Familiari.rifiuto_ingiustificato, GR_Familiari.non_collocabile_lavoro, GR_Familiari.non_collocabile_lavoro_certificato, GR_Familiari.non_collocabile_lavoro_data, ");
			//								  12									13									14										15								16
			sql.append("GR_Familiari.disponibilita_lavoro, GR_Familiari.disponibilita_lavoro_data, GR_Familiari.disponibilita_lavoro_ente, GR_Familiari.cercaPrimo_lavoro, GR_Familiari.cercaPrimo_lavoro_ente, ");
			//								  17												18									19									20				
			sql.append("GR_Familiari.cercaPrimo_lavoro_ID_soggetto, GR_Familiari.cercaPrimo_lavoro_descrizione, GR_Familiari.deroga_assistente, GR_Familiari.deroga_assistente_descrizione, ");
			//								  21										22								23								24									25									26	
			sql.append("GR_Familiari.deroga_assistente_ID_soggetto, GR_Familiari.deroga_studente, GR_Familiari.deroga_studente_ente, GR_Familiari.deroga_serv_civile, GR_Familiari.deroga_serv_civile_ente, GR_Familiari.cercaPrimo_lavoro_ecc, ");
			//					   27			  28						29					   30					31									32							33						   34							35
			sql.append("Soggetti.cognome, Soggetti.nome, Familiari.residenza_storica, Soggetti.data_nascita, Familiari.ID_relazione_parentela, Soggetti.ID_tp_sex, GR_Familiari.ID_tp_esclusione, GR_Familiari.RSA_spese, GR_Familiari.genitore_altro_figlio, ");
			//			     			 36							  37							 38													39
			sql.append("GR_Familiari.condannato, GR_Familiari.senza_decurtazione, GR_Familiari.avviabile_lavoro_colloc_mirato, GR_Familiari.ultimoGiorno_contrObblig_data, ");
			//			 							40							  41
			sql.append("GR_Familiari.deroga_disocc_non_accert, GR_Familiari.deroga_gestante ");	
			sql.append("FROM Familiari INNER JOIN ");
			sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN ");
			sql.append("Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto AND Dich_icef.ID_soggetto = Soggetti.ID_soggetto INNER JOIN ");
			sql.append("GR_Familiari ON Familiari.ID_domanda = GR_Familiari.ID_domanda AND Familiari.ID_dichiarazione = GR_Familiari.ID_dichiarazione INNER JOIN ");
			sql.append("Domande ON Familiari.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE (Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
			sql.append("ORDER BY Familiari.ID_dichiarazione");

			doQuery(sql.toString());			

			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("datiRedditoGaranzia", datiRedditoGaranzia);
			session.setAttribute("datiRinnovo", datiRinnovo);
			session.setAttribute("particolarita", particolarita);

		} else {
			records = session.getRecords();
			datiRedditoGaranzia = (Table)session.getAttribute("datiRedditoGaranzia");
			datiRinnovo = (Table)session.getAttribute("datiRinnovo");
			particolarita = (Table)session.getAttribute("particolarita");
		}
	}

	public double percentualeSpettanteNonOccupati(boolean effettivi)
	{
		double ret = 0.0;

		if (records == null)
			return 0;

		try {
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);

			int nPersoneNucleoLavorativo = 0;
			int sommaPercentuali = 0;

			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean aventeRequisitiAiFiniDelCalcoloImporto = aventeRequisitiAiFiniDelCalcoloImportoConResidenti(i);

				boolean condannato = records.getBoolean(i,36);
				if(!effettivi)
				{
					condannato = false;
				}

				if(aventeRequisitiAiFiniDelCalcoloImporto && !condannato)
				{

					Calendar dataNascita = Calendar.getInstance();
					try {
						dataNascita = records.getCalendar(i, 30);
					} catch (Exception e) {
						System.out.println("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda);
						e.printStackTrace();
						dataNascita.set(1900, 0, 1, 0, 0);
					}	            	

					// verifico se è in età lavorativa
					int eta = getEta(dataNascita,(Calendar)dataPresentazioneDomanda.clone());
					boolean maschio = true;
					String sex = (String)records.getElement(i,32);
					if(sex.equalsIgnoreCase("F"))
					{
						maschio = false;
					}
					boolean daVerificare = true;
					if(eta<18)
					{
						daVerificare = false;
					}
					if(maschio)
					{
						if(eta>=65)
						{
							daVerificare = false;
						}
					}
					else
					{
						if(eta>=60)
						{
							daVerificare = false;
						}
					}
					
					boolean non_collocabile_lavoro = records.getBoolean(i,9);
					boolean deroga_assistente =records.getBoolean(i,19);
					boolean deroga_serv_civile =records.getBoolean(i,24);
					boolean deroga_studente =records.getBoolean(i,22);
					boolean deroga_disocc_non_accert = records.getBoolean(i,40);
					boolean deroga_gestante	= records.getBoolean(i,41);
					if(non_collocabile_lavoro || deroga_assistente || 
							deroga_serv_civile || deroga_studente || 
							deroga_disocc_non_accert || deroga_gestante)
					{
						daVerificare = false;
					}
					
					if(daVerificare)
					{
						nPersoneNucleoLavorativo++;

						boolean senzaReddito24m = records.getBoolean(i,7);
						boolean dimissioniNonGiustaCausa = records.getBoolean(i,5);
						boolean dimissioniGiustaCausa = records.getBoolean(i,6);
						boolean cercaPrimoLavoro = records.getBoolean(i,15);
						boolean cercaPrimoLavoroEcc = records.getBoolean(i,26);

						if (senzaReddito24m || dimissioniNonGiustaCausa || dimissioniGiustaCausa || cercaPrimoLavoro || cercaPrimoLavoroEcc)
						{
							boolean avviabile_lavoro_colloc_mirato = records.getBoolean(i, 38);
							boolean senza_decurtazione_servizi_sociali = records.getBoolean(i, 37);
							
							if(servizio == idServizioRedditoGaranzia)
							{
								//Se la domanda è una domanda di automatismo non c'è mai la decurtazione
								senza_decurtazione_servizi_sociali = false;
							}

							if(!avviabile_lavoro_colloc_mirato && !senza_decurtazione_servizi_sociali)
							{
								if(records.getString(i, 39)!=null && records.getString(i, 39).length()>0)
								{
									Calendar ultimoGiorno_contrObblig_data = getCalendar(i, 39);
									int mesi = getMesi(ultimoGiorno_contrObblig_data, dataPresentazioneDomanda);
									/*
									System.out.println("ultimoGiorno_contrObblig_data: "+ultimoGiorno_contrObblig_data.getTime().toString());
									System.out.println("dataPresentazioneDomanda: "+dataPresentazioneDomanda.getTime().toString());
									*/
									if(mesi<=8)
									{
										sommaPercentuali+=100;
									}
									if(mesi>8 && mesi <=12)
									{
										sommaPercentuali+=85;
									}
									else if(mesi > 12)
									{
										sommaPercentuali+=75;
									}
								}
								else
								{
									//data non definita
									sommaPercentuali+=100;
								}
							}
							else
							{
								//soggetto avviabile al lavoro oppure con la deroga dei servizi sociali
								sommaPercentuali+=100;
							}							
						}
						else
						{
							//soggetto occupato
							sommaPercentuali+=100;
						}
					}
				}
				else
				{
					//esclusa d'ufficio o 
					//detenuto in istituto di pena o
					//ospitato presso strutture residenziali socio-sanitarie o socio-assistenziali o
					//non residente, convivente e genitore di minori residenti o 
					//soggetto con grado di parentela coniuge non residente 
				}
			}


			boolean rinnovoConBeneficioPrecedente = true;

			if(servizio == idServizioRedditoGaranziaSociale)
			{
				//se sociale imposto il rinnovo con beneficio a 2
				rinnovoConBeneficioPrecedente = true;
			}
			else
			{
				//automatismo, il rinnovo con beneficio è quello definito nella tabella
				if(datiRinnovo!=null && datiRinnovo.getRows()>0)
				{
					rinnovoConBeneficioPrecedente = datiRinnovo.getBoolean(1, 13);
					int ciclo = datiRinnovo.getInteger(1, 3);
					if(ciclo==-1)
					{
						//domanda mai trasmessa, mi calcolo il numero di rinnovi
						StringBuffer sql2 = new StringBuffer("SELECT MAX(GR_rinnovi_12.ciclo) AS maxCiclo, GR_rinnovi_12.ID_soggetto ");
						sql2.append("FROM GR_rinnovi_12 INNER JOIN Domande ON Domande.id_soggetto = GR_rinnovi_12.ID_soggetto ");
						sql2.append("WHERE (Domande.ID_domanda = "+IDdomanda+") AND (GR_Rinnovi_12.automatismo = 1) AND (GR_rinnovi_12.ID_domanda <> "+IDdomanda+") AND (GR_rinnovi_12.ciclo<>-1) ");
						sql2.append("GROUP BY GR_rinnovi_12.ID_soggetto");
						Table t2 = dataTransfer.executeQuery(sql2.toString());
						if(t2.getRows()>0 && t2.getString(1, 1)!=null)	
						{
							int ultimoCiclo = t2.getInteger(1, 1);
							int idSoggetto = t2.getInteger(1, 2);

							StringBuffer sqlUltimoRinnovo;
							//											   				  1				            2				  			3
							sqlUltimoRinnovo = new StringBuffer("SELECT GR_Rinnovi_12.anno_inizio, GR_Rinnovi_12.mese_inizio, GR_Rinnovi_12.per_mesi, ");
							//			    						  4				            5
							sqlUltimoRinnovo.append("GR_Rinnovi_12.rinnovo, GR_Rinnovi_12.rinnovo_con_beneficio ");
							sqlUltimoRinnovo.append("FROM GR_Rinnovi_12 ");
							sqlUltimoRinnovo.append("WHERE (GR_Rinnovi_12.rinnovo_con_beneficio>=1) AND (GR_Rinnovi_12.automatismo = 1) AND (GR_Rinnovi_12.ciclo = "+ultimoCiclo+") AND (GR_Rinnovi_12.ID_soggetto = "+idSoggetto+") AND (GR_Rinnovi_12.ID_domanda <> "+IDdomanda+") ");
							sqlUltimoRinnovo.append("ORDER BY GR_Rinnovi_12.rinnovo DESC ");

							Table tUltimoRinnovo = dataTransfer.executeQuery(sqlUltimoRinnovo.toString());

							boolean scaduti12Mesi = false;
							if(tUltimoRinnovo.getRows()==0)	
							{
								//nessuna domanda di rinnovo con beneficio di automatismo fatta per l'ultimo ciclo
								scaduti12Mesi = false;
							}
							else
							{
								int annoInizioAutomatismoUltimoCicloUltimoRinnovoConBeneficio = tUltimoRinnovo.getInteger(1, 1);
								int meseInizioAutomatismoUltimoCicloUltimoRinnovoConBeneficio = tUltimoRinnovo.getInteger(1, 2);
								int perMesiAutomatismoUltimoCicloUltimoRinnovoConBeneficio = tUltimoRinnovo.getInteger(1, 3);

								int annoScadenzaUltimoCicloUltimoRinnovoConBeneficio = annoInizioAutomatismoUltimoCicloUltimoRinnovoConBeneficio;
								int meseScadenzaUltimoCicloUltimoRinnovoConBeneficio = meseInizioAutomatismoUltimoCicloUltimoRinnovoConBeneficio;

								for(int i=0; i<(12+perMesiAutomatismoUltimoCicloUltimoRinnovoConBeneficio); i++)
								{
									if(meseScadenzaUltimoCicloUltimoRinnovoConBeneficio>12)
									{
										annoScadenzaUltimoCicloUltimoRinnovoConBeneficio++;
										meseScadenzaUltimoCicloUltimoRinnovoConBeneficio = 1;
									}
									meseScadenzaUltimoCicloUltimoRinnovoConBeneficio++;
								}

								Calendar calDatiDomandaAttuale = (Calendar)dataPresentazioneDomanda.clone();
								calDatiDomandaAttuale.add(Calendar.MONTH, 1);
								calDatiDomandaAttuale.set(Calendar.DAY_OF_MONTH, 1);
								Calendar calDatiDomandaUltima = new GregorianCalendar(annoScadenzaUltimoCicloUltimoRinnovoConBeneficio, meseScadenzaUltimoCicloUltimoRinnovoConBeneficio-1, 1);
								
								//System.out.println("calDatiDomandaAttuale: "+calDatiDomandaAttuale.getTime().toString());
								//System.out.println("calDatiDomandaUltima: "+calDatiDomandaUltima.getTime().toString());
								
								if(calDatiDomandaAttuale.getTime().getTime()>=calDatiDomandaUltima.getTime().getTime())
								{
									//sono passati almeno 12 mesi dalla scadenza dell'ultimo rinnovo dell'automatismo con beneficio dell'ultimo ciclo
									scaduti12Mesi = true;
								}
								else
								{
									//non sono ancora passati 12 mesi dalla scadenza dell'ultimo rinnovo dell'automatismo con beneficio dell'ultimo ciclo
									scaduti12Mesi = false;
								}
							}

							if(scaduti12Mesi)
							{
								//sono scaduti i 12 mesi di intervallo
								rinnovoConBeneficioPrecedente = false;
							}
							else
							{
								//non sono ancora scaduti i 12 mesi di intervallo

								StringBuffer sql3 = new StringBuffer("SELECT COUNT(ID_domanda) AS numRinnovi ");
								sql3.append("FROM GR_rinnovi_12 ");
								sql3.append("WHERE (ID_soggetto = "+idSoggetto+") AND (ID_domanda <> "+IDdomanda+") AND (rinnovo_con_beneficio >= 1) AND (GR_Rinnovi_12.automatismo = 1) ");
								sql3.append("AND (ciclo = "+ultimoCiclo+") ");

								Table t3 = dataTransfer.executeQuery(sql3.toString());
								
								StringBuffer sql4 = new StringBuffer("SELECT COUNT(ID_domanda) AS numRinnovi ");
								sql4.append("FROM GR_rinnovi_12 ");
								sql4.append("WHERE (ID_soggetto = "+idSoggetto+") AND (ID_domanda <> "+IDdomanda+") AND (GR_Rinnovi_12.transizione = 1) AND (GR_Rinnovi_12.automatismo = 1) ");
								sql4.append("AND (ciclo = "+ultimoCiclo+") ");
								
								int numRinnoviConBeneficioPerTransizione = 0;
								Table t4 = dataTransfer.executeQuery(sql4.toString());
								if(t4.getRows()!=0)	
								{
									numRinnoviConBeneficioPerTransizione = t4.getInteger(1, 1);
								}								
								
								if(t3.getRows()==0)	
								{
									rinnovoConBeneficioPrecedente = false;
								}
								else
								{
									int numRinnoviConBeneficio = t3.getInteger(1, 1) + numRinnoviConBeneficioPerTransizione;
									if(numRinnoviConBeneficio>0)
									{
										rinnovoConBeneficioPrecedente = true;
									}
									else
									{
										rinnovoConBeneficioPrecedente = false;
									}
								}
							}
						}
						else
						{
							//transizione
							
							//il soggetto non ha cicli iniziati

							//verifica transizione
							String sqlTrasizione = "SELECT GR_Rinnovi_12_transizione.data_fine_ciclo_24_mesi, GR_Rinnovi_12_transizione.rinnovi_esauriti, GR_Rinnovi_12_transizione.numero_rinnovi_con_beneficio "+
							"FROM GR_Rinnovi_12_transizione INNER JOIN Domande ON Domande.id_soggetto = GR_Rinnovi_12_transizione.ID_soggetto "+
							"WHERE (Domande.ID_domanda = "+IDdomanda+")";

							Table tTransizione = dataTransfer.executeQuery(sqlTrasizione);

							int numRinnoviConBeneficio = 0;

							if(tTransizione.getRows()>0)
							{
								Calendar dataFineCiclo24Mesi = tTransizione.getCalendar(1, 1);
								boolean rinnoviEsauriti = tTransizione.getBoolean(1, 2);
								int numero_rinnovi_con_beneficio = tTransizione.getInteger(1, 3);

								Calendar calDatiDomandaAttuale = (Calendar)dataPresentazioneDomanda.clone();
								calDatiDomandaAttuale.add(Calendar.MONTH, 1);
								calDatiDomandaAttuale.set(Calendar.DAY_OF_MONTH, 1);

								if(calDatiDomandaAttuale.getTime().getTime()<dataFineCiclo24Mesi.getTime().getTime())
								{
									numRinnoviConBeneficio = numero_rinnovi_con_beneficio;
								}
							}

							if(numRinnoviConBeneficio>0)
							{
								rinnovoConBeneficioPrecedente = true;
							}
							else
							{
								rinnovoConBeneficioPrecedente = false;
							}
						}
					}
				}
				else
				{
					System.err.println("ERRORE  c_elab.pat.reddgar12.QRedditoGaranzia.percentualeSpettanteNonOccupati: Rinnovi non trovati per la domanda: "+IDdomanda);
					return 0;
				}
			}

			if(rinnovoConBeneficioPrecedente)
			{
				//applico la riduzione solo se c'è almeno una domanda di rinnovo con beneficio precedente, ovvero, come scritto nella terminologia della delibera, se la domanda è un rinnovo e non la prima domanda del ciclo
				if(nPersoneNucleoLavorativo>0 && sommaPercentuali>0)
				{
					double sommaPerc = sommaPercentuali;
					double nPers = nPersoneNucleoLavorativo;
					double cento = 100.0;
					ret = (sommaPerc/nPers/cento);
				}
				else
				{
					ret = 1.0;
				}
			}
			else
			{
				ret = 1.0;
			}

		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0;
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0;
		}

		return ret;
	}

	public double percentualeSpettanteDecuratazioneStrutturaCoperturaParziale()
	{
		double ret = 1.0;

		if(servizio == idServizioRedditoGaranziaSociale)
		{
			boolean nucleo_struttura_coperturaParz = datiRedditoGaranzia.getBoolean(1,20);
			if(nucleo_struttura_coperturaParz && datiRedditoGaranzia.getString(1, 21) != null && datiRedditoGaranzia.getString(1,21).length()>0)
			{
				int nucleo_struttura_coperturaParz_decurt = datiRedditoGaranzia.getInteger(1, 21);
				if(nucleo_struttura_coperturaParz_decurt<0)
				{
					nucleo_struttura_coperturaParz_decurt = 0;
				}
				if(nucleo_struttura_coperturaParz_decurt>60)
				{
					nucleo_struttura_coperturaParz_decurt = 60;
				}
				double perc100 = 100.0;
				double one = 1.0;
				double perc = nucleo_struttura_coperturaParz_decurt;
				ret = one - (perc/perc100);
			}
		}

		return ret;
	}

	public boolean aventeRequisitiAiFiniDelCalcoloImportoConResidenti(int rowNumber)
	{
		boolean ret = false;

		boolean esclusaUfficio = records.getBoolean(rowNumber, 1);
		int id_tp_esclusione = records.getInteger(rowNumber, 33);
		boolean genitoreAltroFiglio = records.getBoolean(rowNumber,35);
		int relazioneParentela = records.getInteger(rowNumber,31);
		boolean coniugeNonResidente = false;
		if(relazioneParentela==reddGar_IDRelazioneParentela_ConiugeNonResidente ||
				relazioneParentela==reddGarSociale_IDRelazioneParentela_ConiugeNonResidente)
		{
			coniugeNonResidente = true;
		}				

		if(!esclusaUfficio && !coniugeNonResidente && (id_tp_esclusione==0) && !genitoreAltroFiglio)
		{
			ret = true;
		}
		return ret;
	}

	public int getNumeroBeneficiari(boolean effettivi)
	{
		if (records == null)
			return 0;

		int nBeneficiari = 0;

		try {
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);

			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean residenteDaAlmeno3Anni = records.getBoolean(i,29);
				boolean aventeRequisitiAiFiniDelCalcoloImporto = aventeRequisitiAiFiniDelCalcoloImportoConResidenti(i);

				boolean condannato = records.getBoolean(i,36);
				if(!effettivi)
				{
					condannato = false;
				}

				if(aventeRequisitiAiFiniDelCalcoloImporto && !condannato)
				{
					Calendar dataNascita = Calendar.getInstance();
					try {
						dataNascita = records.getCalendar(i, 30);
					} catch (Exception e) {
						System.out.println("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda);
						e.printStackTrace();
						dataNascita.set(1900, 0, 1, 0, 0);
					}	            	

					// verifico se è un maggiorenne
					int eta = getEta(dataNascita,(Calendar)dataPresentazioneDomanda.clone());
					boolean maggiorenne = true;
					if(eta<18)
					{
						maggiorenne = false;
					}

					if(!maggiorenne) 
					{
						//è un minorenne, allora lo aggiungo sempre
						nBeneficiari++;
					}
					else if (residenteDaAlmeno3Anni)
					{
						//è stata selezionata la residenza triennale, allora lo aggiungo
						nBeneficiari++;
					}

				}
				else
				{
					//esclusa d'ufficio o 
					//detenuto in istituto di pena o
					//ospitato presso strutture residenziali socio-sanitarie o socio-assistenziali o
					//non residente, convivente e genitore di minori residenti o 
					//soggetto con grado di parentela coniuge non residente 
				}

			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0;
		}
		return nBeneficiari;
	}

	public int getNumeroIdonei()
	{
		if (records == null)
			return 0;

		int nIdonei = 0;
		int nIdoneiEResidentiMaggioriDi3Anni = 0;
		int nIdoneiMinoriDi3Anni = 0;

		try {
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);

			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean residenteDaAlmeno3Anni = records.getBoolean(i,29);
				boolean aventeRequisitiAiFiniDelCalcoloImporto = aventeRequisitiAiFiniDelCalcoloImportoConResidenti(i);

				if(aventeRequisitiAiFiniDelCalcoloImporto)
				{
					Calendar dataNascita = Calendar.getInstance();
					try {
						dataNascita = records.getCalendar(i, 30);
					} catch (Exception e) {
						System.out.println("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda);
						e.printStackTrace();
						dataNascita.set(1900, 0, 1, 0, 0);
					}	            	

					// verifico se è ha compiuto almeno tre anni
					int eta = getEta(dataNascita,(Calendar)dataPresentazioneDomanda.clone());
					boolean maggiore_3anni = true;
					if(eta<3)
					{
						maggiore_3anni = false;
						nIdoneiMinoriDi3Anni++;
					}
					if(maggiore_3anni && residenteDaAlmeno3Anni) 
					{
						nIdoneiEResidentiMaggioriDi3Anni++;
					}

				}
				else
				{
					//esclusa d'ufficio o 
					//detenuto in istituto di pena o
					//ospitato presso strutture residenziali socio-sanitarie o socio-assistenziali o
					//non residente, convivente e genitore di minori residenti o 
					//soggetto con grado di parentela coniuge non residente 
				}

			}

			if(nIdoneiEResidentiMaggioriDi3Anni>0)
			{
				nIdonei = nIdoneiEResidentiMaggioriDi3Anni + nIdoneiMinoriDi3Anni;
			}
			else
			{
				nIdonei = 0;
			}



		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0;
		}
		return nIdonei;
	}

	public static int getEta(Calendar calNascita, Calendar calRif) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int eta = 0;
		if (calRif == null)
			calRif = Calendar.getInstance();

		eta = calRif.get(Calendar.YEAR) - calNascita.get(Calendar.YEAR);

		if (calRif.get(Calendar.MONTH) - calNascita.get(Calendar.MONTH) < 0) {
			eta = eta - 1;
		}

		if (calRif.get(Calendar.MONTH) == calNascita.get(Calendar.MONTH)
				&& calRif.get(Calendar.DAY_OF_MONTH)
				< calNascita.get(Calendar.DAY_OF_MONTH)) {
			eta = eta - 1;
		}

		//        if(dataNascita.getYear()>= 90) { // l'utente è nato tra il 1890  e 1900
		//            eta += 100;
		//        }

		//System.out.println("usufrutto = " + eta);

		return eta;
	}

	public static int getMesi(Calendar calDa, Calendar calRif) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int mesi = 0;
		if (calRif == null || calDa == null)
			return 0;

		if (calDa.getTime().getTime()>calRif.getTime().getTime())
			return 0;

		mesi = 12 * (calRif.get(Calendar.YEAR) - calDa.get(Calendar.YEAR));
		mesi += calRif.get(Calendar.MONTH) - calDa.get(Calendar.MONTH);

		if (calRif.get(Calendar.DAY_OF_MONTH) - calDa.get(Calendar.DAY_OF_MONTH) < 0) {
			mesi = mesi - 1;
		}

		return mesi;
	}

	public boolean domandaConAttestazioneInsussistenzaNecessitaProgettoSociale() {
		boolean ret = false;

		if(datiRinnovo!=null && datiRinnovo.getRows()>0)
		{
			String idAttestazioneInsussistenza = (String)datiRinnovo.getElement(1, 1);
			if(idAttestazioneInsussistenza!=null)
			{
				ret = true;
			}
		}

		return ret;
	}

	public boolean domandaConAttestazioneInsussistenzaNecessitaProgettoSocialeCheSanaIncongruita() {
		boolean ret = false;

		if(datiRinnovo!=null && datiRinnovo.getRows()>0)
		{
			String idAttestazioneInsussistenza = (String)datiRinnovo.getElement(1, 1);
			String sanaAncheIncongruitaString = (String)datiRinnovo.getElement(1, 12);
			boolean sanaAncheIncongruita = false;
			if(sanaAncheIncongruitaString!=null)
			{
				if(Integer.valueOf(sanaAncheIncongruitaString)!=0)
				{
					sanaAncheIncongruita = true;
				}
			}
			if(idAttestazioneInsussistenza!=null && sanaAncheIncongruita)
			{
				ret = true;
			}
		}

		return ret;
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}

	public static void main(String[] args) throws Exception {
		String dataDaStr = "31/12/2011";
		String dataPresentazioneStr = "31/01/2010";


		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataDa = Calendar.getInstance();
		dataDa.setTime((Date)formatter.parse(dataDaStr));
		dataDa.set(Calendar.HOUR_OF_DAY, 0);
		dataDa.set(Calendar.MINUTE, 0);
		dataDa.set(Calendar.SECOND, 0);
		dataDa.set(Calendar.MILLISECOND, 0);

		Calendar dataPresentazione = Calendar.getInstance();
		dataPresentazione.setTime((Date)formatter.parse(dataPresentazioneStr));
		dataPresentazione.set(Calendar.HOUR_OF_DAY, 0);
		dataPresentazione.set(Calendar.MINUTE, 0);
		dataPresentazione.set(Calendar.SECOND, 0);
		dataPresentazione.set(Calendar.MILLISECOND, 0);

		System.out.println("data da: "+dataDa.getTime().toString());
		System.out.println("data pres: "+dataPresentazione.getTime().toString());

		int mesi = getMesi(dataDa, dataPresentazione);
		System.out.println("numero mesi: "+mesi);
	}
}
