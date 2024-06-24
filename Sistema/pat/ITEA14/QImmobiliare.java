package c_elab.pat.ITEA14;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


/** query per il quadro E della dichiarazione ICEF anno corrente
 * @author s_largher
 */
public abstract class QImmobiliare extends QItea {

	/** QImmobiliare constructor */
	public QImmobiliare() {
	}
	
	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		getImmob_corrente();
	}
	
	/**
	* calcola il valore degli immobili NON distringuendo tra residenza e altri!!!
	*/
	protected double getValoreImmobili( ) {
		Table immobili = getImmob_corrente();

		//******************
		//CAMBIAMI: va cambiata ogni anno
		c_elab.pat.icef14.QImmobiliare qImmobiliare = new c_elab.pat.icef14.QImmobiliare();
		//c_elab.pat.icef11.QImmobiliare qImmobiliare = new c_elab.pat.icef11.QImmobiliare();
		//*******************
		
		qImmobiliare.setDataTransfer(dataTransfer);
		qImmobiliare.setTable(immobili);
		boolean usaDetrazioneMaxValoreNudaProprieta = true;
		return qImmobiliare.getValoreTotaleImmobili(usaDetrazioneMaxValoreNudaProprieta);
	}
}
