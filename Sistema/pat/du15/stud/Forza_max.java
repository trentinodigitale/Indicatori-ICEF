package c_elab.pat.du15.stud;
/**
 * CAMBIAMI PassaValoriDu2015 con PassaValoriDu2015
 */
import it.clesius.evolservlet.icef.du.PassaValoriDu2015;
public class Forza_max extends QTariffa {
    
    /** ritorna il valore double da assegnare all'input node
     * 
     * FORZA MAX if forza_max=1 forza al massimo se è una tariffa e forza al minimo se è un contributo
     * @return double 
     */
	public double getValue() {
        try {
        	boolean forzaMax=PassaValoriDu2015.isForzaMax(IDdomanda);
        	if(forzaMax){
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