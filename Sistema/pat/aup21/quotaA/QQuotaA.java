package c_elab.pat.aup21.quotaA;

import it.clesius.apps2core.ElainNode;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public abstract class QQuotaA extends ElainNode {


	/*
	 * cambiare di anno in anno (id_servizio anno precedente)!!!!!
	 */
	private static int id_servizio_anno_precedente=70018;

	protected Table idDichiarazione;
	//protected Table datiDomanda;
	protected Table datiSoggetti;

	protected Table redditiC1Corrente;
	protected Table redditiC1Precedente;

	protected Table redditiC2Corrente;
	protected Table redditiC2Precedente;

	protected Table redditiC3Corrente;
	protected Table redditiC3Precedente;

	/** QProvvisorio constructor */
	public QQuotaA() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QQuotaA.class.getName(), IDdomanda );
		records=null;
		//datiDomanda=null;
		datiSoggetti=null;
		idDichiarazione=null;
		redditiC1Corrente = null;
		redditiC1Precedente = null;
		redditiC2Corrente = null;
		redditiC2Precedente = null;
		redditiC3Corrente = null;
		redditiC3Precedente = null;

	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QQuotaA.class.getName(), IDdomanda  );
		super.init(dataTransfer);

		if (!session.isInitialized()) {

			StringBuffer sql = new StringBuffer();

					

			sql = new StringBuffer();

			sql.append("select ID_dich,anno_produzione_redditi_max from r_dich  ");
			sql.append("inner join r_servizi on r_dich.anno_produzione_redditi = r_servizi.anno_produzione_redditi_min ");
			sql.append("inner join Domande on Domande.ID_servizio=r_servizi.ID_servizio where ID_domanda= "+IDdomanda);
			doQuery(sql.toString());
			idDichiarazione = records;

//			sql = new StringBuffer();
//			sql.append("select data_presentazione from doc  where ID= "+IDdomanda);
//			doQuery(sql.toString());
//			datiDomanda = records;
//			sql = new StringBuffer();
			sql = new StringBuffer();
			sql.append("select id_soggetto from Familiari inner join Dich_icef on Dich_icef.ID_dichiarazione=Familiari.ID_dichiarazione where ID_domanda= "+IDdomanda);
			doQuery(sql.toString());
			datiSoggetti = records;

			String filter="";
			for (int i = 1; i <= datiSoggetti.getRows(); i++) {
				filter=filter+datiSoggetti.getString(i, 1);
				if(i<datiSoggetti.getRows()) {
					filter=filter+",";
				}
			}

			sql = new StringBuffer();
			sql.append("SELECT Redditi_dipendente.ID_tp_reddito, 100 as peso_reddito, Redditi_dipendente.importo, Redditi_dipendente.ID_dichiarazione,Dich_icef.ID_soggetto,soggetti.ID_tp_sex,soggetti.data_nascita ");
			sql.append("	FROM Dich_icef 	INNER JOIN Redditi_dipendente ON Dich_icef.ID_dichiarazione = Redditi_dipendente.ID_dichiarazione  inner join soggetti on soggetti.ID_soggetto=Dich_icef.ID_soggetto ");
			sql.append("	where Dich_icef.ID_soggetto in(");
			sql.append(filter);
			sql.append("	) and Dich_icef.ID_dich= "+idDichiarazione.getInteger(1, 1)+" order by Redditi_dipendente.importo desc");
			doQuery(sql.toString());
			redditiC1Corrente = records;

			sql = new StringBuffer();
			sql.append("SELECT Redditi_dipendente.ID_tp_reddito, 100 as peso_reddito, Redditi_dipendente.importo, Redditi_dipendente.ID_dichiarazione,Dich_icef.ID_soggetto,soggetti.ID_tp_sex,soggetti.data_nascita ");
			sql.append("	FROM Dich_icef 	INNER JOIN Redditi_dipendente ON Dich_icef.ID_dichiarazione = Redditi_dipendente.ID_dichiarazione  inner join soggetti on soggetti.ID_soggetto=Dich_icef.ID_soggetto ");
			sql.append("	inner join Familiari on  Familiari.ID_dichiarazione=Dich_icef.ID_dichiarazione	inner join Domande on  Familiari.ID_domanda=Domande.ID_domanda and ID_servizio= "+id_servizio_anno_precedente +" inner join doc on  Familiari.ID_domanda=doc.ID and crc is not null ");
			sql.append("	where Dich_icef.ID_soggetto in(");
			sql.append(filter);
			sql.append("	) and Dich_icef.ID_dich= "+(idDichiarazione.getInteger(1, 1)-1)+" order by Redditi_dipendente.importo desc");
			doQuery(sql.toString());
			redditiC1Precedente = records;


			sql = new StringBuffer();
			sql.append("SELECT 100 as peso_reddito, Redditi_agricoli.quantita, R_Importi_agricoli.importo, Redditi_agricoli.costo_locazione, Redditi_agricoli.costo_dipendenti, Redditi_agricoli.quota, Redditi_agricoli.ID_dichiarazione,Dich_icef.ID_soggetto,soggetti.ID_tp_sex,soggetti.data_nascita  ");
			sql.append("	FROM Dich_icef 	INNER JOIN Redditi_agricoli ON Dich_icef.ID_dichiarazione = Redditi_agricoli.ID_dichiarazione  inner join soggetti on soggetti.ID_soggetto=Dich_icef.ID_soggetto ");
			sql.append("	INNER JOIN R_Importi_agricoli ON (Redditi_agricoli.ID_tp_agricolo = R_Importi_agricoli.ID_tp_agricolo) AND (Redditi_agricoli.ID_zona_agricola = R_Importi_agricoli.ID_zona_agricola) AND (Redditi_agricoli.ID_dich = R_Importi_agricoli.ID_dich)  ");
			sql.append("	where Dich_icef.ID_soggetto in(");
			sql.append(filter);
			sql.append("	) and Dich_icef.ID_dich= "+(idDichiarazione.getInteger(1, 1)-1)+" order by (Redditi_agricoli.quantita* R_Importi_agricoli.importo) desc");
			doQuery(sql.toString());
			redditiC2Corrente = records;

			sql = new StringBuffer();
			sql.append("SELECT 100 as peso_reddito, Redditi_agricoli.quantita, R_Importi_agricoli.importo, Redditi_agricoli.costo_locazione, Redditi_agricoli.costo_dipendenti, Redditi_agricoli.quota, Redditi_agricoli.ID_dichiarazione,Dich_icef.ID_soggetto,soggetti.ID_tp_sex,soggetti.data_nascita  ");
			sql.append("	FROM Dich_icef 	INNER JOIN Redditi_agricoli ON Dich_icef.ID_dichiarazione = Redditi_agricoli.ID_dichiarazione  inner join soggetti on soggetti.ID_soggetto=Dich_icef.ID_soggetto ");
			sql.append("	inner join Familiari on  Familiari.ID_dichiarazione=Dich_icef.ID_dichiarazione	inner join Domande on  Familiari.ID_domanda=Domande.ID_domanda and ID_servizio= "+id_servizio_anno_precedente +" inner join doc on  Familiari.ID_domanda=doc.ID and crc is not null ");
			sql.append("	INNER JOIN R_Importi_agricoli ON (Redditi_agricoli.ID_tp_agricolo = R_Importi_agricoli.ID_tp_agricolo) AND (Redditi_agricoli.ID_zona_agricola = R_Importi_agricoli.ID_zona_agricola) AND (Redditi_agricoli.ID_dich = R_Importi_agricoli.ID_dich)  ");
			sql.append("	where Dich_icef.ID_soggetto in(");
			sql.append(filter);
			sql.append("	) and Dich_icef.ID_dich= "+(idDichiarazione.getInteger(1, 1)-1)+" order by (Redditi_agricoli.quantita* R_Importi_agricoli.importo) desc");
			doQuery(sql.toString());
			redditiC2Precedente = records;

			sql = new StringBuffer();
			sql.append("SELECT 100 as peso_reddito, Redditi_impresa.reddito_dichiarato as reddito, Redditi_impresa.ID_dichiarazione,Dich_icef.ID_soggetto,soggetti.ID_tp_sex,soggetti.data_nascita  ");
			sql.append("	FROM Dich_icef 	INNER JOIN Redditi_impresa ON Dich_icef.ID_dichiarazione = Redditi_impresa.ID_dichiarazione  inner join soggetti on soggetti.ID_soggetto=Dich_icef.ID_soggetto ");
			sql.append("	where Dich_icef.ID_soggetto in(");
			sql.append(filter);
			sql.append("	) and Dich_icef.ID_dich= "+(idDichiarazione.getInteger(1, 1)-1)+" order by Redditi_impresa.reddito_dichiarato desc");
			doQuery(sql.toString());
			redditiC3Corrente = records;

			sql = new StringBuffer();
			sql.append("SELECT 100 as peso_reddito, Redditi_impresa.reddito_dichiarato as reddito, Redditi_impresa.ID_dichiarazione,Dich_icef.ID_soggetto,soggetti.ID_tp_sex,soggetti.data_nascita  ");
			sql.append("	FROM Dich_icef 	INNER JOIN Redditi_impresa ON Dich_icef.ID_dichiarazione = Redditi_impresa.ID_dichiarazione  inner join soggetti on soggetti.ID_soggetto=Dich_icef.ID_soggetto ");
			sql.append("	inner join Familiari on  Familiari.ID_dichiarazione=Dich_icef.ID_dichiarazione	inner join Domande on  Familiari.ID_domanda=Domande.ID_domanda and ID_servizio= "+id_servizio_anno_precedente +" inner join doc on  Familiari.ID_domanda=doc.ID and crc is not null ");
			sql.append("	where Dich_icef.ID_soggetto in(");
			sql.append(filter);
			sql.append("	) and Dich_icef.ID_dich= "+(idDichiarazione.getInteger(1, 1)-1)+" order by Redditi_impresa.reddito_dichiarato desc");
			doQuery(sql.toString());
			redditiC3Precedente = records;



			session.setInitialized( true );
			session.setRecords( records );



			//session.setAttribute("datiDomanda", datiDomanda);
			session.setAttribute("idDichiarazione", idDichiarazione);
			session.setAttribute("datiSoggetti", datiSoggetti);

			session.setAttribute("redditiC1Corrente", redditiC1Corrente);
			session.setAttribute("redditiC1Precedente", redditiC1Precedente);
			session.setAttribute("redditiC2Corrente", redditiC2Corrente);
			session.setAttribute("redditiC2Precedente", redditiC2Precedente);
			session.setAttribute("redditiC3Corrente", redditiC3Corrente);
			session.setAttribute("redditiC3Precedente", redditiC3Precedente);

		} else {
			records = session.getRecords();

			//datiDomanda = (Table)session.getAttribute("datiDomanda");
			idDichiarazione = (Table)session.getAttribute("idDichiarazione");
			datiSoggetti = (Table)session.getAttribute("datiSoggetti");

			redditiC1Corrente = (Table)session.getAttribute("redditiC1Corrente");
			redditiC1Precedente = (Table)session.getAttribute("redditiC1Precedente");

			redditiC2Corrente = (Table)session.getAttribute("redditiC2Corrente");
			redditiC2Precedente = (Table)session.getAttribute("redditiC2Precedente");
			redditiC3Corrente = (Table)session.getAttribute("redditiC3Corrente");
			redditiC3Precedente = (Table)session.getAttribute("redditiC3Precedente");


		}
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		double val=0.0;
		return val;
	}
	
	boolean abilita2019=true;
	
	public double getFranchigia_nuovi_redd() {
		
		if(!abilita2019) {
			return 0;
		}
		
		double val=0.0;
		String dbg="";
		try {
			Hashtable<Integer, DatiSoggetto> datiCorrenti=new Hashtable<Integer, DatiSoggetto>();
			Hashtable<Integer, DatiSoggetto> datiPrecedenti=new Hashtable<Integer, DatiSoggetto>();

			int  anno_redditi = idDichiarazione.getInteger(1, 2);
			
			Date date=new SimpleDateFormat("dd/MM/yyyy").parse("31/12/"+anno_redditi);
			Calendar  presentazione=Calendar.getInstance();
			presentazione.setTime(date);
			
			Vector<Integer> componentiAttuali=new Vector<Integer>();
			for (int i = 1; i <= datiSoggetti.getRows(); i++) {
				if(!componentiAttuali.contains(datiSoggetti.getInteger(i, 1))) {
					componentiAttuali.add(datiSoggetti.getInteger(i, 1));
				}
			}
			for (int i = 0; i <componentiAttuali.size(); i++) {
				Integer id_soggetto = componentiAttuali.get(i);
				DatiSoggetto sd=datiCorrenti.get(id_soggetto);
				if(sd==null) {
					sd=new DatiSoggetto();
					sd.setDataRiferimento(presentazione);
					datiCorrenti.put(id_soggetto, sd);
					sd.addRedditoTotale(getValueC1(redditiC1Corrente,id_soggetto,sd));
					sd.addRedditoTotale(getValueC2(redditiC2Corrente,id_soggetto,sd));
					sd.addRedditoTotale(getValueC3(redditiC3Corrente,id_soggetto,sd));
				}
			}
			for (int i = 0; i <componentiAttuali.size(); i++) {
				Integer id_soggetto = componentiAttuali.get(i);
				DatiSoggetto sd=datiPrecedenti.get(id_soggetto);
				if(sd==null) {
					sd=new DatiSoggetto();
					sd.setDataRiferimento(presentazione);
					datiPrecedenti.put(id_soggetto, sd);
					sd.addRedditoTotale(getValueC1(redditiC1Precedente,id_soggetto,sd));
					sd.addRedditoTotale(getValueC2(redditiC2Precedente,id_soggetto,sd));
					sd.addRedditoTotale(getValueC3(redditiC3Precedente,id_soggetto,sd));
				}
			}
			for (int i = 0; i <componentiAttuali.size(); i++) {
				Integer id_soggetto = componentiAttuali.get(i);
				DatiSoggetto sdcorrenti=datiCorrenti.get(id_soggetto);
				DatiSoggetto sdprecedenti=datiPrecedenti.get(id_soggetto);
				if(sdcorrenti!=null && sdprecedenti!=null && sdcorrenti.getSesso()!=null && sdprecedenti.getSesso()!=null ) {
					if(sdcorrenti.getRedditoTotale()-sdprecedenti.getRedditoTotale()>1200) {
						boolean rilevato=false;
						if(sdcorrenti.getEta()<55 && sdcorrenti.getSesso().equals("M")) {
							val=val+1200;
							rilevato=true;
						}
						if(sdcorrenti.getEta()<55 && sdcorrenti.getSesso().equals("F")) {
							val=val+2400;
							rilevato=true;
						}
						if(sdcorrenti.getEta()>=55 && sdcorrenti.getSesso().equals("M")) {
							val=val+2400;
							rilevato=true;
						}
						if(sdcorrenti.getEta()>=55 && sdcorrenti.getSesso().equals("F")) {
							val=val+4800;
							rilevato=true;
						}
						if(rilevato) {
							dbg=dbg+"["+id_soggetto+"\t"+sdprecedenti.getRedditoTotale()+"\t>\t"+sdcorrenti.getRedditoTotale()+"]";
						}
					}
				}
			}


		}catch(Exception e) {
			e.printStackTrace();
		}
		if(val>0) {
			System.out.println(val+"\t"+dbg);
		}
		return val;

	}


	public static final String ANP = "ANP";
	public static final String DIV = "DIV";
	public static final String DIP = "DIP";

	public double getValueC1(Table recordsin,int id_soggettoin,DatiSoggetto sd) {

		if (recordsin == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= recordsin.getRows(); i++) {
				if ( ((String) recordsin.getElement(i, 1)).equals(ANP) 
						|| ((String) recordsin.getElement(i, 1)).equals(DIV) 
						|| ((String) recordsin.getElement(i, 1)).equals(DIP) ) {

					double peso_par = recordsin.getDouble(i, 2);
					double importo = recordsin.getDouble(i, 3);
					double id_soggetto = recordsin.getInteger(i, 5);
					if(id_soggetto==id_soggettoin) {
						
						sd.setSesso(recordsin.getString(i, 6));
						Calendar  calNascita = recordsin.getCalendar(i, 7);
						sd.setEta(getEta(calNascita, sd.getDataRiferimento()));
						
						tot = tot +  Sys.round( importo - aggiusta, round ) * peso_par / 100.0;
					}
				}
			}
			return tot;
		} catch (Exception n) {
			n.printStackTrace();
			return 0.0;
		} 
	}


	public double getValueC2(Table recordsin,int id_soggettoin,DatiSoggetto sd) {

		if (recordsin == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= recordsin.getRows(); i++) {

				String id_dich = recordsin.getString(i,7);
				double peso_par = recordsin.getDouble(i, 1);

				double agricolo = (java.lang.Math.max (0,
						// quantità *
						recordsin.getDouble(i, 2) *
						// importo -
						recordsin.getDouble(i, 3) -
						// costo locazione terreni -
						recordsin.getDouble(i, 4) -
						// costo dipendenti
						recordsin.getDouble(i, 5) )); 
				// quota possesso
				double quota = recordsin.getDouble(i, 6);

				double importo = agricolo * quota / 100.0;

				//importo = importo * peso_par / 100.0;
				double id_soggetto = recordsin.getInteger(i, 8);
				if(id_soggetto==id_soggettoin) {
					sd.setSesso(recordsin.getString(i, 9));
					Calendar  calNascita = recordsin.getCalendar(i, 10);
					sd.setEta(getEta(calNascita, sd.getDataRiferimento()));
					tot = tot +  importo * peso_par / 100.0;
				}
			}
			return Sys.round(tot - aggiusta, round);
		} catch (Exception n) {
			n.printStackTrace();
			return 0.0;
		} 
	}



	public double getValueC3(Table recordsin,int id_soggettoin,DatiSoggetto sd) {

		if (recordsin == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= recordsin.getRows(); i++) {

				double peso_par = recordsin.getDouble(i, 1);
				double importo = recordsin.getDouble(i, 2);
				String id_dich = recordsin.getString(i, 3);

				//importo = importo * peso_par / 100.0;
				double id_soggetto = recordsin.getInteger(i, 4);
				if(id_soggetto==id_soggettoin) {
					sd.setSesso(recordsin.getString(i, 5));
					Calendar  calNascita = recordsin.getCalendar(i, 6);
					sd.setEta(getEta(calNascita, sd.getDataRiferimento()));
					tot = tot +  Sys.round( importo - aggiusta, round ) * peso_par / 100.0;
				}
			}
			return tot;
		} catch (Exception n) {
			n.printStackTrace();
			return 0.0;
		} 
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


		return eta;
	}


	private class DatiSoggetto {
		int eta=0;
		double redditoTotale;
		double redditoTotalePrecedente;
		String sesso;
		Calendar  dataRiferimento;
		
		public int getEta() {
			return eta;
		}
		public void setEta(int eta) {
			this.eta = eta;
		}
		public double getRedditoTotale() {
			return redditoTotale;
		}
		public void addRedditoTotale(double redditoTotale) {
			this.redditoTotale = this.redditoTotale+redditoTotale;
		}
		public String getSesso() {
			return sesso;
		}
		public void setSesso(String sesso) {
			this.sesso = sesso;
		}
		public Calendar getDataRiferimento() {
			return dataRiferimento;
		}
		public void setDataRiferimento(Calendar dataRiferimento) {
			this.dataRiferimento = dataRiferimento;
		}
		
		
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
	}



}
