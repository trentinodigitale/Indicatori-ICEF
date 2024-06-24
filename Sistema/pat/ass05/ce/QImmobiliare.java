/**
 *Created on 28-mag-2004
 */

package c_elab.pat.ass05.ce;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.anf.PassaValoriANF05;

import java.util.Calendar;
import it.clesius.clesius.util.Sys;
import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import c_elab.pat.icef.*;


/** query per il quadro E della dichiarazione ICEF
 * @author g_barbieri
 */
public abstract class QImmobiliare extends ElainNode {
       
    //TODO
    protected double valore_mq_imm_estero = 500.0;

	/** QImmobiliare constructor */
	public QImmobiliare() {
	}

	/**
	* restituisce la data di nascita dell'usufruttuario.
	*/
	private Calendar BirthDate(String strDataNascita, Calendar dataRif)
	{
		if(strDataNascita != null && !strDataNascita.equals("")) {

		   int anno =(new Integer(strDataNascita.substring(0,4))).intValue();
		   int mese = (new Integer(strDataNascita.substring(5,7))).intValue() -1;
		   int giorno =(new Integer(strDataNascita.substring(8,10))).intValue();
       
		   Calendar rightNow = Calendar.getInstance();
		   rightNow.set(anno,mese,giorno);
		   return rightNow;
		   //return new Date(anno,mese,giorno);
		} else {
		   return dataRif;
		}
	}
	
	/**
	* calcola il valore degli immobili distringuendo tra residenza e altri
	*/
	protected double getValoreImmobili( boolean residenza ) {
		double total = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		int durata;
		Calendar theDate;

		for (int i = 1; i <= records.getRows(); i++ ){
			try {
				if ( 
					((((String)records.getElement(i,1)).equals("0")) && !residenza ) 
					|| 
					(!(((String)records.getElement(i,1)).equals("0")) && residenza ) ) {
						
					String tipo = (String)records.getElement(i,5);
                    //N.B. il valore ICI è già rapportato alla quota di possesso del patrimonio immobiliare (vedi query)
					double val_ici = Sys.round(new Double((String) records.getElement(i, 3)).doubleValue() - aggiusta, round);
					double peso_patrim = new Double((String)records.getElement(i,2)).doubleValue()/ 100;
					double aNum = val_ici * peso_patrim;
					
					int yearRif = 2000;
					int monthRif = 12;
					int dayRif = 31;
					//tratta i casi fino ad agosto 2005 dove datarif era la data della domanda
					if ( new Long((String)records.getElement(i,10)).longValue() < 93000 ) {
						String strDataRif = (String)records.getElement(i,8);
						yearRif = new Integer(strDataRif.substring(0,4)).intValue();
						monthRif = new Integer(strDataRif.substring(5,7)).intValue();
						dayRif = new Integer(strDataRif.substring(8,10)).intValue();
					// tratta i nuovi casi dove datarif è il 31/12 dell'anno di rif del patrimonio
					} else {
						yearRif = new Integer((String)records.getElement(i,9)).intValue();
					}
					Calendar dataRif = Calendar.getInstance();
					dataRif.set(yearRif, monthRif -1, dayRif);
					
					//System.out.println("data rif = " + yearRif + " - " + monthRif + " - " + dayRif);
	
					if(tipo.equals("PR")) {
						// proprietà
						total += aNum;
					} else if (tipo.equals("UV") || tipo.equals("SV") || tipo.equals("AV") ) {
						// Usufrutto a vita, Uso a vita, Abitazione a vita
						theDate = BirthDate((String)records.getElement(i,7), dataRif);
						total += Usufrutto.getValoreUsuf_aVita(aNum, theDate, dataRif, yearRif);
					} else if (tipo.equals("UT") || tipo.equals("ST") || tipo.equals("AT") ) {
						// Usufrutto a termine, Uso a termine, Abitazione a termine
						durata = new Integer((String)records.getElement(i,6)).intValue();
						total += Usufrutto.getValoreUsuf_aTermine(aNum, durata, yearRif);
					} else if (tipo.equals("NV") ) {
						// Nuda propr. con usufrutto a vita
						theDate = BirthDate((String)records.getElement(i,7), dataRif);
						total += Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);
					} else if (tipo.equals("NT") ) {
						// Nuda propr. con usufrutto a termine
						durata = new Integer((String)records.getElement(i,6)).intValue();
						total += Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);
					} else {
						// tipo non trovato
						System.out.println("Tipo diritto n. " + tipo + " non previsto nella classe Immobiliare");
						total += 0;
					}
				} else {
					total += 0;
				}
			} catch(NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}
		return total;
	}
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		ElabStaticContext.getInstance().resetSession( QImmobiliare.class.getName(), IDdomanda, "records" );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QImmobiliare.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			
			super.init(dataTransfer);
            
			/* tolto dalla versi0ne dell'icef del 24-05-05
            // legge i mq degli immobili all'estero
			StringBuffer sql = new StringBuffer();
			//                               1                                    2
			sql.append(
				"SELECT Dich_icef.mq_complessivi, R_Relazioni_parentela.peso_patrimonio ");
			sql.append("FROM Familiari ");
			sql.append(
				"INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
			sql.append(
				"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(
				"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			doQuery(sql.toString());

            tb_imm_estero = records;
            */          
                        
            // legge i dati degli immobili del quadro E
			StringBuffer sql = new StringBuffer();
			//                                      1                                         2                                  3                                                                       4                                       5                                     6                                          7                          8                                9                                   10
			sql.append(
				"SELECT Patrimoni_immobiliari.residenza_nucleo, R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici * Patrimoni_immobiliari.quota / 100, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione ");
			sql.append("FROM Familiari ");
			sql.append(
				"INNER JOIN Patrimoni_immobiliari ON Familiari.ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
			sql.append(
				"INNER JOIN Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione ");
			sql.append(
				"INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append(
				"INNER JOIN Doc ON Familiari.ID_domanda = Doc.ID ");
			sql.append(
				"INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(" AND Familiari.ID_dichiarazione in ");
			sql.append(PassaValoriANF05.getID_dichiarazioni(IDdomanda));

			doQuery(sql.toString());
			
                        //System.out.println(sql.toString());

			session.setInitialized(true);
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
}
