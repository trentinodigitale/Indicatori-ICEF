/** 
 *Created on 14-giu-2012
 */

package c_elab.pat.du14.anf; 

public class Chiede_beneficio extends Q_assegno {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		
		double chiedeBeneficio = 0.0;
		
		try 
		{
			if(tb_dati_dom != null && tb_dati_dom.getString(1,4)!=null && !tb_dati_dom.getString(1,4).equals("0"))
			{
				chiedeBeneficio = 1.0;
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return chiedeBeneficio;
	}
		
}