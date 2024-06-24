package c_elab.pat.scuola11;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/**
 * 
 * @author s_largher
 *
 * Recupera i dati utilizzati dalle classi di trasformazione
 */
public abstract class QStudente extends ElainNode {
    
	protected Table datiScuolaStudente;

    public double getValue() {
        return 0.0;
    }
    
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QStudente.class.getName(), IDdomanda, "records" );
     }
     
    public QStudente(){
    }
    
    /**
     * Recupera i dati (datiScuolaStudente):
     * 1. Domande.ID_provincia_residenza;
     * 2. SCUOLE_Studenti.media_voti, 
     * 3. SCUOLE_Studenti.trasporti, 
     * 4. SCUOLE_Studenti.mensa, 
     * 5. SCUOLE_Studenti.tassa, 
     * 6. SCUOLE_Studenti.libri, 
     * 7. SCUOLE_Studenti.convitto_alloggio
     * 
     * Recupera i dati (records): 
     * 1. data_nascita (dello studente r_relazioni_parentela se esiste altrimenti richiedente)
     * 
     * @param RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		    super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT DISTINCT CASE WHEN Soggetti_stu.ID_domanda IS NOT NULL ");
            sql.append(" THEN Soggetti_stu.data_nascita ELSE Soggetti_rich.data_nascita END AS data_nascita_studente ");
            sql.append(" FROM  Domande  LEFT OUTER JOIN");
            sql.append(" (");
            sql.append("SELECT familiari.id_domanda, soggetti.data_nascita");
            sql.append(" FROM   familiari INNER JOIN");
            sql.append(" dich_icef ON familiari.ID_dichiarazione = dich_icef.ID_dichiarazione INNER JOIN");
            sql.append(" r_relazioni_parentela ON familiari.ID_relazione_parentela = r_relazioni_parentela.ID_relazione_parentela INNER JOIN");
            sql.append(" soggetti ON soggetti.id_soggetto = dich_icef.id_soggetto ");
            sql.append(" WHERE parentela = 'Studente'");
            sql.append(") Soggetti_stu ON Domande.ID_domanda = Soggetti_stu.id_domanda INNER JOIN");
            sql.append(" Soggetti Soggetti_rich ON Domande.ID_soggetto = Soggetti_rich.ID_soggetto");
            sql.append(" WHERE Domande.ID_domanda=");
            sql.append(IDdomanda);
            
            doQuery(sql.toString());
            
            datiScuolaStudente = records;

            doQuery("SELECT Domande.ID_provincia_residenza, SCUOLE_Studenti.media_voti, SCUOLE_Studenti.trasporti, SCUOLE_Studenti.mensa, SCUOLE_Studenti.tassa, SCUOLE_Studenti.libri, SCUOLE_Studenti.convitto_alloggio " +
            		"FROM Domande INNER JOIN SCUOLE_Studenti ON Domande.ID_domanda = SCUOLE_Studenti.ID_domanda " +
            		"WHERE Domande.ID_domanda=" + IDdomanda);
            
			session.setInitialized( true );
			session.setRecords( records );
			session.setAttribute("datiScuolaStudente", datiScuolaStudente);

		} else {
			records = session.getRecords();
			datiScuolaStudente = (Table)session.getAttribute("datiScuolaStudente");
		}
    }
}

