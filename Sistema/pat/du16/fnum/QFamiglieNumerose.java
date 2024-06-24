package c_elab.pat.du16.fnum;
/**
 * CAMBIAMI PassaValoriDu2016 con PassaValoriDu2016
 */
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.du.PassaValoriDu2016;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

/***************************************************************************************************/
/*** CAMBIAMI - NOTA BENE - VA CAMBIATA OGNI ANNO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***/
/***************************************************************************************************/
import c_elab.pat.du16.DU_Util;
import c_elab.pat.icef16.C1_aut;
import c_elab.pat.icef16.C1_dip;
import c_elab.pat.icef16.C1_pens;
import c_elab.pat.icef16.C2_agr;
import c_elab.pat.icef16.C3_imp;
import c_elab.pat.icef16.C4_part;
/***************************************************************************************************/
/*** CAMBIAMI - NOTA BENE - VA CAMBIATA OGNI ANNO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***/
/***************************************************************************************************/


public abstract class QFamiglieNumerose extends ElainNode {
		
	//CAMBIAMI: va cambiata ogni anno
	protected double limiteImportoRedditiFiglioOEquiparato = 6000;
	protected String nodoImportoFNum = "contributo";
	
	protected Table datiFamiglieNumerose;
	protected Table datiImportoPagato;
	
	private C1_aut ca1;
	private C1_dip cd1;
	private C1_pens cp1;
	private C2_agr c2;
	private C3_imp c3;
	private C4_part c4;
	private Hashtable table_redditi;
	
	/** QFamiglieNumerose constructor */
	public QFamiglieNumerose() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		datiFamiglieNumerose = null;
		datiImportoPagato = null;
		ElabContext.getInstance().resetSession(  QFamiglieNumerose.class.getName(), IDdomanda );
		
		if(ca1!=null) {
			ca1.reset();
		}
		if(cd1!=null) {
			cd1.reset();
		}
		if(cp1!=null) {
			cp1.reset();
		}
		if(c2!=null) {
			c2.reset();
		}
		if(c3!=null) {
			c3.reset();
		}
		if(c4!=null) {
			c4.reset();
		}
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QFamiglieNumerose.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();

			// 		   					1         	   		 2					3					   4							  5
			sql.append("SELECT FN_Dati.concepiti, Doc.data_presentazione, FN_Dati.resTAA, DU_Dati.escludi_ufficio_fnum, DU_dati.richiede_fnum,Domande.escludi_ufficio ");
			sql.append("FROM FN_Dati INNER JOIN ");
			sql.append("Domande ON FN_Dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("DU_Dati ON DU_Dati.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE FN_Dati.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());
			datiFamiglieNumerose = records;
			
			sql = new StringBuffer();	
			//							   1										 2
			sql.append("SELECT Doc_edizioni_esiti.importo, Doc_edizioni_esiti.edizione_doc ");
			sql.append("FROM Doc_edizioni_esiti ");
			sql.append("WHERE Doc_edizioni_esiti.node = '"+nodoImportoFNum+"' AND Doc_edizioni_esiti.id_doc = ");
			sql.append(IDdomanda + " ");
			sql.append("ORDER BY Doc_edizioni_esiti.edizione_doc DESC");
			
			doQuery(sql.toString());
			datiImportoPagato = records;
			
			sql = new StringBuffer();
			//							         1				             2				         		3							4
			sql.append("SELECT  Familiari.ID_dichiarazione, Familiari.ID_relazione_parentela, Soggetti.data_nascita, Familiari.residenza_storica ");
			sql.append("FROM Familiari INNER JOIN ");
			sql.append("Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN ");
			sql.append("Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto INNER JOIN ");
			sql.append("Domande ON Familiari.ID_domanda = Domande.ID_domanda INNER JOIN ");
			sql.append("Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("WHERE (Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(") ");
			if(PassaValoriDu2016.getID_dichiarazioni(IDdomanda)!=null)
			{
				sql.append(" AND Familiari.ID_dichiarazione IN ");
				sql.append(PassaValoriDu2016.getID_dichiarazioni(IDdomanda));
			}
			sql.append(" ORDER BY Familiari.ID_dichiarazione");

			doQuery(sql.toString());			
			
			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("datiFamiglieNumerose", datiFamiglieNumerose);
			session.setAttribute("datiImportoPagato", datiImportoPagato);

		} else {
			super.init(dataTransfer);
			records = session.getRecords();
			datiFamiglieNumerose = (Table)session.getAttribute("datiFamiglieNumerose");
			datiImportoPagato = (Table)session.getAttribute("datiImportoPagato");
		}
	}
	
	public int getNumeroConcepiti()
	{
		if (datiFamiglieNumerose == null)
			return 0;
		
		return datiFamiglieNumerose.getInteger(1, 1);
	}
	
