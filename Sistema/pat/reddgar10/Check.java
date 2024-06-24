package c_elab.pat.reddgar10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Check extends QRedditoGaranzia {

	//CAMBIAMI: va cambiata se vengono introdotti nuovi servizi
	private long idServizioRedditoGaranzia = 30000;
	private long idServizioRedditoGaranziaSociale = 30001;
	//VERIFICAMI: va modificato se cambia la delibera
	private int numMaxRinnoviConBeneficioRedditoGaranzia = 4;

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		double check = 0.0;

		try 
		{
			//Check 1: ci deve essere almeno un soggetto avente diritto al fine del calcolo dell'importo.
			//Il numero dei soggetti aventi diritto al fine del calcolo dell'importo è dato 
			//dal numero dei soggetti aventi almeno 3 anni di età non contando:
			//	- i non residenti da almeno 3 anni in Trentino,
			//	- gli esclusi d'ufficio, 
			//	- quelli detenuti in istituto di pena, 
			//	- ospitati presso strutture residenziali socio-sanitarie o socio-assistenziali,
			//	- che non coabitano, 
			//	- non residente, convivente e genitore di minori residenti e 
			//	- soggetti con grado di parentela coniuge non residente
			//solo se c'è almeno uno di questi soggetti idonei si sommano anche i soggetti con meno di 3 anni di età non contando:
			//	- gli esclusi d'ufficio, 
			//	- quelli detenuti in istituto di pena, 
			//	- ospitati presso strutture residenziali socio-sanitarie o socio-assistenziali,
			//	- che non coabitano, 
			//	- non residente, convivente e genitore di minori residenti e 
			//	- soggetti con grado di parentela coniuge non residente
			int numeroIdonei = getNumeroIdonei();
			if(numeroIdonei == 0)
			{
				check += 100000000;
			}

			//Check 2: l'intera domanda è stata esclusa d'ufficio
			boolean domandaEsclusaUfficio = datiRedditoGaranzia.getBoolean(1, 9);
			if(domandaEsclusaUfficio)
			{
				check += 20000000;
			}

			//Check 3: mancanza d'impegno nella ricerca del lavoro per almeno un componente. 
			//verifico che i soggetti in età lavorativa disoccupati abbiano indicato la disponibilità al lavoro o una deroga
			//non contando:
			//i non residenti da almeno 3 anni in Trentino,
			//gli esclusi d'ufficio, 
			//quelli detenuti in istituto di pena, 
			//ospitati presso strutture residenziali socio-sanitarie o socio-assistenziali,
			//che non coabitano, 
			//non residente, convivente e genitore di minori residenti e 
			//soggetti con grado di parentela coniuge non residente 
			//Questo check vale solo per l'APAPI (Reddito Garanzia)
			if(servizio == idServizioRedditoGaranzia)
			{
				boolean mancanzaImpegnoRicercaLavoro = mancanzaImpegnoRicercaLavoro();
				if(mancanzaImpegnoRicercaLavoro)
				{
					check += 3000000;
				}
			}

			//Check 4: domanda di tipo Reddito Garanzia (ovvero l'APAPI) che invece andava presentata ai Servizi Sociali (Reddito Garanzia Sociale),
			//controllo che tutti i componenti in età lavorativa non siano dei fannulloni
			//non contando:
			//i non residenti da almeno 3 anni in Trentino,
			//gli esclusi d'ufficio, 
			//quelli detenuti in istituto di pena, 
			//ospitati presso strutture residenziali socio-sanitarie o socio-assistenziali,
			//che non coabitano, 
			//non residente, convivente e genitore di minori residenti e 
			//soggetti con grado di parentela coniuge non residente 
			//Questo check non vale se per la domanda in questione è presente un'attestazione di insussistenza della necessità di progetto sociale
			//Questo check vale solo per l'APAPI (Reddito Garanzia)
			if(servizio == idServizioRedditoGaranzia)
			{
				boolean domandaPresentataErroneamenteAPAPI = domandaPresentataErroneamenteAPAPI();

				if(domandaPresentataErroneamenteAPAPI)
				{
					boolean domandaConAttestazioneInsussistenzaNecessitaProgettoSociale = domandaConAttestazioneInsussistenzaNecessitaProgettoSociale();
					if(!domandaConAttestazioneInsussistenzaNecessitaProgettoSociale)
					{
						check += 400000;
					}
				}
			}

			//Check 5: ICEF sopra il limite
			//Questo check lo gestisce la rete

			//Check 6: Numero rinnovi eccedenti il limite
			//Sono escluse le domande in cui tutti lavorano o quelle per cui è presente un'attestazione di insussistenza della necessità di progetto sociale
			//Questo check vale solo per l'APAPI (Reddito Garanzia)
			if(servizio == idServizioRedditoGaranzia)
			{
				boolean numeroRinnoviEccedenteLimite = numeroRinnoviEccedenteLimite();
				if(numeroRinnoviEccedenteLimite)
				{
					check += 6000;
				}
			}

			//Check 7: Domanda con necessita' di un preventivo vaglio del servizio sociale per nucleo familiare che risulta residente senza fissa dimora
			//Questo check non vale se per la domanda in questione è presente un'attestazione di insussistenza della necessità di progetto sociale
			//Questo check vale solo per l'APAPI (Reddito Garanzia)
			if(servizio == idServizioRedditoGaranzia)
			{
				boolean nucleoSenzaFissaDimora = datiRedditoGaranzia.getBoolean(1, 13);
				if(nucleoSenzaFissaDimora)
				{
					boolean domandaConAttestazioneInsussistenzaNecessitaProgettoSociale = domandaConAttestazioneInsussistenzaNecessitaProgettoSociale();
					if(!domandaConAttestazioneInsussistenzaNecessitaProgettoSociale)
					{
						check += 700;
					}
				}
			}

			//Check 8: Domanda INCONGRUA e l'utente non ha ancora scelto se accettare o meno l'importo dei consumi
			//o ha scelto di non accettare i consumi e il servizio sociale non si è ancora espresso oppure ha giudicato che va bene l'importo calcolato sui consumi
			//Questo check lo gestisce la rete


			//se c'è un errore costruisco l'array di motivi d'escusione, altrimenti torno 0
			if(check!=0)
			{
				check += 1000000000;
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return -1.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return -1.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return -1.0;
		}
		return check;
	}

	private boolean numeroRinnoviEccedenteLimite() {

		boolean ret = false;

		if(datiRinnovo!=null && datiRinnovo.getRows()>0)
		{
			int rinnovoConBeneficio = datiRinnovo.getInteger(1, 11);
			boolean tuttiLavorano = datiRinnovo.getBoolean(1, 5);
			boolean attestazioneInsussistenza = domandaConAttestazioneInsussistenzaNecessitaProgettoSociale();
			int idSoggettoTitolare = datiRinnovo.getInteger(1, 2);
			int ciclo24Mesi = datiRinnovo.getInteger(1, 3);

			if(idSoggettoTitolare!=-1 && ciclo24Mesi!=-1)
			{
				if(rinnovoConBeneficio!=-1 && rinnovoConBeneficio>numMaxRinnoviConBeneficioRedditoGaranzia && !tuttiLavorano && !attestazioneInsussistenza)
				{
					ret = true;
				}
				else
				{
					ret = false;
				}
			}
		}

		return ret;
	}

	private boolean domandaPresentataErroneamenteAPAPI() 
	{
		boolean ret = false;

		int numeroComponentiAPAPI = 0;
		int numeroComponentiServiziSociali = 0;
		int numeroComponentiDaVerificare = 0;

		try
		{
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);


			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean residenteDaAlmeno3Anni = records.getBoolean(i,29);
				boolean aventeRequisitiAiFiniDelCalcoloImporto = aventeRequisitiAiFiniDelCalcoloImporto(i);

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
					boolean nonCollocabileLavoro = records.getBoolean(i,9);
					if(nonCollocabileLavoro)
					{
						daVerificare = false;
					}

					if(daVerificare && residenteDaAlmeno3Anni)
					{
						numeroComponentiDaVerificare++;

						boolean senzaReddito24m = records.getBoolean(i,7);
						boolean dimissioniNonGiustaCausa = records.getBoolean(i,5);
						boolean dimissioniGiustaCausa = records.getBoolean(i,6);
						boolean cercaPrimoLavoro = records.getBoolean(i,15);
						boolean cercaPrimoLavoroEcc = records.getBoolean(i,26);

						if(senzaReddito24m || dimissioniNonGiustaCausa /*|| dimissioniGiustaCausa || cercaPrimoLavoro*/ || cercaPrimoLavoroEcc)
						{
							numeroComponentiServiziSociali++;
						}
						else
						{
							numeroComponentiAPAPI++;
						}
					}
				}
				else
				{
					//esclusa d'ufficio o 
					//detenuto in istituto di pena o
					//ospitato presso strutture residenziali socio-sanitarie o socio-assistenziali o
					//che non coabita o
					//non residente, convivente e genitore di minori residenti o 
					//soggetto con grado di parentela coniuge non residente 
				}
			}

			if(numeroComponentiDaVerificare>0)
			{
				if(numeroComponentiDaVerificare==numeroComponentiServiziSociali)
				{
					ret = true;
				}
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return false;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return false;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return false;
		}
		return ret;
	}

	public boolean domandaInCuiTuttiLavorano() 
	{
		boolean ret = false;

		try
		{
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);

			int numeroComponentiCheLavorano = 0;
			int numeroComponentiDaVerificare = 0;

			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean residenteDaAlmeno3Anni = records.getBoolean(i,29);
				boolean aventeRequisitiAiFiniDelCalcoloImporto = aventeRequisitiAiFiniDelCalcoloImporto(i);

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

					if(daVerificare && residenteDaAlmeno3Anni)
					{
						numeroComponentiDaVerificare++;

						boolean occupato = records.getBoolean(i,4);
						boolean non_collocabile_lavoro= records.getBoolean(i, 9);
						boolean deroga_assistente =records.getBoolean(i,19);
						boolean deroga_serv_civile =records.getBoolean(i,24);
						boolean deroga_studente =records.getBoolean(i,22);

						if(occupato || non_collocabile_lavoro || deroga_assistente || deroga_serv_civile || deroga_studente)
						{
							numeroComponentiCheLavorano++;
						}
					}
				}
				else
				{
					//esclusa d'ufficio o 
					//detenuto in istituto di pena o
					//ospitato presso strutture residenziali socio-sanitarie o socio-assistenziali o
					//che non coabita o
					//non residente, convivente e genitore di minori residenti o 
					//soggetto con grado di parentela coniuge non residente 
				}
			}

			if(numeroComponentiDaVerificare==numeroComponentiCheLavorano)
			{
				ret = true;
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return false;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return false;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return false;
		}
		return ret;
	}

	public boolean domandaInCuiNessunoLavora() 
	{
		boolean ret = false;

		try
		{
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);

			int numeroComponentiCheLavorano = 0;
			int numeroComponentiDaVerificare = 0;

			for (int i = 1; i <= records.getRows(); i++) 
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

				if(daVerificare)
				{
					numeroComponentiDaVerificare++;

					boolean occupato = records.getBoolean(i,4);

					if(occupato)
					{
						numeroComponentiCheLavorano++;
					}
				}
			}

			if(numeroComponentiCheLavorano==0)
			{
				ret = true;
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return false;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return false;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return false;
		}
		return ret;
	}


	private boolean mancanzaImpegnoRicercaLavoro() {

		boolean ret = false;

		try
		{
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);

			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean residenteDaAlmeno3Anni = records.getBoolean(i,29);
				boolean aventeRequisitiAiFiniDelCalcoloImporto = aventeRequisitiAiFiniDelCalcoloImporto(i);

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

					if(daVerificare && residenteDaAlmeno3Anni)
					{
						boolean deroga_assistente =records.getBoolean(i,19);
						boolean deroga_serv_civile =records.getBoolean(i,24);
						boolean deroga_studente =records.getBoolean(i,22);
						boolean disponibilita_lavoro =records.getBoolean(i,12);

						boolean senzaReddito24m = records.getBoolean(i,7);
						boolean dimissioniNonGiustaCausa = records.getBoolean(i,5);
						boolean dimissioniGiustaCausa = records.getBoolean(i,6);
						boolean cercaPrimoLavoro = records.getBoolean(i,15);
						boolean cercaPrimoLavoroEcc = records.getBoolean(i,26);

						if( (senzaReddito24m || dimissioniNonGiustaCausa || dimissioniGiustaCausa || cercaPrimoLavoro || cercaPrimoLavoroEcc)
								&& !deroga_assistente && !deroga_serv_civile && !deroga_studente && !disponibilita_lavoro)
						{
							ret = true;
						}	
					}
				}
				else
				{
					//esclusa d'ufficio o 
					//detenuto in istituto di pena o
					//ospitato presso strutture residenziali socio-sanitarie o socio-assistenziali o
					//che non coabita o
					//non residente, convivente e genitore di minori residenti o 
					//soggetto con grado di parentela coniuge non residente 
				}
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return false;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return false;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return false;
		}
		return ret;
	}

	public static void main(String[] args) {

		try 
		{
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Date d1 = df.parse("02-11-2000 00:00:00");
			calendar.setTime(d1);

			Calendar calendar2 = Calendar.getInstance();
			Date d2 = df.parse("02-11-2009 00:00:00");
			calendar2.setTime(d2);

			System.out.println(calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));
			System.out.println(calendar2.get(Calendar.DAY_OF_MONTH)+"/"+(calendar2.get(Calendar.MONTH)+1)+"/"+calendar2.get(Calendar.YEAR));


			int eta = getEta(calendar, calendar2);
			System.out.println(eta);

		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}



	}
}
