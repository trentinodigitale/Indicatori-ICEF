package c_elab.pat.du16.stud;
/**
 * CAMBIAMI PassaValoriDu2016 con PassaValoriDu2016
 */
import it.clesius.evolservlet.icef.du.PassaValoriDu2016;
public class Forza_max extends QTariffa {
    
    /** ritorna il valore double da assegnare all'input node
     * 
     * FORZA MAX if forza_max=1 forza al massimo se è una tariffa e forza al minimo se è un contributo
     * @return double 
     */
	public double getValue() {
        try {
        	boolean forza=false;
        	boolean forzaMax=PassaValoriDu2016.isForzaMax(IDdomanda);
        	
        	if(forzaMax || 
        			 (records.getElement(1, records.getIndexOfColumnName("escludi_ufficio"))!= null && c_elab.pat.Util_apapi.stringToBoolean((String) records.getElement(1, records.getIndexOfColumnName("escludi_ufficio"))))){
        		forza=true;
        	}
        			
        	if(forza){
        		return 1.0;
        	}else{
        		return 0.0;
        	}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}