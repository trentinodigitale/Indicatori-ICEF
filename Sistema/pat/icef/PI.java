/**
 *Created on 03-giu-2004
 */

package c_elab.pat.icef;

/**
 * legge i valori del quadro F dove residenza è false (0)
 * 
 * @author g_barbieri
 */
public class PI extends QImmobiliare {

	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		double tot = 0.0;
		
		/* tolto dalla versi0ne dell'icef del 24-05-05
		if (tb_imm_estero != null) {
			try {
				for (int i = 1; i <= tb_imm_estero.getRows(); i++) {
					tot = tot
							+ new Double((String) tb_imm_estero
									.getElement(i, 1)).doubleValue()
							* valore_mq_imm_estero
							* new Double((String) tb_imm_estero
									.getElement(i, 2)).doubleValue() / 100.0;
				}
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName()
						+ ": " + n.toString());
				//return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in "
						+ getClass().getName() + ": " + nfe.toString());
				//return 0.0;
			}
		}
		*/

		if (records == null)
			return tot;
		else
			// se param = false ritorna gli immobili oltre la residenza
			return tot + getValoreImmobili(false);
	}
}