package c_elab.pat.mant20;

import java.util.Calendar;


/**
--0 ok
--1 lo calcola Wolfgang per condizione economica
--2 lo calcola Wolfgang per mancanza requisito figli (V_n_figli)

--3 mancato requisito residenza
--4 mancata presentazione surroga
--5 esclusione d'ufficio

DINIEGO MAI
REVOCA DATA
colaesce istruttoria

 *
 * 
 * @author a_pichler
 *
 */
public class V_check extends QDatiVariazioneDomanda {
	private static final int id_diniego_residenzaApapi		=103;
	private static final int id_revoca_residenzaApapi		=202;
	private static final int id_diniego_surrogaApapi		=104;
	private static final int id_diniego_ufficioApapi		=105;
	private static final int id_revoca_ufficioApapi			=203;
	
	private int id_mancata_residenzaOut		=3;
	private int id_mancata_surrogaOut		=4;
	private int id_esclusioneOut			=5;
	

	public double getValue() {
    	
        try {
        	int esito[] = new int[12];
        	boolean  variazione[]= new boolean[12];
            int id_diniego=0;
    		int id_sospensione=0;
    		boolean diniego=false;
    		boolean sospensione=false;
    		
        	for (int j = 0; j < 12; j++) {
        		esito[j] = 0;
        		variazione[j]=false;
			}
        	
        	boolean variazioneDa=false;
        	String data_variazione=null;
        	Calendar dataVariazione=null; 
        	
			Calendar dataPresentazione = records.getCalendar(1,records.getIndexOfColumnName("data_presentazione"));
			int mese = dataPresentazione.get(Calendar.MONTH)+1;
			int anno = dataPresentazione.get(Calendar.YEAR);
			
			int meseInizio = mese;
			int annoInizio	=anno;
			meseInizio++;
			if(meseInizio>12)
			{
				meseInizio = 1;
				annoInizio++;
			}
			dataPresentazione.set(Calendar.HOUR_OF_DAY, 0);
			dataPresentazione.set(Calendar.MINUTE, 0); 
			dataPresentazione.set(Calendar.SECOND, 0);
			dataPresentazione.set(Calendar.MILLISECOND, 0);
			
			//inizio del beneficio primo giorno del mese successivo alla data di presentazione
			Calendar data_ini=(Calendar) dataPresentazione.clone();
			data_ini.set(Calendar.DAY_OF_MONTH, 1);
			data_ini.add(Calendar.MONTH, 1);
			
			Calendar data_fine=(Calendar) data_ini.clone();
			data_fine.add(Calendar.MONTH, 12);
			
			int escludi_ufficio=0;
			if(records.getString(1, records.getIndexOfColumnName("escludi_ufficio"))!=null){
				escludi_ufficio=records.getInteger(1, records.getIndexOfColumnName("escludi_ufficio"));
			}
			
        	if(escludi_ufficio!=0 && 
        		records.getString(1,records.getIndexOfColumnName("escludi_ufficio_data"))!=null 
        		&& records.getString(1,records.getIndexOfColumnName("escludi_ufficio_data")).length()>0){
        		
        		dataVariazione = records.getCalendar(1, records.getIndexOfColumnName("escludi_ufficio_data"));
        		variazioneDa=true;
        		variazione = c_elab.pat.Util_apapi.get_ha_requisiti_da_a_comp(dataVariazione,data_fine,meseInizio,annoInizio,true);
        		
            }
        	
				switch (escludi_ufficio) {
		        case id_diniego_residenzaApapi:  
		        		 id_diniego=id_mancata_residenzaOut;
		        		 diniego=true;
		                 break;
		        case id_diniego_surrogaApapi:  
		        		 id_diniego=id_mancata_surrogaOut;
		        		 diniego=true;
		                 break;
		        case id_diniego_ufficioApapi:  
		        		 id_diniego=id_esclusioneOut;
		        		 diniego=true;
		                 break;
		        case id_revoca_residenzaApapi: 
		        		 sospensione=true;
		        		 id_sospensione=id_mancata_residenzaOut;
		                 break;
		        case id_revoca_ufficioApapi:  
		        		 sospensione=true;
		        		 id_sospensione=id_esclusioneOut;
		                 break;
		       
		        default: escludi_ufficio = 0;
		                 break;
				}    
				 
				if(escludi_ufficio!=0 && diniego){
        			for (int j = 0; j < 12; j++) { //per ogni mese
        				esito[j] = id_diniego;
        			}
        		}else if(escludi_ufficio!=0 && sospensione){
        			for (int j = 0; j < 12; j++) { //per ogni mese
        				if(variazioneDa){
	        				boolean var=variazione[j];
	        				if(var){
	        					esito[j] = id_sospensione;
	        				}else{
	        					esito[j] = 0;
	        				}
        				}else{
        					esito[j] = id_sospensione;
        				}
        				
        			}
        		
        		}	
				
        	try {
				return c_elab.pat.Util_apapi.toDouble(esito);
			} catch (NumberFormatException nfe) {
				System.out.println("ERRORE NumberFormatException in "
						+ getClass().getName() + ": " + nfe.toString());
				return 1000000000000.0;
			}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 1000000000000.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 1000000000000.0;
        } catch (Exception e) {
        	e.printStackTrace();
        	return 1000000000000.0;
        }
    }
}
