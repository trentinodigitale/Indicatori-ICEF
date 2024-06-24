package c_elab.pat.comp20;


public class RettaRSAnores extends QRettaRSAnores {
    
    
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {

		if (datiRSA == null || datiRSA.getRows()==0)
			return 0.0;

		double retta = 0.0;
		double provvidenzeIC = 0.0;

		try {
			if (datiIND != null && datiIND.getRows()>0){
				for (int row = 1; row <= datiIND.getRows(); row++) {				
					provvidenzeIC +=  datiIND.getDouble(row,2);
				}
			}
			retta +=  datiRSA.getDouble(1,2);
			
			return java.lang.Math.max(0.0, retta-provvidenzeIC);
			
			
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
    }
}