	public int getNumeroAffidati()
	{
		if (records == null)
			return 0;
		
		int numeroAffidati = 0;
		
		try {
			for (int i = 1; i <= records.getRows(); i++) 
			{
				int idRelazioneParentela = records.getInteger(i,2);	            	
	            if(idRelazioneParentela==DU_Util.getCodiceMinoreAffidatoOPersonaAccolta())
	            {
	            	numeroAffidati++;
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
		
		return numeroAffidati;
	}
	
	public int getNumeroFigli()
	{
		if (records == null)
			return 0;
		
		table_redditi = new Hashtable();
		
		ca1 = new C1_aut();
		ca1.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
		ca1.init(dataTransfer);
		ca1.getValue();
		updateTableRedditi( ca1.getTable_spese() );
		
		cd1 = new C1_dip();
		cd1.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
		cd1.init(dataTransfer);
		cd1.getValue();
		updateTableRedditi( cd1.getTable_spese() );
		
		cp1 = new C1_pens();
		cp1.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
		cp1.init(dataTransfer);
		cp1.getValue();
		updateTableRedditi( cp1.getTable_spese() );
		
		c2 = new C2_agr();
		c2.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
		c2.init(dataTransfer);
		c2.getValue();
		updateTableRedditi( c2.getTable_spese() );		
		
		c3 = new C3_imp();
		c3.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
		c3.init(dataTransfer);
		c3.getValue();
		updateTableRedditi( c3.getTable_spese() );		
	
		c4 = new C4_part();
		c4.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
		c4.init(dataTransfer);
		c4.getValue();
		updateTableRedditi( c4.getTable_spese() );	
		
		int nFigli = 0;
				
		try {
			Calendar dataNascitaBeneficiario = Calendar.getInstance();
			
			for (int i = 1; i <= records.getRows(); i++) 
			{
				int idDich = records.getInteger(i,1);
				int idRelazioneParentela = records.getInteger(i,2);
				Calendar dataNascita = Calendar.getInstance();
				try {
					dataNascita = records.getCalendar(i, 3);
	            } catch (Exception e) {
	            	System.out.println("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda);
	            	e.printStackTrace();
	            	dataNascita.set(1900, 0, 1, 0, 0);
	            }	            	
	            if(idRelazioneParentela==DU_Util.getCodiceRichiedente())
	            {
	            	dataNascitaBeneficiario = (Calendar) dataNascita.clone();
	            	break;
	            }
			}
			
			for (int i = 1; i <= records.getRows(); i++) 
			{
				int idDich = records.getInteger(i,1);
				int idRelazioneParentela = records.getInteger(i,2);
				Calendar dataNascita = Calendar.getInstance();
				try {
					dataNascita = records.getCalendar(i, 3);
	            } catch (Exception e) {
	            	System.out.println("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda);
	            	e.printStackTrace();
	            	dataNascita.set(1900, 0, 1, 0, 0);
	            }
	            boolean residenteNelNucleoDelRichiedente = records.getBoolean(i,4);
	            
	            //TODO verifica se è corretto quanto fatto cioè che
	            //relazione di parentela FiglioOEquiparato implica già che il soggetto sia residente nel nucleo del richiedente
	            
	            if(idRelazioneParentela==DU_Util.getCodiceFiglioOEquiparato()/* && residenteNelNucleoDelRichiedente*/)
	            {
	            	if(dataNascita.getTime().getTime()>dataNascitaBeneficiario.getTime().getTime())
	            	{
		            	double importoRedditi = 0;
		            	if(table_redditi.containsKey(""+idDich))
		            	{
		            		importoRedditi = ((Double[])table_redditi.get( ""+idDich ))[0].doubleValue();
		            	}
		            	
						if(importoRedditi<limiteImportoRedditiFiglioOEquiparato)
		            	{
							nFigli++;
		            	}
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
		return nFigli;
	}
	
	private void updateTableRedditi(Hashtable table_spese ) {

		if( table_spese!=null && table_spese.size()>0 ) {
			Enumeration spese = table_spese.keys();
			while ( spese.hasMoreElements() ) {
				String id_dich = (String)spese.nextElement();
				if ( table_redditi.containsKey(id_dich) ) {
					double imp = ((Double[])table_redditi.get( id_dich ))[0].doubleValue();
					double peso_par = ((Double[])table_redditi.get( id_dich ))[1].doubleValue();
					double imp_next = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
					table_redditi.remove(id_dich);
					table_redditi.put( id_dich, new Double[]{ new Double(imp+imp_next), new Double(peso_par)} );
				}else {
					table_redditi.put( id_dich, (Double[])table_spese.get(id_dich) );
				}
			}
		}
	}
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}
