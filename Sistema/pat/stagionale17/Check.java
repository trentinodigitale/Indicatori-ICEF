package c_elab.pat.stagionale17;

import java.util.Calendar;

public class Check extends QDati {

	
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 * 
	 * 1 (10000 +    1)  escluso per non essere nello stato di disoccupazione o non essere lavoratore autonomo
	 * 2 (10000 +   10)  assenza del requisito di residenza e domicilio
	 * 3 (10000 +  100)  assenza del requisito di età anagrafica al momento della domanda
	 * 4 (10000 + 1000)  esclusione d'ufficio
	 */
	public double getValue() {
		
		double check = 10000;

		try {
			
			int escluso 			= records.getInteger(1, records.getIndexOfColumnName("escludi_ufficio"));
			int no_residenza 		= records.getInteger(1, records.getIndexOfColumnName("no_residenza"));
			String id_tp_sex 		= records.getString(1, records.getIndexOfColumnName("id_tp_sex"));
			
	        Calendar dataNascita 	= records.getCalendar(1, records.getIndexOfColumnName("data_nascita"));
	        Calendar dataPres 		= records.getCalendar(1, records.getIndexOfColumnName("data_presentazione"));
	        
	        int lg68 				= records.getInteger(1, records.getIndexOfColumnName("legge_68_99"));
	        
	        int disoccupato 		= records.getInteger(1, records.getIndexOfColumnName("disoccupato"));
	        int lavAut 				= records.getInteger(1, records.getIndexOfColumnName("lavAut"));
	        
	        
	        boolean errorAge = false;
			int age = getEta(dataNascita, dataPres);
			//di almeno 49 anni per le donne 
			//di almeno 53 per gli uomini
			//per legge 68/99 di almeno 44 per le donne
			//per legge 68/99 di almeno 48 per uomini
			if(id_tp_sex.equals("F")) {
				
				if(lg68==1) {
					if(age < 44) {
						errorAge = true;
					}
				}else if(age < 49) {
					errorAge = true;
				}
			}else if(id_tp_sex.equals("M")) {
				if(lg68==1) {
					if(age < 48) {
						errorAge = true;
					}
				}else if(age < 53) {
					errorAge = true;
				}
			}
			
			
			
			//* 1 (10000 +    1)  escluso per non essere nello stato di disoccupazione o non essere lavoratore autonomo
			if(disoccupato==0 && lavAut==0) {
				check += 1;
			}			
			
			//* 2 (10000 +   10)  assenza del requisito di residenza e domicilio
			if(no_residenza!=0) {
				check += 10;
			}
			
			//* 3 (10000 +  100)  assenza del requisito di età anagrafica al momento della domanda
			if(errorAge) {
				check += 100;
			}
            
			//* 4 (10000 + 1000)  esclusione d'ufficio
			if(escluso!=0) {
				check += 1000;
			}
			
			if(check<=10000) {
				check = 0;
			}
			return check;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int getEta(Calendar calNascita, Calendar calRif) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int eta = 0;
		if (calRif == null)
			calRif = Calendar.getInstance();

		eta = calRif.get(Calendar.YEAR) - calNascita.get(Calendar.YEAR);

		if (calRif.get(Calendar.MONTH) - calNascita.get(Calendar.MONTH) < 0) {
			eta = eta - 1;
		}

		if (calRif.get(Calendar.MONTH) == calNascita.get(Calendar.MONTH)
				&& calRif.get(Calendar.DAY_OF_MONTH)
				< calNascita.get(Calendar.DAY_OF_MONTH)) {
			eta = eta - 1;
		}


		return eta;
	}
}
