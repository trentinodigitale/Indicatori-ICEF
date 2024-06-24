package c_elab.pat.maternitaIsee;

import java.util.Calendar;

/*****************************************************
                classe Check
		idoneo assegno fam. numerose  per termini domanda
 ******************************************************/


public class Sospesa_in extends QDati{
	
	
	//CAMBIAMI 
	int 	idPermessoSogg = 31;
	
	public double getValue(){
		
		double sospesa_in = 0.0;
		
		try {
			int id_dichiarazione 	=  dati.getInteger(1, dati.getIndexOfColumnName("id_dichiarazione"));
			int isee_non_conforme 	=  dati.getInteger(1, dati.getIndexOfColumnName("isee_non_conforme"));
			int id_permesso_sogg 	=  dati.getInteger(1, dati.getIndexOfColumnName("id_tp_permesso")); 
			int is_richiesta	 	=  dati.getInteger(1, dati.getIndexOfColumnName("is_richiesta"));
			
			if(id_dichiarazione == 0){
				sospesa_in = 1;
			}
			if(isee_non_conforme != 0){
				sospesa_in = 1;
			}
			if(id_permesso_sogg == idPermessoSogg){
				sospesa_in = 1;
			}
			if(is_richiesta != 0){
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
