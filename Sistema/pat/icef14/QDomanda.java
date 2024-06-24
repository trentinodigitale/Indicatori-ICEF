package c_elab.pat.icef14;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

import java.util.Calendar;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;




/** query per i dati della domanda
 * @author g_barbieri
 */
public  class QDomanda extends ElainNode {

	/** QDomanda constructor */
	public QDomanda() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabContext.getInstance().resetSession( QDomanda.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession( QDomanda.class.getName(), IDdomanda );

		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();

			if(!IDdomanda.startsWith("*"))
			{
				//modalità normale con domanda
				//                       	1                        2					3				4							5
				sql.append("SELECT Domande.un_genitore, Domande.genitori_lavoratori, Domande.mq, Domande.lavoro_femminile, Domande.ID_tp_monogenitore, ");
				//              			6                        7								8						9							
				sql.append("tp_servizi.beneficiario,Dati_famiglia.ID_tp_obbligo_mantenimento,doc.data_presentazione,domande.data_nascita, ");
				//              			10                  			         11                       12
				sql.append("R_Relazioni_parentela.id_tp_relazione_parentela, Domande.ID_periodo, Soggetti.data_nascita ");
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

			session.setInitialized( true );
			session.setRecords( records );
		} else {
			records = session.getRecords();
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

	public double isMonogenitore() throws Exception{
		int ret=0;
		String beneficiario=records.getString(1, 6);
		
		if(beneficiario.compareToIgnoreCase("FAM")==0){
			return records.getDouble(1, 1);
		}
		if(beneficiario.compareToIgnoreCase("MIN")==0){
			int ID_tp_monogenitore=records.getInteger(1, 5);
			
			if(ID_tp_monogenitore!=0 && ID_tp_monogenitore!=7){
				if(ID_tp_monogenitore==9 || ID_tp_monogenitore==1){
					int ID_tp_obbligo_mantenimento=records.getInteger(1, 7);
					if(ID_tp_obbligo_mantenimento!=4){
						return 1;
					}else{
						return 0;
					}
				}else{
					return 1;
				}
			}
		}
	
		if(beneficiario.compareToIgnoreCase("SOG")==0){
			
			if(!isMaggiorenne(records.getCalendar(1, 9),records.getCalendar(1, 8))){
				int ID_tp_monogenitore=records.getInteger(1, 5);
				if(ID_tp_monogenitore!=0 && ID_tp_monogenitore!=7){
					
					if(ID_tp_monogenitore==9 || ID_tp_monogenitore==1){
						int ID_tp_obbligo_mantenimento=records.getInteger(1, 7);
						if(ID_tp_obbligo_mantenimento!=4){
							return 1;
						}else{
							return 0;
						}
					}else{
						return 1;
					}
				}
				
				
			}else{
				int haConiuge=0;
				int haMinore=0;
				for(int i=1;i<=records.getRows();i++){
					int ID_tp_relazione_parentela=records.getInteger(i, 10);
					if(ID_tp_relazione_parentela==11 || ID_tp_relazione_parentela==13){
						haConiuge++;
					}
					if(ID_tp_relazione_parentela==21){
						if(!isMaggiorenne( records.getCalendar(i, 12), records.getCalendar(i, 8) ) ) {
							haMinore++;
						}
					}
				}
				if(haConiuge>0){
					return 0;
				}
				if(haMinore>0){
					return 1;
				}
			}
		}

		return ret;
	}
}
