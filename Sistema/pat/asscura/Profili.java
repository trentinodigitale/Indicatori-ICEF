package c_elab.pat.asscura;


public class Profili extends QDati {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		// se uguale 1 la rete visualizza i 4 massimale  disponibili
		// se a  non vengono stampati
		try {
			return super.getProfili();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
