package c_elab.pat.stagionale22;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


public abstract class QDatiStagionali extends ElainNode {

	public static String ID_tp_SCAU = "SCAU"; 			//SCAU
	public static String ID_tp_INPS = "INPS";			//INPS
	
	public static Integer ID_tp_progettone 		= 1;
	public static Integer ID_tp_extraprogettone = 0;	
	
	public static Integer year_1 = 2019;
	public static Integer year_2 = 2020;
	public static Integer year_3 = 2021;
	
	
	/** QDati constructor */
	public QDatiStagionali() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	public void reset() {
		ElabContext.getInstance().resetSession(  QDatiStagionali.class.getName(), IDdomanda );
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  QDatiStagionali.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ID_attivita, data_dal, data_al, ");
			sql.append(" LAV_stagionali.ID_tp_mansione, note, tp_lavoro, ditta, id_tp_cooperativa, ");
			//evinco se attività progettone o extra progettone
			//sql.append("CASE WHEN id_tp_cooperativa > 0 THEN 1 ELSE 2 END AS tp_cooperativa, ");
			//evinco se attività (SCAU)progettone o (INPS)extraprogettone
			sql.append("LAV_tp_mansioni.inps_scau, LAV_tp_mansioni.isProgettone ");
			sql.append("FROM  LAV_stagionali "
					+ "INNER JOIN LAV_tp_mansioni "
					+ "ON LAV_stagionali.ID_tp_mansione = LAV_tp_mansioni.ID_tp_mansione "
					+ "AND LAV_stagionali.ID_servizio = LAV_tp_mansioni.ID_servizio ");
			sql.append("WHERE LAV_stagionali.ID_domanda = ");
			sql.append(IDdomanda);
			//sql.append(" AND tp_cooperativa = "+ID_tp_extra_cooperativa);

			//System.out.println(sql.toString());
			
			doQuery(sql.toString());

			session.setInitialized( true );
			session.setRecords( records );

		} else {
			records = session.getRecords();
		}
	}
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		return 0.0;
	}

	
	public static double monthsBetween(Calendar dateAl, Calendar dateDal, Integer year){
		
		Calendar date_al = (Calendar)dateAl.clone();
		Calendar date_dal = (Calendar)dateDal.clone();
		
		date_al.set(Calendar.HOUR_OF_DAY, 0);
		date_al.set(Calendar.MINUTE, 0);
		date_al.set(Calendar.SECOND, 0);
		
		date_dal.set(Calendar.HOUR_OF_DAY, 0);
		date_dal.set(Calendar.MINUTE, 0);
		date_dal.set(Calendar.SECOND, 0);
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String date_dal_ = format.format(date_dal.getTime());
		String date_al_  = format.format(date_al.getTime());
		
		//System.out.println(year+" --- "+date_dal_+" "+date_al_);
		
		boolean isDate1After = false;
		if(date_al.after(date_dal) ) {
			isDate1After = true;
		}else if(date_al.compareTo(date_dal)==0){
			return 0;
		}else {
			return -1;
		}
		
		
		int year_al = date_al.get(Calendar.YEAR);
        int year_dal = date_dal.get(Calendar.YEAR);
        
        if(year_dal!=year ) {
        	//la data dal è antecedente year, quindi considero 1-1-year 
        	if(year_dal<year ) {
        		date_dal.set(Calendar.YEAR, year);
        		date_dal.set(Calendar.MONTH, 0);
        		date_dal.set(Calendar.DAY_OF_MONTH, 1);
        	//data dal è successiva a year, quindi non considero il record
        	}else if(year_dal>year) {
        		return 0;
        	}
        }
        if(year_al!=year) {
        	//la data al è antecedente year, quindi non considero il record
        	if(year_al<year ) {
        		return 0;
        	//data al è successiva a year, quindi considero 31-12-year
        	}else if(year_al>year) {
        		date_al.set(Calendar.YEAR, year);
        		date_al.set(Calendar.MONTH, 11);
        		date_al.set(Calendar.DAY_OF_MONTH, 31);
        	}
        }
        
        
		date_dal_ = format.format(date_dal.getTime());
		date_al_  = format.format(date_al.getTime());
        
        //System.out.println("data dal "+format.format(date_dal.getTime()) + " data al  "+format.format(date_al.getTime()));      
        //System.out.println("data al  "+format.format(date_al.getTime()));
		
		int mese_al = date_al.get(Calendar.MONTH);
		int mese_dal = date_dal.get(Calendar.MONTH);
		double day_al = date_al.get(Calendar.DAY_OF_MONTH);
    	double day_dal = date_dal.get(Calendar.DAY_OF_MONTH);
		
     	double lastDay_dal = date_dal.getActualMaximum(Calendar.DAY_OF_MONTH);
     	double firstDay_al = date_al.getActualMinimum(Calendar.DAY_OF_MONTH);
     	
        double monthsBetween = 0;
        
        try {
        	
        	while(date_dal.before(date_al)) {
            	
            	date_dal_ = format.format(date_dal.getTime());
        		date_al_  = format.format(date_al.getTime());
        		
        		day_dal = date_dal.get(Calendar.DAY_OF_MONTH);
        		
        		//System.out.println(date_dal_+" - "+date_al_);
            	
            	if(date_dal.get(Calendar.MONTH)==date_al.get(Calendar.MONTH)) {
            		//stesso mese, verifico giorni
            		double diff = day_al - day_dal;
                	if(diff>=15 ) {
                		monthsBetween += 1;
                		
                	//compreso un giorno
                	}else if(day_al - (day_dal-1) >= 15) {
                		monthsBetween += 1;
                	}
                	break;
            	}else if(date_dal.equals(date_al) || date_dal.before(date_al)) {
            		
            		if(lastDay_dal-day_dal >= 15) {
            			monthsBetween += 1;
            			
            		}
            		int m = date_dal.get(Calendar.MONTH);
            		m = m+1;
            		int dd = 1;
            		date_dal.set(Calendar.MONTH, m);
        			date_dal.set(Calendar.DAY_OF_MONTH, dd);
            		
            	}
            }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        
        //System.out.println("year "+year+" = "+monthsBetween+" per "+date_dal_+";"+date_al_);
        //System.out.print(" data dal "+format.format(date_dal.getTime()) + " data al  "+format.format(date_al.getTime())+" totale "+monthsBetween);
        return monthsBetween; //arrotonda(monthsBetween, 0);
    }
	
	public static double monthsBetweenRememberMonths(Calendar dateAl, Calendar dateDal, Integer year, Vector<Integer> mesiGiaContati){
		
		Calendar date_al = (Calendar)dateAl.clone();
		Calendar date_dal = (Calendar)dateDal.clone();
		
		date_al.set(Calendar.HOUR_OF_DAY, 0);
		date_al.set(Calendar.MINUTE, 0);
		date_al.set(Calendar.SECOND, 0);
		
		date_dal.set(Calendar.HOUR_OF_DAY, 0);
		date_dal.set(Calendar.MINUTE, 0);
		date_dal.set(Calendar.SECOND, 0);
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String date_dal_ = format.format(date_dal.getTime());
		String date_al_  = format.format(date_al.getTime());
		
		//System.out.println(year+" --- "+date_dal_+" "+date_al_);
		
		boolean isDate1After = false;
		if(date_al.after(date_dal) ) {
			isDate1After = true;
		}else if(date_al.compareTo(date_dal)==0){
			return 0;
		}else {
			return -1;
		}
		
		
		int year_al = date_al.get(Calendar.YEAR);
        int year_dal = date_dal.get(Calendar.YEAR);
        
        if(year_dal!=year ) {
        	//la data dal è antecedente year, quindi considero 1-1-year 
        	if(year_dal<year ) {
        		date_dal.set(Calendar.YEAR, year);
        		date_dal.set(Calendar.MONTH, 0);
        		date_dal.set(Calendar.DAY_OF_MONTH, 1);
        	//data dal è successiva a year, quindi non considero il record
        	}else if(year_dal>year) {
        		return 0;
        	}
        }
        if(year_al!=year) {
        	//la data al è antecedente year, quindi non considero il record
        	if(year_al<year ) {
        		return 0;
        	//data al è successiva a year, quindi considero 31-12-year
        	}else if(year_al>year) {
        		date_al.set(Calendar.YEAR, year);
        		date_al.set(Calendar.MONTH, 11);
        		date_al.set(Calendar.DAY_OF_MONTH, 31);
        	}
        }
        
        
		date_dal_ = format.format(date_dal.getTime());
		date_al_  = format.format(date_al.getTime());
        
        //System.out.println("data dal "+format.format(date_dal.getTime()) + " data al  "+format.format(date_al.getTime()));      
        //System.out.println("data al  "+format.format(date_al.getTime()));
		
		int mese_al = date_al.get(Calendar.MONTH);
		int mese_dal = date_dal.get(Calendar.MONTH);
		double day_al = date_al.get(Calendar.DAY_OF_MONTH);
    	double day_dal = date_dal.get(Calendar.DAY_OF_MONTH);
		
     	double lastDay_dal = date_dal.getActualMaximum(Calendar.DAY_OF_MONTH);
     	double firstDay_al = date_al.getActualMinimum(Calendar.DAY_OF_MONTH);
     	
        double monthsBetween = 0;
        
        try {
        	
        	while(date_dal.before(date_al)) {
            	
            	date_dal_ = format.format(date_dal.getTime());
        		date_al_  = format.format(date_al.getTime());
        		
        		day_dal = date_dal.get(Calendar.DAY_OF_MONTH);
        		
        		//System.out.println(date_dal_+" - "+date_al_);
            	
            	if(date_dal.get(Calendar.MONTH)==date_al.get(Calendar.MONTH)) {
            		//stesso mese, verifico giorni
            		double diff = day_al - day_dal;
                	if(diff>=15 ) {
                		if(!mesiGiaContati.contains(date_dal.get(Calendar.MONTH))) { 
                			monthsBetween += 1;
                			mesiGiaContati.add(date_dal.get(Calendar.MONTH));
                		}
                	}
                		
                	//compreso un giorno
                	}else if(day_al - (day_dal-1) >= 15) {
                		if(!mesiGiaContati.contains(date_dal.get(Calendar.MONTH))) { 
                			monthsBetween += 1;
                			mesiGiaContati.add(date_dal.get(Calendar.MONTH));
                		}	
                	break;
            	}else if(date_dal.equals(date_al) || date_dal.before(date_al)) {
            		
            		if(lastDay_dal-day_dal >= 15) {
            			if(!mesiGiaContati.contains(date_dal.get(Calendar.MONTH))) {
            				monthsBetween += 1;
            				mesiGiaContati.add(date_dal.get(Calendar.MONTH));
            			}	
            		}
            		int m = date_dal.get(Calendar.MONTH);
            		m = m+1;
            		int dd = 1;
            		date_dal.set(Calendar.MONTH, m);
        			date_dal.set(Calendar.DAY_OF_MONTH, dd);
            		
            	}
            }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        
        //System.out.println("year "+year+" = "+monthsBetween+" per "+date_dal_+";"+date_al_);
        //System.out.print(" data dal "+format.format(date_dal.getTime()) + " data al  "+format.format(date_al.getTime())+" totale "+monthsBetween);
        return monthsBetween; //arrotonda(monthsBetween, 0);
    }	
	
}
