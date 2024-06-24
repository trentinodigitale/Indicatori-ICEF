package c_elab.pat.du21.stud;

import java.text.ParseException;
import java.util.Calendar;
//import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//CAMBIAMI PassaValoriDu2019

import it.clesius.apps2core.ElainNode;
import it.clesius.apps2core.ricalcolo.client.memory.MemoryCache;
import it.clesius.db.sql.RunawayData;
//import it.clesius.db.util.DateTimeFormat;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.du.PassaValoriDu2021;

/** 
 * CAMBIAMI PassaValoriDu2020
 * 
 * legge il numero dei minori o i maggiorenni fino a 20 anni frequentanti
 * @author g_barbieri
 */
public class Figli extends ElainNode {

	private static Log log = LogFactory.getLog( Figli.class );
	private Calendar datarif  = Calendar.getInstance();
	private boolean calcolaFigli = true;

	/** Figli constructor data fine anno scolastico */
	public Figli() {
		//CAMBIAMI: va cambiata ogni anno - FATTO AP
		datarif.set(2022, 5, 10, 23, 59); // 10 giugno 2022
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
		calcolaFigli = PassaValoriDu2021.isCalcolaFigli(IDdomanda);
		if(calcolaFigli)
		{
			/*StringBuffer sql = new StringBuffer();
			// alla data del 9 giugno 2013
			// minori di 18 anni OR (minori di 20 anni AND studenti)  
			//                                    1                     2
			sql.append("SELECT STUD_Studenti.ID_tp_scuola, Soggetti.data_nascita ");
			sql.append("FROM Soggetti INNER JOIN Dich_icef ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN Familiari ON Dich_icef.ID_dichiarazione = Familiari.ID_dichiarazione LEFT OUTER JOIN STUD_Studenti ON Familiari.ID_domanda = STUD_Studenti.ID_domanda AND Dich_icef.ID_soggetto = STUD_Studenti.ID_soggetto ");
			sql.append("WHERE Familiari.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(" ORDER BY STUD_Studenti.ID_tp_scuola");
			doQuery(sql.toString());
			 */
			getFamiliariFromSql(IDdomanda);
		}
	}


