package c_elab.pat.stagionale17;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import it.clesius.apps2core.InvalidDbNetException;
import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.sql.servlet.RDServlet;
import it.clesius.db.util.Table;

public class TestClasseTrasformazione { 

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		try {

			StringBuffer	message		= new StringBuffer();
			String			idDomanda	= "";//"10272622"; //"7589500";//"7588995";
			String			IDServizio  = "100020";

			System.setProperty("http.proxySet", "true");
			System.setProperty("http.proxyHost", "192.168.30.246");
			System.setProperty("http.proxyPort", "3128");
			System.setProperty("http.nonProxyHosts", "localhost|192.168.30.156|192.168.30.224|192.168.30.220|10.150.40.11|172.31.16.76|servizi.clesius.it");

			RunawayData rd = connect("http://icef.provincia.tn.it/clesius/icef/servlet/data", "clesio");//ICEF PROD

				String sql = "SELECT Domande.ID_domanda, R_Servizi.servizio, R_Servizi.ID_servizio "+
				"FROM     Domande INNER JOIN "+
				"					   Doc ON Domande.ID_domanda = Doc.ID INNER JOIN "+
				"					   R_Servizi ON Domande.ID_servizio = R_Servizi.ID_servizio "+
				"WHERE  (Domande.ID_ente_erogatore != 0) "+
				" and Domande.ID_SERVIZIO = "+IDServizio;
				
				if(idDomanda.length()>0) {
					sql+=" and domande.id_domanda = "+idDomanda;
				}
				//+ "AND (Doc.id_tp_stato >= 3000) "
				//+ " AND (C_NodIN.class = N'"+classeTrasTmp+"') "
				//+ "AND C_ElaIN.input_value>0 "+
				sql+=" ORDER BY  Domande.ID_domanda DESC";

					System.out.println(sql);
					Table table = rd.executeQuery(sql);

					int sbagliate = 0;
					int totale = 0;
					totale = table.getRows();
					
					System.out.println("id;mansione;"
							+ "data dal;data al;"
							+ "scau prog 2014;scau prog 2015;scau prog 2016;"
							+ "scau extra 2014;scau extra 2015;scau extra 2016;"
							+ "inps prog 2014;inps prog 2015;inps prog 2016;"
							+ "inps extra 2014;inps extra 2015;inps extra 2016"
							);
					
					for (int i = 1; i <= table.getRows(); i++) {

						String idDomandaTmp = table.getString(i, 1);
						String servizio =  table.getString(i, 2);
						long IDservizio =  table.getInteger(i, 3);
						
						sql = "SELECT ID_attivita, data_dal, data_al, LAV_stagionali.ID_tp_mansione, note, tp_lavoro, ditta, id_tp_cooperativa, "
						//evinco se attività progettone o extra progettone
						//sql.append("CASE WHEN id_tp_cooperativa > 0 THEN 1 ELSE 2 END AS tp_cooperativa, ");
						//evinco se attività (SCAU)progettone o (INPS)extraprogettone
						+"LAV_tp_mansioni.inps_scau, LAV_tp_mansioni.tp_mansione, LAV_tp_mansioni.isProgettone " 
						+"FROM  LAV_stagionali INNER JOIN LAV_tp_mansioni ON LAV_stagionali.ID_tp_mansione = LAV_tp_mansioni.ID_tp_mansione "
						+"WHERE LAV_stagionali.ID_domanda = "+idDomandaTmp;
						
						Table records = rd.executeQuery(sql);
						//System.out.println("inizio calcolo id "+idDomandaTmp+";");
						
						double mesi = 0;
						
						String ID_tp_SCAU = "SCAU";
						String ID_tp_INPS = "INPS";

						Integer ID_tp_progettone 		= 1;
						Integer ID_tp_extraprogettone 	= 0;	
						
						Integer year_1 = 2014;
						Integer year_2 = 2015;
						Integer year_3 = 2016;
						
						Integer tp_progettone = 0;
						Integer year = 0;
						
						for (int r = 1; r <= records.getRows(); r++) {
							
							String tp_mansione = records.getString(r, records.getIndexOfColumnName("tp_mansione") );
							Calendar data_dal = records.getCalendar(r, records.getIndexOfColumnName("data_dal"));
							Calendar data_al = records.getCalendar(r, records.getIndexOfColumnName("data_al"));
							
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							String date_dal_ = format.format(data_dal.getTime());
							String date_al_  = format.format(data_al.getTime());
							
							//System.out.println(date_dal_+" - "+date_al_);
							
							String inps_scau = records.getString(r, records.getIndexOfColumnName("inps_scau"));
							
							int isProgettone = records.getInteger(r, records.getIndexOfColumnName("isProgettone"));
							
							double p_scau_1 = 0;
							double p_scau_2 = 0;
							double p_scau_3 = 0;
							
							double e_scau_1 = 0;
							double e_scau_2 = 0;
							double e_scau_3 = 0;
							
							double p_inps_1 = 0;
							double p_inps_2 = 0;
							double p_inps_3 = 0;
							
							double e_inps_1 = 0;
							double e_inps_2 = 0;
							double e_inps_3 = 0;
							
							if(inps_scau.equals(ID_tp_SCAU) && isProgettone==ID_tp_progettone) {
								 p_scau_1 = monthsBetween(data_al, data_dal, year_1);
								 p_scau_2 = monthsBetween(data_al, data_dal, year_2);
								 p_scau_3 = monthsBetween(data_al, data_dal, year_3);
							}
							else if(inps_scau.equals(ID_tp_SCAU) && isProgettone==ID_tp_extraprogettone) {
								 e_scau_1 = monthsBetween(data_al, data_dal, year_1);
								 e_scau_2 = monthsBetween(data_al, data_dal, year_2);
								 e_scau_3 = monthsBetween(data_al, data_dal, year_3);
							}
							else if(inps_scau.equals(ID_tp_INPS) && isProgettone==ID_tp_progettone) {
								 p_inps_1 = monthsBetween(data_al, data_dal, year_1);
								 p_inps_2 = monthsBetween(data_al, data_dal, year_2);
								 p_inps_3 = monthsBetween(data_al, data_dal, year_3);
							}	
							else if(inps_scau.equals(ID_tp_INPS) && isProgettone==ID_tp_extraprogettone) {
								 e_inps_1 = monthsBetween(data_al, data_dal, year_1);
								 e_inps_2 = monthsBetween(data_al, data_dal, year_2);
								 e_inps_3 = monthsBetween(data_al, data_dal, year_3);
							}
						
							System.out.println(idDomandaTmp+";"
							+tp_mansione+";"
							+date_dal_+";"+date_al_+";"
							+p_scau_1+";"+p_scau_2+";"+p_scau_3+";"
							+e_scau_1+";"+e_scau_2+";"+e_scau_3+";"
							+p_inps_1+";"+p_inps_2+";"+p_inps_3+";"
							+e_inps_1+";"+e_inps_2+";"+e_inps_3+";");
						}
						
						
						
						/*
						
						System.out.println("SCAU_1 = ");
						Double SCAU_1 = calcola(records, ID_tp_SCAU, year_1 );
						System.out.println(" SCAU_1 = "+SCAU_1);
						
						System.out.println("SCAU_2 = ");
						Double SCAU_2 = calcola(records, ID_tp_SCAU, year_2 );
						System.out.println(" SCAU_2 = "+SCAU_2);
						
						System.out.println("SCAU_3 = ");
						Double SCAU_3 = calcola(records, ID_tp_SCAU, year_3 );
						System.out.println(" SCAU_3 = "+SCAU_3);
						
						System.out.println("INPS_1 = ");
						Double INPS_1 = calcola(records, ID_tp_INPS, year_1 );
						System.out.println(" INPS_1 = "+INPS_1);
						
						System.out.println("INPS_2 = ");
						Double INPS_2 = calcola(records, ID_tp_INPS, year_2 );
						System.out.println(" INPS_2 = "+INPS_2);
						
						System.out.println("INPS_3 = ");
						Double INPS_3 = calcola(records, ID_tp_INPS, year_3 );
						System.out.println(" INPS_3 = "+INPS_3);			
						*/
						
						//System.out.println("fine calcolo id "+idDomandaTmp+" -  ");
						
					}
					//System.out.println(message.toString());
					//System.out.println("ERRATE: "+ sbagliate);
					

		}
		catch ( Exception e0 ) {
			//System.out.println ( e0.toString () );
			throw new InvalidDbNetException ( "Errore ClassNotFound in getNode: " + e0.toString () , e0 );
		}

	}
	
	
	private static double calcola(Table records, Integer tp_progettone, Integer year) {
		try {
			double mesi = 0; 
			
			int row = 1;
			while(row<=records.getRows()) {
				
				int inps_scau = records.getInteger(row, records.getIndexOfColumnName("inps_scau"));
				
				if(inps_scau==tp_progettone) {
					
					Calendar data_dal = records.getCalendar(row, records.getIndexOfColumnName("data_dal"));
					Calendar data_al = records.getCalendar(row, records.getIndexOfColumnName("data_al"));
					
					mesi += monthsBetween(data_al, data_dal, year); 
					
				}
				
				row++;
			}
			return mesi;
		} catch (Exception e) {
			return -1;
		}
		
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
        		
        		System.out.println(date_dal_+" - "+date_al_);
            	
            	if(date_dal.get(Calendar.MONTH)==date_al.get(Calendar.MONTH)) {
            		//stesso mese, verifico giorni
            		double diff = day_al - day_dal;
                	if(diff>=15 ) {
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
        
        
        System.out.println("year "+year+" = "+monthsBetween+" per "+date_dal_+";"+date_al_);
        //System.out.print(" data dal "+format.format(date_dal.getTime()) + " data al  "+format.format(date_al.getTime())+" totale "+monthsBetween);
        return monthsBetween; //arrotonda(monthsBetween, 0);
    }
	
	public static double round(double value, int decimalPlace)
    {
      double power_of_ten = 1;
      // floating point arithmetic can be very tricky.
      // that's why I introduce a "fudge factor"
      double fudge_factor = 0.05;
      while (decimalPlace-- > 0) {
         power_of_ten *= 10.0d;
         fudge_factor /= 10.0d;
      }
      return Math.round((value + fudge_factor)* power_of_ten)  / power_of_ten;
    }


	/**
	 * utilizzato per creare il runawaydata
	 * sia del db di simulazione sia del db di produzione
	 */

	public static RunawayData connect(String dbServlet, String applAut) throws DBException {

		System.out.println("\n>> Connecting  = " + dbServlet);

		RunawayData    hrun= new RDServlet();

		Hashtable h = new Hashtable();

		h.put("servletName", dbServlet); 
		h.put("applAut",applAut);
		h.put("serialization","true");

		try {
			hrun.init(h);
		}
		catch(Exception e ) {
			throw (new DBException("Error ElaboraXml.connect " + e.getMessage()));
		}       

		return hrun;
	}
	
	
	

}
