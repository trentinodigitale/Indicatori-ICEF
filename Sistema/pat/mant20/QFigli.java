package c_elab.pat.mant20;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;

/**
 * 
 *
 * Recupera i dati utilizzati dalle classi di trasformazione
 */
public abstract class QFigli extends ElainNode {
    
	
    public double getValue() {
        return 0.0;
    }
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QFigli.class.getName(), IDdomanda, "records" );
    }
    
    public QFigli(){
    }
    
    /**
     * @param RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QFigli.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT Soggetti.data_nascita ");
            sql.append(",Familiari.nucleo_dal ");
            sql.append(",Familiari.nucleo_al ");
            sql.append(",Familiari.nucleo_dal_inv ");
            sql.append(",Familiari.nucleo_al_inv ");
           	sql.append(",Familiari.non_invalido_figlio ");
            sql.append(",Familiari.ID_relazione_parentela ");
            sql.append(",Doc.data_presentazione ");
            sql.append(",Soggetti.cognome ");
            sql.append(",Soggetti.nome ");
            sql.append(",Soggetti.data_nascita ");

            sql.append(" FROM Familiari ");
    		sql.append(" INNER JOIN R_Relazioni_parentela  ");
    		sql.append(" ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela  ");
    		sql.append(" INNER JOIN Doc ON Doc.ID = Familiari.ID_domanda  ");
    		sql.append(" INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
    		sql.append(" INNER JOIN Soggetti ON Dich_icef.ID_soggetto = Soggetti.ID_soggetto ");
    		sql.append(" WHERE (Familiari.ID_domanda =  ");
    		sql.append(IDdomanda);
    		sql.append(") AND (R_Relazioni_parentela.ID_relazione_parentela IN ("+Mant20_Params.minore+"))");
    		
    		doQuery(sql.toString());
            
    		session.setInitialized(true);
			session.setRecords( records ); 
        } else {
			records = session.getRecords();
        }
    }
    
    protected boolean[] isNelNucleo(int i, int meseinit, int anno,Calendar nucleo_dal, Calendar data_fine, boolean compresoA) {
		return c_elab.pat.Util_apapi.get_ha_requisiti_da_a_comp(nucleo_dal, data_fine,meseinit, anno, compresoA);
	}
	
	protected boolean[] isMinore(int i, int meseinit, int anno, int anni, Calendar data_nascita,boolean compresoA) {
		// se si valuta la data di adozione (caso figlio unico e relazione parentela minore affidato o persona accolta) allora la data di nascita diviene la data dal se questa non è nulla
		Calendar data_X_anni = (Calendar) data_nascita.clone();
		data_X_anni.add(Calendar.YEAR, anni);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return c_elab.pat.Util_apapi.get_ha_requisiti_da_a_comp(data_nascita, data_X_anni, meseinit, anno, compresoA);
	}
	
	protected boolean[] isNato(int i, int meseinit, int anno,Calendar data_nascita, Calendar data_fine, boolean compresoA) {
		//dalla data di nascita
		return c_elab.pat.Util_apapi.get_ha_requisiti_da_a_comp(data_nascita, data_fine, meseinit, anno, compresoA);
	}

	

	

}