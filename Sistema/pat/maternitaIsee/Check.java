package c_elab.pat.maternitaIsee;

import java.util.Calendar;
import c_elab.pat.maternitaIsee.QDati;

/*****************************************************
                classe Check
		idoneo assegno maternità per termini domanda
******************************************************/


public class Check extends QDati {
	
	//CAMBIAMI 
	protected String 	provinciaRes = "TN";
	protected int 		esclusioneUfficio_residenza 	= 103;
	protected int 		esclusioneUfficio_titoloSogg 	= 105;
	protected int 		esclusioneUfficio 				= 106;
  
	public double getValue() {
		
		double check = 0.0;
		
		try {
			
			int esclusioneUff =  dati.getInteger(1, dati.getIndexOfColumnName("escludi_ufficio"));
			
			//check 1 = 100000: condizione economica oltre i limiti (gestisce la rete)
			
			//check 2 = 020000: numero dei figli deve essere maggiore a 3 (gestisce la rete)
			
			//check 3 = 003000: mancanza del requisito di residenza
			//String idProvRes = (String)( dati.getString(1, dati.getIndexOfColumnName("ID_provincia_residenza")));
			if(/*!idProvRes.equals(provinciaRes) ||*/ esclusioneUff == esclusioneUfficio_residenza){
				check += 3000;
			}
			
			//check 4 = 000400: presentazione domanda fuori termini
	        String data_domanda = "1990-01-01";
	        
	        Calendar cal_domanda = Calendar.getInstance();
	        Calendar cal_figlio = getData();
	        
	        try {
	            data_domanda = (String)(dati.getElement(1,1));
	        } catch(NullPointerException n) {
	        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
	            return 0;
	        }
	        try {
	            cal_domanda.set(new Integer(data_domanda.substring(0,4)).intValue(), new Integer(data_domanda.substring(5,7)).intValue() - 1, new Integer(data_domanda.substring(8,10)).intValue(), 12, 0);
	        } catch (NullPointerException npe) {
	        	System.out.println("Null pointer in " + getClass().getName() + ": " + npe.toString());
	            return 0;
	        } catch (IndexOutOfBoundsException iobe) {
	        	System.out.println("---> ERRORE IN " + getClass().getName() + ": " + iobe.toString());
	            return 0;
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("---> ERRORE IN " + getClass().getName() + ": " + e.toString());
	            return 0;
			}
	        
	        // se la domanda è entro 6 mesi dal parto
	        cal_figlio.add(Calendar.MONTH, +6);
	        cal_figlio.add(Calendar.DAY_OF_MONTH, +1);
	        if ( cal_domanda.before(cal_figlio) ) {
	        	
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
    public Check(){		//{{INIT_CONTROLS
		//}}
    }
}
