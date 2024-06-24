package c_elab.pat.tnTril19;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.util.Calendar;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;




/** query per i dati della domanda
 * @author g_barbieri
 */
public  class QDomanda extends ElainNode {
	protected Table tbDatiStud;
	
	/** QDomanda constructor */
	public QDomanda() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QDomanda.class.getName(), IDdomanda );
		tbDatiStud 	= 	null;
		
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QDomanda.class.getName(), IDdomanda );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			if(!IDdomanda.startsWith("*"))
			{
			 StringBuffer sql = new StringBuffer();
			 sql.append("SELECT        ID_domanda, ID_tp_qualifica ");
			 sql.append(" FROM            TRIL_Dati  ");
			 sql.append(" WHERE        (ID_domanda = " + IDdomanda+")" );
			 doQuery(sql.toString());
			 tbDatiStud = records;
			
			}
			
			StringBuffer sql = new StringBuffer();
			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				//                       	1                        2					3				4							5
				sql.append("SELECT Domande.un_genitore, Domande.genitori_lavoratori, Domande.mq, Domande.lavoro_femminile, Domande.ID_tp_monogenitore, ");
				//              			6                        7								8						9							
				sql.append("tp_servizi.beneficiario,Dati_famiglia.ID_tp_obbligo_mantenimento,doc.data_presentazione,domande.data_nascita, ");
				//              			10                  			         11                       12						13							14
				sql.append("R_Relazioni_parentela.id_tp_relazione_parentela, Domande.ID_periodo, Soggetti.data_nascita, Familiari.non_invalido, Familiari.non_invalido_figlio ");
				sql.append("from domande ");
				sql.append("inner join doc on domande.id_domanda=doc.id ");
				sql.append("inner join r_servizi on domande.id_servizio=r_servizi.id_servizio ");
				sql.append("inner join tp_servizi on r_servizi.id_tp_servizio=tp_servizi.id_tp_servizio ");
				sql.append("left outer join Dati_famiglia on domande.id_domanda=Dati_famiglia.id_domanda ");
				sql.append("inner join familiari on domande.id_domanda=familiari.id_domanda ");
				sql.append("inner join dich_icef on familiari.id_dichiarazione=dich_icef.id_dichiarazione ");
				sql.append("inner join soggetti on soggetti.id_soggetto=dich_icef.id_soggetto ");
				sql.append("inner join R_Relazioni_parentela on R_Relazioni_parentela.id_relazione_parentela=familiari.id_relazione_parentela ");


				sql.append("WHERE Domande.ID_domanda = ");
				sql.append(IDdomanda);



			}
			else
			{
				//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

				String id_dichiarazione = IDdomanda.substring(1);

				//              		1                      2
				sql.append(
						"SELECT     0 AS un_genitore, 0 AS genitori_lavoratori ");
				sql.append(
						"FROM         Dich_icef ");
				sql.append(
						"WHERE     (ID_dichiarazione = ");
				sql.append(id_dichiarazione);
				sql.append(
						")");
			}

			doQuery(sql.toString());
			
			session.setAttribute("tbDatiStud", tbDatiStud);
			session.setInitialized( true );
			session.setRecords( records );
		} else {
			records = session.getRecords();
			tbDatiStud = (Table)session.getAttribute("tbDatiStud");
		}
	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
	
	
	public  boolean isMaggiorenne(Calendar dataNascita,Calendar data_presentazione)
	{
		boolean ret = true;
		dataNascita.add(Calendar.YEAR, 18);
		if(data_presentazione.after(dataNascita)){
			//maggiorenne
			ret = true;
		}else{
			//minnorenne
			ret = false;
		}
		return ret;
	}

	
	//CAMBIAMI CONTROLLAMI CHE E' MEGLIO
	public double isMonogenitore() throws Exception{
		int ret=0;
		String beneficiario="";

		if(tbDatiStud!=null && tbDatiStud.getRows()>0){
			int id_tp_qualifica=tbDatiStud.getInteger(1, tbDatiStud.getIndexOfColumnName("ID_tp_qualifica"));
				
			boolean studente=false;
				
			if(id_tp_qualifica<=3){
				studente=true;
			}
				
			if(studente){
				//in dubbio fra MIN - DU  e FAM
				beneficiario="SOG";
			}else if(!studente){
				beneficiario="FAM";
			}
		}
		
		
		
		// per politiche di tipo FAM legge il flag dalla maschera
		if(beneficiario.compareToIgnoreCase("FAM")==0){
			return records.getDouble(1, 1);
		}
		
		// per politiche di tipo MIN calcola dai dati	
		if(beneficiario.compareToIgnoreCase("MIN")==0){
			
			/* tp_monogenitore
			0	-
			1	a) i genitori sono separati o divorziati e nessuno dei due ha costituito un nuovo nucleo
			2	b) presentata domanda di scioglimento o cessazione degli effetti civili del matrimonio
			3	c) l'altro genitore è deceduto
			4	d) l'altro genitore non ha riconosciuto il figlio/a
			5	e) l'altro genitore è stato escluso dalla potestà dei figli o è stato allontanato dalla residenza familiare
			6	f) l'altro genitore ha abbandonato il nucleo familiare, è irreperibile o non collabora ed il fatto è stato accertato dai servizi sociali
			7	g) il richiedente è coniugato o è convivente more uxorio con una persona diversa dall'altro genitore
			8	h) l'altro genitore è coniugato con altra persona o è genitore di altri figli iscritti nella propria scheda anagrafica
			9	i) il genitore richiedente ha dichiarato l'assegno di mantenimento nel quadro C5 o sussiste uno dei casi previsti nella sezione 'obbligo di mantenimento dei figli'
			*/
			int ID_tp_monogenitore=0;
			try {
				ID_tp_monogenitore=records.getInteger(1, 5);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//se non è "-" AND non è "il richiedente è coniugato o è convivente .." (quindi se c'è effettivamente un solo genitore)
			if(ID_tp_monogenitore!=0 && ID_tp_monogenitore!=7){
				//se è "i genitori sono separati o divorziati e nessuno dei due ha costituito un nuovo nucleo" 
				//OR è "il genitore richiedente ha dichiarato l'assegno di mantenimento o sussiste l'obbligo di mantenimento" (quindi se c'è l'obbligo di mantenimento)
				if(ID_tp_monogenitore==9 || ID_tp_monogenitore==1){
					/* tp_obbligo_mantenimento
					0	-
					1	l'altro genitore ha già adempiuto agli obblighi previsti dall’Autorità giudiziaria
					2	il genitore ha avviato le procedure giudiziali per l'assegno di mantenimento o la separazione è avvenuta dopo il 01/01/2012
					3	l'altro genitore adempie agli impegni assunti tra i genitori in via extragiudiziale
					4	l'altro genitore non adempie agli obblighi previsti dall'Autorità giudiziaria o agli impegni assunti tra i genitori in via extragiudiziale
					*/
					int ID_tp_obbligo_mantenimento=records.getInteger(1, 7);
					
					if(ID_tp_obbligo_mantenimento!=4){
						// se l'altro genitore adempie è monogenitore purchè ci sia almeno un figlio minore o disabile (sia icef che anf)
						//gb 2015
						int i=1;
						while(i<=records.getRows()){
							if(records.getInteger(i, 10) == 21){ //21=figlio
								if(!isMaggiorenne(records.getCalendar(i, 12),records.getCalendar(i, 8)) || records.getInteger(i, 13)==0 || records.getInteger(i, 14)==0 ){
									return 1; 
								}
							}
							i++;
						}
						return 0;
					} else {
						// se l'altro genitore NON adempie NON è monogenitore
						return 0;
					}
				} else {
					// nei casi 2,3,4,5,6,8 è sempre monogenitore purchè ci sia almeno un figlio minore o disabile (sia icef che anf)
					//gb 2015
					int i=1;
					while(i<=records.getRows()){
						if(records.getInteger(i, 10) == 21){ //21=figlio
							if(!isMaggiorenne(records.getCalendar(i, 12),records.getCalendar(i, 8)) || records.getInteger(i, 13)==0 || records.getInteger(i, 14)==0 ){
								return 1; 
							}
						}
						i++;
					}
					return 0;
				}
			}
		}
	
		// per politiche di tipo SOG calcola dai dati	
		if(beneficiario.compareToIgnoreCase("SOG")==0){
			
			// se il beneficiario è minorenne valogno le regole delle politiche di tipo MIN
			if(!isMaggiorenne(records.getCalendar(1, 9),records.getCalendar(1, 8))){
				int ID_tp_monogenitore=0;
				try {
					 ID_tp_monogenitore=records.getInteger(1, 5);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if(ID_tp_monogenitore!=0 && ID_tp_monogenitore!=7){
					
					if(ID_tp_monogenitore==9 || ID_tp_monogenitore==1){
						int ID_tp_obbligo_mantenimento=records.getInteger(1, 7);
						if(ID_tp_obbligo_mantenimento!=4){
							return 1; //NB non cè bisogno della verifica se c'è almeno un minore in quanto il beneficiario è minorenne
						}else{
							return 0;
						}
					}else{
						return 1; //NB non cè bisogno della verifica se c'è almeno un minore in quanto il beneficiario è minorenne
					}
				}
				
			// se il beneficiario è maggiorenne è un monogenitore se non ha il coniuge/convivente AND ha almeno un figlio minore
			} else {
				int haConiuge=0;
				int haMinore=0;
				for(int i=1;i<=records.getRows();i++){
					int ID_tp_relazione_parentela=records.getInteger(i, 10);
					// se è un coniuge o convivente
					if(ID_tp_relazione_parentela==11 || ID_tp_relazione_parentela==13){
						haConiuge++;
					}
					// se è un figlio
					if(ID_tp_relazione_parentela==21){
						if(!isMaggiorenne( records.getCalendar(i, 12), records.getCalendar(i, 8) ) ) {
							haMinore++;
						}
					}
				}
				if(haConiuge>0){
					// se c'è un coniuge o convivente NON è monogenitore 
					return 0;
				}
				if(haMinore>0){
					// se NON c'è un coniuge o convivente AND ci sono minori è monogenitore 
					return 1;
				}
			}
		}

		return ret;
	}
}
