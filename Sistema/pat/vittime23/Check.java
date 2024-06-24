package c_elab.pat.vittime23;

import java.util.Calendar;

public class Check extends QVittime {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		double check = 0.0;
		try {
			String id_prov_residenza			= records.getString(1, records.getIndexOfColumnName("id_provincia_residenza"));
			String id_tp_sex 					= records.getString(1, records.getIndexOfColumnName("id_tp_sex"));
			boolean violenza_domestica 			= records.getBoolean(1, records.getIndexOfColumnName("violenza_domestica"));
			boolean residenza_tn 				= records.getBoolean(1, records.getIndexOfColumnName("residenza_tn"));
			boolean procedimento_penale 		= records.getBoolean(1, records.getIndexOfColumnName("procedimento_penale"));
			boolean no_istanza_precedente		= records.getBoolean(1, records.getIndexOfColumnName("no_istanza_precedente"));
			Calendar data_autorita_giudiziaria 	= records.getCalendar(1, records.getIndexOfColumnName("autorita_giudiziaria_data"));
			Calendar data_presentazione 		= records.getCalendar(1, records.getIndexOfColumnName("data_presentazione"));

			int art_571 					= records.getInteger(1, records.getIndexOfColumnName("art_571"));
			int art_572 					= records.getInteger(1, records.getIndexOfColumnName("art_572"));
			int art_575 					= records.getInteger(1, records.getIndexOfColumnName("art_575"));
			int art_582 					= records.getInteger(1, records.getIndexOfColumnName("art_582"));
			int art_583 					= records.getInteger(1, records.getIndexOfColumnName("art_583"));
			int art_584 					= records.getInteger(1, records.getIndexOfColumnName("art_584"));
			int art_583_2 					= records.getInteger(1, records.getIndexOfColumnName("art_583_2"));
			int art_609_2 					= records.getInteger(1, records.getIndexOfColumnName("art_609_2"));
			int art_609_4 					= records.getInteger(1, records.getIndexOfColumnName("art_609_4"));
			int art_609_8 					= records.getInteger(1, records.getIndexOfColumnName("art_609_8"));
			int art_612 					= records.getInteger(1, records.getIndexOfColumnName("art_612"));
			int art_612_2 					= records.getInteger(1, records.getIndexOfColumnName("art_612_2"));
			
			int min_art_600 				= records.getInteger(1, records.getIndexOfColumnName("min_art_600"));
			int min_art_600_2 				= records.getInteger(1, records.getIndexOfColumnName("min_art_600_2"));
			int min_art_600_3 				= records.getInteger(1, records.getIndexOfColumnName("min_art_600_3"));
			int min_art_600_5 				= records.getInteger(1, records.getIndexOfColumnName("min_art_600_5"));
			int min_art_601 				= records.getInteger(1, records.getIndexOfColumnName("min_art_601"));
			int min_art_602 				= records.getInteger(1, records.getIndexOfColumnName("min_art_602"));
			int min_art_609_5 				= records.getInteger(1, records.getIndexOfColumnName("min_art_609_5"));
			int min_art_609_11 				= records.getInteger(1, records.getIndexOfColumnName("min_art_609_11"));
			
			int tot_reati_minorenni = Math.abs(min_art_600) + Math.abs(min_art_600_2) + Math.abs(min_art_600_3) + Math.abs(min_art_600_5) + 
					Math.abs(min_art_601) + Math.abs(min_art_602) + Math.abs(min_art_609_5) + Math.abs(min_art_609_11);
			
			int tot_reati_penali = Math.abs(art_571) + Math.abs(art_572) + Math.abs(art_575) + Math.abs(art_582) + Math.abs(art_583) + Math.abs(art_583_2) + Math.abs(art_584) + 
									Math.abs(art_609_2) + Math.abs(art_609_4) + Math.abs(art_609_8) + Math.abs(art_612) + Math.abs(art_612_2)
									+ tot_reati_minorenni;
			
			//1.  residenza non in Provincia di Trento al momento della presentazione della domanda
			if( !id_prov_residenza.equals("TN")){ 
				check += 100000000;
			}
			
			//2. residenza non in provincia di Trento nel momento in cui la persona ha subito l'offesa dal reato
			if( !residenza_tn ){
				check += 20000000;
			}
			
			//3. fattispecie di reato non rientrante nell'ambito della violenza domestica
			if(id_tp_sex.equals("M") && !violenza_domestica ){
				check += 3000000;
			}
			
			//4.  fattispecie di reato non contemplata dalla disciplina
			if( !procedimento_penale || tot_reati_penali == 0){
				check += 400000;
			}
			
			//5. presentazione della domanda oltre il termine di 5 anni dalla data di adozione del provvedimento dell'autorità giudiziaria
			if(data_autorita_giudiziaria != null){
				if(getDiffAnni(data_autorita_giudiziaria, data_presentazione) >= 5){
					check += 50000;
				}
			}
			
			//6. istanza di accesso al fondo presentata in riferimento al medesimo risarcimento del danno riconosciuto alla persona offesa
			if( !no_istanza_precedente){
				check += 6000;
			}
			
			
			//se c'è un errore costruisco l'array di motivi d'esclusione, altrimenti torno 0
			if(check != 0){
				check += 1000000000;
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return -1.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return -1.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return -1.0;
		}
		return check;	
	}
	
	
	
	public static int getDiffAnni(Calendar data_autorita_giudiziaria, Calendar data_presentazione) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int anni = 0;
		if (data_presentazione == null)
			data_presentazione = Calendar.getInstance();

		anni = data_presentazione.get(Calendar.YEAR) - data_autorita_giudiziaria.get(Calendar.YEAR);

		if (data_presentazione.get(Calendar.MONTH) - data_autorita_giudiziaria.get(Calendar.MONTH) < 0) {
			anni = anni - 1;
		}

		if (data_presentazione.get(Calendar.MONTH) == data_autorita_giudiziaria.get(Calendar.MONTH)
				&& data_presentazione.get(Calendar.DAY_OF_MONTH) < data_autorita_giudiziaria.get(Calendar.DAY_OF_MONTH)) {
			anni = anni - 1;
		}
		return anni;
	}
}
