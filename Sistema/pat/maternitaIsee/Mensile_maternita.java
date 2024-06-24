package c_elab.pat.maternitaIsee;

import java.util.Calendar;

/**
 * Importo mensile dell'assegno in EURO per l'assegno maternita.
 */
public class Mensile_maternita extends QDati {
	
	/**
	 * @param
	 */
    public double getValue() {
    	
    	try {
	        Calendar cal_figlio = getData();
        
	        Calendar uno_1_2014 = Calendar.getInstance();
	        uno_1_2014.set(2014, 0, 1, 0, 0); // 1 gennaio 2014

	        Calendar trentuno_12_2014 = Calendar.getInstance();
	        trentuno_12_2014.set(2014, 11, 31, 23, 59); // 31 dicembre 2014
	        
	        Calendar uno_1_2015 = Calendar.getInstance();
	        uno_1_2015.set(2015, 0, 1, 0, 0); // 1 gennaio 2015
	        
	        Calendar trentuno_12_2017 = Calendar.getInstance();
	        trentuno_12_2017.set(2017, 11, 31, 23, 59); // 31 dicembre 2017
	        
	        Calendar uno_1_2018 = Calendar.getInstance();
	        uno_1_2018.set(2018, 0, 1, 0, 0); // 1 gennaio 2018
	        
	        Calendar trentuno_12_2018 = Calendar.getInstance();
	        trentuno_12_2018.set(2018, 11, 31, 23, 59); // 31 dicembre 2018
	        
	        Calendar uno_1_2019 = Calendar.getInstance();
	        uno_1_2019.set(2019, 0, 1, 0, 0); // 1 gennaio 2019
	        
	        Calendar trentuno_12_2019 = Calendar.getInstance();
	        trentuno_12_2019.set(2019, 11, 31, 23, 59); // 31 dicembre 2019

	        Calendar uno_1_2020 = Calendar.getInstance();
	        uno_1_2020.set(2020, 0, 1, 0, 0); // 1 gennaio 2020
	        
	        Calendar trentuno_12_2020 = Calendar.getInstance();
	        trentuno_12_2020.set(2020, 11, 31, 23, 59); // 31 dicembre 2020	   
	        
	        Calendar uno_1_2021 = Calendar.getInstance();
	        uno_1_2021.set(2021, 0, 1, 0, 0); // 1 gennaio 2021
	        
	        Calendar trentuno_12_2021 = Calendar.getInstance();
	        trentuno_12_2021.set(2021, 11, 31, 23, 59); // 31 dicembre 2021
	        
	        Calendar uno_1_2022 = Calendar.getInstance();
	        uno_1_2022.set(2022, 0, 1, 0, 0); // 1 gennaio 2022
	        
	        Calendar trentuno_12_2022 = Calendar.getInstance();
	        trentuno_12_2022.set(2022, 11, 31, 23, 59); // 31 dicembre 2022
	        
	        Calendar uno_1_2023 = Calendar.getInstance();
	        uno_1_2023.set(2023, 0, 1, 0, 0); // 1 gennaio 2022
	        
	        Calendar trentuno_12_2023 = Calendar.getInstance();
	        trentuno_12_2023.set(2023, 11, 31, 23, 59); // 31 dicembre 2022
	        
	    	// 2014 - se nato tra l'1-1-2014 e 31-12-2014 compresi 
	        if (!(cal_figlio.before(uno_1_2014)) && !(cal_figlio.after(trentuno_12_2014))) {
	            return 338.21;
	        // 2015, 2016, 2017 - se nato tra l'1-1-2015 e 31-12-2017 compresi
	        }else if (!(cal_figlio.before(uno_1_2015)) && !(cal_figlio.after(trentuno_12_2017))){  // 2015 - se nato tra l'1-1-2015 e 31-12-2015 compresi 
	        	return 338.89;
	        // 2018
			} else if (!(cal_figlio.before(uno_1_2018)) && !(cal_figlio.after(trentuno_12_2018))) {
	            return 342.62;
	         // 2019
			} else if (!(cal_figlio.before(uno_1_2019)) && !(cal_figlio.after(trentuno_12_2019))) {
	            return 346.39;
	         // 2020
    		} else if (!(cal_figlio.before(uno_1_2020)) && !(cal_figlio.after(trentuno_12_2020))) {
	            return 348.12;   
	         // 2021   
    		} else if (!(cal_figlio.before(uno_1_2021)) && !(cal_figlio.after(trentuno_12_2021))) {
	            return 348.12; 
	          //2022  
	        } else if (!(cal_figlio.before(uno_1_2022)) && !(cal_figlio.after(trentuno_12_2022))) {
	            return 354.73;
	            //2023
	        } else if (!(cal_figlio.before(uno_1_2023)) && !(cal_figlio.after(trentuno_12_2023))) {
				return 383.46;
			} else {
	            return 383.46;
	        }
    	
        } catch (NullPointerException npe) {
        	npe.printStackTrace();
            System.out.println("Null pointer in " + getClass().getName() + ": " + npe.toString());
            return -1.0;
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println("---> ERRORE IN " + getClass().getName() + ": " + iobe.toString());
            return -1.0;
        } catch ( NumberFormatException ex ) {
	        System.out.println("---> ERRORE IN " + getClass().getName() + ": " + ex.toString());
            return -1.0;
        }
    	
    	
        
    }
    
    public Mensile_maternita(){
    }
}
