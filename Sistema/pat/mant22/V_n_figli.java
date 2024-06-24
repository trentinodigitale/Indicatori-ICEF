package c_elab.pat.mant22;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.sql.servlet.RDServlet;


/** 
 *Created on 11-ott-2005
 */

/**
 * legge se ci sono figli minori o equiparati 
 * 
 */
public class V_n_figli extends QFigli {

    //CAMBIAMI: va cambiata ogni anno - FATTO
    //private static int anno = 2017;

    

    public static void main(String[] a){


    	Hashtable h = new Hashtable();
    	h.put("servletName", "http://172.31.16.75:8080/clesius/icef/servlet/data");
    	h.put("applAut","clesio");
    	h.put("serialization","true");

    	try{
    		RunawayData    hrun= new RDServlet(); 
    		hrun.init(h);
    		V_n_figli qd=new V_n_figli();

    		qd.setVariables("12728345", 2012, 9405, 9405, ""+107, true);
    		qd.init(hrun);
    		//System.out.println("CCCCCCCCCCCC");
    		qd.getValue();

    	} catch (Exception e){

    	}
    }
	
    
	
    
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 * @throws ParseException 
	 */
	public double getValue() {

		try {
			//Grande pezza escludi anf V_figli=0
			int presenti[] = new int[12];
			int figli_idonei[] = new int[12];
			int minori18[] = new int[12];
			
			// init figli_idonei per ogni mese a 0
			for (int j = 0; j < 12; j++) {
				figli_idonei[j] = 0;
			}
			Calendar dataPresentazione = records.getCalendar(1,8);
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
			
			Calendar data_presentazione=records.getCalendar(1, 8);
			data_presentazione.set(Calendar.HOUR_OF_DAY, 0);
			data_presentazione.set(Calendar.MINUTE, 0); 
			data_presentazione.set(Calendar.SECOND, 0);
			data_presentazione.set(Calendar.MILLISECOND, 0);
			
			//inizio del beneficio primo giorno del mese successivo alla data di presentazione
			Calendar data_ini=(Calendar) data_presentazione.clone();
			data_ini.set(Calendar.DAY_OF_MONTH, 1);
			data_ini.add(Calendar.MONTH, 1);
			
			//dodici mesi dopo l'inizio del beneficio
			Calendar data_fine=(Calendar) data_ini.clone();
			data_fine.add(Calendar.MONTH, 12);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			System.out.println("data pres "+formatter.format(data_presentazione.getTime())+" da "+formatter.format(data_ini.getTime())+" a "+formatter.format(data_fine.getTime()));
			
			/**
			 *  per ogni componente indicato come figlio o equiparato
			 */
			for (int i = 1; i <= records.getRows(); i++) {
				//data nascita compresa si inizia a cambiare dal primo giorno del mese successivo
				Calendar data_nascita =  records.getCalendar(i, 1);
				data_nascita.set(Calendar.HOUR_OF_DAY, 0);
				data_nascita.set(Calendar.MINUTE, 0); 
				data_nascita.set(Calendar.SECOND, 0);
				data_nascita.set(Calendar.MILLISECOND, 0);
				//il mese in cui compie gli anni è compreso
				
				
				Calendar data_nucleo_dal=null;
				Calendar data_nucleo_al=null;
				if(records.getString(i, 2)!=null){
					data_nucleo_dal=records.getCalendar(i, 2);
				}
				if(records.getString(i, 3)!=null){
					data_nucleo_al=records.getCalendar(i, 3);
				}
				
				//System.out.println("stud "+records.getString(i, 9)+" "+records.getString(i, 10)+" "+records.getString(i, 11));
				
				// e' nato?
				boolean nato[] = isNato(i, meseInizio, annoInizio,data_nascita,data_fine,true);

				// e' nel nucleo?cotnrollare du presumo che data fine sia data nel
				boolean nel_nucleo[] = isNelNucleo(i, meseInizio, annoInizio, data_nucleo_dal,data_nucleo_al,true);
				
				// e' presente ovvero è nato AND nel_nucleo?
				boolean presente[] = c_elab.pat.Util_apapi.and(nato, nel_nucleo);
				presenti = c_elab.pat.Util_apapi.add(presenti, presente);
 
				// e' un minore di 18 anni presente nel nucleo?
				boolean minore18[] = isMinore(i, meseInizio, annoInizio, 18,data_nascita,true);
				minore18 = c_elab.pat.Util_apapi.and(minore18, presente);
				minori18 = c_elab.pat.Util_apapi.add(minori18, minore18);
				
				
			}

			for (int j = 0; j < 12; j++) { //per ogni mese
				
			
				figli_idonei[j] = minori18[j];
		
			}
			
			try {
				return c_elab.pat.Util_apapi.toDouble(figli_idonei);
			} catch (NumberFormatException nfe) {
				System.out.println("ERRORE NumberFormatException in "
						+ getClass().getName() + ": " + nfe.toString());
				return 1000000000000.0;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return 1000000000000.0;
			// TODO: handle exception
		}
	}
}