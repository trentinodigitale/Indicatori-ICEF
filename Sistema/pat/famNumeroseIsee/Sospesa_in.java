package c_elab.pat.famNumeroseIsee;

import java.util.Calendar;

/*****************************************************
                classe Check
		idoneo assegno fam. numerose  per termini domanda
 ******************************************************/


public class Sospesa_in extends QDati{
	
	
	//CAMBIAMI 
	int 	idPermessoSogg = 41;
	
	public double getValue(){
		
		double sospesa_in = 0.0;
		
		try {
			int id_dichiarazione 	=  records.getInteger(1, records.getIndexOfColumnName("id_dichiarazione"));
			int isee_non_conforme 	=  records.getInteger(1, records.getIndexOfColumnName("isee_non_conforme"));
			int id_permesso_sogg 	=  records.getInteger(1, records.getIndexOfColumnName("id_tp_permesso")); 
			
			if(id_dichiarazione == 0){
				sospesa_in = 1;
			}
			if(isee_non_conforme != 0){
				sospesa_in = 1;
			}
			if(id_permesso_sogg == idPermessoSogg){
				sospesa_in = 1;
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
		return sospesa_in;
		
	}
	
	public Sospesa_in(){	
		
	}
}
