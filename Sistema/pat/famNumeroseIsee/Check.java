package c_elab.pat.famNumeroseIsee;

import java.util.Calendar;

/*****************************************************
                classe Check
		idoneo assegno fam. numerose  per termini domanda
 ******************************************************/


public class Check extends QDati{
	
	
	//CAMBIAMI 
	protected String 	provinciaRes 					= "TN";
	protected int 		esclusioneUfficio_residenza 	= 103;
	protected int 		esclusioneUfficio_titoloSogg 	= 105;
	protected int 		esclusioneUfficio 				= 106;
	
	public double getValue(){
		
		double check = 0.0;
		
		try {
			
			int esclusioneUff =  records.getInteger(1, records.getIndexOfColumnName("escludi_ufficio")); 
			
			//check 1 = 100000: condizione economica oltre i limiti (gestisce la rete)
			//check 2 = 900000: condizione economica sotto i limiti (gestisce la rete)
			
			
			//check 2 = 020000: numero dei figli deve essere maggiore a 3 (gestisce la rete)
			
			
			//check 3 = 003000: mancancaza del requisito di residenza
			String idProvRes = (String)( records.getString(1, records.getIndexOfColumnName("ID_provincia_residenza")));
			if(!idProvRes.equals(provinciaRes) || esclusioneUff == esclusioneUfficio_residenza){
				check += 3000;
			}
			
			
			//check 4 = 000400: presntazione domanda fuori termini
			String data_domanda = "1990-01-01";
			int anno_fam_numerose = 1990;

			Calendar cal_domanda = Calendar.getInstance();

			try {
				data_domanda = (String)(records.getElement(1,1));
				anno_fam_numerose = new Integer ((String)(records.getElement(1,2))).intValue();
			} catch(NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0;
			} catch(NumberFormatException nfe) {
				System.out.println("NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0;
			}

			Calendar inizio_validita_fam_numerose = Calendar.getInstance();
			inizio_validita_fam_numerose.set(anno_fam_numerose, 0, 1, 0, 0); // 1 gennaio anno_fam_numerose
			Calendar fine_validita_fam_numerose = Calendar.getInstance();
			fine_validita_fam_numerose.set(anno_fam_numerose + 1, 0, 31, 23, 59); // 31 gennaio anno_fam_numerose + 1

			try {
				cal_domanda.set(new Integer(data_domanda.substring(0,4)).intValue(), new Integer(data_domanda.substring(5,7)).intValue() - 1, new Integer(data_domanda.substring(8,10)).intValue(), 12, 0);
			} catch (NullPointerException npe) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + npe.toString());
				return 0;
			} catch (IndexOutOfBoundsException iobe) {
				System.out.println("---> ERRORE IN " + getClass().getName() + ": " + iobe.toString());
				return 0;
			}

			// se la domanda è entro 31 gennaio anno_fam_numerose + 1
			if (cal_domanda.after(inizio_validita_fam_numerose) && cal_domanda.before(fine_validita_fam_numerose) ) {
				
			} else {
				check += 400;
			}
			
			
			//check 5 = 000050: titolo soggiorno non valido
			if( esclusioneUff == esclusioneUfficio_titoloSogg){
				check += 50;
			}
			
			
			//check 6 = 000006: esclusione domanda d'ufficio
			if(esclusioneUff == esclusioneUfficio){
				check += 6;
			}
			
			if(check!=0){
				check += 1000000;
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
	
	public Check(){	
		
	}
}
