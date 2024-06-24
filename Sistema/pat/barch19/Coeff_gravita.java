package c_elab.pat.barch19;

public class Coeff_gravita extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	/*
	CLASSI DI GRAVITA’ DELLA DISABILITA’ COEFFICIENTE
	Invalidi non deambulanti o con bisogno di assistenza
	continua e ciechi assoluti 1,00
	Sordi e ciechi con residuo visivo 0,65
	Minori invalidi civili con assegno 0,50
	Invalidi civili 100 % ed equiparati 0,30
	Altri Invalidi 65 anni e oltre 0,25
	Invalidi civili dal 74% al 99% ed equiparati 0,20
	Invalidi civili dal 66% al 73% ed equiparati 0,15
	Invalidi dal 34% al 65% 0,05
	*/
	public double getValue() {
		
		try {
			double coeff = records.getDouble(1, records.getIndexOfColumnName("coeff_invalidita"));
			return coeff;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
