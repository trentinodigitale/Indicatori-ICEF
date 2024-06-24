package c_elab.pat.stagionale17;

import java.util.Calendar;

public class P_SCAU2 extends QDatiStagionali {

	private static String tp_inps_scau 	= ID_tp_SCAU;
	private static int year				= year_2;
	private static int id_tp_progettone	= ID_tp_progettone;
	
	
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		double mesi = 0.0;

		try {
			int row = 1;
			while(row<=records.getRows()) {
				
				String inps_scau = records.getString(row, records.getIndexOfColumnName("inps_scau"));
				int tp_progettone = records.getInteger(row, records.getIndexOfColumnName("isProgettone"));
				
				if(inps_scau.equals(tp_inps_scau) && tp_progettone==id_tp_progettone) {
					
					Calendar data_dal = records.getCalendar(row, records.getIndexOfColumnName("data_dal"));
					Calendar data_al = records.getCalendar(row, records.getIndexOfColumnName("data_al"));
					
					mesi += monthsBetween(data_al, data_dal, year); 
					
				}
				
				row++;
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("ERROR Exception in " + getClass().getName() + ": " + e.toString());
			return 0.0;
		}
		return mesi;
	}


}