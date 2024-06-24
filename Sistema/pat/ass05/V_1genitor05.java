package c_elab.pat.ass05;

/** 
 *Created on 11-ott-2005
 */

/**
 * legge se c'è 1 solo genitore per il periodo lug-dic 2005
 * 
 * @author g_barbieri
 */
public class V_1genitor05 extends Q_assegno {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		// se la table tb_coniuge_da_a è null non c'è nessun record
		// e quindi nessun componente è stato indicato come coniuge o convivente
		if (tb_coniuge_da_a == null)
			return 1000000111111.0;
		if (tb_coniuge_da_a.getRows() == 0)
			return 1000000111111.0;

		try {
			boolean con2genitori[] = Util.get_ha_requisiti_da_a(
					(String) tb_coniuge_da_a.getElement(1, 1),
					(String) tb_coniuge_da_a.getElement(1, 2), 7, 2005, false);

			try {
				return Util.toDouble(Util.not(con2genitori, 6));
			} catch (NumberFormatException nfe) {
				System.out.println("ERRORE NumberFormatException in "
						+ getClass().getName() + ": " + nfe.toString());
				return 1000000000000.0;
			}
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "
					+ n.toString());
			return 1000000000000.0;
		}
	}
}