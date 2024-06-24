package c_elab.pat.icef17;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.PassaValoriIcef;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

/** 
 *
 * @author l_leonardi
 */
public abstract class QDatiFamiglia extends ElainNode {

	
	private final int 		id_tp_erogazione_ass_mant 						= 	980;

	private final String 	tp_servizio_SOG 								=	"SOG";
	
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     *
     *
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QDatiFamiglia.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            
            sql.append("SELECT        tp_servizi.beneficiario ");
            sql.append("FROM  tp_servizi ");
            sql.append("INNER JOIN R_Servizi ON tp_servizi.ID_tp_servizio = R_Servizi.id_tp_servizio ");
            sql.append("WHERE ID_servizio = "+super.servizio );
            
            doQuery(sql.toString());
            
            String id_tp_servizio = "";
            int id_tp_relazione_parentela = 1;

            if(records!=null && records.getRows()>0) {
            	id_tp_servizio = records.getString(1, 1);
            }
            if(id_tp_servizio.equals(tp_servizio_SOG)) {
            	id_tp_relazione_parentela = 31;
            }else {
            	id_tp_relazione_parentela = 1;
            }
            
            sql = new StringBuffer();
            
            
            //                         					   1                   				   2			3
            sql.append("SELECT  Dati_famiglia.ID_tp_obbligo_mantenimento, Dati_famiglia.assegno_extra, doc.data_presentazione, ");
            sql.append(" redd.importo, redd.importo_sentenza ");
            sql.append(" FROM Dati_famiglia INNER JOIN ");
            sql.append(" Domande ON Dati_famiglia.ID_domanda = Domande.ID_domanda ");
            sql.append(" INNER JOIN Doc ON Doc.id = Domande.ID_domanda ");
            sql.append(" INNER JOIN familiari on domande.id_domanda = familiari.id_domanda ");
            sql.append(" INNER JOIN r_relazioni_parentela on familiari.id_relazione_parentela = r_relazioni_parentela.id_relazione_parentela ");
            sql.append(" LEFT OUTER JOIN ( select id_dichiarazione, sum(importo) as importo, sum(importo_sentenza) as importo_sentenza ");
            sql.append(" from redditi_altri where redditi_altri.id_tp_erogazione = "+id_tp_erogazione_ass_mant+" group by id_dichiarazione )  ");
            sql.append(" redd on redd.id_dichiarazione = familiari.id_dichiarazione ");
            sql.append(" WHERE (Domande.ID_domanda = "+IDdomanda);
            sql.append(")");
            sql.append(" AND r_relazioni_parentela.id_tp_relazione_parentela = "+id_tp_relazione_parentela);
            doQuery(sql.toString());
            
        	String componentiPV =  PassaValoriIcef.getID_dichiarazioni(IDdomanda); //classe metodo DefComponentiDich
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}
			
            
        	session.setInitialized(true);
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}
    }
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession(QDatiFamiglia.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QDatiFamiglia(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
