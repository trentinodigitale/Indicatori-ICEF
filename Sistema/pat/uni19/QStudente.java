package c_elab.pat.uni19;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


/** legge i dati della tabella UNI_Dati e altre tabelle specifiche 
 *
 * @author s_largher
 */
public abstract class QStudente extends ElainNode {
    
	protected Table 		uni_domanda;
	protected Table 		uni_dati;
	protected Table 		icef10;
	protected Table 		uni_isee_paritario;
	protected Table 		icef_paritario;
	protected Table 		isee_paritario;
	protected Table 		isee_connesso;
	
	
	
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
            StringBuffer sql = new StringBuffer();
            //                         1                            2
            sql.append("SELECT  UNI_Dati_R_Servizi.parametro, UNI_Dati.valore ");
            sql.append(" FROM    Domande INNER JOIN ");
            sql.append(" UNI_Dati_R_Servizi ON Domande.ID_servizio = UNI_Dati_R_Servizi.ID_servizio LEFT OUTER JOIN ");
            sql.append(" UNI_Dati ON Domande.ID_domanda = UNI_Dati.ID_domanda AND UNI_Dati_R_Servizi.posizione = UNI_Dati.pos AND UNI_Dati_R_Servizi.parametro = UNI_Dati.parametro ");
            sql.append(" WHERE     Domande.ID_domanda = ");
            sql.append(IDdomanda);
            sql.append(" ORDER BY UNI_Dati_R_Servizi.posizione ");

            doQuery(sql.toString());
            uni_dati = records;
            
            doQuery(
                    "SELECT ID_domanda, ID_servizio, riconferma, ID_politica_opera, paritario "		
                    + " FROM UNI_Domanda "
                    + " WHERE UNI_Domanda.id_domanda = " + IDdomanda + ";"
                );
            uni_domanda = records;
            
            doQuery(
            		"select domande.ID_domanda as id_domanda_dom_icef, dom_dic_icef.ID_domanda as id_domanda_dic_icef, C_ElaOUT.numeric_value as icef10 "
                    + " from domande "
                    + " inner join domande dom_dic_icef on domande.codice_fiscale = dom_dic_icef.codice_fiscale "
                    + " inner join doc doc_dic_icef on dom_dic_icef.id_domanda = doc_dic_icef.id "
                    + " inner join C_ElaOUT on dom_dic_icef.ID_domanda = C_ElaOUT.ID_domanda "
                    + " where dom_dic_icef.ID_servizio = " + Uni19_Params.id_servizio_dic_icef + " and doc_dic_icef.crc is not null and doc_dic_icef.ID_tp_stato > 0 "
                    + " and C_ElaOUT.node = 'ICEF10' "
                    + " and domande.id_domanda = "
                    + " " + IDdomanda + ";"
            		);
            icef10 = records;
            
            doQuery(
            		"select domande.ID_domanda as id_domanda_dom_icef, dom_dic_icef_par.ID_domanda as id_domanda_parificata, C_ElaOUT.numeric_value as icef_parificato "
                    + " from domande "
                    + " inner join domande dom_dic_icef_par on domande.codice_fiscale = dom_dic_icef_par.codice_fiscale "
                    + " inner join doc doc_dic_icef_par on dom_dic_icef_par.id_domanda = doc_dic_icef_par.id "
                    + " inner join C_ElaOUT on dom_dic_icef_par.ID_domanda = C_ElaOUT.ID_domanda "
                    + " where dom_dic_icef_par.ID_servizio = " + Uni19_Params.id_servizio_dic_icef_parificato + " and doc_dic_icef_par.crc is not null and doc_dic_icef_par.ID_tp_stato > 0 "
                    + " and C_ElaOUT.node = 'ICEF10' "
                    + " and domande.id_domanda = "
                    + " " + IDdomanda + ";"
            		);
            icef_paritario = records;
            
            doQuery(
            		"select domande.ID_domanda as id_domanda_dom_isee, dom_isee_par.id_domanda as id_domanda_parificata, isee.numeric_value as ISEE, vse.numeric_value as VSE, isp.numeric_value as ISP "
            		+ "from domande "
            		+ "inner join domande dom_isee_par on domande.codice_fiscale = dom_isee_par.codice_fiscale "
            		+ "inner join doc doc_isee_par on doc_isee_par.id = dom_isee_par.ID_domanda "
            		+ "inner join C_ElaOUT isee on dom_isee_par.ID_domanda = isee.ID_domanda "
            		+ "inner join C_ElaOUT vse on dom_isee_par.ID_domanda = vse.ID_domanda "
            		+ "inner join C_ElaOUT isp on dom_isee_par.ID_domanda = isp.ID_domanda "
            		+ "where domande.ID_domanda = "
            		+ IDdomanda + " "
            		+ "and isee.node = 'ISEE' "
            		+ "and vse.node = 'VSE' "
            		+ "and isp.node = 'ISP' "
            		+ "and dom_isee_par.ID_servizio = " + Uni19_Params.id_servizio_dom_isee_parificato + " and doc_isee_par.crc is not null and doc_isee_par.ID_tp_stato > 0; "
            		);
            isee_paritario = records;
            
            doQuery(
            		"SELECT  domande.id_domanda, domande.id_dichiarazione, domande.codice_fiscale, isee159_clesiusdoc.protocollo_dsu as protocollo,	r_servizi.id_tp_isee_richiesto"
                    + " from domande "
                    + " INNER JOIN isee159_clesiusdoc on domande.id_dichiarazione=isee159_clesiusdoc.id_doc "
                    + " INNER JOIN r_servizi ON domande.id_servizio = r_servizi.id_servizio "
                    + " WHERE domande.id_domanda = "
                    + " " + IDdomanda + ";"
            		);
            isee_connesso = records;
            
            
            session.setAttribute("uni_domanda", uni_domanda);
            session.setAttribute("uni_dati", uni_dati);
            session.setAttribute("icef10", icef10);
            session.setAttribute("icef_paritario", icef_paritario);
            session.setAttribute("isee_paritario", isee_paritario);
            session.setAttribute("isee_connesso", isee_connesso);
            
		} else {
			uni_domanda 	= (Table)session.getAttribute("uni_domanda");
			uni_dati 		= (Table)session.getAttribute("uni_dati");
			icef10			= (Table)session.getAttribute("icef10");
			icef_paritario	= (Table)session.getAttribute("icef_paritario");
			isee_paritario	= (Table)session.getAttribute("isee_paritario");
			isee_connesso	= (Table)session.getAttribute("isee_connesso");
		}
    }
    
    /** resetta le variabili statiche
     * @see ElainNode#reset()
     */
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QStudente.class.getName(), IDdomanda, "records" );
    	uni_domanda = null;
    	uni_dati 	= null;
    	icef10		= null;
    	icef_paritario 	= null;
    	isee_paritario	= null;
    	isee_connesso 	= null;
    }
    
    /** QStudente constructor */
    public QStudente(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
