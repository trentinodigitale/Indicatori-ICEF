package c_elab.pat.reddgar;

import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QRedditoGaranzia extends ElainNode {
	
	//CAMBIAMI: va cambiata ogni anno
	protected int reddGar_IDRelazioneParentela_ConiugeNonConvivente = 3080;
	private int reddGarSociale_IDRelazioneParentela_ConiugeNonConvivente = 3081;
	
	protected Table datiRedditoGaranzia;
	protected Table datiRinnovo;
	
	/** QProvvisorio constructor */
	public QRedditoGaranzia() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession(  QRedditoGaranzia.class.getName(), IDdomanda );
		datiRedditoGaranzia = null;
		datiRinnovo = null;
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QRedditoGaranzia.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			
			//              	  		   1         	   			2	    	   		   3   		 	  		  4					  		  5			  		   6					  7	
			sql.append("SELECT GR_Dati.locazione, GR_Dati.imp_canone_mensile, GR_Dati.sociale_no, GR_Dati.sociale_attestazione, GR_Dati.sociale_data, GR_Dati.sociale_si, GR_Dati.sociale_inizio_mese, "); 
			//				   		   8				      9					   10						     11
			sql.append("GR_Dati.sociale_durata, GR_Dati.esclusa_ufficio, Doc.data_presentazione, GR_Dati.sociale_inizio_anno ");
			sql.append("FROM GR_Dati INNER JOIN ");
			sql.append("Domande ON GR_Dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE GR_Dati.ID_domanda = ");
			sql.append(IDdomanda);
			
			doQuery(sql.toString());
			datiRedditoGaranzia = records;
			
			
			sql = new StringBuffer();	
			
			//							   1				2			  3			  4			  5	
			sql.append("SELECT ID_attestazione_insuss, ID_soggetto, ciclo_24_mesi, rinnovo, tutti_lavorano, ");
			//			   		6			  7			   8		  9		      10			    11					12	
			sql.append("importo_mensile, anno_inizio, mese_inizio, per_mesi, data_rinnovo, rinnovo_con_beneficio, primo_rinnovo ");
			sql.append("FROM GR_Rinnovi ");
			sql.append("WHERE ID_domanda = ");
			sql.append(IDdomanda);
			
			doQuery(sql.toString());
			datiRinnovo = records;
			
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
			//					   27			  28						29					   30					31									32
			sql.append("Soggetti.cognome, Soggetti.nome, Familiari.residenza_storica, Soggetti.data_nascita, Familiari.ID_relazione_parentela, Soggetti.ID_tp_sex ");
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

		} else {
			records = session.getRecords();
			datiRedditoGaranzia = (Table)session.getAttribute("datiRedditoGaranzia");
			datiRinnovo = (Table)session.getAttribute("datiRinnovo");
		}
	}
	
	public double getNumeroResidenti(boolean verificoSoloMaggiorenni, boolean verificoEsclusioneUfficio)
	{
		if (records == null)
			return 0.0;

		double nResidenti = 0.0;
				
		
		try {
			
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);
			
			for (int i = 1; i <= records.getRows(); i++) 
			{
				
				boolean esclusaUfficio = records.getBoolean(i, 1);
				boolean residenteDaAlmeno3Anni = records.getBoolean(i,29);
				int relazioneParentela = records.getInteger(i,31);
				boolean coniugeNonConvivente = false;
				if(relazioneParentela==reddGar_IDRelazioneParentela_ConiugeNonConvivente ||
						relazioneParentela==reddGarSociale_IDRelazioneParentela_ConiugeNonConvivente)
				{
					coniugeNonConvivente = true;
				}
				
				if(!verificoEsclusioneUfficio)
				{
					esclusaUfficio = false;
				}
	            if(!esclusaUfficio && !coniugeNonConvivente)
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
	            		if(!verificoSoloMaggiorenni)
	            		{
	            			//è un minorenne, allora lo aggiungo
	            			nResidenti = nResidenti + 1.0;
	            		}
	            	}
	            	else if (residenteDaAlmeno3Anni)
					{
	            		//è stata selezionata la residenza triennale, allora lo aggiungo
	            		nResidenti = nResidenti + 1.0;
	            	}
	            }
	            else
	            {
	            	//esclusa d'ufficio oppure il grado di parentela è "Coniuge non convivente", non aggiungo mai il componente
	            }
	            
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return nResidenti;
	}
	
	public double getNumeroIdonei(boolean verificoEsclusioneUfficio )
	{
		if (records == null)
			return 0.0;

		double nIdonei = 0.0;
				
		try {
			Calendar dataPresentazioneDomanda = datiRedditoGaranzia.getCalendar(1, 10);
			
			for (int i = 1; i <= records.getRows(); i++) 
			{
				boolean esclusaUfficio = records.getBoolean(i, 1);
				boolean residenteDaAlmeno3Anni = records.getBoolean(i,29);
				
				if(!verificoEsclusioneUfficio)
				{
					esclusaUfficio = false;
				}
				
	            if(!esclusaUfficio)
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
	        		}
	        		
	        		
	        		if(maggiore_3anni && residenteDaAlmeno3Anni) 
	            	{
	            		
	        			nIdonei = nIdonei + 1.0;
	            	}
	            	
	            }
	            else
	            {
	            	//esclusa d'ufficio oppure il grado di parentela è "Coniuge non convivente", non aggiungo mai il componente
	            }
	            
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
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
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}
