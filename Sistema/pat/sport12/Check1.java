package c_elab.pat.sport12;


/** 
 * legge i motivi di non idoneità 
 * 
 * 1 resindenza in TAA
 *
 * @author s_largher
 */
public class Check1 extends QStudente {
    
    /** ritorna il valore double da assegnare all'input node
     * @return double 
     */
	public double getValue() {
    	//check1 = 0 idoneo
		try {
			if(records.getInteger(1, 7)!= 0){
				
				return 0.0;
			}else{
				return 1.0;
			}
				
		
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 1.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 1.0;
        }
    }
}