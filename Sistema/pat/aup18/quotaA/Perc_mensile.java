package c_elab.pat.aup18.quotaA;

import it.clesius.evolservlet.icef.PassaValoriIcef;

/** query per il calcolo della percentuale totale dell'importo mensile
 * @author g_barbieri
 */
public class Perc_mensile extends QQuotaA {


	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */

	public double getValue(){

		//da forzare se i servizi sociali hanno indicatoRiduzione in struttura 1-x/100 perchè x è la percentuale di riduzione 
		//solo se è indicato in aup_dati.nucleo_struttura_parz=1 e caso sociale =1

		if(  PassaValoriIcef.getForzaRiduzionePercentualeStruttura(IDdomanda)!=null && PassaValoriIcef.getForzaRiduzionePercentualeStruttura(IDdomanda)>0){
			return 1.0- (PassaValoriIcef.getForzaRiduzionePercentualeStruttura(IDdomanda)/100);
		}
		
		//TODO
		double ret = 1.0;
		return ret;
	}





}