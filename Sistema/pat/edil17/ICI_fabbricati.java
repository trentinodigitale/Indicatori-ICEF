package c_elab.pat.edil17;


public class ICI_fabbricati extends QImmobiliareEdil {
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	
	//servizio esonero restituzione contributo solo per 2014 (da togliere per anni successivi)
	//private long idServizioEsonero = 12014;
	
	public double getValue() {
		
		double ICI_fabbricati_oggetto_intervento = 0.0;
		
		if( records!=null && records.getRows()>0 ) {
 		
			for (int i = 1; i <= records.getRows(); i++) {
				try {
					// se immobile oggetto dell'intervento 				
					if (records.getElement(i, 14) != null) {
						// se l'immobile oggetto dell'intervento è un fabbricato
						if (!(records.getString(i, 12)).equals("TE")){
							ICI_fabbricati_oggetto_intervento += getValoreICIRiga(i);
						}
					}
					/*
					else if(super.servizio==idServizioEsonero && records.getInteger(i, 1)!=0 ) {
						//includi sempre la residenza se servizio esonero restituzione contributo
						ICI_fabbricati_oggetto_intervento += getValoreICIRiga(i);
					}
					*/
				} catch (NullPointerException n) {
					System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
					return 0.0;
				} catch (NumberFormatException nfe) {
					System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
					return 0.0;
				}
			}	
		}
		return ICI_fabbricati_oggetto_intervento;
	}
}