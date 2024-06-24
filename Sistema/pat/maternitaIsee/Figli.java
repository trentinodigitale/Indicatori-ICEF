package c_elab.pat.maternitaIsee;

import c_elab.pat.maternitaIsee.QDati;

/*****************************************************
                classe Figli
		idoneo assegno maternità per termini domanda
 ******************************************************/


public class Figli extends QDati {
	public double getValue() {
		double nMinori = 0.0;
		try {
			
			nMinori = minori.getRows();
			
			return nMinori;
		} catch(Exception n) {
			System.out.println("Exception in " + getClass().getName() + ": " + n.toString());
			return 0.0;
		}
	}
	public Figli(){		//{{INIT_CONTROLS
		//}}
	}
}
