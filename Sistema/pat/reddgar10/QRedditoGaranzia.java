package c_elab.pat.reddgar10;

import java.util.Calendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QRedditoGaranzia extends ElainNode {
		
	protected Table datiRedditoGaranzia;
	protected Table datiRinnovo;
	protected Table particolarita;
	
	//CAMBIAMI: va cambiata ogni anno
	protected int reddGar_IDRelazioneParentela_ConiugeNonResidente = 5078;
	private int reddGarSociale_IDRelazioneParentela_ConiugeNonResidente = 5087;
	
	private void setRelazioneParentelaConiugeNONResidente () {
		if((servizio == 30000 && periodo == 30300) || (servizio == 30001 && periodo == 30301)){
			reddGar_IDRelazioneParentela_ConiugeNonResidente = 6112;
			reddGarSociale_IDRelazioneParentela_ConiugeNonResidente = 6122;
		}
	}
	
	
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

		setRelazioneParentelaConiugeNONResidente();
		
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();
			
			//              	  		   1         	   			2	    	   		   3   		 	  		  4					  		  5			  		   6					  7	
			sql.append("SELECT GR_Dati.locazione, GR_Dati.imp_canone_mensile, GR_Dati.sociale_no, GR_Dati.sociale_attestazione, GR_Dati.sociale_data, GR_Dati.sociale_si, GR_Dati.sociale_inizio_mese, "); 
			//				   		   8				      9					   10						     11								12					
			sql.append("GR_Dati.sociale_durata, GR_Dati.esclusa_ufficio, Doc.data_presentazione, GR_Dati.sociale_inizio_anno, GR_Dati.imp_int_mutuo_mensile, ");
			//						13								14								15								16						17
			sql.append("GR_Dati.senza_fissa_dimora, GR_Dati.ID_tp_scelta_incongrua, GR_Dati.ID_tp_scelta_sociale_icef, GR_Dati.ID_tp_esclusione, GR_Dati.mesi_revoca, ");
			//								18								19
			sql.append("GR_Dati.incongrua_almeno_una_volta, GR_Dati.attesa_scelta_sociale_icef ");
			sql.append("FROM GR_Dati INNER JOIN ");
			sql.append("Domande ON GR_Dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE GR_Dati.ID_domanda = ");
			sql.append(IDdomanda);
			
			doQuery(sql.toString());
			datiRedditoGaranzia = records;
			
			
			sql = new StringBuffer();	
			
			//							   1									  2			  			   3			  		  4			  			 5	
			sql.append("SELECT GR_Rinnovi.ID_attestazione_insuss, GR_Rinnovi.ID_soggetto, GR_Rinnovi.ciclo_24_mesi, GR_Rinnovi.rinnovo, GR_Rinnovi.tutti_lavorano, ");
			//			   					6			  			7			   			8		  			  9		      			10	
			sql.append("GR_Rinnovi.importo_mensile, GR_Rinnovi.anno_inizio, GR_Rinnovi.mese_inizio, GR_Rinnovi.per_mesi, GR_Rinnovi.data_rinnovo, ");
			//	    						11							  12							13
			sql.append("GR_Rinnovi.rinnovo_con_beneficio, GR_Rinnovi.primo_rinnovo, GR_Att_dati.sana_anche_incongruita ");
			sql.append("FROM GR_Rinnovi LEFT OUTER JOIN ");
			sql.append("GR_Att_dati ON GR_Att_dati.ID_domanda = GR_Rinnovi.ID_attestazione_insuss ");
			sql.append("WHERE GR_Rinnovi.ID_domanda = ");
			sql.append(IDdomanda);
			
			doQuery(sql.toString());
			datiRinnovo = records;
			
			sql = new StringBuffer();
			//              							1                          2                         				3						 4
			sql.append("SELECT     tp_invalidita.coeff_invalidita, Familiari.spese_invalidita, R_Relazioni_parentela.peso_componente, Familiari.ID_dichiarazione ");
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
			sql.append("Soggetti.cognome, Soggetti.nome, Familiari.residenza_storica, Soggetti.data_nascita, Familiari.ID_relazione_parentela, Soggetti.ID_tp_sex, GR_Familiari.ID_tp_esclusione, GR_Familiari.RSA_spese, GR_Familiari.genitore_altro_figlio ");
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
	
	public boolean aventeRequisitiAiFiniDelCalcoloImporto(int rowNumber)
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
	
	public int getNumeroResidenti()
	{
		if (records == null)
			return 0;

		int nResidenti = 0;
				
		try {
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
	            		nResidenti++;
	            	}
	            	else if (residenteDaAlmeno3Anni)
					{
	            		//è stata selezionata la residenza triennale, allora lo aggiungo
	            		nResidenti++;
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
			return 0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0;
		}
		return nResidenti;
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
	    			//che non coabita o
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
	
	public boolean domandaConAttestazioneInsussistenzaNecessitaProgettoSocialeCheSanaInconguita() {
		boolean ret = false;

		if(datiRinnovo!=null && datiRinnovo.getRows()>0)
		{
			String idAttestazioneInsussistenza = (String)datiRinnovo.getElement(1, 1);
			String sanaAncheIncongruitaString = (String)datiRinnovo.getElement(1, 13);
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
}
