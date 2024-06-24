package c_elab.pat.du12.acq;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.du.PassaValoriDu2012;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import c_elab.pat.du12.DU_Util;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QContrPotAcq extends ElainNode {
	
	//CAMBIAMI: va cambiata ogni anno	
	protected String dataRiferimentoFigli26Inizio = "01/07/1986";
	protected String dataRiferimentoFigli26Fine = "30/06/2012";
	protected int numeroMassimoFigli26 = 8;
	protected String dataRiferimentoNuoviNatiInizio = "28/06/2011";
	protected String dataRiferimentoNuoviNatiFine = "27/06/2012 ";
	
	//VERIFICAMI: va verificata ogni anno
	protected String nodoImportoAcq = "contributo_acquisti";
	
	protected Table datiContrPotAcq;
	protected Table datiImportoPagato;
	
	/** QFamiglieNumerose constructor */
	public QContrPotAcq() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		datiContrPotAcq = null;
		datiImportoPagato = null;
		ElabContext.getInstance().resetSession(  QContrPotAcq.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QContrPotAcq.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();

			// 		   					1         	   		    2					3					   		   					4							5												6													7
			sql.append("SELECT Doc.data_presentazione, FN_Dati.resTAA, DU_Dati.escludi_ufficio_contr_sost_consumi, DU_dati.richiede_contr_sost_consumi, DU_dati.forzatura_contr_sost_consumi_garanzia, DU_dati.id_domanda_garanzia_forz_contr_sost_consumi, Domande.escludi_ufficio ");
			sql.append("FROM FN_Dati INNER JOIN ");
			sql.append("Domande ON FN_Dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("DU_Dati ON DU_Dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE FN_Dati.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			datiContrPotAcq = records;
			
			sql = new StringBuffer();	
			//							   1										 2
			sql.append("SELECT Doc_edizioni_esiti.importo, Doc_edizioni_esiti.edizione_doc ");
			sql.append("FROM Doc_edizioni_esiti ");
			sql.append("WHERE Doc_edizioni_esiti.node = '"+nodoImportoAcq+"' AND Doc_edizioni_esiti.id_doc = ");
			sql.append(IDdomanda + " ");
			sql.append("ORDER BY Doc_edizioni_esiti.edizione_doc DESC");
			
			doQuery(sql.toString());
			datiImportoPagato = records;
			
			sql = new StringBuffer();
			//							         1				             2				         		3
			sql.append("SELECT  Familiari.ID_dichiarazione, Familiari.ID_relazione_parentela, Soggetti.data_nascita ");
			sql.append("FROM Familiari INNER JOIN ");
			sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN ");
			sql.append("Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto INNER JOIN ");
			sql.append("Domande ON Familiari.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE (Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
			
			if(PassaValoriDu2012.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2012.getID_dichiarazioni(IDdomanda));
				sql.append(" ORDER BY Familiari.ID_dichiarazione");
			}

			doQuery(sql.toString());			
			
			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("datiContrPotAcq", datiContrPotAcq);
			session.setAttribute("datiImportoPagato", datiImportoPagato);

		} else {
			super.init(dataTransfer);
			records = session.getRecords();
			datiContrPotAcq = (Table)session.getAttribute("datiContrPotAcq");
			datiImportoPagato = (Table)session.getAttribute("datiImportoPagato");
		}
	}
	
	public int getNumeroFigli26()
	{
		if (records == null)
			return 0;
		
		int nFigli26 = 0;
				
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar dataRifFigli26Inizio = Calendar.getInstance();
			dataRifFigli26Inizio.setTime((Date)formatter.parse(dataRiferimentoFigli26Inizio));
			dataRifFigli26Inizio.set(Calendar.HOUR_OF_DAY, 0);
			dataRifFigli26Inizio.set(Calendar.MINUTE, 0);
			dataRifFigli26Inizio.set(Calendar.SECOND, 0);
			dataRifFigli26Inizio.set(Calendar.MILLISECOND, 0);
			
			Calendar dataRifFigli26Fine = Calendar.getInstance();
			dataRifFigli26Fine.setTime((Date)formatter.parse(dataRiferimentoFigli26Fine));
			dataRifFigli26Fine.set(Calendar.HOUR_OF_DAY, 0);
			dataRifFigli26Fine.set(Calendar.MINUTE, 0);
			dataRifFigli26Fine.set(Calendar.SECOND, 0);
			dataRifFigli26Fine.set(Calendar.MILLISECOND, 0);
			
			
			for (int i = 1; i <= records.getRows(); i++) 
			{
				Calendar dataNascita = Calendar.getInstance();
				try {
					dataNascita = records.getCalendar(i, 3);
	            } catch (Exception e) {
	            	System.out.println("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda);
	            	e.printStackTrace();
	            	dataNascita.set(1900, 0, 1, 0, 0);
	            }
	    			
	    		int idRelazioneParentela = records.getInteger(i,2);	            	
	            if(idRelazioneParentela==DU_Util.getCodiceFiglioOEquiparato() ||
	            		idRelazioneParentela==Acq_Util.getCodiceFiglioOEquiparato())
	            {
	            	if ( dataNascita.getTime().getTime() >= dataRifFigli26Inizio.getTime().getTime() && 
		    				dataNascita.getTime().getTime() <= dataRifFigli26Fine.getTime().getTime())	{
	            		nFigli26++;
		    		}	            	
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
		
		if(nFigli26 > numeroMassimoFigli26)
		{
			nFigli26 = numeroMassimoFigli26;
		}
		
		return nFigli26;
	}
	
	public int getNumeroNati()
	{
		if (records == null)
			return 0;
		
		int nNati = 0;
				
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar dataRifNuoviNatiInizio = Calendar.getInstance();
			dataRifNuoviNatiInizio.setTime((Date)formatter.parse(dataRiferimentoNuoviNatiInizio));
			dataRifNuoviNatiInizio.set(Calendar.HOUR_OF_DAY, 0);
			dataRifNuoviNatiInizio.set(Calendar.MINUTE, 0);
			dataRifNuoviNatiInizio.set(Calendar.SECOND, 0);
			dataRifNuoviNatiInizio.set(Calendar.MILLISECOND, 0);			
			
			Calendar dataRifNuoviNatiFine = Calendar.getInstance();
			dataRifNuoviNatiFine.setTime((Date)formatter.parse(dataRiferimentoNuoviNatiFine));
			dataRifNuoviNatiFine.set(Calendar.HOUR_OF_DAY, 0);
			dataRifNuoviNatiFine.set(Calendar.MINUTE, 0);
			dataRifNuoviNatiFine.set(Calendar.SECOND, 0);
			dataRifNuoviNatiFine.set(Calendar.MILLISECOND, 0);
			
			for (int i = 1; i <= records.getRows(); i++) 
			{
				Calendar dataNascita = Calendar.getInstance();
				try {
					dataNascita = records.getCalendar(i, 3);
	            } catch (Exception e) {
	            	System.out.println("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda);
	            	e.printStackTrace();
	            	dataNascita.set(1900, 0, 1, 0, 0);
	            }
	    		
	            int idRelazioneParentela = records.getInteger(i,2);	            	
	            if(idRelazioneParentela==DU_Util.getCodiceFiglioOEquiparato() ||
	            		idRelazioneParentela==Acq_Util.getCodiceFiglioOEquiparato())
	            {
		    		if ( dataNascita.getTime().getTime() >= dataRifNuoviNatiInizio.getTime().getTime() && 
		    				dataNascita.getTime().getTime() <= dataRifNuoviNatiFine.getTime().getTime())	{
		    			nNati++;
		    		}
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
		return nNati;
	}
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}
