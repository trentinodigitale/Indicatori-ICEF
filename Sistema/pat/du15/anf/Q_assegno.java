package c_elab.pat.du15.anf;

/** 
 *Created on 11-ott-2005 
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
/***************************************************************************************************/
/*** CAMBIAMI - NOTA BENE - VA CAMBIATA OGNI ANNO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***/
/***************************************************************************************************/
import c_elab.pat.du15.DU_Util;
/***************************************************************************************************/
/*** CAMBIAMI - NOTA BENE - VA CAMBIATA OGNI ANNO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***/
/***************************************************************************************************/


/**
 * legge tutti i dati per il calcolo dell'assegno
 * 
 */
public abstract class Q_assegno extends ElainNode {

	protected Table tb_richiedente_da_a;
	protected Table tb_richiedente_lavoro;
	protected Table tb_richiedente_residenza;
	protected Table tb_coniuge_da_a;
	protected Table tb_dati_dom;
	protected Table tb_data_beneficio;

	/** Q_assegno constructor */
	public Q_assegno() {
	}

	/**
	 * resetta le variabili statiche
	 * 
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( Q_assegno.class.getName(), IDdomanda, "records" );
		tb_richiedente_da_a = null;
		tb_richiedente_lavoro = null;
		tb_richiedente_residenza = null;
		tb_coniuge_da_a = null;
		tb_dati_dom = null;
		tb_data_beneficio= null;
	}

	/**
	 * inizializza la Table records con i valori letti dalla query sul DB
	 * 
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer  it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( Q_assegno.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {

			super.init(dataTransfer);

			// legge i dati da.. a.. del richiedente
			StringBuffer sb = new StringBuffer();
			//                                 1 				2
			sb.append("SELECT Familiari.nucleo_dal, Familiari.nucleo_al ");
			sb.append(" FROM Familiari ");
			sb.append(" WHERE (((Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceRichiedente());
			sb.append(") AND ((Familiari.ID_domanda)=");
			sb.append(IDdomanda);
			sb.append("))");
			//System.out.println(sb.toString());
			doQuery(sb.toString());
			tb_richiedente_da_a = records;

			// legge i dati della domanda
			sb = null;
			sb = new StringBuffer();
			//                          1							2						 3							4
			sb.append("SELECT Doc.data_presentazione, Doc.data_sottoscrizione, DU_Dati.escludi_ufficio_anf, DU_Dati.richiede_anf ");
			sb.append("FROM Domande ");
			sb.append("INNER JOIN DU_Dati ON DU_Dati.ID_domanda = Domande.ID_domanda ");
			sb.append("INNER JOIN Doc ON Domande.ID_domanda = Doc.ID ");
			sb.append("WHERE (Domande.ID_domanda=");
			sb.append(IDdomanda);
			sb.append(")");
			//System.out.println(sb.toString());
			doQuery(sb.toString());
			tb_dati_dom = records;

			// legge i dati di lavoro del richiedente
			sb = null;
			sb = new StringBuffer();
			//                                  1                            2                        3						4						5					6								7						8					9							10						11						12						13							14
			sb.append("SELECT ANF_richiedente.dic_lav_prec, ANF_richiedente.gen_lav, ANF_richiedente.feb_lav, ANF_richiedente.mar_lav, ANF_richiedente.apr_lav, ANF_richiedente.mag_lav, ANF_richiedente.giu_lav, ANF_richiedente.lug_lav, ANF_richiedente.ago_lav, ANF_richiedente.set_lav, ANF_richiedente.ott_lav, ANF_richiedente.nov_lav, ANF_richiedente.dic_lav, ANF_richiedente.cittadinoUE_dom_anfEstero ");
			sb.append(" FROM Familiari INNER JOIN ANF_richiedente ON ANF_richiedente.ID_domanda = Familiari.ID_domanda ");
			sb.append(" WHERE (((Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceRichiedente());
			sb.append(") AND ((Familiari.ID_domanda)=");
			sb.append(IDdomanda);
			sb.append("))");
			//System.out.println(sb.toString());
			doQuery(sb.toString());
			tb_richiedente_lavoro = records;

			// legge i dati di residenza del richiedente
			sb = null;
			sb = new StringBuffer();
			//                                 1                             2                        3						4						5					6								7						8					9							10						11						12						13
			sb.append("SELECT ANF_richiedente.dic_res_prec, ANF_richiedente.gen_res, ANF_richiedente.feb_res, ANF_richiedente.mar_res, ANF_richiedente.apr_res, ANF_richiedente.mag_res, ANF_richiedente.giu_res, ANF_richiedente.lug_res, ANF_richiedente.ago_res, ANF_richiedente.set_res, ANF_richiedente.ott_res, ANF_richiedente.nov_res, ANF_richiedente.dic_res ");
			sb.append(" FROM Familiari INNER JOIN ANF_richiedente ON ANF_richiedente.ID_domanda = Familiari.ID_domanda ");
			sb.append(" WHERE (((Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceRichiedente());
			sb.append(") AND ((Familiari.ID_domanda)=");
			sb.append(IDdomanda);
			sb.append("))");
			//System.out.println(sb.toString());
			doQuery(sb.toString());
			tb_richiedente_residenza = records;

			//legge la data di inizio beneficio
			sb = null;
			sb = new StringBuffer();
			// al primo salvataggio legge la data di presentazione in tutti gli altri casi la data di inizio beneficio
			//                          1															
			sb.append("SELECT CASE WHEN ANF_data_inizio_beneficio.ID_domanda IS NOT NULL THEN ANF_data_inizio_beneficio.data_inizio_beneficio ELSE Doc.data_presentazione END AS data_inizio_beneficio");
			sb.append(" FROM ANF_data_inizio_beneficio RIGHT OUTER JOIN Doc ON ANF_data_inizio_beneficio.ID_domanda = Doc.ID");
			sb.append(" WHERE (Doc.ID =");
			sb.append(IDdomanda);
			sb.append(")");	//System.out.println(sb.toString());
			doQuery(sb.toString());
			tb_data_beneficio = records;
			
			// legge i dati da.. a.. del coniuge o convivente
			sb = null;
			sb = new StringBuffer();
			//                              1                     2
			sb.append("SELECT Familiari.nucleo_dal, Familiari.nucleo_al ");
			sb.append(" FROM Familiari ");
			sb.append(" WHERE (((Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceConiugeResidente());
			sb.append(" Or (Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceConviventeResidente());
			//sb.append(" Or (Familiari.ID_relazione_parentela)=");
			//sb.append(DU_Util.getCodiceConiugeNonResidente());
			sb.append(" Or (Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceConviventeNonResidente());
			sb.append(" Or (Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceGenitoreNonConiugatoENonConvivente());
			sb.append(") AND ((Familiari.ID_domanda)=");
			sb.append(IDdomanda);
			sb.append("))");
			//System.out.println(sb.toString());
			doQuery(sb.toString());
			tb_coniuge_da_a = records;

			// legge i dati dei figli
			sb = null;
			sb = new StringBuffer();
			//                               1                    2                     3                       4                         5                          6									7
			sb.append("SELECT Soggetti.data_nascita, Familiari.nucleo_dal, Familiari.nucleo_al, Familiari.nucleo_dal_inv, Familiari.nucleo_al_inv, Familiari.non_invalido_figlio, Familiari.ID_relazione_parentela ");
			sb.append(" FROM Familiari INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto ");
			sb.append(" WHERE (((Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceFiglioOEquiparato());
			sb.append(" Or (Familiari.ID_relazione_parentela)=");
			sb.append(DU_Util.getCodiceMinoreAffidatoOPersonaAccolta());
			sb.append(") AND ((Familiari.ID_domanda)=");
			sb.append(IDdomanda);
			sb.append("))");
			//System.out.println(sb.toString());
			doQuery(sb.toString());

			session.setInitialized(true);
			session.setRecords( records );
			session.setAttribute("tb_richiedente_da_a", tb_richiedente_da_a);
			session.setAttribute("tb_richiedente_lavoro", tb_richiedente_lavoro);
			session.setAttribute("tb_richiedente_residenza", tb_richiedente_residenza);
			session.setAttribute("tb_coniuge_da_a", tb_coniuge_da_a);
			session.setAttribute("tb_dati_dom", tb_dati_dom);
			session.setAttribute("tb_data_beneficio", tb_data_beneficio);
			
        } else {
			records = session.getRecords();
			tb_richiedente_da_a = (Table)session.getAttribute("tb_richiedente_da_a");
			tb_richiedente_lavoro = (Table)session.getAttribute("tb_richiedente_lavoro");
			tb_richiedente_residenza = (Table)session.getAttribute("tb_richiedente_residenza");
			tb_coniuge_da_a = (Table)session.getAttribute("tb_coniuge_da_a");
			tb_dati_dom = (Table)session.getAttribute("tb_dati_dom");
			tb_data_beneficio = (Table)session.getAttribute("tb_data_beneficio");
        }
	}
	
	protected boolean[] isNelNucleo(int i, int meseinit, int anno) {
		return c_elab.pat.Util_apapi.get_ha_requisiti_da_a((String) records.getElement(i, 2), (String) records.getElement(i, 3),meseinit, anno, false);
	}
	
	protected boolean[] isMinore(int i, int meseinit, int anno, int anni, boolean valuta_adozione) {
		Calendar data_nascita = c_elab.pat.Util_apapi.stringdate2date((String) records.getElement(i, 1));
		// se si valuta la data di adozione (caso figlio unico e relazione parentela minore affidato o persona accolta) allora la data di nascita diviene la data dal se questa non è nulla
		if (valuta_adozione) {
			int idRelazioneParentela = records.getInteger(i, 7);	            	
            Calendar data_adozione = c_elab.pat.Util_apapi.stringdate2date((String) records.getElement(i, 2));
			if (data_adozione != null && idRelazioneParentela==DU_Util.getCodiceMinoreAffidatoOPersonaAccolta()) {
				data_nascita = data_adozione;
			}
		}
		Calendar data_X_anni = data_nascita;
		data_X_anni.add(Calendar.YEAR, anni);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return c_elab.pat.Util_apapi.get_ha_requisiti_da_a((String) records.getElement(i, 1), formatter.format(data_X_anni.getTime()), meseinit, anno, false);
	}
	
	protected boolean[] isNato(int i, int meseinit, int anno) {
		return c_elab.pat.Util_apapi.get_ha_requisiti_da_a((String) records.getElement(i, 1), "2050-12-31", meseinit, anno, false);
	}

	protected boolean[] isDisabile(int i, int meseinit, int anno) {
		if ( ((String) records.getElement(i, 6)).equals("0") ) {
			return c_elab.pat.Util_apapi.get_ha_requisiti_da_a((String) records.getElement(i, 4), (String) records.getElement(i, 5), meseinit, anno, false);
		} else {
			return c_elab.pat.Util_apapi.get_ha_requisiti_da_a((String) records.getElement(i, 4), (String) records.getElement(i, 5),	meseinit, anno, true);
		}
	}
	
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}
}