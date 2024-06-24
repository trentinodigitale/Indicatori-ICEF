package c_elab.pat.asscura;

import java.util.Calendar;

import it.clesius.clesius.util.Sys;
import it.clesius.db.util.Table;

/**
 * Ritorna il valore nelle dichiarazioni connesse (TUTTI I COMPONENTI) di:<BR>
 * 062 - Assegno di cura (L.P. 15/2012);<BR>
 * 103 - Sussidio economico per assistenza di persone non autosufficienti (assegno di cura), 
 * dal periodo 30503 e dal servizio 30603 e per il servizio 61000 (AI).<BR>
 * ATTENZIONE DA VERIFICARE IL CODICE OGNI ANNO!
 */
public class Precedente extends QDati {

	//VERIFICAMI: verificare che sia la stessa ogni anno che cambia la dichiarazione ICEF
	private final String	ID_TP_EROGAZIONE_NEW	= "062"; // Assegno di cura (L.P. 15/2012)
	private final String	ID_TP_EROGAZIONE_OLD	= "103"; // Sussidio economico per assistenza di persone non autosufficienti (assegno di cura)
	
	private final int		ID_SERVIZIO_30500		= 30500;
	private final int		ID_SERVIZIO_30602		= 30602;
	private final int		ID_SERVIZIO_61000		= 61000;
	
	private final int		ID_PERIODO_30503		= 30503;
	
	private final double	ROUND					= 1.0;
	private final double	AGGIUSTA				= 0.01;
	
	
	/** 
	 * ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	
	public double getValue() {
		if (datiC5 == null) {
			return 0.0;
		}
		
		
		Calendar comapre= Calendar.getInstance();
		comapre.set(Calendar.YEAR, 2016);
		comapre.set(Calendar.DAY_OF_MONTH, 1);
		comapre.set(Calendar.MONTH, 6);
		comapre.set(Calendar.HOUR, 1);
	
		try {
			Calendar data_presentazione=iAcDati.getCalendar(1, 1);
			if(data_presentazione.after(comapre)){
				return 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
		double	tot		= 0.0;

		try {
			for (int i = 1; i <= datiC5.getRows(); i++) {
				String idTpErogazione = datiC5.getString(i, 3);
				if(idTpErogazione.equals(ID_TP_EROGAZIONE_NEW)) {
					tot = tot + Sys.round(new Double((String) datiC5.getElement(i, 2)).doubleValue() - AGGIUSTA, ROUND) * new Double((String) datiC5.getElement(i, 1)).doubleValue() / 100.0;
				}
				if(idTpErogazione.equals(ID_TP_EROGAZIONE_OLD) 
						&& ( (getIdServizio() >= ID_SERVIZIO_61000)
								|| (getIdServizio() >= ID_SERVIZIO_30602)
								|| (getIdServizio() == ID_SERVIZIO_30500 && getIdPeriodo() >= ID_PERIODO_30503)
							)
						) {
					tot = tot + Sys.round(new Double((String) datiC5.getElement(i, 2)).doubleValue() - AGGIUSTA, ROUND) * new Double((String) datiC5.getElement(i, 1)).doubleValue() / 100.0;
				}
			}
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
