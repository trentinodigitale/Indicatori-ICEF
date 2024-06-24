package c_elab.pat.stagionale22;

import java.util.Calendar;
import java.util.Vector;

public class E_SCAU1 extends QDatiStagionali {

	private static String tp_inps_scau 	= ID_tp_SCAU;
	private static int year				= year_1;
	private static int id_tp_progettone	= ID_tp_extraprogettone;
	
	
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		double mesi = 0.0;

		try {
			int row = 1;
			
			//tengo traccia in questo vettore dei mesi che mi hanno dato un +1, in modo da non contarli più volte
			//nel caso in cui un soggetto ha fatto più lavori nello stesso mese: in  questo caso non deve avere 2 punti, ma sempre 1
			//non dev'essere avvantaggiato perchè ha lavorato mezza giornata da una parte e mezza da un'altra
			Vector<Integer> mesiConteggiati = new Vector<Integer>();			
			
			while(row<=records.getRows()) {
				
				String inps_scau = records.getString(row, records.getIndexOfColumnName("inps_scau"));
				int tp_progettone = records.getInteger(row, records.getIndexOfColumnName("isProgettone"));
				
				if(inps_scau.equals(tp_inps_scau) && tp_progettone==id_tp_progettone) {
					
					Calendar data_dal = records.getCalendar(row, records.getIndexOfColumnName("data_dal"));
					Calendar data_al = records.getCalendar(row, records.getIndexOfColumnName("data_al"));
					
					mesi += monthsBetween(data_al, data_dal, year);
					//mesi += monthsBetweenRememberMonths(data_al, data_dal, year, mesiConteggiati);
					
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