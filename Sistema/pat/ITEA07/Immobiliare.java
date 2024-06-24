/**
 *Created on 28-mag-2004
 */

package c_elab.pat.ITEA07;

import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.util.Calendar;
import c_elab.pat.icef.Usufrutto;


/** query per il quadro E della dichiarazione ICEF
 * @author g_barbieri
 */
public class Immobiliare extends QItea {

	/** QImmobiliare constructor */
	public Immobiliare() {
	}

	public void init(RunawayData dataTransfer) {
		super.init(dataTransfer);
		getImmob();
		getepuImmobiliTutti();
		getepuImmobiliFranchigia();
		calcola_quote(false);
		// inizializza l'anno precedente solo se il servizio è accesso
		if (servizio == 13000 || servizio == 13005) {
			getImmob_prec();
			getepuImmobiliTutti_prec();
			getepuImmobiliFranchigia_prec();
			calcola_quote(true);
		}
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
	* calcola il valore degli immobili distringuendo tra quote > o < 50%
	*/
	protected double getValoreImmobili( boolean gt50, boolean prec ) {
		double total = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		int durata;
		Calendar theDate;
		Table immobili = getImmob();
		
		if (prec)
			immobili = getImmob_prec();

		for (int i = 1; i <= immobili.getRows(); i++ ){
			try {
				String key_immobili = getElement( immobili, "ID_dichiarazione" , i ) + "-" + getElement( immobili, "immobile" , i );
				boolean quota_fino_a_50 = getQuote_familiari_fino_a_50().contains(key_immobili); 
				// SE la richiesta è per immobili con quota > 50% (gt50 = true) 
				// AND l'immobile ha una quota familiare > 50% (!quote_familiari_fino_a_.contains(key_immobili))
				// OPPURE
				// SE la richiesta è per immobili con quota <= 50% (gt50 = false) 
				// AND l'immobile ha una quota familiare <= 50% (quote_familiari_gt50.contains(key_immobili))
				if ( ( gt50 && !quota_fino_a_50 ) || ( !gt50 && quota_fino_a_50 ) ) {
				
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
						total += Usufrutto.getValoreNudaProprieta_aVita(aNum, theDate, dataRif, yearRif);
					} else if (tipo.equals("NT") ) {
						// Nuda propr. con usufrutto a termine
						durata = new Integer(getElement( immobili, "anni_usufrutto" , i )).intValue();
						total += Usufrutto.getValoreNudaProprieta_aTermine(aNum, durata, yearRif);
					} else {
						// tipo non trovato
						System.out.println("Tipo diritto n. " + tipo + " non previsto nella classe Immobiliare");
						total += 0;
					}
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
	
	
	private void calcola_quote(boolean prec) {
		
		Table p_epuImmobiliTutti = getepuImmobiliTutti();
		Table p_epuImmobiliFranchigia = getepuImmobiliFranchigia();
		
		if (prec) {
			p_epuImmobiliTutti = getepuImmobiliTutti_prec();
			p_epuImmobiliFranchigia = getepuImmobiliFranchigia_prec();
		}
		 
		if (p_epuImmobiliFranchigia!=null && p_epuImmobiliFranchigia.getRows()>0 && p_epuImmobiliTutti!=null) {
			try {
				for (int i=1; i<=p_epuImmobiliTutti.getRows(); i++) {
					int idDTutti = Integer.parseInt(getElement( p_epuImmobiliTutti, "ID_dichiarazione" , i )); 
					int immTutti = Integer.parseInt(getElement( p_epuImmobiliTutti, "immobile" , i )); 
					for (int j=1; j<=p_epuImmobiliFranchigia.getRows(); j++) {
						int idDFran = Integer.parseInt(getElement( p_epuImmobiliFranchigia, "ID_dichiarazione" , j ));
						int immFran = Integer.parseInt(getElement( p_epuImmobiliFranchigia, "immobile" , j ));
						if (idDTutti==idDFran && immTutti==immFran) {
							getQuote_familiari_fino_a_50().add(Integer.toString(idDTutti) + "-" + Integer.toString(immTutti));
							break;
						}
					}
				}
	        } catch(NullPointerException n) {
	            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
	        } catch (NumberFormatException nfe) {
	            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
	        }
		}
	}
}
