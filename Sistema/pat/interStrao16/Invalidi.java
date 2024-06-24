package c_elab.pat.interStrao16;

import java.util.Calendar;

import c_elab.pat.icef16.LocalVariables;
import it.clesius.clesius.util.Sys;



public class Invalidi extends QInterStrao {

	double QBI = 5400.0;
	double DMI=0;
	
	public static final double QBI_2017 = 2700.0;  

	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		if (particolarita == null)
			return 0.0;

		//if (records == null)
			//return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			Calendar comapre= Calendar.getInstance();
			comapre.set(Calendar.YEAR, 2016);
			comapre.set(Calendar.DAY_OF_MONTH, 1);
			comapre.set(Calendar.MONTH, 10);
			comapre.set(Calendar.HOUR, 1);
		
			for (int j = 1; j <= particolarita.getRows(); j++){
				double qbi=QBI;
				Calendar data_presentazione=particolarita.getCalendar(j, 7);
				double coeff_invalidita=0;
//				>=01/11/2016 regole nuove
				if(data_presentazione.after(comapre)){
					 qbi=QBI_2017;
					try{
						coeff_invalidita= particolarita.getDouble(j, 6);
					}catch(Exception e){
						coeff_invalidita=0;
					}
				}else{
					qbi=QBI;
					coeff_invalidita= particolarita.getDouble(j, 1);
				}
					
				
						
				double speseRsa =  0.0; 
				if (particolarita.getElement(j, 5) != null ){
					speseRsa = particolarita.getDouble(j, 5);
				}

				// max perché la deduzione massima non viene più applicata
				double value = java.lang.Math.max( DMI, java.lang.Math.max( qbi *coeff_invalidita, particolarita.getDouble(j, 2) ) );
			
				double pesoReddito = particolarita.getDouble(j, 3);
				value = java.lang.Math.max(speseRsa, value);
				value =	Sys.round( value - aggiusta, round) * pesoReddito / 100.0;	
				tot = tot + value;
			}
			return tot;	
			
		} catch (Exception n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}