	public void getFamiliariFromSql(String IDdomanda) {
		//TRASPORTI
		StringBuffer sb = new StringBuffer();
		// 			                         1                       2                      3 				    4                         5
		sb.append("SELECT Familiari.ID_dichiarazione, Soggetti.data_nascita, Familiari.nucleo_dal, Familiari.nucleo_al, GETDATE() as data_attuale, ");
		//								6						  		7					 		8			  		  9	                    10			
		sb.append("doc_domanda.data_presentazione, doc_domanda.data_sottoscrizione, doc_domanda.id_tp_stato, doc_domanda.crc, STUD_Studenti.ID_tp_scuola,Domande.id_domanda ");
		sb.append("FROM  Domande INNER JOIN ");
		sb.append(" Doc AS doc_domanda ON Domande.ID_domanda = doc_domanda.ID INNER JOIN");
		sb.append(" Familiari ON Domande.ID_domanda = Familiari.ID_domanda INNER JOIN");
		sb.append(" Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN");
		sb.append(" Doc ON Dich_icef.ID_dichiarazione = Doc.ID INNER JOIN");
		sb.append(" Soggetti ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto LEFT OUTER JOIN ");
		sb.append(" STUD_Studenti ON Familiari.ID_domanda = STUD_Studenti.ID_domanda AND Dich_icef.ID_soggetto = STUD_Studenti.ID_soggetto ");
		sb.append("WHERE (Familiari.ID_domanda = ");
		sb.append(IDdomanda);
		sb.append(") ");
		sb.append("ORDER BY Familiari.ID_dichiarazione");

		if(memoryElab){
			Table tb=MemoryCache.getInstance().getMemoryCacheTable(this.getClass(),IDdomanda,IDdomanda);
			if(tb==null){
				doQuery(sb.toString());
				MemoryCache.getInstance().addMemoryCache(this.getClass(),IDdomanda,records,"id_domanda");
				tb=MemoryCache.getInstance().getMemoryCacheTable(this.getClass(),IDdomanda,IDdomanda);
			}
			records=tb;
		}else{
			doQuery(sb.toString());
		}

		//System.out.println(sb.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if(calcolaFigli)
		{
			Calendar data_nascita = Calendar.getInstance();
			int tot = 0;
			try {
				for (int i = 1; i <= records.getRows(); i++) {
					try {
						data_nascita = records.getCalendar(i, 2);
					} catch (Exception e) {
						log.error("Errore di lettura della data di nascita per l'elemento " + i + " della domanda " + IDdomanda, e);
						data_nascita.set(1900, 0, 1, 0, 0);
					}

					String ID_dichiarazione = records.getString(i, 1);

					Calendar nucleo_dal = getData(records, i, 3);
					Calendar nucleo_al = getData(records, i, 4);
					Calendar data_attuale = records.getCalendar(i, 5);
					Calendar data_presentazione = records.getCalendar(i, 6);
					Calendar data_sottoscrizione = getData(records, i, 7);
					int id_tp_stato = records.getInteger(i, 8);
					String crc = records.getString(i, 9);

					data_attuale.set(Calendar.HOUR_OF_DAY, 0);
					data_attuale.set(Calendar.MINUTE, 0);
					data_attuale.set(Calendar.SECOND, 0);
					data_attuale.set(Calendar.MILLISECOND, 0);

					Calendar data_riferimento = data_presentazione;

					//attuale e mensa
					/*
	    			boolean maiTrasmessa = true;
	    			if ( crc != null ) {
	    				if ( !(crc.equals("") || crc.equals("0") ) ) {
	    					maiTrasmessa = false;
	    				}
	    			}

	    			if(maiTrasmessa)
	    			{
	    				data_riferimento = data_presentazione;
	    			}
	    			else
	    			{
	    				if(id_tp_stato==0)
	    				{
	    					data_riferimento = data_attuale;
	    				}
	    				else
	    				{
	    					data_riferimento = data_sottoscrizione;
	    				}
	    			}
					 */
					/*
	    			if(nucleo_dal!=null)
	    			{
	    				//System.out.println("Data dal:" + nucleo_dal.getTime().toString()+ DateTimeFormat.toSqlDate(nucleo_dal.getTime()) );
	    				//System.out.println("Data riferimento:" + data_riferimento.getTime().toString()+ DateTimeFormat.toSqlDate(data_riferimento.getTime()) );
	    				//System.out.println("Data presentazione:" + data_presentazione.getTime().toString()+ DateTimeFormat.toSqlDate(data_presentazione.getTime()) );
	    			}
	    			else
	    			{
	    				//System.out.println("Data dal:" + nucleo_dal);
	    			}
	    			if(nucleo_al!=null)
	    			{
	    				//System.out.println("Data al:" + nucleo_al.getTime().toString()+ DateTimeFormat.toSqlDate(nucleo_dal.getTime()) );
	    			}
	    			else
	    			{
	    				//System.out.println("Data al:" + nucleo_al);
	    			}
					 */

					//le date dal e al si considerano comprese
					if( (nucleo_dal == null || nucleo_dal.getTime().getTime() <= data_riferimento.getTime().getTime()) &&
							(nucleo_al == null || nucleo_al.getTime().getTime() >= data_riferimento.getTime().getTime()))
					{	    					
						//per la mensa non si considerano per il calcolo dell'ICEF i nuovi nati, ovvero i soggetti aggiunti al nucleo la cui data di aggiunta 
						//è maggiore alla data di presentazione e la cui data di aggiunta corrisponde alla data di nascita
						if(nucleo_dal != null && nucleo_dal.getTime().getTime() > data_presentazione.getTime().getTime() && 
								data_nascita.getTime().getTime() == nucleo_dal.getTime().getTime() )
						{
							//non lo prendo in considerazione
							System.out.println( "CLASSE FIGLI. DOMANDA: "+IDdomanda+" DICH STUDENTE: "+ID_dichiarazione+" non lo prendo in considerazione!!! ");
						}
						else
						{
							//System.out.println( "lo prendo in considerazione!!! ");
							//idDichiarazioni.add(ID_dichiarazione);

							if(records.getString(i, 10) == null || (records.getString(i, 10)).equals("")) {
								// minore non iscritto a scuola
								Calendar data18anni = (Calendar)datarif.clone();
								data18anni.add(Calendar.YEAR, -18);
								if (data_nascita.after(data18anni)) {
									tot++;
								}
							} else {
								// componente iscritto a scuola
								Calendar data20anni = (Calendar)datarif.clone();
								data20anni.add(Calendar.YEAR, -20);
								if (data_nascita.after(data20anni)) {
									tot++;
								}
							}	    						
						}
					}
				}
				return tot;
			} catch(NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			} catch (ParseException pe) {
				System.out.println("ERROR ParseException in " + getClass().getName() + ": " + pe.toString());
				return 0.0;
			}
		}
		else
		{
			return 1.0;
		}
	}

	private Calendar getData(Table t, int row, int column) throws ParseException
	{
		Calendar ret = null;
		try
		{
			ret = t.getCalendar(row, column);
		}
		catch (NullPointerException e) {
			ret = null;
		}
		return ret;
	}    
}
