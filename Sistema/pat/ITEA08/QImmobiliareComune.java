/**
 *Created on 28-mag-2004
 */

package c_elab.pat.ITEA08;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import c_elab.pat.icef.Usufrutto;


/** query per il quadro E della dichiarazione ICEF anno corrente
 * @author g_barbieri
 */
public abstract class QImmobiliareComune extends QItea {
	
	private Hashtable hashtableMaxUsofruttoForDich = new Hashtable();
	
	/** QImmobiliare constructor */
	public QImmobiliareComune() {
	}
	
	private void addMaxUsofruttoForDichValue(String idDich, 
			long value,
			String categoriaCatastale)
	{
		//Tutti gli A tranne A1,A8,A9
		if(categoriaCatastale.equalsIgnoreCase("A2") ||
				categoriaCatastale.equalsIgnoreCase("A3") ||
				categoriaCatastale.equalsIgnoreCase("A4") ||
				categoriaCatastale.equalsIgnoreCase("A5") ||
				categoriaCatastale.equalsIgnoreCase("A6") ||
				categoriaCatastale.equalsIgnoreCase("A7") ||
				categoriaCatastale.equalsIgnoreCase("A10")||
				categoriaCatastale.equalsIgnoreCase("A11"))
		{
			Long oldMaxUsofrutto = (Long)hashtableMaxUsofruttoForDich.get(idDich);
			if(oldMaxUsofrutto!=null)
			{
				long oldValue = oldMaxUsofrutto.longValue();

				if(value>oldValue)
				{
					//cerco solo il valore massimo per ogni soggetto
					hashtableMaxUsofruttoForDich.put(idDich, new Long(value));
				}
			}
			else
			{
				//nessun valore finora per questo soggetto
				hashtableMaxUsofruttoForDich.put(idDich,  new Long(value));
			}
		}
	}

	private double getTotalOfMaxUsofruttoForDich()
	{
		double ret = 0;

		Enumeration e = hashtableMaxUsofruttoForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			Long maxUsofrutto = (Long)hashtableMaxUsofruttoForDich.get(key);
			ret+=maxUsofrutto.longValue();
		}
		return ret;
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
		} else {
		   return dataRif;
		}
	}
	
	/**
	* calcola il valore degli immobili NON distringuendo tra residenza e altri!!!
	*/
	protected double getValoreImmobili(Table immobili) {
		double total = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		int durata;
		Calendar theDate;
		
		for (int i = 1; i <= immobili.getRows(); i++ ){
			try {
				String tipo = getElement( immobili, "ID_tp_diritto" , i );
                //N.B. il valore ICI è già rapportato alla quota di possesso del patrimonio immobiliare (vedi query)
				double val_ici = Sys.round(new Double(getElement( immobili, "valore" , i )).doubleValue() - aggiusta, round);
				double peso_patrim = new Double(getElement( immobili, "peso_patrimonio" , i )).doubleValue()/ 100;
				double aNum = val_ici * peso_patrim;
					
				int yearRif = 2000;
				int monthRif = 12;
				int dayRif = 31;
				yearRif = new Integer(getElement( immobili, "anno_produzione_patrimoni" , i )).intValue();
				Calendar dataRif = Calendar.getInstance();
				dataRif.set(yearRif, monthRif -1, dayRif);
					
				//System.out.println("data rif = " + yearRif + " - " + monthRif + " - " + dayRif);
				
				if(tipo.equals("PR")) {
					// proprietà
					total += aNum;
				} else if (tipo.equals("UV") || tipo.equals("SV") || tipo.equals("AV") ) {
					// Usufrutto a vita, Uso a vita, Abitazione a vita
					theDate = BirthDate(getElement( immobili, "data_nascita_usufruttuario" , i ), dataRif);
					total += Usufrutto.getValoreUsuf_aVita(aNum, theDate, dataRif, yearRif);
				} else if (tipo.equals("UT") || tipo.equals("ST") || tipo.equals("AT") ) {
					// Usufrutto a termine, Uso a termine, Abitazione a termine
					durata = new Integer(getElement( immobili, "anni_usufrutto" , i )).intValue();
					total += Usufrutto.getValoreUsuf_aTermine(aNum, durata, yearRif);
				} else if (tipo.equals("NV") ) {
					// Nuda propr. con usufrutto a vita
					theDate = BirthDate(getElement( immobili, "data_nascita_usufruttuario" , i ), dataRif);
					long value = Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);
					
					String idDich = getElement( immobili, "ID_dichiarazione" , i );
					String categoriaCatastale = getElement( immobili, "ID_tp_cat_catastale" , i );
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					addMaxUsofruttoForDichValue(idDich,value,categoriaCatastale);
					
					total += value;
				} else if (tipo.equals("NT") ) {
					// Nuda propr. con usufrutto a termine
					durata = new Integer(getElement( immobili, "anni_usufrutto" , i )).intValue();
					long value = Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);
				
					String idDich = getElement( immobili, "ID_dichiarazione" , i );
					String categoriaCatastale = getElement( immobili, "ID_tp_cat_catastale" , i );
					//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
					addMaxUsofruttoForDichValue(idDich,value,categoriaCatastale);
					
					total += value;
				} else {
					// tipo non trovato
					System.out.println("Tipo diritto n. " + tipo + " non previsto nella classe Immobiliare");
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
		
		//Modifica ICEF del 12/11/2008 (tolto dal totale degli immobili il max valore della nuda proprietà per ogni soggetto collegato alla domanda)
		total = total - getTotalOfMaxUsofruttoForDich();
		
		return total;
	}
}
