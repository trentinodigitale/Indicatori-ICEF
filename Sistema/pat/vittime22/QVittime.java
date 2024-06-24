package c_elab.pat.vittime22;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** legge i dati della tabella UNI_Dati
 *
 * @author s_largher
 */
public abstract class QVittime extends ElainNode {
    
     
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QVittime.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT Soggetti.ID_tp_sex, ");
			sql.append("VDONNE_Dati.residenza_tn, ");
			sql.append("Domande.id_provincia_residenza, ");
			sql.append("VDONNE_Dati.spesa, VDONNE_Dati.procedimento_penale, VDONNE_Dati.autorita_giudiziaria_data, Doc.data_presentazione, VDONNE_Dati.no_istanza_precedente, ");
			sql.append("VDONNE_Dati.autorita_giudiziaria, VDONNE_Dati.autorita_giudiziaria_data, VDONNE_Dati.violenza_domestica, ");
			sql.append("VDONNE_Dati.art_572, VDONNE_Dati.art_571, VDONNE_Dati.art_582, VDONNE_Dati.art_583, VDONNE_Dati.art_583_2, VDONNE_Dati.art_609_2,  ");
			sql.append("VDONNE_Dati.art_609_4, VDONNE_Dati.art_609_8, VDONNE_Dati.art_612, VDONNE_Dati.art_612_2, VDONNE_Dati.art_575, VDONNE_Dati.art_584, ");
			sql.append("VDONNE_Dati.min_art_600, VDONNE_Dati.min_art_600_2, VDONNE_Dati.min_art_600_3, VDONNE_Dati.min_art_600_5, VDONNE_Dati.min_art_601, ");
			sql.append("VDONNE_Dati.min_art_602, VDONNE_Dati.min_art_609_5, VDONNE_Dati.min_art_609_11 ");	
			sql.append("FROM Domande ");
			sql.append("INNER JOIN VDONNE_dati ON Domande.ID_domanda = VDONNE_dati.ID_domanda ");
			sql.append("INNER JOIN Doc ON Domande.ID_domanda = Doc.ID ");
			sql.append("INNER JOIN Soggetti ON Domande.ID_soggetto = Soggetti.ID_soggetto ");
			sql.append(" WHERE Domande.ID_domanda = "+IDdomanda+";");

            doQuery(sql.toString());
            
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
    	ElabStaticContext.getInstance().resetSession( QVittime.class.getName(), IDdomanda, "records" );
    }
    
    /** QStudente constructor */
    public QVittime(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
