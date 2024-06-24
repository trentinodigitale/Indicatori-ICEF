package c_elab.pat.ITEA15; 

import it.clesius.clesius.util.Sys;
import c_elab.pat.icef15.LocalVariables;


/**
 * 
 * @author a_pichler
 *
 */
public class Sopraggiunta_deduzione extends QSopraggiuntaInvalidita { 
	
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		
		try {
			for (int i = 1; i <= records.getRows(); i++) {
				// da verificare non è così
				
				// max perché la deduzione massima non viene più applicata
				double value = java.lang.Math.max( LocalVariables.DMI,  LocalVariables.QBI * datiEpuInv.getDouble(i, 3) );
				double pesoReddito = 100;  //records.getDouble(i, 8); NB da rivedere!!!!!!!!
				value =	Sys.round( value - aggiusta, round) * pesoReddito / 100.0;	
					
				tot = tot + value;
			}
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}