package c_elab.pat.ITEA15;


import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;


/** query per il quadro E della dichiarazione ICEF anno precedente
 * @author s_largher
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
		
		//******************
		//CAMBIAMI: va cambiata ogni anno
		c_elab.pat.icef15.QImmobiliare qImmobiliare = new c_elab.pat.icef15.QImmobiliare();
		//c_elab.pat.icef11.QImmobiliare qImmobiliare = new c_elab.pat.icef11.QImmobiliare();
		//*******************
		
		qImmobiliare.setDataTransfer(dataTransfer);
		qImmobiliare.setTable(immobili);
		boolean usaDetrazioneMaxValoreNudaProprieta = true;
		return qImmobiliare.getValoreTotaleImmobili(usaDetrazioneMaxValoreNudaProprieta);
	}
}
