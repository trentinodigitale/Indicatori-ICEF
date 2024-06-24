package c_elab.pat.tnTril16;

/**
 * 
 * @author a_pichler
 *
 */
public class Mq extends QDomanda {

	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		try {
			tot = records.getDouble(1, 3);
			
			//arrotondo alla decina inferiore
			int num = (int)tot;
			num = (num/10);
			tot = num*10.0;
			
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
	
	public static void main(String[] args) {
		double tot = 50;
		int num = (int)tot;
		int m = (num/10);
		tot = m*10.0;
		System.out.println("tot: "+tot);
	}
}