/**
 *Created on 28-mag-2004
 */

package c_elab.pat.edil10;

import it.clesius.db.sql.RunawayData;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;


/** query per il quadro F della dichiarazione ICEF
 * @author g_barbieri
 * 
 * 
 * TODO: applicare la franchigia individuale FIT per i terreni agricoli (ID_tp_immobile = TA)
 * 
 */
public class QImmobiliare extends c_elab.pat.icef09.QImmobiliare {

	/** QImmobiliare constructor */
	public QImmobiliare() {
	}

	protected void reset() {
		ElabContext.getInstance().resetSession( QImmobiliare.class.getName(), IDdomanda );
	}
	
	private int anno_patrimonio;
	

	public void setAnno(String ID_domanda) {
		anno_patrimonio = 0;
		
		doQuery("select count(ID_dichiarazione) AS tot FROM Edil_familiari where id_domanda = "+ID_domanda);
		if(records!=null && records.getRows()>0) {
			if( records.getInteger(1,1)>0 )	{
				anno_patrimonio = 2007;
			}else {
				anno_patrimonio = 2008;
			}
		}
		return;
	}

	
	
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {
		ElabSession session;
		this.dataTransfer = dataTransfer;
		
		setAnno(IDdomanda);
		
		if(anno_patrimonio==2007) {
			
			//edil familiari
			session = ElabContext.getInstance().getSession( QImmobiliare.class.getName(), IDdomanda );
			
			if (!session.isInitialized()) {
				StringBuffer sql = new StringBuffer();
				//      											1                           		2                   				3                                        4                                     5                                     6                                          7                          8                                9                                   10						11									12								 13									14					15
				sql.append("SELECT Patrimoni_immobiliari.residenza_nucleo, R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, Dich_icef.anno_attualizzato, Dich_icef.mese  ");
				sql.append("FROM Edil_Familiari ");
				sql.append("INNER JOIN Patrimoni_immobiliari ON Edil_Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
				sql.append("INNER JOIN Dich_icef ON Edil_Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
				sql.append("INNER JOIN Domande ON Edil_Familiari.ID_domanda = Domande.ID_domanda ");
				sql.append("INNER JOIN Doc ON Edil_Familiari.ID_domanda = Doc.ID ");
				sql.append("INNER JOIN R_Relazioni_parentela ON Edil_Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
				sql.append("WHERE Domande.ID_domanda = ");
				sql.append(IDdomanda);
				
				doQuery(sql.toString());
	
				session.setInitialized( true );
				session.setRecords( records );
			
			}else {
				records = session.getRecords();
			}
			
		}else if(anno_patrimonio==2008){
			//familiari 
			super.init(dataTransfer);
		}
		else {
			//error
		}
		
	}

}