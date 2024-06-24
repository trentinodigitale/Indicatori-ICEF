package c_elab.pat.du12.anf;

/** 
 *Created on 11-giu-2006
 */

/**
 * legge se c'è 1 solo genitore
 * 
 */
public class V_1genitor extends Q_assegno {

    //CAMBIAMI: va cambiata ogni anno
    private static int anno = 2013;

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
			return 1111111111111.0;
		if (tb_coniuge_da_a.getRows() == 0)
			return 1111111111111.0;

		try {
			boolean con2genitori[] = c_elab.pat.Util_apapi.get_ha_requisiti_da_a(
					(String) tb_coniuge_da_a.getElement(1, 1),
					(String) tb_coniuge_da_a.getElement(1, 2), 1, anno, false);

			try {
				return c_elab.pat.Util_apapi.toDouble(c_elab.pat.Util_apapi.not(con2genitori));
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