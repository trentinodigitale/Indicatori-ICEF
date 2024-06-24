/**
 *Created on 28-mag-2004
 */

package c_elab.pat.ITEA09;


import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import java.util.Calendar;
import c_elab.pat.icef.Usufrutto;


/** query per il quadro E della dichiarazione ICEF anno precedente
 * @author g_barbieri
 */
public abstract class QImmobiliare0 extends QItea {

	/** QImmobiliare0 constructor */
	public QImmobiliare0() {
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		getImmob_precedente();
	}
	
	/**
	* calcola il valore degli immobili NON distringuendo tra residenza e altri!!!
	*/
	protected double getValoreImmobili( ) {
		Table immobili = getImmob_precedente();
		
		c_elab.pat.icef09.QImmobiliare qImmobiliare = new c_elab.pat.icef09.QImmobiliare();
		qImmobiliare.setDataTransfer(dataTransfer);
		qImmobiliare.setTable(immobili);
		boolean usaDetrazioneMaxValoreNudaProprieta = true;
		return qImmobiliare.getValoreTotaleImmobili(usaDetrazioneMaxValoreNudaProprieta);
	}
}
